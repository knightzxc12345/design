// ==========================
// 全域設定
// ==========================
let currentPage = 0;
let pageSize = 10;
const API_BASE = `${DOMAIN}/quotation`;
const STATUS_MAP = {
    DRAFT: { label: "草稿", badgeClass: "text-bg-secondary", btnClass: "btn-outline-secondary" },
    NEGOTIATING: { label: "議價中", badgeClass: "text-bg-dark", btnClass: "btn-outline-dark" },
    IN_PROGRESS: { label: "案件進行中", badgeClass: "text-bg-primary", btnClass: "btn-outline-primary" },
    COMPLETED: { label: "已完成", badgeClass: "text-bg-success", btnClass: "btn-outline-success" },
    CANCELLED: { label: "已取消", badgeClass: "text-bg-danger", btnClass: "btn-outline-danger" }
};

// ==========================
// 搜尋 / 分頁功能
// ==========================
async function loadQuotations(page = 0) {
    currentPage = page;
    const keyword = document.getElementById("keyword")?.value || "";

    const res = await fetch(`${API_BASE}/v1/page?page=${page}&size=${pageSize}&keyword=${keyword}`);
    const data = (await res.json()).data;
    if (!data) return;

    const quotations = data.responses || [];
    const pageInfo = data.page;
    const tbody = document.getElementById("quotationTableBody");
    tbody.innerHTML = "";

    quotations.forEach(quotation => {
        const status = STATUS_MAP[quotation.quotationStatus] || {
            label: quotation.quotationStatus,
            class: 'text-bg-secondary'
        };

        tbody.innerHTML += `
            <tr>
                <td>${quotation.quotationNo}</td>
                <td>${quotation.createTime}</td>
                <td>${quotation.customerName}</td>
                <td class="text-success">${quotation.totalCostPrice != null ? formatNumber(quotation.totalCostPrice) : ""}</td>
                <td class="text-primary">${quotation.totalPrice != null ? formatNumber(quotation.totalPrice) : ""}</td>
                <td class="text-secondary">${quotation.totalNegotiatedPrice != null ? formatNumber(quotation.totalNegotiatedPrice) : ""}</td>
                <td><div class="badge rounded-pill py-2 px-3 ${status.badgeClass}">${status.label}</div></td>
                <td>${quotation.createUser}</td>
                <td>
                    <div class="btn-group" role="group">
                        <a class="btn btn-outline-secondary d-flex align-items-center" href="${API_BASE}/view/${quotation.uuid}" onclick="saveListState()">
                            <i class="bi bi-eye me-1"></i> 查看
                        </a>
                        <a class="btn btn-outline-secondary d-flex align-items-center" style="white-space: nowrap;" href="${API_BASE}/edit/${quotation.uuid}" onclick="saveListState()">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </a>
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-sm ${status.btnClass} dropdown-toggle" data-bs-toggle="dropdown">
                                ${status.label}
                            </button>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#" onclick="updateStatus('${quotation.uuid}', 'DRAFT', this)">草稿</a></li>
                                <li><a class="dropdown-item" href="#" onclick="updateStatus('${quotation.uuid}', 'NEGOTIATING', this)">議價中</a></li>
                                <li><a class="dropdown-item" href="#" onclick="updateStatus('${quotation.uuid}', 'IN_PROGRESS', this)">案件進行中</a></li>
                                <li><a class="dropdown-item" href="#" onclick="updateStatus('${quotation.uuid}', 'COMPLETED', this)">已完成</a></li>
                                <li><a class="dropdown-item" href="#" onclick="updateStatus('${quotation.uuid}', 'CANCELLED', this)">已取消</a></li>
                            </ul>
                        </div>
                        <a class="btn btn-outline-secondary d-flex align-items-center" style="white-space: nowrap;" href="${API_BASE}/file/${quotation.uuid}">
                            <i class="bi bi-pencil me-1"></i> 前往檔案櫃
                        </a>
                        <a class="btn btn-outline-secondary d-flex align-items-center"
                           style="white-space: nowrap;"
                           href="javascript:void(0)"
                           onclick="downloadCathay('${quotation.uuid}', '${quotation.customerName}')">
                           <i class="bi bi-pencil me-1"></i> 下載Excel(國泰)
                        </a>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination(pageInfo, loadQuotations);
}

async function downloadCathay(uuid, customerName) {
    const btn = event.currentTarget;
    const originalHTML = btn.innerHTML;
    btn.disabled = true;
    btn.innerHTML = `
        <span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
        下載中...
    `;
    try {
        const res = await fetch(`${API_BASE}/v1/download/CATHAY/${uuid}`);
        if (!res.ok) throw new Error("下載失敗");
        const blob = await res.blob();
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        const today = new Date();
        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, '0');
        const dd = String(today.getDate()).padStart(2, '0');
        const dateStr = `${yyyy}-${mm}-${dd}`;
        const fileName = `${dateStr}-${customerName}.xlsx`;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        a.remove();
        URL.revokeObjectURL(url);
    } catch (e) {
        alert(e.message);
    } finally {
        btn.disabled = false;
        btn.innerHTML = originalHTML;
    }
}

function saveListState() {
    const keyword = document.getElementById("keyword")?.value || "";
    sessionStorage.setItem("quotationListState", JSON.stringify({
        page: currentPage,
        keyword: keyword
    }));
}

async function updateStatus(uuid, status, btn) {
    const dropdownBtn = btn.closest(".btn-group").querySelector(".dropdown-toggle");
    const originalHTML = dropdownBtn.innerHTML;

    // 先顯示 loading
    dropdownBtn.disabled = true;
    dropdownBtn.innerHTML = `
        <span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
        更新中...
    `;
    dropdownBtn.disabled = false;
    try {
        const res = await fetch(`${API_BASE}/v1/${uuid}/${status}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({})
        });
        const data = await res.json();

        if (data.code === "SYS0001") {
            showToast("修改成功！", "success");
        } else {
            showToast("修改失敗：" + data.message, "danger");
        }
        await new Promise(resolve => setTimeout(resolve, 1000));

    } catch (err) {
        showToast("修改失敗：" + err.message, "danger");
    } finally {
        // 更新 badge
        const tr = btn.closest("tr");
        const badge = tr.querySelector("td:nth-child(7) .badge");
        badge.className = `badge rounded-pill py-2 px-3 ${STATUS_MAP[status].badgeClass}`;
        badge.innerText = STATUS_MAP[status].label;
        // 更新 dropdown button
        dropdownBtn.innerHTML = STATUS_MAP[status].label;
        dropdownBtn.className = `btn btn-sm ${STATUS_MAP[status].btnClass} dropdown-toggle`;
        // 關閉 dropdown
        const dropdownInstance = bootstrap.Dropdown.getInstance(dropdownBtn);
        if (dropdownInstance) dropdownInstance.hide();
    }
}

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async () => {
    const state = JSON.parse(sessionStorage.getItem("quotationListState") || '{}');
    const page = state.page || 0;
    const keyword = state.keyword || "";
    document.getElementById("keyword").value = keyword;
    await loadQuotations(page);
    clearStorage();
});
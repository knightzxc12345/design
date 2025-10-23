// ==========================
// 全域設定
// ==========================
let currentPage = 0;
let pageSize = 10;
const API_BASE = `${DOMAIN}/quotation`;
const STATUS_MAP = {
    DRAFT:      { label: '草稿',        class: 'text-bg-secondary' },
    NEGOTIATING:{ label: '議價中',      class: 'text-bg-warning' },
    APPROVED:   { label: '已核准',      class: 'text-bg-success' },
    COMPLETED:  { label: '已完成',      class: 'text-bg-primary' },
    CANCELLED:  { label: '已取消',      class: 'text-bg-danger' },
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
                <td><div class="badge rounded-pill py-2 px-3 ${status.class}">${status.label}</div></td>
                <td>${quotation.createUser}</td>
                <td>
                    <div class="btn-group" role="group">
                        <a class="btn btn-sm btn-outline-secondary d-flex align-items-center me-1" href="${API_BASE}/view/${quotation.uuid}" onclick="saveListState()">
                            <i class="bi bi-eye me-1"></i> 查看
                        </a>
                        <a class="btn btn-outline-success d-flex align-items-center" style="white-space: nowrap;" href="${API_BASE}/edit/${quotation.uuid}" onclick="saveListState()">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </a>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination(pageInfo, loadQuotations);
}

function saveListState() {
    const keyword = document.getElementById("keyword")?.value || "";
    sessionStorage.setItem("quotationListState", JSON.stringify({
        page: currentPage,
        keyword: keyword
    }));
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
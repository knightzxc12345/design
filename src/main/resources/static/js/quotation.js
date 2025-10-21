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
        const status = STATUS_MAP[quotation.status] || { label: quotation.status, class: 'text-bg-secondary' };

        tbody.innerHTML += `
            <tr>
                <td>${quotation.quotationNo}</td>
                <td>${quotation.createTime}</td>
                <td>${quotation.customerName}</td>
                <td>${p.totalCostPrice != null ? formatNumber(p.totalCostPrice) : ""}</td>
                <td>${p.totalPrice != null ? formatNumber(p.totalPrice) : ""}</td>
                <td>${p.totalNegotiatedPrice != null ? formatNumber(p.totalNegotiatedPrice) : ""}</td>
                <td><div class="badge rounded-pill py-2 px-3 ${statusClass}">${statusLabel}</div></td>
                <td>${quotation.createUser}</td>
                <td>
                    <div class="btn-group" role="group">
                        <button class="btn btn-sm btn-outline-secondary d-flex align-items-center me-1" onclick="showDetail('${quotation.uuid}')">
                            <i class="bi bi-eye me-1"></i> 查看
                        </button>
                        <a class="btn btn-outline-success d-flex align-items-center" style="white-space: nowrap;" href="/quotation/edit/${quotation.uuid}'">
                            <i class="bi bi-plus-lg me-1"></i> 編輯
                        </a>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination(pageInfo, loadQuotations);
}
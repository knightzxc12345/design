let currentPage = 0;
let pageSize = 10;
const API_BASE = "http://localhost:8787/design/supplier";

async function loadSuppliers(page = 0) {
    currentPage = page;
    const keywordInput = document.getElementById("keyword");
    const keyword = keywordInput ? keywordInput.value : "";
    const res = await fetch(`${API_BASE}/v1/page?page=${page}&size=${pageSize}&keyword=${keyword}`);
    const data = await res.json();
    const result = data.data;
    if (!result) return;

    const suppliers = result.responses || [];
    const pageInfo = result.page;
    const tbody = document.getElementById("supplierTableBody");
    tbody.innerHTML = "";

    suppliers.forEach(supplier => {
        const statusLabel = supplier.status === 'ACTIVE' ? '啟用' : '停用';
        const statusClass = supplier.status === 'ACTIVE' ? 'text-bg-success' : 'text-bg-danger';
        tbody.innerHTML += `
            <tr>
                <td>${supplier.name}</td>
                <td>${supplier.vatNumber}</td>
                <td>${supplier.email || ""}</td>
                <td>${supplier.contactName || ""}</td>
                <td>
                    <div class="badge rounded-pill py-2 px-3 ${statusClass}">${statusLabel}</div>
                </td>
                <td>
                    <div class="btn-group" role="group">
                        <button class="btn btn-sm btn-outline-secondary d-flex align-items-center me-1" onclick="showDetail('${supplier.uuid}')">
                            <i class="bi bi-eye me-1"></i> 查看
                        </button>
                        <button class="btn btn-sm btn-outline-secondary d-flex align-items-center me-1" onclick="openEditModal('${supplier.uuid}')">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </button>
                        <button class="btn btn-sm btn-outline-secondary d-flex align-items-center" onclick="deleteSupplier('${supplier.uuid}')">
                            <i class="bi bi-trash me-1"></i> 刪除
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination("pagination", pageInfo, currentPage, loadSuppliers);
}

async function showDetail(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const d = (await res.json()).result;
    document.getElementById("supplierDetail").innerHTML = `
        <p><b>名稱：</b> ${d.name}</p>
        <p><b>統一編號：</b> ${d.vatNumber}</p>
        <p><b>電話：</b> ${d.phone || ""}</p>
        <p><b>傳真：</b> ${d.fax || ""}</p>
        <p><b>信箱：</b> ${d.email || ""}</p>
        <p><b>地址：</b> ${d.address || ""}</p>
        <p><b>聯絡人：</b> ${d.contactName || ""} (${d.contactPhone || ""})</p>
        <p><b>備註：</b> ${d.remark || ""}</p>
        <p><b>狀態：</b>
            <div class="form-check form-switch d-inline">
                <input class="form-check-input" type="checkbox" ${d.status === 'ACTIVE' ? 'checked' : ''} disabled>
            </div>
        </p>
    `;
    new bootstrap.Modal(document.getElementById("supplierModal"), { backdrop: 'static', keyboard: false }).show();
}

function openCreateModal() {
    clearCreateModal();
    new bootstrap.Modal(document.getElementById("createModal"), { backdrop: 'static', keyboard: false }).show();
}

function clearCreateModal() {
    document.getElementById("createName").value = "";
    document.getElementById("createVatNumber").value = "";
    document.getElementById("createPhone").value = "";
    document.getElementById("createFax").value = "";
    document.getElementById("createEmail").value = "";
    document.getElementById("createAddress").value = "";
    document.getElementById("createContactName").value = "";
    document.getElementById("createContactPhone").value = "";
    document.getElementById("createRemark").value = "";
}

async function saveNewSupplier(e) {
    e.preventDefault();

    const payload = {
        name: document.getElementById("createName").value,
        vatNumber: document.getElementById("createVatNumber").value,
        phone: document.getElementById("createPhone").value,
        fax: document.getElementById("createFax").value,
        email: document.getElementById("createEmail").value,
        address: document.getElementById("createAddress").value,
        contactName: document.getElementById("createContactName").value,
        contactPhone: document.getElementById("createContactPhone").value,
        remark: document.getElementById("createRemark").value
    };

    try {
        const res = await fetch(`${API_BASE}/v1`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await res.json();

        if (data.code === "SYS0001") {
            bootstrap.Modal.getInstance(document.getElementById("createModal")).hide();
            clearCreateModal();
            loadSuppliers(currentPage);
            showToast("新增成功！", "success");
        } else {
            showToast("新增失敗：" + data.message, "danger");
        }
    } catch (error) {
        showToast("新增失敗：" + error.message, "danger");
    }
}

async function openEditModal(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const d = (await res.json()).result;

    document.getElementById("editUuid").value = uuid;
    document.getElementById("editName").value = d.name;
    document.getElementById("editVatNumber").value = d.vatNumber || "";
    document.getElementById("editPhone").value = d.phone || "";
    document.getElementById("editFax").value = d.fax || "";
    document.getElementById("editEmail").value = d.email || "";
    document.getElementById("editAddress").value = d.address || "";
    document.getElementById("editContactName").value = d.contactName || "";
    document.getElementById("editContactPhone").value = d.contactPhone || "";
    document.getElementById("editRemark").value = d.remark || "";
    document.getElementById("editStatus").checked = d.status === "ACTIVE";

    new bootstrap.Modal(document.getElementById("editModal"), { backdrop: 'static', keyboard: false }).show();
}

function clearEditModal() {
    document.getElementById("editUuid").value = "";
    document.getElementById("editName").value = "";
    document.getElementById("editVatNumber").value = "";
    document.getElementById("editPhone").value = "";
    document.getElementById("editFax").value = "";
    document.getElementById("editEmail").value = "";
    document.getElementById("editAddress").value = "";
    document.getElementById("editContactName").value = "";
    document.getElementById("editContactPhone").value = "";
    document.getElementById("editRemark").value = "";
    document.getElementById("editStatus").checked = true;
}

async function saveEditSupplier(e) {
    e.preventDefault();
    const uuid = document.getElementById("editUuid").value;
    const payload = {
        name: document.getElementById("editName").value,
        vatNumber: document.getElementById("editVatNumber").value,
        phone: document.getElementById("editPhone").value,
        fax: document.getElementById("editFax").value,
        email: document.getElementById("editEmail").value,
        address: document.getElementById("editAddress").value,
        contactName: document.getElementById("editContactName").value,
        contactPhone: document.getElementById("editContactPhone").value,
        remark: document.getElementById("editRemark").value,
        status: document.getElementById("editStatus").checked ? "ACTIVE" : "INACTIVE"
    };
    try {
        const res = await fetch(`${API_BASE}/v1/${uuid}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        if (data.code === "SYS0001") {
            bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
            clearEditModal();
            loadSuppliers(currentPage);
            showToast("修改成功！", "success");
        } else {
            showToast("修改失敗：" + data.message, "danger");
        }
    } catch (error) {
        showToast("修改失敗：" + error.message, "danger");
    }
}

async function deleteSupplier(uuid) {
    if (!confirm("確定要刪除嗎？")) return;

    try {
        const res = await fetch(`${API_BASE}/v1/${uuid}`, { method: "DELETE" });
        const data = await res.json();
        if (data.code === "SYS0001") {
            loadSuppliers(currentPage);
            showToast("刪除成功！", "success");
        } else {
            showToast("刪除失敗：" + data.message, "danger");
        }
    } catch (error) {
        showToast("刪除失敗：" + error.message, "danger");
    }
}

function clearSearch() {
    document.getElementById("keyword").value = "";
    loadSuppliers(0);
}

document.addEventListener("DOMContentLoaded", () => {
    loadSuppliers();
});

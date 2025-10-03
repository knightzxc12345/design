let currentPage = 0;
let pageSize = 10;
const API_BASE = "http://localhost:8787/design/customer";
let deleteCustomerUuid = null;

async function loadCustomers(page = 0) {
    currentPage = page;
    const keywordInput = document.getElementById("keyword");
    const keyword = keywordInput ? keywordInput.value : "";
    const res = await fetch(`${API_BASE}/v1/page?page=${page}&size=${pageSize}&keyword=${keyword}`);
    const data = (await res.json()).data;
    if (!data) return;

    const customers = data.responses || [];
    const pageInfo = data.page;
    const tbody = document.getElementById("customerTableBody");
    tbody.innerHTML = "";

    customers.forEach(customer => {
        const statusLabel = customer.status === 'ACTIVE' ? '啟用' : '停用';
        const statusClass = customer.status === 'ACTIVE' ? 'text-bg-success' : 'text-bg-danger';
        tbody.innerHTML += `
            <tr>
                <td>${customer.name}</td>
                <td>${customer.vatNumber}</td>
                <td>${customer.email || ""}</td>
                <td>${customer.contactName || ""}</td>
                <td>
                    <div class="badge rounded-pill py-2 px-3 ${statusClass}">${statusLabel}</div>
                </td>
                <td>
                    <div class="btn-group" role="group">
                        <button class="btn btn-sm btn-outline-secondary d-flex align-items-center me-1" onclick="showDetail('${customer.uuid}')">
                            <i class="bi bi-eye me-1"></i> 查看
                        </button>
                        <button class="btn btn-sm btn-outline-secondary d-flex align-items-center me-1" onclick="openEditModal('${customer.uuid}')">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </button>
                        <button class="btn btn-outline-danger btn-sm" onclick="openDeleteModal('${customer.uuid}', '${customer.name}')">
                            <i class="bi bi-trash"></i> 刪除
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination("pagination", pageInfo, currentPage, loadCustomers);
}

async function showDetail(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;

    document.getElementById("viewUuid").value = data.uuid || "";
    document.getElementById("viewName").value = data.name || "";
    document.getElementById("viewVatNumber").value = data.vatNumber || "";
    document.getElementById("viewPhone").value = data.phone || "";
    document.getElementById("viewFax").value = data.fax || "";
    document.getElementById("viewEmail").value = data.email || "";
    document.getElementById("viewAddress").value = data.address || "";
    document.getElementById("viewContactName").value = data.contactName || "";
    document.getElementById("viewContactPhone").value = data.contactPhone || "";
    document.getElementById("viewRemark").value = data.remark || "";
    document.getElementById("viewStatus").checked = (data.status === "ACTIVE");

    new bootstrap.Modal(document.getElementById("viewModal"), {
        backdrop: "static",
        keyboard: false
    }).show();
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

async function saveNewCustomer(e) {
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
            loadCustomers(currentPage);
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
    const data = (await res.json()).data;

    document.getElementById("editUuid").value = uuid;
    document.getElementById("editName").value = data.name;
    document.getElementById("editVatNumber").value = data.vatNumber || "";
    document.getElementById("editPhone").value = data.phone || "";
    document.getElementById("editFax").value = data.fax || "";
    document.getElementById("editEmail").value = data.email || "";
    document.getElementById("editAddress").value = data.address || "";
    document.getElementById("editContactName").value = data.contactName || "";
    document.getElementById("editContactPhone").value = data.contactPhone || "";
    document.getElementById("editRemark").value = data.remark || "";
    document.getElementById("editStatus").checked = data.status === "ACTIVE";

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

async function saveEditCustomer(e) {
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
            loadCustomers(currentPage);
            showToast("修改成功！", "success");
        } else {
            showToast("修改失敗：" + data.message, "danger");
        }
    } catch (error) {
        showToast("修改失敗：" + error.message, "danger");
    }
}

function openDeleteModal(uuid, customerName) {
    deleteCustomerUuid = uuid;
    document.getElementById('deleteConfirmMessage').innerText = `你確定要刪除「${customerName}」嗎？`;
    const modal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
    modal.show();
}

document.addEventListener("DOMContentLoaded", () => {
    const confirmBtn = document.getElementById('confirmDeleteBtn');
    confirmBtn?.addEventListener('click', async function () {
        if (!deleteCustomerUuid) return;

        try {
            const res = await fetch(`${API_BASE}/v1/${deleteCustomerUuid}`, { method: "DELETE" });
            const data = await res.json();
            if (data.code === "SYS0001") {
                bootstrap.Modal.getInstance(document.getElementById('deleteConfirmModal')).hide();
                loadCustomers(currentPage);
                showToast("刪除成功！", "success");
            } else {
                showToast("刪除失敗：" + data.message, "danger");
            }
        } catch (error) {
            showToast("刪除失敗：" + error.message, "danger");
        } finally {
            deleteCustomerUuid = null;
        }
    });

    loadCustomers();
});

function clearSearch() {
    document.getElementById("keyword").value = "";
    loadCustomers(0);
}

document.addEventListener("DOMContentLoaded", () => {
    loadCustomers();
});
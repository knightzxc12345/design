// ==========================
// 全域設定
// ==========================
let currentPage = 0;
let pageSize = 10;
const API_BASE = `${DOMAIN}/item`;
let deleteItemUuid = null;
let suppliers = []; // 全域供應商陣列

// ==========================
// 搜尋 / 分頁功能
// ==========================
async function loadItems(page = 0) {
    currentPage = page;
    const keyword = document.getElementById("keyword")?.value || "";

    const res = await fetch(`${API_BASE}/v1/page?page=${page}&size=${pageSize}&keyword=${keyword}`);
    const data = (await res.json()).data;
    if (!data) return;

    const items = data.responses || [];
    const pageInfo = data.page;
    const tbody = document.getElementById("itemTableBody");
    tbody.innerHTML = "";

    items.forEach(item => {
        const statusLabel = item.status === "ACTIVE" ? "啟用" : "停用";
        const statusClass = item.status === "ACTIVE" ? "text-bg-success" : "text-bg-danger";

        tbody.innerHTML += `
            <tr>
                <td>${item.generalTerm}</td>
                <td>${item.name}</td>
                <td>${item.dimension || ""}</td>
                <td>${item.description || ""}</td>
                <td>${item.unit || ""}</td>
                <td>${item.price != null ? formatNumber(item.price) : ""}</td>
                <td>${item.supplierName || ""}</td>
                <td><div class="badge rounded-pill py-2 px-3 ${statusClass}">${statusLabel}</div></td>
                <td>
                    <div class="btn-group" role="group">
                        <button class="btn btn-sm btn-outline-secondary me-1" onclick="openEditModal('${item.uuid}')">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="openDeleteModal('${item.uuid}', '${item.generalTerm}')">
                            <i class="bi bi-trash"></i> 刪除
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination(pageInfo, loadItems);
}

function clearSearch() {
    document.getElementById("keyword").value = "";
    loadItems(0);
}

// ==========================
// 新增功能 (CREATE)
// ==========================
function openCreateModal() {
    clearCreateModal();
    populateCreateSupplierSelect();
    new bootstrap.Modal(document.getElementById("createModal"), { backdrop: "static", keyboard: false }).show();
}

function clearCreateModal() {
    const ids = ["createGeneralTerm","createName","createDimension","createDescription","createUnit","createPrice","createSupplierUuid","createStatus"];
    ids.forEach(id => { const el = document.getElementById(id); if (el) el.value = ""; });
}

function populateCreateSupplierSelect() {
    const select = document.getElementById("createSupplierUuid");
    if (!select) return;
    select.innerHTML = "";
    suppliers.forEach((supplier, index) => {
        select.innerHTML += `<option value="${supplier.uuid}" ${index === 0 ? "selected" : ""}>${supplier.name}</option>`;
    });
}

async function saveNewItem(e) {
    e.preventDefault();
    const payload = {
        generalTerm: document.getElementById("createGeneralTerm").value.trim(),
        name: document.getElementById("createName").value.trim(),
        dimension: document.getElementById("createDimension").value.trim(),
        description: document.getElementById("createDescription").value.trim(),
        unit: document.getElementById("createUnit").value.trim(),
        price: parseFloat(document.getElementById("createPrice").value),
        supplierUuid: document.getElementById("createSupplierUuid").value,
        status: "ACTIVE"
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
            loadItems(currentPage);
            showToast("新增成功！", "success");
        } else {
            showToast("新增失敗：" + data.message, "danger");
        }
    } catch (error) {
        showToast("新增失敗：" + error.message, "danger");
    }
}


// ==========================
// 編輯功能 (UPDATE)
// ==========================
async function openEditModal(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;
    document.getElementById("editUuid").value = uuid;
    document.getElementById("editGeneralTerm").value = data.generalTerm;
    document.getElementById("editName").value = data.name;
    document.getElementById("editDimension").value = data.dimension || "";
    document.getElementById("editDescription").value = data.description || "";
    document.getElementById("editUnit").value = data.unit || "";
    document.getElementById("editPrice").value = data.price || "";
    document.getElementById("editSupplierUuid").value = data.supplierUuid || "";
    document.getElementById("editStatus").checked = data.status === "ACTIVE";

    new bootstrap.Modal(document.getElementById("editModal"), { backdrop: "static", keyboard: false }).show();
}

function clearEditModal() {
    const ids = ["editUuid","editGeneralTerm","editName","editDimension","editDescription","editUnit","editPrice","editSupplierUuid","editStatus"];
    ids.forEach(id => { const el = document.getElementById(id); if (el) el.value = ""; });
}

function populateEditSupplierSelect(selectedUuid) {
    const select = document.getElementById("editSupplierUuid");
    if (!select) return;
    select.innerHTML = "";
    suppliers.forEach((supplier, index) => {
        const isSelected = selectedUuid
            ? supplier.uuid === selectedUuid
            : index === 0;
        select.innerHTML += `<option value="${supplier.uuid}" ${isSelected ? "selected" : ""}>${supplier.name}</option>`;
    });
}

async function saveEditItem(e) {
    e.preventDefault();
    const uuid = document.getElementById("editUuid").value;
    const payload = {
        generalTerm: document.getElementById("editGeneralTerm").value.trim(),
        name: document.getElementById("editName").value.trim(),
        dimension: document.getElementById("editDimension").value.trim(),
        description: document.getElementById("editDescription").value.trim(),
        unit: document.getElementById("editUnit").value.trim(),
        price: parseFloat(document.getElementById("editPrice").value),
        supplierUuid: document.getElementById("editSupplierUuid").value,
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
            loadItems(currentPage);
            showToast("修改成功！", "success");
        } else {
            showToast("修改失敗：" + data.message, "danger");
        }
    } catch (error) {
        showToast("修改失敗：" + error.message, "danger");
    }
}


// ==========================
// 刪除功能 (DELETE)
// ==========================
function openDeleteModal(uuid, itemGeneralTerm) {
    deleteItemUuid = uuid;
    document.getElementById("deleteConfirmMessage").innerText = `你確定要刪除「${itemGeneralTerm}」嗎？`;
    new bootstrap.Modal(document.getElementById("deleteConfirmModal")).show();
}

async function confirmDelete() {
    if (!deleteItemUuid) return;
    try {
        const res = await fetch(`${API_BASE}/v1/${deleteItemUuid}`, { method: "DELETE" });
        const data = await res.json();
        if (data.code === "SYS0001") {
            bootstrap.Modal.getInstance(document.getElementById("deleteConfirmModal")).hide();
            loadItems(currentPage);
            showToast("刪除成功！", "success");
        } else showToast("刪除失敗：" + data.message, "danger");
    } catch (error) {
        showToast("刪除失敗：" + error.message, "danger");
    } finally {
        deleteItemUuid = null;
    }
}

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async () => {
    suppliers = await loadSuppliersData();
    populateCreateSupplierSelect();
    populateEditSupplierSelect();
    loadItems();
    document.getElementById("confirmDeleteBtn")?.addEventListener("click", confirmDelete);
});

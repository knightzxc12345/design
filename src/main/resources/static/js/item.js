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
                <td>${item.unit || ""}</td>
                <td>${item.supplierName || ""}</td>
                <td><div class="badge rounded-pill py-2 px-3 ${statusClass}">${statusLabel}</div></td>
                <td>
                    <div class="btn-group" role="group">
                        <button class="btn btn-sm btn-outline-secondary me-1" onclick="showDetail('${item.uuid}')">
                            <i class="bi bi-eye me-1"></i> 查看
                        </button>
                        <button class="btn btn-sm btn-outline-secondary me-1" onclick="openEditModal('${item.uuid}')">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="openDeleteModal('${item.uuid}', '${item.name}')">
                            <i class="bi bi-trash"></i> 刪除
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination("pagination", pageInfo, currentPage, loadItems);
}

function clearSearch() {
    document.getElementById("keyword").value = "";
    loadItems(0);
}

// ==========================
// 查看功能 (READ)
// ==========================
async function showDetail(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;

    document.getElementById("viewUuid").value = data.uuid || "";
    document.getElementById("viewGeneralTerm").value = data.generalTerm || "";
    document.getElementById("viewName").value = data.name || "";
    document.getElementById("viewDimension").value = data.dimension || "";
    document.getElementById("viewDescription").value = data.description || "";
    document.getElementById("viewUnit").value = data.unit || "";
    document.getElementById("viewPrice").value = data.price || "";
    document.getElementById("viewSupplierName").value = data.supplierName || "";
    document.getElementById("viewStatus").checked = (data.status === "ACTIVE");
    document.getElementById("viewStatusStr").textContent = (data.status === "ACTIVE" ? "啟用" : "停用");

    new bootstrap.Modal(document.getElementById("viewModal"), { backdrop: "static", keyboard: false }).show();
}

// ==========================
// 新增功能 (CREATE)
// ==========================
function openCreateModal() {
    clearCreateModal();
    new bootstrap.Modal(document.getElementById("createModal"), { backdrop: "static", keyboard: false }).show();
}

function clearCreateModal() {
    const ids = ["createGeneralTerm","createName","createDimension","createDescription","createUnit","createPrice","createSupplierUuid","createStatus"];
    ids.forEach(id => { const el = document.getElementById(id); if (el) el.value = ""; });
    document.getElementById("createImagePreview").style.display = "none";
}

function previewCreateImageFile(event) {
    const file = event.target.files[0];
    const preview = document.getElementById("createImagePreview");
    if (file) {
        const reader = new FileReader();
        reader.onload = e => { preview.src = e.target.result; preview.style.display = "block"; };
        reader.readAsDataURL(file);
    } else { preview.src = ""; preview.style.display = "none"; }
}

function populateCreateSupplierSelect() {
    const select = document.getElementById("createSupplierUuid");
    if (!select) return;
    select.innerHTML = "";
    suppliers.forEach(supplier => {
        select.innerHTML += `<option value="${supplier.uuid}">${supplier.name}</option>`;
    });
}

async function saveNewItem(e) {
    e.preventDefault();
    const formData = new FormData();
    formData.append("generalTerm", document.getElementById("createGeneralTerm").value.trim());
    formData.append("name", document.getElementById("createName").value.trim());
    formData.append("dimension", document.getElementById("createDimension").value.trim());
    formData.append("description", document.getElementById("createDescription").value.trim());
    formData.append("unit", document.getElementById("createUnit").value.trim());
    formData.append("price", parseFloat(document.getElementById("createPrice").value));
    formData.append("supplierUuid", document.getElementById("createSupplierUuid").value);
    formData.append("status", "ACTIVE");
    try {
        const res = await fetch(`${API_BASE}/v1`, { method: "POST", body: formData });
        const data = await res.json();
        if (data.code === "SYS0001") {
            bootstrap.Modal.getInstance(document.getElementById("createModal")).hide();
            clearCreateModal();
            loadItems(currentPage);
            showToast("新增成功！", "success");
        } else showToast("新增失敗：" + data.message, "danger");
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
    document.getElementById("editImagePreview").style.display = "none";
}

function populateEditSupplierSelect() {
    const select = document.getElementById("editSupplierUuid");
    if (!select) return;
    select.innerHTML = "";
    suppliers.forEach(supplier => {
        select.innerHTML += `<option value="${supplier.uuid}">${supplier.generalTerm}</option>`;
    });
}

async function saveEditItem(e) {
    e.preventDefault();
    const uuid = document.getElementById("editUuid").value;
    const formData = new FormData();
    formData.append("generalTerm", document.getElementById("editGeneralTerm").value.trim());
    formData.append("name", document.getElementById("editName").value.trim());
    formData.append("dimension", document.getElementById("edittDimension").value.trim());
    formData.append("description", document.getElementById("editDescription").value.trim());
    formData.append("unit", document.getElementById("editUnit").value.trim());
    formData.append("price", parseFloat(document.getElementById("editPrice").value));
    formData.append("supplierUuid", document.getElementById("editSupplierUuid").value);
    formData.append("status", document.getElementById("editStatus").checked ? "ACTIVE" : "INACTIVE");

    try {
        const res = await fetch(`${API_BASE}/v1/${uuid}`, { method: "PUT", body: formData });
        const data = await res.json();
        if (data.code === "SYS0001") {
            bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
            clearEditModal();
            loadItems(currentPage);
            showToast("修改成功！", "success");
        } else showToast("修改失敗：" + data.message, "danger");
    } catch (error) {
        showToast("修改失敗：" + error.message, "danger");
    }
}

// ==========================
// 刪除功能 (DELETE)
// ==========================
function openDeleteModal(uuid, itemName) {
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

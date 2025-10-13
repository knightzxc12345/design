// ==========================
// 全域設定
// ==========================
let currentPage = 0;
let pageSize = 10;
const API_BASE = `${DOMAIN}/product`;
let deleteProductUuid = null;
let suppliers = [];
let items = [];
let prices = [];

// ==========================
// 搜尋 / 分頁功能
// ==========================
async function loadProducts(page = 0) {
    currentPage = page;
    const keyword = document.getElementById("keyword")?.value || "";

    const res = await fetch(`${API_BASE}/v1/page?page=${page}&size=${pageSize}&keyword=${keyword}`);
    const data = (await res.json()).data;
    if (!data) return;

    const products = data.responses || [];
    const pageInfo = data.page;
    const tbody = document.getElementById("productTableBody");
    tbody.innerHTML = "";

    products.forEach(p => {
        const statusLabel = p.status === "ACTIVE" ? "啟用" : "停用";
        const statusClass = p.status === "ACTIVE" ? "text-bg-success" : "text-bg-danger";

        tbody.innerHTML += `
            <tr>
                <td>${p.name}</td>
                <td>${p.code}</td>
                <td>${p.dimension || ""}</td>
                <td>${p.unit || ""}</td>
                <td>${p.price || ""}</td>
                <td><div class="badge rounded-pill py-2 px-3 ${statusClass}">${statusLabel}</div></td>
                <td>
                    ${p.imageUrl ? `<img src="${p.imageUrl}" class="img-fluid rounded" style="max-height:50px; cursor:pointer;" onclick="openImagePreview('${p.imageUrl}')">` : ''}
                </td>
                <td>
                    <div class="btn-group" role="group">
                        <button class="btn btn-sm btn-outline-secondary me-1" onclick="showProductDetail('${p.uuid}')">
                            <i class="bi bi-eye me-1"></i> 查看
                        </button>
                        <button class="btn btn-sm btn-outline-secondary me-1" onclick="openEditProductModal('${p.uuid}')">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="openDeleteProductModal('${p.uuid}', '${p.name}')">
                            <i class="bi bi-trash"></i> 刪除
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination("pagination", pageInfo, currentPage, loadProducts);
}

function clearSearch() {
    document.getElementById("keyword").value = "";
    loadProducts(0);
}

// ==========================
// 查看產品 (READ)
// ==========================
async function showDetail(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;

    document.getElementById("viewUuid").value = data.uuid || "";
    document.getElementById("viewName").value = data.name || "";
    document.getElementById("viewCode").value = data.code || "";
    document.getElementById("viewDimension").value = data.dimension || "";
    document.getElementById("viewDescription").value = data.description || "";
    document.getElementById("viewUnit").value = data.unit || "";
    document.getElementById("viewPrice").value = data.price || "";
    document.getElementById("viewStatus").checked = (data.status === "ACTIVE");
    document.getElementById("viewStatusStr").textContent = data.status === "ACTIVE" ? "啟用" : "停用";
    document.getElementById("viewImagesContainer").innerHTML = data.imageUrl
        ? `<img src="${data.imageUrl}" class="img-fluid rounded" style="max-height:200px;">`
        : "";
    const productListEl = document.getElementById("viewProductList");
    productListEl.innerHTML = (data.productItems || []).map(i => `<li>${i.name} x ${i.quantity}</li>`).join("");

    new bootstrap.Modal(document.getElementById("viewModal"), { backdrop: "static", keyboard: false }).show();
}

// ==========================
// 新增產品 (CREATE)
// ==========================
function openCreateModal() {
    clearCreateModal();
    new bootstrap.Modal(document.getElementById("createModal"), { backdrop: "static", keyboard: false }).show();
}

async function clearCreateModal() {
    const ids = ["createName","createCode","createDimension","createDescription","createUnit","createPrice","createImageFile"];
    ids.forEach(id => { const el = document.getElementById(id); if(el) el.value = ""; });
    document.getElementById("createImagePreview").style.display = "none";

    // 初始化品項列
    const container = document.getElementById("createProductItemsContainer");
    container.innerHTML = `
        <label>品項 <span class="text-danger">*</span></label>
    `;

    // 確保 suppliers 已經載入再新增
    if (suppliers.length > 0) {
        await addProductItemRow();
    }
}

// 圖片預覽
function previewCreateImageFile(event) {
    const file = event.target.files[0];
    const preview = document.getElementById("createImagePreview");
    if (file) {
        const reader = new FileReader();
        reader.onload = e => { preview.src = e.target.result; preview.style.display = "block"; };
        reader.readAsDataURL(file);
    } else { preview.src = ""; preview.style.display = "none"; }
}

// 品項操作
async function addProductItemRow(){
    const container = document.getElementById("createProductItemsContainer");
    const row = document.createElement("div");
    row.className = "d-flex gap-2 mb-1 product-item-row";

    row.innerHTML = `
        <select class="form-select supplier-select" required onchange="onSupplierChange(this)">
            ${suppliers.map(s => `<option value="${s.uuid}">${s.name}</option>`).join("")}
        </select>
        <select class="form-select item-select" required>
        </select>
        <input type="number" class="form-control item-quantity" placeholder="數量" min="1" value="1" style="max-width:60px;" required>
        <button type="button" class="btn btn-outline-danger" onclick="removeProductItemRow(this)">
            <i class="bi bi-trash"></i>
        </button>
    `;
    container.appendChild(row);

    const supplierSelect = row.querySelector(".supplier-select");
    const itemSelect = row.querySelector(".item-select");
    if (supplierSelect.value) {
        updateItemSelect(itemSelect, supplierSelect.value);
    }
}

function removeProductItemRow(btn){
    const container = document.getElementById("createProductItemsContainer");
    if(container.children.length > 1) btn.closest(".product-item-row").remove();
}

function onSupplierChange(select){
    const supplierUuid = select.value;
    const row = select.closest(".product-item-row");
    updateItemSelect(row.querySelector(".item-select"), supplierUuid);
}

function updateItemSelect(itemSelect, supplierUuid){
    if(!items) return;
    const filtered = items.filter(i => i.supplierUuid === supplierUuid);
    itemSelect.innerHTML = filtered.length === 0
        ? `<option value="">無品項</option>`
        : filtered.map(i => `<option value="${i.uuid}">${i.name}</option>`).join("");
}

function getProductItemsData(){
    const container = document.getElementById("createProductItemsContainer");
    return Array.from(container.querySelectorAll(".product-item-row")).map(row => ({
        uuid: row.querySelector(".item-select").value,
        quantity: parseInt(row.querySelector(".item-quantity").value)
    }));
}

// 儲存新增產品
async function saveNewProduct(e) {
    e.preventDefault();
    const formData = new FormData();
    formData.append("name", document.getElementById("createName").value.trim());
    formData.append("code", document.getElementById("createCode").value.trim());
    formData.append("dimension", document.getElementById("createDimension").value.trim());
    formData.append("description", document.getElementById("createDescription").value.trim());
    formData.append("unit", document.getElementById("createUnit").value.trim());
    formData.append("price", parseFloat(document.getElementById("createPrice").value));
    formData.append("items", JSON.stringify(getProductItemsData()));

    const fileInput = document.getElementById("createImageFile");
    if(fileInput.files[0]) formData.append("file", fileInput.files[0]);

    try {
        const res = await fetch(`${API_BASE}/v1`, { method: "POST", body: formData });
        const data = await res.json();
        if(data.code === "SYS0001") {
            bootstrap.Modal.getInstance(document.getElementById("createModal")).hide();
            clearCreateModal();
            loadProducts(currentPage);
            showToast("新增成功！","success");
        } else showToast("新增失敗：" + data.message,"danger");
    } catch(error) {
        showToast("新增失敗：" + error.message,"danger");
    }
}

// ==========================
// 編輯產品 (UPDATE)
// ==========================
async function openEditModal(uuid){
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;

    document.getElementById("editUuid").value = uuid;
    document.getElementById("editName").value = data.name;
    document.getElementById("editCode").value = data.code;
    document.getElementById("editDimension").value = data.dimension || "";
    document.getElementById("editDescription").value = data.description || "";
    document.getElementById("editUnit").value = data.unit || "";
    document.getElementById("editPrice").value = data.price || "";
    document.getElementById("editStatus").checked = data.status === "ACTIVE";
    if(data.imageUrl){
        document.getElementById("editImagePreview").src = data.imageUrl;
        document.getElementById("editImagePreview").style.display = "block";
    } else {
        document.getElementById("editImagePreview").style.display = "none";
    }

    new bootstrap.Modal(document.getElementById("editModal"), { backdrop: "static", keyboard: false }).show();
}

function clearEditModal(){
    const ids = ["editUuid","editName","editCode","editDimension","editDescription","editUnit","editPrice","editStatus","editImageFile"];
    ids.forEach(id => { const el = document.getElementById(id); if(el) el.value = ""; });
    document.getElementById("editImagePreview").style.display = "none";
}

function previewEditImageFile(event){
    const file = event.target.files[0];
    const preview = document.getElementById("editImagePreview");
    if(file){
        const reader = new FileReader();
        reader.onload = e => { preview.src = e.target.result; preview.style.display = "block"; };
        reader.readAsDataURL(file);
    } else { preview.src = ""; preview.style.display = "none"; }
}

async function saveEditProduct(e){
    e.preventDefault();
    const uuid = document.getElementById("editUuid").value;
    const formData = new FormData();
    formData.append("name", document.getElementById("editName").value.trim());
    formData.append("code", document.getElementById("editCode").value.trim());
    formData.append("dimension", document.getElementById("editDimension").value.trim());
    formData.append("description", document.getElementById("editDescription").value.trim());
    formData.append("unit", document.getElementById("editUnit").value.trim());
    formData.append("price", parseFloat(document.getElementById("editPrice").value));
    formData.append("status", document.getElementById("editStatus").checked ? "ACTIVE" : "INACTIVE");

    const fileInput = document.getElementById("editImageFile");
    if(fileInput.files[0]) formData.append("file", fileInput.files[0]);

    try {
        const res = await fetch(`${API_BASE}/v1/${uuid}`, { method: "PUT", body: formData });
        const data = await res.json();
        if(data.code === "SYS0001"){
            bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
            clearEditModal();
            loadProducts(currentPage);
            showToast("修改成功！","success");
        } else showToast("修改失敗：" + data.message,"danger");
    } catch(error){
        showToast("修改失敗：" + error.message,"danger");
    }
}

// ==========================
// 刪除產品 (DELETE)
// ==========================
function openDeleteModal(uuid, name){
    deleteProductUuid = uuid;
    document.getElementById("deleteConfirmMessage").innerText = `你確定要刪除「${name}」嗎？`;
    new bootstrap.Modal(document.getElementById("deleteConfirmModal")).show();
}

async function confirmDelete(){
    if(!deleteProductUuid) return;
    try{
        const res = await fetch(`${API_BASE}/v1/${deleteProductUuid}`, { method: "DELETE" });
        const data = await res.json();
        if(data.code === "SYS0001"){
            bootstrap.Modal.getInstance(document.getElementById("deleteConfirmModal")).hide();
            loadProducts(currentPage);
            showToast("刪除成功！","success");
        } else showToast("刪除失敗：" + data.message,"danger");
    } catch(error){
        showToast("刪除失敗：" + error.message,"danger");
    } finally{
        deleteProductUuid = null;
    }
}

// ==========================
// 圖片放大
// ==========================
function openImagePreview(imageUrl){
    const modalImg = document.getElementById("imagePreviewModalImg");
    modalImg.src = imageUrl;
    const modal = new bootstrap.Modal(document.getElementById("imagePreviewModal"));
    modal.show();
}

// 供應商 select 下拉初始化
function populateCreateSupplierSelect(){
    const select = document.getElementById("createSupplierUuid");
    if(!select) return;
    select.innerHTML = suppliers.map(s => `<option value="${s.uuid}">${s.name}</option>`).join("");
}

function populateEditSupplierSelect(){
    const select = document.getElementById("editSupplierUuid");
    if(!select) return;
    select.innerHTML = suppliers.map(s => `<option value="${s.uuid}">${s.name}</option>`).join("");
}

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async () => {
    const rawItems = await loadItemsData();
    if(rawItems && rawItems.length > 0){
        const supplierMap = new Map();
        rawItems.forEach(item => {
            if(item.supplierUuid && !supplierMap.has(item.supplierUuid)){
                supplierMap.set(item.supplierUuid, {
                    uuid: item.supplierUuid,
                    name: item.supplierName
                });
            }
        });
        suppliers = Array.from(supplierMap.values());

        items = rawItems.map(i => ({
            uuid: i.uuid,
            name: i.name,
            supplierUuid: i.supplierUuid,
            supplierName: i.supplierName
        }));

        prices = rawItems.map(i => ({
            uuid: i.uuid,
            price: i.price
        }));
    }

    // 初始化 product modal 下拉
    populateCreateSupplierSelect();
    populateEditSupplierSelect();

    // 載入產品列表
    loadProducts();

    // 刪除按鈕
    document.getElementById("confirmDeleteBtn")?.addEventListener("click", confirmDelete);
});

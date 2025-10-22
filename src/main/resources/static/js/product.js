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
                <td>${p.costPrice != null ? formatNumber(p.costPrice) : ""}</td>
                <td>${p.price != null ? formatNumber(p.price) : ""}</td>
                <td><div class="badge rounded-pill py-2 px-3 ${statusClass}">${statusLabel}</div></td>
                <td>
                    ${p.imageUrl ? `<img src="${p.imageUrl}" class="img-fluid rounded" style="max-height:50px; cursor:pointer;" onclick="openImagePreview('${p.imageUrl}')">` : ''}
                </td>
                <td>
                    <div class="btn-group" role="group">
                        <button class="btn btn-sm btn-outline-secondary me-1" onclick="showProductDetail('${p.uuid}')">
                            <i class="bi bi-eye me-1"></i> 查看
                        </button>
                        <button class="btn btn-sm btn-outline-secondary me-1" onclick="openEditModal('${p.uuid}')">
                            <i class="bi bi-pencil me-1"></i> 編輯
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="openDeleteModal('${p.uuid}', '${p.name}')">
                            <i class="bi bi-trash"></i> 刪除
                        </button>
                    </div>
                </td>
            </tr>
        `;
    });

    renderPagination(pageInfo, loadProducts);
}

function clearSearch() {
    document.getElementById("keyword").value = "";
    loadProducts(0);
}

// ==========================
// 查看產品 (READ)
// ==========================
async function showProductDetail(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;

    document.getElementById("viewUuid").value = uuid || "";
    document.getElementById("viewName").value = data.name || "";
    document.getElementById("viewCode").value = data.code || "";
    document.getElementById("viewDimension").value = data.dimension || "";
    document.getElementById("viewDescription").value = data.description || "";
    document.getElementById("viewUnit").value = data.unit || "";
    document.getElementById("viewCostPrice").value = data.costPrice != null ? formatNumber(data.costPrice) : "";
    document.getElementById("viewPrice").value = data.price != null ? formatNumber(data.price) : "";
    document.getElementById("viewStatus").checked = (data.status === "ACTIVE");
    document.getElementById("viewStatusStr").textContent = data.status === "ACTIVE" ? "啟用" : "停用";
    document.getElementById("viewImagesContainer").innerHTML = data.imageUrl
        ? `<img src="${data.imageUrl}" class="img-fluid rounded" style="max-height:200px;">`
        : "";

    const productListEl = document.querySelector(".view-product-item-row");
    productListEl.innerHTML = "";

    // 使用 items & suppliers 對應名稱
    data.items.forEach(item => {
        const supplier = suppliers.find(s => s.uuid === item.supplierUuid);
        const itemInfo = items.find(i => i.uuid === item.uuid);

        const row = document.createElement("div");
        row.className = "d-flex gap-2 mb-2";
        row.innerHTML = `
            <input type="text" class="form-control" value="${supplier ? supplier.name : ''}" placeholder="供應商名稱" disabled>
            <input type="text" class="form-control" value="${itemInfo ? itemInfo.generalTerm : ''}" placeholder="品項名稱" disabled>
            <input type="number" class="form-control" value="${item.quantity}" placeholder="數量" disabled style="max-width:60px;">
            <input type="text" class="form-control text-end" value="${formatNumber(item.price)}" placeholder="價格" disabled>
        `;
        productListEl.appendChild(row);
    });

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

    const container = document.getElementById("createProductItemsContainer");
    container.innerHTML = `<label>品項 <span class="text-danger">*</span></label>`;

    if (suppliers.length > 0) {
        await addProductItemRow();
    }
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

// ==========================
// 新增品項操作
// ==========================
async function addProductItemRow(itemData) {
    const container = document.getElementById("createProductItemsContainer");
    const row = document.createElement("div");
    row.className = "d-flex gap-2 mb-1 create-product-item-row";

    row.innerHTML = `
        <select class="form-select supplier-select" required></select>
        <select class="form-select item-select" required></select>
        <input type="number" class="form-control item-quantity" placeholder="數量" min="1" value="1" style="max-width:80px;" required>
        <input type="text" class="form-control item-price" placeholder="單價" readonly style="max-width:100px;">
        <button type="button" class="btn btn-outline-danger" onclick="removeProductItemRow(this)">
            <i class="bi bi-trash"></i>
        </button>
    `;
    container.appendChild(row);

    const supplierSelect = row.querySelector(".supplier-select");
    const itemSelect = row.querySelector(".item-select");
    const qtyInput = row.querySelector(".item-quantity");

    supplierSelect.innerHTML = suppliers.map(s =>
        `<option value="${s.uuid}" ${itemData?.supplierUuid === s.uuid ? "selected" : ""}>${s.name}</option>`
    ).join("");

    updateItemSelect(itemSelect, supplierSelect.value, itemData?.uuid);

    if(itemData?.quantity) qtyInput.value = itemData.quantity;

    supplierSelect.addEventListener("change", () => onSupplierChange(supplierSelect));
    itemSelect.addEventListener("change", () => updateItemPrice(row));
    qtyInput.addEventListener("input", updateTotalPrice);

    updateItemPrice(row);
}

function onSupplierChange(selectEl) {
    const row = selectEl.closest(".create-product-item-row");
    const supplierUuid = selectEl.value;
    const itemSelect = row.querySelector(".item-select");
    updateItemSelect(itemSelect, supplierUuid);
    updateItemPrice(row);
}

function updateItemSelect(itemSelect, supplierUuid, selectedItemUuid) {
    if(!items) return;
    const filtered = items.filter(i => i.supplierUuid === supplierUuid);
    itemSelect.innerHTML = filtered.length === 0
        ? `<option value="">無品項</option>`
        : filtered.map(i => {
            const priceObj = prices.find(p => p.uuid === i.uuid);
            const price = priceObj ? priceObj.price : 0;
            const selected = selectedItemUuid && i.uuid === selectedItemUuid ? "selected" : "";
            return `<option value="${i.uuid}" data-price="${price}" ${selected}>${i.generalTerm}</option>`;
        }).join("");
}

function updateItemPrice(row){
    const itemSelect = row.querySelector(".item-select");
    const priceInput = row.querySelector(".item-price");
    const selectedOption = itemSelect.options[itemSelect.selectedIndex];
    const price = parseFloat(selectedOption.dataset.price || 0);
    priceInput.value = formatNumber(Math.round(price));
    updateTotalPrice();
}

function updateTotalPrice() {
    let total = 0;
    document.querySelectorAll("#createProductItemsContainer .create-product-item-row").forEach(row => {
        const qty = parseInt(unformatNumber(row.querySelector(".item-quantity").value)) || 0;
        const price = parseInt(unformatNumber(row.querySelector(".item-price").value)) || 0;
        total += qty * price;
    });
    const totalInput = document.getElementById("createCostPrice");
    if(totalInput) totalInput.value = formatNumber(total);
}

function removeProductItemRow(btn){
    btn.closest(".create-product-item-row").remove();
    updateTotalPrice();
}

function getProductItemsData(){
    const container = document.getElementById("createProductItemsContainer");
    return Array.from(container.querySelectorAll(".create-product-item-row")).map(row => ({
        uuid: row.querySelector(".item-select").value,
        quantity: parseInt(row.querySelector(".item-quantity").value) || 0
    }));
}

// ==========================
// 儲存新增產品
// ==========================
async function saveNewProduct(e) {
    e.preventDefault();
    document.querySelectorAll(".item-price, .item-quantity, #createPrice").forEach(input => {
        input.value = unformatNumber(input.value);
    });

    const formData = new FormData();
    formData.append("name", document.getElementById("createName").value.trim());
    formData.append("code", document.getElementById("createCode").value.trim());
    formData.append("dimension", document.getElementById("createDimension").value.trim());
    formData.append("description", document.getElementById("createDescription").value.trim());
    formData.append("unit", document.getElementById("createUnit").value.trim());
    formData.append("price", parseInt(document.getElementById("createPrice").value) || 0);
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
async function openEditModal(uuid) {
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;

    document.getElementById("editUuid").value = uuid;
    document.getElementById("editName").value = data.name;
    document.getElementById("editCode").value = data.code;
    document.getElementById("editDimension").value = data.dimension || "";
    document.getElementById("editDescription").value = data.description || "";
    document.getElementById("editUnit").value = data.unit || "";
    document.getElementById("editCostPrice").value = formatNumber(data.costPrice || 0);
    document.getElementById("editPrice").value = formatNumber(data.price || 0);
    document.getElementById("editStatus").checked = data.status === "ACTIVE";

    const preview = document.getElementById("editImagePreview");
    if(data.imageUrl) {
        preview.src = data.imageUrl;
        preview.style.display = "block";
    } else preview.style.display = "none";

    const container = document.getElementById("editProductItemsContainer");
    container.innerHTML = `<label>品項 <span class="text-danger">*</span></label>`;

    if(Array.isArray(data.items)) {
        data.items.forEach(item => addEditProductItemRow(item));
    }

    updateEditTotalPrice();
    new bootstrap.Modal(document.getElementById("editModal"), { backdrop: "static", keyboard: false }).show();
}

// ==========================
// 編輯品項操作
// ==========================
function addEditProductItemRow(itemData) {
    const container = document.getElementById("editProductItemsContainer");
    const row = document.createElement("div");
    row.className = "d-flex gap-2 mb-1 edit-product-item-row";

    row.innerHTML = `
        <select class="form-select supplier-select" required></select>
        <select class="form-select item-select" required></select>
        <input type="number" class="form-control item-quantity" placeholder="數量" min="1" value="1" style="max-width:80px;" required>
        <input type="text" class="form-control item-price text-end" placeholder="單價" readonly style="max-width:100px;">
        <button type="button" class="btn btn-outline-danger" onclick="removeEditProductItemRow(this)">
            <i class="bi bi-trash"></i>
        </button>
    `;
    container.appendChild(row);

    const supplierSelect = row.querySelector(".supplier-select");
    const itemSelect = row.querySelector(".item-select");
    const qtyInput = row.querySelector(".item-quantity");

    // 選中對應供應商
    supplierSelect.innerHTML = suppliers.map(s =>
        `<option value="${s.uuid}" ${itemData?.supplierUuid === s.uuid ? "selected" : ""}>${s.name}</option>`
    ).join("");

    // 用品項 uuid 選中正確品項
    updateEditItemSelect(itemSelect, supplierSelect.value, itemData.uuid);

    if(itemData?.quantity) qtyInput.value = itemData.quantity;

    supplierSelect.addEventListener("change", () => onEditSupplierChange(supplierSelect));
    itemSelect.addEventListener("change", () => updateEditItemPrice(row));
    qtyInput.addEventListener("input", updateEditTotalPrice);

    updateEditItemPrice(row);
}

function onEditSupplierChange(selectEl) {
    const row = selectEl.closest(".edit-product-item-row");
    const itemSelect = row.querySelector(".item-select");
    updateEditItemSelect(itemSelect, selectEl.value);
    updateEditItemPrice(row);
}

function updateEditItemSelect(itemSelect, supplierUuid, selectedItemUuid) {
    if (!items) return;

    const filtered = items.filter(i => i.supplierUuid === supplierUuid);

    // 生成選項
    itemSelect.innerHTML = filtered.length === 0
        ? `<option value="">無品項</option>`
        : filtered.map(i => {
            const priceObj = prices.find(p => p.uuid === i.uuid);
            const price = priceObj ? priceObj.price : 0;
            return `<option value="${i.uuid}" data-price="${price}">${i.generalTerm}</option>`;
        }).join("");

    // 明確選中
    if (selectedItemUuid) {
        const optionToSelect = Array.from(itemSelect.options).find(opt => opt.value === selectedItemUuid);
        if (optionToSelect) optionToSelect.selected = true;
    }

    // 更新價格
    updateEditItemPrice(itemSelect.closest(".edit-product-item-row"));
}

function updateEditItemPrice(row){
    const itemSelect = row.querySelector(".item-select");
    const priceInput = row.querySelector(".item-price");
    const selectedOption = itemSelect.options[itemSelect.selectedIndex];
    const price = parseFloat(selectedOption?.dataset.price || 0);
    priceInput.value = formatNumber(Math.round(price));
    updateEditTotalPrice();
}

function updateEditTotalPrice() {
    let total = 0;
    document.querySelectorAll("#editProductItemsContainer .edit-product-item-row").forEach(row => {
        const qty = parseInt(row.querySelector(".item-quantity").value) || 0;
        const price = parseInt(unformatNumber(row.querySelector(".item-price").value)) || 0;
        total += qty * price;
    });
    const totalInput = document.getElementById("editCostPrice");
    if(totalInput) totalInput.value = formatNumber(total);
}

function removeEditProductItemRow(btn){
    btn.closest(".edit-product-item-row").remove();
    updateEditTotalPrice();
}

function getEditProductItemsData(){
    const container = document.getElementById("editProductItemsContainer");
    return Array.from(container.querySelectorAll(".edit-product-item-row")).map(row => ({
        uuid: row.querySelector(".item-select").value,
        quantity: parseInt(row.querySelector(".item-quantity").value) || 0
    }));
}

async function saveEditProduct(e){
    e.preventDefault();
    document.querySelectorAll(".item-price, #editPrice").forEach(input => input.value = unformatNumber(input.value));

    const uuid = document.getElementById("editUuid").value;
    const formData = new FormData();
    formData.append("name", document.getElementById("editName").value.trim());
    formData.append("code", document.getElementById("editCode").value.trim());
    formData.append("dimension", document.getElementById("editDimension").value.trim());
    formData.append("description", document.getElementById("editDescription").value.trim());
    formData.append("unit", document.getElementById("editUnit").value.trim());
    formData.append("price", parseInt(document.getElementById("editPrice").value) || 0);
    formData.append("status", document.getElementById("editStatus").checked ? "ACTIVE" : "INACTIVE");
    formData.append("items", JSON.stringify(getEditProductItemsData()));

    const fileInput = document.getElementById("editImageFile");
    if(fileInput.files[0]) formData.append("file", fileInput.files[0]);

    try{
        const res = await fetch(`${API_BASE}/v1/${uuid}`, { method:"PUT", body:formData });
        const data = await res.json();
        if(data.code === "SYS0001"){
            bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
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
        const res = await fetch(`${API_BASE}/v1/${deleteProductUuid}`, { method:"DELETE" });
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

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async ()=>{
    const rawItems = await loadItemsData();
    if(rawItems && rawItems.length > 0){
        const supplierMap = new Map();
        rawItems.forEach(item=>{
            if(item.supplierUuid && !supplierMap.has(item.supplierUuid)){
                supplierMap.set(item.supplierUuid, { uuid:item.supplierUuid, name:item.supplierName });
            }
        });
        suppliers = Array.from(supplierMap.values());

        items = rawItems.map(i=>({ uuid:i.uuid, generalTerm:i.generalTerm, supplierUuid:i.supplierUuid, supplierName:i.supplierName }));
        prices = rawItems.map(i=>({ uuid:i.uuid, price:i.price }));
    }

    loadProducts();
    document.getElementById("confirmDeleteBtn")?.addEventListener("click", confirmDelete);
});

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
                <td>${p.no}</td>
                <td>${p.name}</td>
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
    document.getElementById("viewNo").value = data.no || "";
    document.getElementById("viewName").value = data.name || "";
    document.getElementById("viewDescription").value = data.description || "";
    document.getElementById("viewDimension").value = data.dimension || "";
    document.getElementById("viewUnit").value = data.unit || "";
    document.getElementById("viewCostPrice").value = data.costPrice != null ? formatNumber(data.costPrice) : "";
    document.getElementById("viewPrice").value = data.price != null ? formatNumber(data.price) : "";
    document.getElementById("viewStatus").checked = (data.status === "ACTIVE");
    document.getElementById("viewStatusStr").textContent = data.status === "ACTIVE" ? "啟用" : "停用";
    document.getElementById("viewImagesContainer").innerHTML = data.imageUrl
        ? `<img src="${data.imageUrl}" class="img-fluid rounded" style="max-height:200px;">`
        : "";

    const productListEl = document.querySelector(".view-product-item-row");
    productListEl.innerHTML = `
        <div class="d-flex gap-2 mb-2 fw-bold">
            <div class="flex-grow-1">供應商</div>
            <div class="flex-grow-1">編號</div>
            <div class="flex-grow-1">品名</div>
            <div style="width:80px;">數量</div>
            <div style="width:100px;">金額</div>
        </div>
    `;

    // 使用 items & suppliers 對應名稱
    data.items.forEach(item => {
        const supplier = suppliers.find(s => s.uuid === item.supplierUuid);
        const itemInfo = items.find(i => i.uuid === item.uuid);

        const row = document.createElement("div");
        row.className = "d-flex gap-2 mb-2";
        row.innerHTML = `
            <input type="text" class="form-control" value="${supplier ? supplier.name : ''}" placeholder="供應商名稱" disabled>
            <input type="text" class="form-control" value="${itemInfo ? itemInfo.no : ''}" placeholder="品名" disabled>
            <input type="text" class="form-control" value="${itemInfo ? itemInfo.name : ''}" placeholder="品名" disabled>
            <input type="number" class="form-control" value="${item.quantity}" placeholder="數量" disabled style="width:80px;">
            <input type="text" class="form-control text-end" value="${formatNumber(item.price)}" placeholder="價格" disabled style="width:100px;">
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
    const ids = ["createNo","createName","createDimension","createDescription","createUnit","createPrice","createImageFile"];
    ids.forEach(id => { const el = document.getElementById(id); if(el) el.value = ""; });
    document.getElementById("createImagePreview").style.display = "none";

    const container = document.getElementById("createProductItemsContainer");
    container.innerHTML = `
        <div class="d-flex gap-2 mb-2 fw-bold">
            <div class="flex-grow-1">供應商</div>
            <div class="flex-grow-1">編號</div>
            <div class="flex-grow-1">品名</div>
            <div style="width:80px;">數量</div>
            <div style="width:100px;">金額</div>
            <div style="width:35px;"></div>
        </div>
    `;

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
        <select class="form-select no-select" required></select>
        <select class="form-select item-select" required></select>
        <input type="number" class="form-control item-quantity" placeholder="數量" min="1" value="1" style="max-width:80px;" required>
        <input type="text" class="form-control item-price" placeholder="單價" readonly style="max-width:100px;">
        <button type="button" class="btn btn-outline-danger" onclick="removeProductItemRow(this)">
            <i class="bi bi-trash"></i>
        </button>
    `;
    container.appendChild(row);

    const supplierSelect = row.querySelector(".supplier-select");
    const noSelect = row.querySelector(".no-select");
    const itemSelect = row.querySelector(".item-select");
    const qtyInput = row.querySelector(".item-quantity");

    // 初始化供應商選單
    supplierSelect.innerHTML = suppliers.map(s =>
        `<option value="${s.uuid}" ${itemData?.supplierUuid === s.uuid ? "selected" : ""}>${s.name}</option>`
    ).join("");

    // 更新「編號」與「品名」
    updateNoSelect(noSelect, supplierSelect.value, itemData?.no);
    updateItemSelectByNo(itemSelect, supplierSelect.value, noSelect.value, itemData?.uuid);

    if (itemData?.quantity) qtyInput.value = itemData.quantity;

    // 綁定事件
    supplierSelect.addEventListener("change", () => {
        updateNoSelect(noSelect, supplierSelect.value);
        updateItemSelectByNo(itemSelect, supplierSelect.value, noSelect.value);
        updateItemPrice(row);
    });

    noSelect.addEventListener("change", () => {
        updateItemSelectByNo(itemSelect, supplierSelect.value, noSelect.value);
        updateItemPrice(row);
    });

    itemSelect.addEventListener("change", () => updateItemPrice(row));
    qtyInput.addEventListener("input", updateTotalPrice);

    updateItemPrice(row);
}

// 更新「編號」下拉
function updateNoSelect(noSelect, supplierUuid, selectedNo) {
    const filtered = items.filter(i => i.supplierUuid === supplierUuid);
    const distinctNos = [...new Set(filtered.map(i => i.no))];
    noSelect.innerHTML = distinctNos.length === 0
        ? `<option value="">無編號</option>`
        : distinctNos.map(no => `<option value="${no}" ${selectedNo === no ? "selected" : ""}>${no}</option>`).join("");
}

// 更新「品名」下拉（依編號）
function updateItemSelectByNo(itemSelect, supplierUuid, selectedNo, selectedItemUuid) {
    const filtered = items.filter(i => i.supplierUuid === supplierUuid && i.no === selectedNo);
    itemSelect.innerHTML = filtered.length === 0
        ? `<option value="">無品名</option>`
        : filtered.map(i => {
            const priceObj = prices.find(p => p.uuid === i.uuid);
            const price = priceObj ? priceObj.price : 0;
            const selected = selectedItemUuid && i.uuid === selectedItemUuid ? "selected" : "";
            return `<option value="${i.uuid}" data-price="${price}" ${selected}>${i.name}</option>`;
        }).join("");
}

// 更新價格與總價
function updateItemPrice(row) {
    const itemSelect = row.querySelector(".item-select");
    const priceInput = row.querySelector(".item-price");
    const selectedOption = itemSelect.options[itemSelect.selectedIndex];
    const price = parseFloat(selectedOption?.dataset.price || 0);
    priceInput.value = formatNumber(Math.round(price));
    updateTotalPrice();
}

function updateTotalPrice() {
    let total = 0;
    document.querySelectorAll("#createProductItemsContainer .create-product-item-row").forEach(row => {
        const qty = parseInt(row.querySelector(".item-quantity").value) || 0;
        const price = parseInt(unformatNumber(row.querySelector(".item-price").value)) || 0;
        total += qty * price;
    });
    const totalInput = document.getElementById("createCostPrice");
    if (totalInput) totalInput.value = formatNumber(total);
}

function removeProductItemRow(btn) {
    btn.closest(".create-product-item-row").remove();
    updateTotalPrice();
}

function getProductItemsData() {
    const container = document.getElementById("createProductItemsContainer");
    return Array.from(container.querySelectorAll(".create-product-item-row")).map(row => {
        const itemSelect = row.querySelector(".item-select");
        const noSelect = row.querySelector(".no-select");
        return {
            uuid: itemSelect.value,
            quantity: parseInt(row.querySelector(".item-quantity").value) || 0
        };
    });
}

// 儲存新增產品
async function saveNewProduct(e) {
    e.preventDefault();
    document.querySelectorAll(".item-price, .item-quantity, #createPrice").forEach(input => {
        input.value = unformatNumber(input.value);
    });

    const formData = new FormData();
    formData.append("no", document.getElementById("createNo").value.trim());
    formData.append("name", document.getElementById("createName").value.trim());
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

    // 取得產品資料
    const res = await fetch(`${API_BASE}/v1/${uuid}`);
    const data = (await res.json()).data;

    // 填入基本欄位
    document.getElementById("editUuid").value = uuid;
    document.getElementById("editNo").value = data.no || "";
    document.getElementById("editName").value = data.name || "";
    document.getElementById("editDimension").value = data.dimension || "";
    document.getElementById("editDescription").value = data.description || "";
    document.getElementById("editUnit").value = data.unit || "";
    document.getElementById("editPrice").value = data.price != null ? formatNumber(data.price) : "";
    document.getElementById("editStatus").checked = data.status === "ACTIVE";
    const preview = document.getElementById("editImagePreview");
    if (data.imageUrl) {
        preview.src = data.imageUrl;
        preview.style.display = "block";
    } else {
        preview.src = "";
        preview.style.display = "none";
    }

    // 處理品項
    const container = document.getElementById("editProductItemsContainer");
    container.innerHTML = `
        <div class="d-flex gap-2 mb-2 fw-bold">
            <div class="flex-grow-1">供應商</div>
            <div class="flex-grow-1">編號</div>
            <div class="flex-grow-1">品名</div>
            <div style="width:80px;">數量</div>
            <div style="width:100px;">金額</div>
            <div style="width:35px;"></div>
        </div>
    `;
    data.items.forEach(item => addEditProductItemRow(item));

    // 開啟 modal
    new bootstrap.Modal(document.getElementById("editModal"), { backdrop: "static", keyboard: false }).show();
}

function addEditProductItemRow(itemData) {
    const container = document.getElementById("editProductItemsContainer");
    const row = document.createElement("div");
    row.className = "d-flex gap-2 mb-1 edit-product-item-row";

    row.innerHTML = `
        <select class="form-select supplier-select" required></select>
        <select class="form-select no-select" required></select>
        <select class="form-select item-select" required></select>
        <input type="number" class="form-control item-quantity" placeholder="數量" min="1" value="1" style="max-width:80px;" required>
        <input type="text" class="form-control item-price text-end" placeholder="單價" readonly style="max-width:100px;">
        <button type="button" class="btn btn-outline-danger" onclick="removeEditProductItemRow(this)">
            <i class="bi bi-trash"></i>
        </button>
    `;
    container.appendChild(row);

    const supplierSelect = row.querySelector(".supplier-select");
    const noSelect = row.querySelector(".no-select");
    const itemSelect = row.querySelector(".item-select");
    const qtyInput = row.querySelector(".item-quantity");

    // 初始化供應商
    supplierSelect.innerHTML = suppliers.map(s =>
        `<option value="${s.uuid}" ${itemData?.supplierUuid === s.uuid ? "selected" : ""}>${s.name}</option>`
    ).join("");

    // 初始化編號 & 品名
    updateEditNoSelect(noSelect, supplierSelect.value, itemData?.no);
    updateEditItemSelectByNo(itemSelect, supplierSelect.value, noSelect.value, itemData?.uuid);

    if (itemData?.quantity) qtyInput.value = itemData.quantity;

    // 綁定事件
    supplierSelect.addEventListener("change", () => {
        updateEditNoSelect(noSelect, supplierSelect.value);
        updateEditItemSelectByNo(itemSelect, supplierSelect.value, noSelect.value);
        updateEditItemPrice(row);
    });

    noSelect.addEventListener("change", () => {
        updateEditItemSelectByNo(itemSelect, supplierSelect.value, noSelect.value);
        updateEditItemPrice(row);
    });

    itemSelect.addEventListener("change", () => updateEditItemPrice(row));
    qtyInput.addEventListener("input", updateEditTotalPrice);

    updateEditItemPrice(row);
}

// 更新「編號」下拉
function updateEditNoSelect(noSelect, supplierUuid, selectedNo) {
    const filtered = items.filter(i => i.supplierUuid === supplierUuid);
    const distinctNos = [...new Set(filtered.map(i => i.no))];
    noSelect.innerHTML = distinctNos.length === 0
        ? `<option value="">無編號</option>`
        : distinctNos.map(no => `<option value="${no}" ${selectedNo === no ? "selected" : ""}>${no}</option>`).join("");
}

// 更新「品名」下拉（依編號）
function updateEditItemSelectByNo(itemSelect, supplierUuid, selectedNo, selectedItemUuid) {
    const filtered = items.filter(i => i.supplierUuid === supplierUuid && i.no === selectedNo);
    itemSelect.innerHTML = filtered.length === 0
        ? `<option value="">無品名</option>`
        : filtered.map(i => {
            const priceObj = prices.find(p => p.uuid === i.uuid);
            const price = priceObj ? priceObj.price : 0;
            const selected = selectedItemUuid && i.uuid === selectedItemUuid ? "selected" : "";
            return `<option value="${i.uuid}" data-price="${price}" ${selected}>${i.name}</option>`;
        }).join("");
}

// 更新價格與總價
function updateEditItemPrice(row) {
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
    if (totalInput) totalInput.value = formatNumber(total);
}

function removeEditProductItemRow(btn) {
    btn.closest(".edit-product-item-row").remove();
    updateEditTotalPrice();
}

function getEditProductItemsData() {
    const container = document.getElementById("editProductItemsContainer");
    return Array.from(container.querySelectorAll(".edit-product-item-row")).map(row => {
        const itemSelect = row.querySelector(".item-select");
        const noSelect = row.querySelector(".no-select");
        return {
            uuid: itemSelect.value,
            quantity: parseInt(row.querySelector(".item-quantity").value) || 0
        };
    });
}

function previewEditImageFile(event) {
    const file = event.target.files[0];
    const preview = document.getElementById("editImagePreview");
    if (file) {
        const reader = new FileReader();
        reader.onload = e => {
            preview.src = e.target.result;
            preview.style.display = "block";
        };
        reader.readAsDataURL(file);
    } else {
        preview.src = "";
        preview.style.display = "none";
    }
}

async function saveEditProduct(e){
    e.preventDefault();
    document.querySelectorAll(".item-price, #editPrice").forEach(input => input.value = unformatNumber(input.value));

    const uuid = document.getElementById("editUuid").value;
    const formData = new FormData();
    formData.append("no", document.getElementById("editNo").value.trim());
    formData.append("name", document.getElementById("editName").value.trim());
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

        items = rawItems.map(i=>({ uuid:i.uuid, no:i.no, name:i.name, supplierUuid:i.supplierUuid, supplierName:i.supplierName }));
        prices = rawItems.map(i=>({ uuid:i.uuid, price:i.price }));
    }

    loadProducts();
    document.getElementById("confirmDeleteBtn")?.addEventListener("click", confirmDelete);
});

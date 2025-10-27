const API_BASE = `${DOMAIN}/quotation`;
let rowIndex = 0;
let products = [];
let customers = [];

// ==========================
// 客戶選擇變更
// ==========================
function onCustomerSelect(select){
    const uuid = select.value;
    const customer = customers.find(c => c.uuid === uuid);
    if(!customer) return;

    document.getElementById('customerPhone').textContent = customer.phone || '';
    document.getElementById('customerAddress').textContent = customer.address || '';
    document.getElementById('customerContact').textContent = customer.contactName || '';
}

// ==========================
// 計算該列的總金額
// ==========================
function updateRowTotal(input) {
    const tr = input.closest('tr');
    const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
    const costPrice = parseInt(tr.cells[5].textContent.replace(/,/g, '')) || 0;
    const price = parseInt(tr.cells[6].textContent.replace(/,/g, '')) || 0;

    tr.querySelector('.costTotal').textContent = formatNumber(quantity * costPrice);
    tr.querySelector('.priceTotal').textContent = formatNumber(quantity * price);

    updateTotal();
}

// ==========================
// 計算整張表總計
// ==========================
function updateTotal() {
    const tbody = document.getElementById('quotationCreateTableBody');
    let totalQuantity = 0;
    let totalCost = 0;
    let totalPrice = 0;
    let totalProfit = 0;

    tbody.querySelectorAll('tr').forEach(tr => {
        const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
        const cost = parseInt(tr.querySelector('.costTotal').textContent.replace(/,/g, '')) || 0;
        const price = parseInt(tr.querySelector('.priceTotal').textContent.replace(/,/g, '')) || 0;
        const profit = price - cost;
        totalQuantity += quantity;
        totalCost += cost;
        totalPrice += price;
        totalProfit += profit;
    });

    document.getElementById('totalQuantity').textContent = formatNumber(totalQuantity);
    document.getElementById('totalCost').textContent = formatNumber(totalCost);
    document.getElementById('totalPrice').textContent = formatNumber(totalPrice);
    document.getElementById('totalProfit').textContent = formatNumber(totalProfit);
}

// ==========================
// 初始化 Select2（No / Name 都可以搜尋）
// ==========================
function initSelect2(tr) {
    $(tr).find('select[name="productNoSelect"], select[name="productNameSelect"]').select2({
        theme: 'bootstrap-5',
        placeholder: '請選擇',
        allowClear: false, // ❌ 不要顯示 X 清除按鈕
        width: '100%'
    });
}

// ==========================
// 新增一列報價資料
// ==========================
function addQuotationRow() {
    if (products.length === 0) {
        alert("目前沒有產品資料！");
        return;
    }

    rowIndex++;
    const tbody = document.getElementById('quotationCreateTableBody');
    const firstProduct = products[0];

    // 取得唯一 No
    const uniqueNos = [...new Set(products.map(p => p.no))];
    const noOptions = uniqueNos.map(no => `<option value="${no}">${no}</option>`).join('');

    // 取得對應 Name
    const nameOptions = products
        .filter(p => p.no === firstProduct.no)
        .map(p => `<option value="${p.uuid}">${p.name}</option>`).join('');

    const tr = document.createElement('tr');
    tr.setAttribute('id', `row-${rowIndex}`);

    tr.innerHTML = `
        <input type="hidden" name="productUuid" value="${firstProduct.uuid}">
        <td style="width:12%;">
            <select name="productNoSelect" class="form-select" onchange="onProductNoChange(this)">
                ${noOptions}
            </select>
        </td>
        <td style="width:16%;">
            <select name="productNameSelect" class="form-select" onchange="onProductNameChange(this)">
                ${nameOptions}
            </select>
        </td>
        <td style="width:16%;">${firstProduct.dimension || ''}</td>
        <td style="width:8%;">${firstProduct.unit || ''}</td>
        <td style="width:8%;">
            <input type="number" name="quantity" class="form-control" value="1" min="1" onchange="updateRowTotal(this)">
        </td>
        <td style="width:8%;" class="text-success">${formatNumber(firstProduct.costPrice || 0)}</td>
        <td style="width:8%;" class="text-primary">${formatNumber(firstProduct.price || 0)}</td>
        <td style="width:8%;" class="costTotal text-success">${formatNumber(firstProduct.costPrice || 0)}</td>
        <td style="width:8%;" class="priceTotal text-primary">${formatNumber(firstProduct.price || 0)}</td>
        <td style="width:6%;">
            <button type="button" class="btn btn-sm btn-danger" onclick="removeQuotationRow(${rowIndex})">
                <i class="bi bi-trash"></i>
            </button>
        </td>
    `;

    tbody.appendChild(tr);

    tr.querySelector('select[name="productNoSelect"]').value = firstProduct.no;
    tr.querySelector('select[name="productNameSelect"]').value = firstProduct.uuid;

    // 初始化 Select2（No & Name 都可搜尋）
    initSelect2(tr);

    updateRowTotal(tr.querySelector('input[name="quantity"]'));
}

// ==========================
// 選擇 No 時更新 Name 下拉及其他欄位
// ==========================
function onProductNoChange(select) {
    const selectedNo = select.value;
    const tr = select.closest('tr');
    const filteredProducts = products.filter(p => p.no === selectedNo);

    const nameSelect = tr.querySelector('select[name="productNameSelect"]');
    nameSelect.innerHTML = filteredProducts.map(p => `<option value="${p.uuid}">${p.name}</option>`).join('');

    // 重新初始化 Name 下拉
    $(nameSelect).select2({
        theme: 'bootstrap-5',
        placeholder: '請選擇產品名稱',
        allowClear: false,
        width: '100%'
    });

    const product = filteredProducts[0];
    tr.querySelector('input[name="productUuid"]').value = product.uuid;
    tr.cells[2].textContent = product.dimension || '';
    tr.cells[3].textContent = product.unit || '';
    tr.cells[5].textContent = formatNumber(product.costPrice || 0);
    tr.cells[6].textContent = formatNumber(product.price || 0);

    updateRowTotal(tr.querySelector('input[name="quantity"]'));
}

// ==========================
// 選擇 Name 時同步 No 與其他欄位
// ==========================
function onProductNameChange(select) {
    const uuid = select.value;
    const tr = select.closest('tr');
    const product = products.find(p => p.uuid === uuid);
    if (!product) return;

    tr.querySelector('select[name="productNoSelect"]').value = product.no;

    tr.querySelector('input[name="productUuid"]').value = product.uuid;
    tr.cells[2].textContent = product.dimension || '';
    tr.cells[3].textContent = product.unit || '';
    tr.cells[5].textContent = formatNumber(product.costPrice || 0);
    tr.cells[6].textContent = formatNumber(product.price || 0);

    updateRowTotal(tr.querySelector('input[name="quantity"]'));
}

// ==========================
// 移除一列
// ==========================
function removeQuotationRow(index) {
    const row = document.getElementById(`row-${index}`);
    if (row) row.remove();
    updateTotal();
}

// ==========================
// 送出報價單
// ==========================
async function submitQuotation() {
    const customerUuid = document.getElementById("customerSelect").value;
    const remark = document.getElementById("remark").value.trim();
    const rows = document.querySelectorAll("#quotationCreateTableBody tr");

    if (rows.length === 0) {
        showToast("請至少新增一筆報價產品！", "warning");
        return;
    }

    const productsPayload = [];
    rows.forEach(tr => {
        const productUuid = tr.querySelector('input[name="productUuid"]').value;
        const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
        productsPayload.push({ productUuid, quantity });
    });

    const payload = { customerUuid, remark, products: productsPayload };

    try {
        const res = await fetch(`${API_BASE}/v1`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await res.json();

        if (data.code === "SYS0001") {
            showToast("新增成功！", "success");
            window.location.href = `${API_BASE}`;
        } else {
            showToast("新增失敗：" + data.message, "danger");
        }
    } catch (err) {
        showToast("新增失敗：" + err.message, "danger");
    }
}

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async () => {
    const rawProducts = await loadProductsData();
    if (rawProducts && rawProducts.length > 0) {
        products = rawProducts.map(p => ({
            uuid: p.uuid,
            no: p.no,
            name: p.name,
            dimension: p.dimension,
            unit: p.unit,
            costPrice: p.costPrice,
            price: p.price
        }));
        addQuotationRow();
    }

    const rawCustomers = await loadCustomersData();
    if(rawCustomers && rawCustomers.length > 0){
        customers = rawCustomers;
        const select = document.getElementById('customerSelect');
        rawCustomers.forEach(c => {
            const option = document.createElement('option');
            option.value = c.uuid;
            option.textContent = c.name;
            select.appendChild(option);
        });
        select.value = rawCustomers[0].uuid;
        onCustomerSelect(select);
    }
});

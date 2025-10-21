let rowIndex = 0;
let products = [];
let customers = [];

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async () => {
    // 產品資料
    const rawProducts = await loadProductsData();
    if (rawProducts && rawProducts.length > 0) {
        products = rawProducts.map(p => ({
            uuid: p.uuid,
            name: p.name,
            code: p.code,
            dimension: p.dimension,
            unit: p.unit,
            costPrice: p.costPrice,
            price: p.price
        }));
        addQuotationRow();
    }
    // 客戶資料
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

function onCustomerSelect(select){
    const uuid = select.value;
    const customer = customers.find(c => c.uuid === uuid);
    if(!customer) return;

    document.getElementById('customerPhone').textContent = customer.phone || '';
    document.getElementById('customerAddress').textContent = customer.address || '';
    document.getElementById('customerContact').textContent = customer.contactName || '';
}

// ==========================
// 產品選擇變更時自動帶資料
// ==========================
function onProductSelect(select) {
    const tr = select.closest('tr');
    const uuid = select.value;
    const product = products.find(p => p.uuid === uuid);
    if (!product) return;

    tr.querySelector('input[name="productUuid"]').value = product.uuid;
    tr.cells[1].textContent = product.code || '';
    tr.cells[2].textContent = product.dimension || '';
    tr.cells[3].textContent = product.unit || '';
    tr.cells[5].textContent = formatNumber(product.costPrice || 0);
    tr.cells[6].textContent = formatNumber(product.price || 0);

    updateRowTotal(tr.querySelector('input[name="quantity"]'));
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
    let totalCost = 0;
    let totalPrice = 0;

    tbody.querySelectorAll('tr').forEach(tr => {
        const cost = parseInt(tr.querySelector('.costTotal').textContent.replace(/,/g, '')) || 0;
        const price = parseInt(tr.querySelector('.priceTotal').textContent.replace(/,/g, '')) || 0;
        totalCost += cost;
        totalPrice += price;
    });

    document.getElementById('totalCost').textContent = formatNumber(totalCost);
    document.getElementById('totalPrice').textContent = formatNumber(totalPrice);
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

    const tr = document.createElement('tr');
    tr.setAttribute('id', `row-${rowIndex}`);

    tr.innerHTML = `
        <input type="hidden" name="productUuid" value="${firstProduct.uuid}">
        <td style="width:20%;">
            <select name="productSelect" class="form-select" onchange="onProductSelect(this)">
                ${products.map(p => `<option value="${p.uuid}">${p.name} - ${p.code}</option>`).join('')}
            </select>
        </td>
        <td style="width:10%;">${firstProduct.code || ''}</td>
        <td style="width:15%;">${firstProduct.dimension || ''}</td>
        <td style="width:10%;">${firstProduct.unit || ''}</td>
        <td style="width:5%;"><input type="number" name="quantity" class="form-control text-end" value="1" min="1" onchange="updateRowTotal(this)"></td>
        <td style="width:10%;">${formatNumber(firstProduct.costPrice || 0)}</td>
        <td style="width:10%;">${formatNumber(firstProduct.price || 0)}</td>
        <td style="width:10%;" class="costTotal">${formatNumber(firstProduct.costPrice || 0)}</td>
        <td style="width:10%;" class="priceTotal">${formatNumber(firstProduct.price || 0)}</td>
        <td style="width:5%;">
            <button type="button" class="btn btn-sm btn-danger" onclick="removeQuotationRow(${rowIndex})">
                <i class="bi bi-trash"></i>
            </button>
        </td>
    `;

    tbody.appendChild(tr);
    tr.querySelector('select[name="productSelect"]').value = firstProduct.uuid;
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
function submitQuotation() {
    alert("送出報價單功能尚未實作");
}
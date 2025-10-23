const API_BASE = `${DOMAIN}/quotation`;
let rowIndex = 0;
let products = [];
let customers = [];

// ==========================
// 載入報價單資料
// ==========================
async function loadQuotation() {
    try {
        const uuid = getUuidFromUrl();
        const res = await fetch(`${API_BASE}/v1/${uuid}`);
        const data = await res.json();
        return data.data || [];
    } catch (err) {
        showToast("取得報價單失敗！", "danger");
    }
}

// ==========================
// 渲染客戶資訊與備註
// ==========================
function renderCustomer(remark) {
    document.getElementById('remark').value = remark || '';
}

function renderProducts(quotationProducts) {
    const tbody = document.getElementById('quotationEditTableBody');
    tbody.innerHTML = ''; // 清空現有列
    rowIndex = 0; // 重設索引

    if (!quotationProducts || quotationProducts.length === 0) {
        addQuotationRow(); // 若無資料，至少新增一列
        return;
    }

    quotationProducts.forEach(qp => {
        rowIndex++;
        const tr = document.createElement('tr');
        tr.setAttribute('id', `row-${rowIndex}`);

        // 嘗試找出該產品的完整資料（從全域 products 陣列）
        const product = products.find(p => p.uuid === qp.uuid) || qp;

        tr.innerHTML = `
            <input type="hidden" name="productUuid" value="${product.uuid}">
            <td style="width:13%;">
                <select name="productSelect" class="form-select" onchange="onProductSelect(this)">
                    ${products.map(p => `
                        <option value="${p.uuid}" ${p.uuid === product.uuid ? 'selected' : ''}>
                            ${p.name} - ${p.code}
                        </option>`).join('')}
                </select>
            </td>
            <td style="width:8%;">${product.code || ''}</td>
            <td style="width:10%;">${product.dimension || ''}</td>
            <td style="width:8%;">${product.unit || ''}</td>
            <td style="width:8%;">
                <input type="number" name="quantity" class="form-control"
                       value="${qp.quantity || 1}" min="1" onchange="updateRowTotal(this)">
            </td>
            <td style="width:8%;" class="text-success">${formatNumber(qp.costPrice || product.costPrice || 0)}</td>
            <td style="width:8%;" class="costTotal text-success">${formatNumber((qp.quantity || 1) * (qp.costPrice || product.costPrice || 0))}</td>
            <td style="width:8%;" class="text-primary">${formatNumber(qp.price || product.price || 0)}</td>
            <td style="width:8%;" class="priceTotal text-primary">${formatNumber((qp.quantity || 1) * (qp.price || product.price || 0))}</td>
            <td style="width:8%;" class="text-secondary">${formatNumber(qp.negotiatedPrice || product.negotiatedPrice || 0)}</td>
            <td style="width:8%;" class="negotiatedPriceTotal text-secondary">${formatNumber((qp.quantity || 1) * (qp.negotiatedPrice || product.negotiatedPrice || 0))}</td>
            <td style="width:5%;">
                <button type="button" class="btn btn-sm btn-danger" onclick="removeQuotationRow(${rowIndex})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;

        tbody.appendChild(tr);
    });

    // 更新總計
    updateTotal();
}

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
    tr.cells[7].textContent = formatNumber(product.price || 0);
    tr.cells[9].textContent = formatNumber(product.price || 0);

    updateRowTotal(tr.querySelector('input[name="quantity"]'));
}

// ==========================
// 計算該列的總金額
// ==========================
function updateRowTotal(input) {
    const tr = input.closest('tr');
    const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
    const costPrice = parseInt(tr.cells[5].textContent.replace(/,/g, '')) || 0;
    const price = parseInt(tr.cells[7].textContent.replace(/,/g, '')) || 0;
    const negotiatedPrice = parseInt(tr.cells[9].textContent.replace(/,/g, '')) || 0;

    tr.querySelector('.costTotal').textContent = formatNumber(quantity * costPrice);
    tr.querySelector('.priceTotal').textContent = formatNumber(quantity * price);
    tr.querySelector('.negotiatedPriceTotal').textContent = formatNumber(quantity * negotiatedPrice);

    updateTotal();
}

// ==========================
// 計算整張表總計
// ==========================
function updateTotal() {
    const tbody = document.getElementById('quotationEditTableBody');
    let totalQuantity = 0;
    let totalCost = 0;
    let totalPrice = 0;
    let totalNegotiatedPrice = 0;
    let totalProfit = 0;

    tbody.querySelectorAll('tr').forEach(tr => {
        const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
        const cost = parseInt(tr.querySelector('.costTotal').textContent.replace(/,/g, '')) || 0;
        const price = parseInt(tr.querySelector('.priceTotal').textContent.replace(/,/g, '')) || 0;
        const negotiatedPrice = parseInt(tr.querySelector('.negotiatedPriceTotal').textContent.replace(/,/g, '')) || 0;
        const profit = price - cost;
        totalQuantity += quantity;
        totalCost += cost;
        totalPrice += price;
        totalNegotiatedPrice += negotiatedPrice;
        totalProfit += profit;
    });

    document.getElementById('totalQuantity').textContent = formatNumber(totalQuantity);
    document.getElementById('totalCost').textContent = formatNumber(totalCost);
    document.getElementById('totalPrice').textContent = formatNumber(totalPrice);
    document.getElementById('totalNegotiatedPrice').textContent = formatNumber(totalNegotiatedPrice);
    document.getElementById('totalProfit').textContent = formatNumber(totalProfit);
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
    const tbody = document.getElementById('quotationEditTableBody');
    const firstProduct = products[0];

    const tr = document.createElement('tr');
    tr.setAttribute('id', `row-${rowIndex}`);

    tr.innerHTML = `
        <input type="hidden" name="productUuid" value="${firstProduct.uuid}">
        <td style="width:13%;">
            <select name="productSelect" class="form-select" onchange="onProductSelect(this)">
                ${products.map(p => `<option value="${p.uuid}">${p.name} - ${p.code}</option>`).join('')}
            </select>
        </td>
        <td style="width:8%;">${firstProduct.code || ''}</td>
        <td style="width:10%;">${firstProduct.dimension || ''}</td>
        <td style="width:8%;">${firstProduct.unit || ''}</td>
        <td style="width:8%;"><input type="number" name="quantity" class="form-control" value="1" min="1" onchange="updateRowTotal(this)"></td>
        <td style="width:8%;" class="text-success">${formatNumber(firstProduct.costPrice || 0)}</td>
        <td style="width:8%;" class="costTotal text-success">0</td>
        <td style="width:8%;" class="text-primary">${formatNumber(firstProduct.price || 0)}</td>
        <td style="width:8%;" class="priceTotal text-primary">0</td>
        <td style="width:8%;" class="text-secondary">${formatNumber(firstProduct.negotiatedPrice || firstProduct.price)}</td>
        <td style="width:8%;" class="negotiatedPriceTotal text-secondary">0</td>
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
async function saveQuotation() {
    const customerUuid = document.getElementById("customerSelect").value;
    const remark = document.getElementById("remark").value.trim();
    const rows = document.querySelectorAll("#quotationEditTableBody tr");

    if (rows.length === 0) {
        showToast("請至少新增一筆報價產品！", "warning");
        return;
    }

    // 組出 products 陣列
    const products = [];
    rows.forEach(tr => {
        const productUuid = tr.querySelector('input[name="productUuid"]').value;
        const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
        products.push({
            productUuid,
            quantity
        });
    });

    const payload = {
        customerUuid,
        remark,
        products
    };

    try {
        const res = await fetch(`${DOMAIN}/quotation/v1/${uuid}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await res.json();

        if (data.code === "SYS0001") {
            showToast("新增成功！", "success");
            window.location.href = `${DOMAIN}/quotation/v1`;
        } else {
            showToast("新增失敗：" + data.message, "danger");
        }
    } catch (err) {
        showToast("新增失敗：" + err.message, "danger");
    }

}

function getUuidFromUrl() {
    const parts = window.location.pathname.split('/');
    return parts[parts.length - 1]; // 取最後一段就是 UUID
}

function back() {
    window.location.href = `${API_BASE}`;
}

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async () => {
    // 報價單資料
    const quotation = await loadQuotation();
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
        select.value = quotation.customer.uuid;
        onCustomerSelect(select);
        renderCustomer(quotation.remark);
        renderProducts(quotation.products);
    }
});
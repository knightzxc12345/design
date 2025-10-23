const API_BASE = `${DOMAIN}/quotation`;

// ==========================
// 計算總計
// ==========================
function updateTotal() {
    const tbody = document.getElementById('quotationCreateTableBody');
    let totalQuantity = 0;
    let totalCost = 0;
    let totalPrice = 0;
    let totalNegotiatedPrice = 0;
    let totalProfit = 0;

    tbody.querySelectorAll('tr').forEach(tr => {
        const quantity = parseInt(tr.dataset.quantity) || 0;
        const cost = parseFloat(tr.dataset.costTotal) || 0;
        const price = parseFloat(tr.dataset.priceTotal) || 0;
        const negotiatedPrice = parseInt(tr.dataset.negotiatedPriceTotal) || 0;
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
// 渲染產品明細
// ==========================
function renderProducts(products) {
    const tbody = document.getElementById('quotationCreateTableBody');
    tbody.innerHTML = '';

    products.forEach(p => {
        const costTotal = (p.costPrice || 0) * (p.quantity || 0);
        const priceTotal = (p.price || 0) * (p.quantity || 0);
        const negotiatedPriceTotal = (p.negotiatedPrice || 0) * (p.quantity || 0);

        const tr = document.createElement('tr');
        tr.dataset.quantity = p.quantity;
        tr.dataset.costTotal = costTotal;
        tr.dataset.priceTotal = priceTotal;
        tr.dataset.negotiatedPriceTotal = negotiatedPriceTotal;

        tr.innerHTML = `
            <td >${p.name}</td>
            <td>${p.code}</td>
            <td>${p.dimension}</td>
            <td>${p.unit}</td>
            <td>${p.quantity}</td>
            <td class="text-success">${formatNumber(p.costPrice)}</td>
            <td class="text-success">${formatNumber(costTotal)}</td>
            <td class="text-primary">${formatNumber(p.price)}</td>
            <td class="text-primary">${formatNumber(priceTotal)}</td>
            <td class="text-secondary">${formatNumber(p.negotiatedPrice)}</td>
            <td class="text-secondary">${formatNumber(negotiatedPriceTotal)}</td>
        `;
        tbody.appendChild(tr);
    });

    updateTotal();
}

// ==========================
// 渲染客戶資訊與備註
// ==========================
function renderCustomer(customer, remark) {
    document.getElementById('customerName').textContent = customer.name || '';
    document.getElementById('customerPhone').textContent = customer.phone || '';
    document.getElementById('customerAddress').textContent = customer.address || '';
    document.getElementById('customerContact').textContent = customer.contactName || '';
    document.getElementById('remark').textContent = remark || '';
}

// ==========================
// 載入報價單資料
// ==========================
async function loadQuotation() {
    try {
        const uuid = getUuidFromUrl();
        const res = await fetch(`${API_BASE}/v1/${uuid}`);
        const data = await res.json();

        if (data.code === 'SYS0001' && data.data) {
            const quotation = data.data;
            renderCustomer(quotation.customer, quotation.remark);
            renderProducts(quotation.products || []);
        } else {
            showToast("查無報價單！", "warning");
        }
    } catch (err) {
        showToast("取得報價單失敗！", "danger");
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
document.addEventListener('DOMContentLoaded', () => {
    loadQuotation();
});

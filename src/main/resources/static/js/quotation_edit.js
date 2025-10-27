const API_BASE = `${DOMAIN}/quotation`;
let rowIndex = 0;
let products = [];
let customers = [];

// ==========================
// 取得 URL 上的報價單 UUID
// ==========================
function getUuidFromUrl() {
    const parts = window.location.pathname.split('/');
    return parts[parts.length - 1];
}

// ==========================
// 載入報價單資料
// ==========================
async function loadQuotation() {
    try {
        const uuid = getUuidFromUrl();
        const res = await fetch(`${API_BASE}/v1/${uuid}`);
        const data = await res.json();
        return data.data || {};
    } catch (err) {
        showToast("取得報價單失敗！", "danger");
        return {};
    }
}

// ==========================
// 客戶資訊顯示
// ==========================
function onCustomerSelect(select) {
    const uuid = select.value;
    const customer = customers.find(c => c.uuid === uuid);
    if (!customer) return;

    document.getElementById('customerPhone').textContent = customer.phone || '';
    document.getElementById('customerAddress').textContent = customer.address || '';
    document.getElementById('customerContact').textContent = customer.contactName || '';
}

// ==========================
// 初始化 Select2（No / Name）
// ==========================
function initSelect2(tr) {
    $(tr).find('select[name="productNoSelect"], select[name="productNameSelect"]').select2({
        theme: 'bootstrap-5',
        placeholder: '請選擇',
        allowClear: false,
        width: '100%'
    });
}

// ==========================
// 渲染報價產品資料（含議價）
// ==========================
function renderProducts(quotationProducts) {
    const tbody = document.getElementById('quotationEditTableBody');
    tbody.innerHTML = '';
    rowIndex = 0;

    if (!quotationProducts || quotationProducts.length === 0) {
        addQuotationRow();
        return;
    }

    quotationProducts.forEach(qp => {
        rowIndex++;
        const tr = document.createElement('tr');
        tr.setAttribute('id', `row-${rowIndex}`);

        const product = products.find(p => p.uuid === qp.productUuid) || qp;
        const uniqueNos = [...new Set(products.map(p => p.no))];
        const noOptions = uniqueNos.map(no => `<option value="${no}" ${no === product.no ? 'selected' : ''}>${no}</option>`).join('');
        const nameOptions = products
            .filter(p => p.no === product.no)
            .map(p => `<option value="${p.uuid}" ${p.uuid === product.uuid ? 'selected' : ''}>${p.name}</option>`).join('');

        tr.innerHTML = `
            <input type="hidden" name="productUuid" value="${product.uuid}">
            <td style="width:10%;">
                <select name="productNoSelect" class="form-select" onchange="onProductNoChange(this)">
                    ${noOptions}
                </select>
            </td>
            <td style="width:15%;">
                <select name="productNameSelect" class="form-select" onchange="onProductNameChange(this)">
                    ${nameOptions}
                </select>
            </td>
            <td style="width:10%;">${product.dimension || ''}</td>
            <td style="width:5%;">${product.unit || ''}</td>
            <td style="width:7%;">
                <input type="number" name="quantity" class="form-control" value="${qp.quantity || 1}" min="1" onchange="updateRowTotal(this)">
            </td>
            <td style="width:7%;" class="text-success">${formatNumber(product.costPrice || 0)}</td>
            <td style="width:7%;" class="text-primary">${formatNumber(product.price || 0)}</td>
            <td style="width:7%;" class="costTotal text-success">${formatNumber((qp.quantity || 1) * (product.costPrice || 0))}</td>
            <td style="width:7%;" class="priceTotal text-primary">${formatNumber((qp.quantity || 1) * (product.price || 0))}</td>
            <td style="width:12%;">
                <input type="number" name="negotiatedPrice" class="form-control" value="${qp.negotiatedPrice || product.price || 0}" onchange="updateRowTotal(this)">
            </td>
            <td style="width:7%;" class="negotiatedPriceTotal text-secondary">${formatNumber((qp.quantity || 1) * (qp.negotiatedPrice || product.price || 0))}</td>
            <td style="width:6%;">
                <button type="button" class="btn btn-sm btn-danger" onclick="removeQuotationRow(${rowIndex})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;

        tbody.appendChild(tr);
        initSelect2(tr);
    });

    updateTotal();
}

// ==========================
// 選擇 No 時更新 Name 下拉及欄位
// ==========================
function onProductNoChange(select) {
    const selectedNo = select.value;
    const tr = select.closest('tr');
    const filteredProducts = products.filter(p => p.no === selectedNo);

    const nameSelect = tr.querySelector('select[name="productNameSelect"]');
    nameSelect.innerHTML = filteredProducts.map(p => `<option value="${p.uuid}">${p.name}</option>`).join('');
    $(nameSelect).select2({ theme:'bootstrap-5', allowClear:false, width:'100%' });

    const product = filteredProducts[0];
    tr.querySelector('input[name="productUuid"]').value = product.uuid;
    tr.cells[2].textContent = product.dimension || '';
    tr.cells[3].textContent = product.unit || '';
    tr.cells[5].textContent = formatNumber(product.costPrice || 0);
    tr.cells[6].textContent = formatNumber(product.price || 0);

    const negotiatedInput = tr.querySelector('input[name="negotiatedPrice"]');
    if (negotiatedInput) negotiatedInput.value = product.price || 0;

    updateRowTotal(tr.querySelector('input[name="quantity"]'));
}

// ==========================
// 選擇 Name 時同步 No 與欄位
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

    const negotiatedInput = tr.querySelector('input[name="negotiatedPrice"]');
    if (negotiatedInput) negotiatedInput.value = product.price || 0;

    updateRowTotal(tr.querySelector('input[name="quantity"]'));
}

// ==========================
// 計算列總金額（含議價）
// ==========================
function updateRowTotal(input) {
    const tr = input.closest('tr');
    const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
    const costPrice = parseInt(tr.cells[5].textContent.replace(/,/g,'')) || 0;
    const price = parseInt(tr.cells[6].textContent.replace(/,/g,'')) || 0;
    const negotiatedPrice = parseInt(tr.querySelector('input[name="negotiatedPrice"]').value) || 0;

    tr.querySelector('.costTotal').textContent = formatNumber(quantity * costPrice);
    tr.querySelector('.priceTotal').textContent = formatNumber(quantity * price);
    tr.querySelector('.negotiatedPriceTotal').textContent = formatNumber(quantity * negotiatedPrice);

    updateTotal();
}

// ==========================
// 計算總計
// ==========================
function updateTotal() {
    const tbody = document.getElementById('quotationEditTableBody');
    let totalQuantity=0, totalCost=0, totalPrice=0, totalNegotiated=0, totalProfit=0;

    tbody.querySelectorAll('tr').forEach(tr=>{
        const quantity = parseInt(tr.querySelector('input[name="quantity"]').value) || 0;
        const cost = parseInt(tr.querySelector('.costTotal').textContent.replace(/,/g,'')) || 0;
        const price = parseInt(tr.querySelector('.priceTotal').textContent.replace(/,/g,'')) || 0;
        const negotiated = parseInt(tr.querySelector('.negotiatedPriceTotal').textContent.replace(/,/g,'')) || 0;
        totalQuantity += quantity;
        totalCost += cost;
        totalPrice += price;
        totalNegotiated += negotiated;
        totalProfit += negotiated - cost;
    });

    document.getElementById('totalQuantity').textContent = formatNumber(totalQuantity);
    document.getElementById('totalCost').textContent = formatNumber(totalCost);
    document.getElementById('totalPrice').textContent = formatNumber(totalPrice);
    document.getElementById('totalNegotiatedPrice').textContent = formatNumber(totalNegotiated);
    document.getElementById('totalProfit').textContent = formatNumber(totalProfit);
}

// ==========================
// 新增列
// ==========================
function addQuotationRow() {
    if(products.length===0){ alert("目前沒有產品資料！"); return; }

    rowIndex++;
    const tbody = document.getElementById('quotationEditTableBody');
    const firstProduct = products[0];
    const uniqueNos = [...new Set(products.map(p=>p.no))];
    const noOptions = uniqueNos.map(no=>`<option value="${no}">${no}</option>`).join('');
    const nameOptions = products.filter(p=>p.no===firstProduct.no).map(p=>`<option value="${p.uuid}">${p.name}</option>`).join('');

    const tr = document.createElement('tr');
    tr.setAttribute('id',`row-${rowIndex}`);
    tr.innerHTML=`
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
        <td style="width:8%;">
            <input type="number" name="negotiatedPrice" class="form-control" value="${firstProduct.price || 0}" onchange="updateRowTotal(this)">
        </td>
        <td style="width:8%;" class="negotiatedPriceTotal text-secondary">${formatNumber(firstProduct.price || 0)}</td>
        <td style="width:6%;">
            <button type="button" class="btn btn-sm btn-danger" onclick="removeQuotationRow(${rowIndex})">
                <i class="bi bi-trash"></i>
            </button>
        </td>
    `;
    tbody.appendChild(tr);
    tr.querySelector('select[name="productNoSelect"]').value = firstProduct.no;
    tr.querySelector('select[name="productNameSelect"]').value = firstProduct.uuid;
    initSelect2(tr);
    updateRowTotal(tr.querySelector('input[name="quantity"]'));
}

// ==========================
// 移除列
// ==========================
function removeQuotationRow(index){
    const row=document.getElementById(`row-${index}`);
    if(row) row.remove();
    updateTotal();
}

// ==========================
// 儲存報價單
// ==========================
async function saveQuotation() {
    const uuid = getUuidFromUrl();
    const customerUuid = document.getElementById("customerSelect").value;
    const remark = document.getElementById("remark").value.trim();
    const rows = document.querySelectorAll("#quotationEditTableBody tr");

    if(rows.length===0){ showToast("請至少新增一筆報價產品！","warning"); return; }

    const productsPayload = [];
    rows.forEach(tr=>{
        const productUuid = tr.querySelector('input[name="productUuid"]').value;
        const quantity = parseInt(tr.querySelector('input[name="quantity"]').value)||0;
        const negotiatedPrice = parseInt(tr.querySelector('input[name="negotiatedPrice"]').value)||0;
        productsPayload.push({ productUuid, quantity, negotiatedPrice });
    });

    const payload = { customerUuid, remark, products: productsPayload };

    try{
        const res = await fetch(`${API_BASE}/v1/${uuid}`,{
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(payload)
        });
        const data = await res.json();
        if(data.code==="SYS0001"){
            showToast("修改成功！","success");
            window.location.href = `${API_BASE}`;
        }else{
            showToast("修改失敗："+data.message,"danger");
        }
    }catch(err){
        showToast("修改失敗："+err.message,"danger");
    }
}

// ==========================
// 返回按鈕
// ==========================
function back() {
    window.location.href = `${API_BASE}`;
}

// ==========================
// 初始化
// ==========================
document.addEventListener("DOMContentLoaded", async () => {
    const quotation = await loadQuotation();

    const rawProducts = await loadProductsData();
    if(rawProducts && rawProducts.length>0){
        products = rawProducts.map(p=>({
            uuid:p.uuid,
            no:p.no,
            name:p.name,
            dimension:p.dimension,
            unit:p.unit,
            costPrice:p.costPrice,
            price:p.price
        }));
    }

    const rawCustomers = await loadCustomersData();
    if(rawCustomers && rawCustomers.length>0){
        customers = rawCustomers;
        const select=document.getElementById('customerSelect');
        rawCustomers.forEach(c=>{
            const option=document.createElement('option');
            option.value=c.uuid;
            option.textContent=c.name;
            select.appendChild(option);
        });
        select.value=quotation.customer?.uuid || rawCustomers[0].uuid;
        onCustomerSelect(select);
    }

    document.getElementById('remark').value = quotation.remark || '';
    renderProducts(quotation.products);
});

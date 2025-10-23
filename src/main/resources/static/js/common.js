const DOMAIN = "http://localhost:8787/design";

function showToast(message, type = "success") {
    const toastEl = document.getElementById("liveToast");
    const toastBody = document.getElementById("toastBody");
    toastBody.innerText = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;
    const toast = new bootstrap.Toast(toastEl, { delay: 3000 });
    toast.show();
}

function renderPagination(pageInfo, onPageClick) {
    const ul = document.getElementById("pagination");
    ul.innerHTML = ""; // 先清空

    if (!pageInfo || typeof onPageClick !== "function") return;

    const totalPages = pageInfo.totalPage || 0;
    const currentPage = pageInfo.page || 0;

    if (totalPages <= 1) return;

    // ← 上一頁
    const liPrev = document.createElement("li");
    liPrev.className = `page-item ${currentPage === 0 ? "disabled" : ""}`;
    const btnPrev = document.createElement("button");
    btnPrev.className = "page-link";
    btnPrev.innerHTML = "&lt;";
    btnPrev.onclick = () => currentPage > 0 && onPageClick(currentPage - 1);
    liPrev.appendChild(btnPrev);
    ul.appendChild(liPrev);

    // 分頁按鈕
    for (let i = 0; i < totalPages; i++) {
        const li = document.createElement("li");
        li.className = `page-item ${i === currentPage ? "active" : ""}`;
        const btn = document.createElement("button");
        btn.className = "page-link";
        btn.innerText = i + 1;
        btn.onclick = () => onPageClick(i);
        li.appendChild(btn);
        ul.appendChild(li);
    }

    // → 下一頁
    const liNext = document.createElement("li");
    liNext.className = `page-item ${currentPage === totalPages - 1 ? "disabled" : ""}`;
    const btnNext = document.createElement("button");
    btnNext.className = "page-link";
    btnNext.innerHTML = "&gt;";
    btnNext.onclick = () => currentPage < totalPages - 1 && onPageClick(currentPage + 1);
    liNext.appendChild(btnNext);
    ul.appendChild(liNext);
}

async function loadCustomersData() {
    const res = await fetch(`${DOMAIN}/customer/v1`);
    const json = await res.json();
    return json.data || [];
}

async function loadCustomersData() {
    const res = await fetch(`${DOMAIN}/customer/v1`);
    const json = await res.json();
    return json.data || [];
}

async function loadSuppliersData() {
    const res = await fetch(`${DOMAIN}/supplier/v1`);
    const json = await res.json();
    return json.data || [];
}

async function loadItemsData() {
    const res = await fetch(`${DOMAIN}/item/v1`);
    const json = await res.json();
    return json.data || [];
}

async function loadProductsData() {
    const res = await fetch(`${DOMAIN}/product/v1`);
    const json = await res.json();
    return json.data || [];
}

// 加上千分位
function formatNumber(num) {
    if (isNaN(num)) return "";
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 移除千分位
function unformatNumber(str) {
    return str ? str.toString().replace(/,/g, "") : "";
}

function clearStorage(){
    sessionStorage.removeItem('quotationListState');
}

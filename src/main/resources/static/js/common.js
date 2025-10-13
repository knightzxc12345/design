const DOMAIN = "http://localhost:8787/design";

function showToast(message, type = "success") {
    const toastEl = document.getElementById("liveToast");
    const toastBody = document.getElementById("toastBody");
    toastBody.innerText = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;
    const toast = new bootstrap.Toast(toastEl, { delay: 3000 });
    toast.show();
}

function renderPagination(containerId, pageInfo, currentPage, onPageClick) {
    const pagination = document.getElementById(containerId);
    pagination.innerHTML = "";

    const totalPages = Math.ceil((pageInfo.total || 0) / (pageInfo.size || 10));

    if (totalPages <= 1) return;

    const wrapper = document.createElement("div");
    wrapper.className = "text-center pt-2";

    const nav = document.createElement("nav");
    nav.className = "v-pagination v-theme--light";
    nav.setAttribute("role", "navigation");
    nav.setAttribute("aria-label", "Pagination Navigation");

    const ul = document.createElement("ul");
    ul.className = "v-pagination__list";

    const liPrev = document.createElement("li");
    liPrev.className = "v-pagination__prev";
    const btnPrev = document.createElement("button");
    btnPrev.type = "button";
    btnPrev.className = "v-btn v-btn--icon v-theme--light rounded-circle v-btn--variant-text";
    btnPrev.disabled = currentPage === 0;
    btnPrev.innerHTML = `<span class="v-btn__content"><i class="mdi-chevron-left mdi"></i></span>`;
    btnPrev.onclick = () => currentPage > 0 && onPageClick(currentPage - 1);
    liPrev.appendChild(btnPrev);
    ul.appendChild(liPrev);

    for (let i = 0; i < totalPages; i++) {
        const li = document.createElement("li");
        li.className = `v-pagination__item ${i === currentPage ? "v-pagination__item--is-active" : ""}`;
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "v-btn v-btn--icon v-theme--light rounded-circle v-btn--variant-text";
        if (i === currentPage) btn.disabled = true;
        btn.setAttribute("aria-label", `Page ${i + 1}${i === currentPage ? ", Current page" : ""}`);
        btn.innerHTML = `<span class="v-btn__content">${i + 1}</span>`;
        btn.onclick = () => onPageClick(i);
        li.appendChild(btn);
        ul.appendChild(li);
    }

    const liNext = document.createElement("li");
    liNext.className = "v-pagination__next";
    const btnNext = document.createElement("button");
    btnNext.type = "button";
    btnNext.className = "v-btn v-btn--icon v-theme--light rounded-circle v-btn--variant-text";
    btnNext.disabled = currentPage === totalPages - 1;
    btnNext.innerHTML = `<span class="v-btn__content"><i class="mdi-chevron-right mdi"></i></span>`;
    btnNext.onclick = () => currentPage < totalPages - 1 && onPageClick(currentPage + 1);
    liNext.appendChild(btnNext);
    ul.appendChild(liNext);

    nav.appendChild(ul);
    wrapper.appendChild(nav);
    pagination.appendChild(wrapper);
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

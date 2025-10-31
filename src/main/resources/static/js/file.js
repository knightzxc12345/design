// ==========================
// 全域設定
// ==========================
let currentPage = 0;
let pageSize = 100;
const API_BASE = `${DOMAIN}/file`;

// ==========================
// 搜尋 / 分頁功能
// ==========================
async function loadFolderFiles(folderUuid, page = 0, size = 30) {
    const container = document.getElementById('fileCardsContainer');
    container.innerHTML = '';

    // Ajax 取得後端分頁資料
    const res = await fetch(`/api/files?parentUuid=${folderUuid}&page=${page}&size=${size}`);
    const data = await res.json();

    data.responses.forEach(folder => {
        folder.files.forEach(f => {
            const col = document.createElement('div');
            col.classList.add('col-md-4', 'mb-3');

            const card = document.createElement('div');
            card.classList.add('card');

            const img = document.createElement('img');
            img.classList.add('card-img-top', 'img-thumbnail');
            img.src = f.imageUrl;
            img.onclick = () => showImageModal(f.imageUrl);

            const body = document.createElement('div');
            body.classList.add('card-body');

            const title = document.createElement('h5');
            title.classList.add('card-title');
            title.innerText = f.tag;

            const remark = document.createElement('p');
            remark.classList.add('card-text');
            remark.innerText = f.remark;

            body.appendChild(title);
            body.appendChild(remark);
            card.appendChild(img);
            card.appendChild(body);
            col.appendChild(card);
            container.appendChild(col);
        });
    });
}
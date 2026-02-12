function showAdminMenu(selected) {
    const contentArea = document.querySelector(".adminContent");
    if (selected.id === "viewOrder") {
        hideMenuBth();

        if(order_list.length === 0) {
            contentArea.innerHTML = `<p> 주문 내역이 없습니다 ! </p>`;
            return;
        }
        contentArea.innerHTML = makeTableShowOrders();
    }
    if(selected.id === "manage") {
        showMenuBtn();
        contentArea.innerHTML = makeTableShowMenus();
    }
}

function makeTableShowMenus() {
    let tableHtml = `
        <table class = "admin-table menus">
            <thead>
                <tr>
                    <th>메뉴번호</th>
                    <th>메뉴이름</th>
                    <th>가격</th>
                    <th>분류</th>
                    <th>사진</th>
                </tr>
            </thead>
            <tbody>`;
    menu_list.forEach(menu => {
        tableHtml += `
                <tr>
                    <td>${menu.id}</td>
                    <td>${menu.menuName}</td>
                    <td>${menu.price.toLocaleString()}원</td>
                    <td>${menu.category}</td>
                    <td><img src="${menu.menuImg}"></td>
                </tr>`;
        // console.log(tableHtml);
        });
    tableHtml += `</tbody></table>`;
    return tableHtml;
}

function makeTableShowOrders() {
    let tableHtml = `
        <table class="admin-table orders">
            <thead>
                <tr>
                    <th>주문번호</th>
                    <th>주문내역</th>
                    <th>총 금액</th>
                    <th>상태</th>
                    <th>주문시간</th>
                </tr>
            </thead>
            <tbody>`;
        
    let sum = 0;
    order_list.forEach(order => {
        sum += (order.status === "결제완료") ? order.price_total : 0;
        const orderList = Object.values(order.orderMap)
            .map(item => `
                ${item.orderedName}
                (${item.amount}개):
                ${item.orderedPrice*item.amount}원`).join("<br> ");
        
        tableHtml += `<tr>
            <td>${order.id}</td>
            <td class="text-left">${orderList}</td>
            <td>${order.price_total.toLocaleString()}원</td>
            <td><span class="status-${order.status}">${order.status}<br>${order.pay_option}</span></td>
            <td>${order.created_at.replace("T", " ")}</td></tr>`;});
    tableHtml += `<tr>
        <td colspan=2><strong>합 계</strong></td>
        <td>${sum.toLocaleString()}원</td>
        <td colspan=2></td>
    </tbody></table>`;
    
    return tableHtml;
}

function hideMenuBth() {
    let add = document.getElementById("addMenu");
    let upd = document.getElementById("updateMenu");
    let del = document.getElementById("deleteMenu");

    add.classList.add("hidden");
    upd.classList.add("hidden");
    del.classList.add("hidden");
}

function showMenuBtn() {
    let add = document.getElementById("addMenu");
    let upd = document.getElementById("updateMenu");
    let del = document.getElementById("deleteMenu");

    add.classList.remove("hidden");
    upd.classList.remove("hidden");
    del.classList.remove("hidden");

}
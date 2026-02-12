function showAdminMenu(selected) {
    const contentArea = document.querySelector(".adminContent");
    if (selected.id === "viewOrder") {
        if(order_list.length === 0) {
            contentArea.innerHTML = `<p> 주문 내역이 없습니다 ! </p>`;
            return;
        }

        let tableHtml = `
            <table class="admin-table">
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
        
        order_list.forEach(order => {
            const orderList = Object.values(order.orderMap)
                            .map(item => `
                                ${item.orderedName}
                                (${item.amount}개):
                                ${item.orderedPrice*item.amount}원`)
                                .join("<br> ");
            tableHtml += `
                <tr>
                    <td>${order.id}</td>
                    <td class="text-left">${orderList}</td>
                    <td>${order.price_total.toLocaleString()}원</td>
                    <td><span class="status-${order.status}">${order.status}</span></td>
                    <td>${order.created_at.replace("T", " ")}</td>
                </tr>
            `;
        });
        tableHtml += `</tbody></table>`
        contentArea.innerHTML = tableHtml;
    }
}
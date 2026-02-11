document.addEventListener("DOMContentLoaded", function () {

    const orderTotalSpan = document.getElementById("order-total");
    const discountSpan = document.getElementById("discount");
    const finalTotalSpan = document.getElementById("final-total");

    function calculateTotal() {
        let total = 0;

        document.querySelectorAll(".order-item").forEach(function (item) {
            const name = item.querySelector(".menu-name").innerText;
            const count = parseInt(item.querySelector(".count").innerText);
            total += priceList[name] * count;
        });

        let discount = 0;
        orderTotalSpan.innerText = total.toLocaleString() + "원";
        discountSpan.innerText = "-" + discount.toLocaleString() + "원";
        finalTotalSpan.innerText = (total - discount).toLocaleString() + "원";
    }

    document.querySelectorAll(".order-item").forEach(function (item) {

        const menuName = item.querySelector(".menu-name").innerText;
        const minusBtn = item.querySelector(".minus");
        const plusBtn = item.querySelector(".plus");
        const countSpan = item.querySelector(".count");
        const priceSpan = item.querySelector(".price");

        const unitPrice = priceList[menuName];

        function updatePrice() {
            const count = parseInt(countSpan.innerText);
            const total = unitPrice * count;
            priceSpan.innerText = total.toLocaleString() + "원";
            calculateTotal(); // 🔥 총합 계산
        }

        plusBtn.addEventListener("click", function () {
            countSpan.innerText = parseInt(countSpan.innerText) + 1;
            updatePrice();
        });

        minusBtn.addEventListener("click", function () {
            let current = parseInt(countSpan.innerText);
            if (current > 0) {
                countSpan.innerText = current - 1;
                updatePrice();
            }
        });

    });

});

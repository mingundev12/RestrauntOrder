

function showMenuListByCategory(selected) {
    let pTag = selected.querySelector("p");

    if(!pTag) return;

    let categoryName = pTag.innerText.replace(/\u00a0/g, "").trim();

    const menuListUl = document.querySelector(".menuList");

    menuListUl.innerHTML = "";

    const filteredMenu = menu_list.filter(menu => menu.category === categoryName);

    if (filteredMenu.length === 0) {
        menuListUl.innerHTML = `
            <li class="menu">
                <div class=\"info\">
                    <img src="./resources/img/coming_soon.png">
                    <p>준비 중입니다.</p>
                </div>
            </li>`;
    } else {
        filteredMenu.forEach(menu => {
            const li = document.createElement("li");
            li.classList.add("menu");
            li.classList.add("menuId_" + menu.id);
            li.innerHTML = `
                <div class="info">
                    <img src=${menu.menuImg}>
                    <p><strong>${menu.menuName}</strong>
                    <br>${menu.price}원</p>
                </div>`;
            
            li.querySelector(".info").addEventListener('click', () => {
                // alert("선택된 메뉴: " + menu.menuName + " (ID: " + menu.id + ")");
                const modal = document.querySelector("#modalWrap");

                modal.querySelector(".menuName").innerText = menu.menuName;
                modal.querySelector("img").src = menu.menuImg;
                modal.querySelector("p").innerText = menu.price + "원";

                modal.classList.remove("hide");

                let close = document.querySelector(".close");

                close.addEventListener("click", hideModal);
            });

            menuListUl.appendChild(li);
        });
    }
}

function hideModal() {
    let modal = document.querySelector("#modalWrap");
    
    modal.classList.add("hide");
}
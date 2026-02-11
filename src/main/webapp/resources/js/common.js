

window.onload = function() {
    const categoryList = document.querySelectorAll(".category");

    for (let category of categoryList) {
        category.addEventListener('click', function(){
            highlight(this);
        });
    }
	
	if(categoryList.length > 0) {
		categoryList[0].click();
	}
}

function highlight(selected) {
    const categoryList = document.querySelectorAll(".category");

    for (let category of categoryList) {
        category.classList.remove("highlighted");
    }
    selected.classList.add("highlighted");

	if(typeof showMenuListByCategory === "function") {
    	showMenuListByCategory(selected);
	}
}
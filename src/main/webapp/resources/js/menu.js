

window.onload = function() {
    const categoryList = document.querySelectorAll(".category");

    for (let category of categoryList) {
        category.addEventListener('click', function(){
            highlight(category);
        });
    }
}

function highlight(selected) {
    const categoryList = document.querySelectorAll(".category");

    for (let category of categoryList) {
        category.classList.remove("highlighted");
    }
    selected.classList.add("highlighted");
}
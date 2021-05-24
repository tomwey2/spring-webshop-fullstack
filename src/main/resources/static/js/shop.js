let sortDirection = false;

let testProducts = [
    {"name": "Test Product 1", "unitsInStock": 30, "unitPrice": 10.50  },
    {"name": "Test Product 2", "unitsInStock": 10, "unitPrice": 6.50  },
];

let testProductCategories = [
    {"name": "Test Category 1" },
    {"name": "Test Category 2" },
];

// Listener
document.addEventListener("DOMContentLoaded", loadFromBackend);
document.getElementById("loadTestProducts").addEventListener("click", loadTestProducts);
document.getElementById("loadTestCategories").addEventListener("click", loadTestProductCategories);

document.getElementById("productCategories").addEventListener("click",function(e) {
    // https://davidwalsh.name/event-delegate
    // e.target is our targetted element.
    if(e.target && e.target.nodeName == "LI") {
        loadProductsOfCategoryFromBackend(e.target.id)
    }
});

function loadFromBackend() {
    loadProductsFromBackend();
    loadProductCategoriesFromBackend();
}

function loadTestProducts() {
    buildProducts(testProducts)
}

function loadTestProductCategories() {
    buildProductCategories(testProductCategories)
}

function loadProductsFromBackend() {
    fetch("http://192.168.2.117:8080/webshop-0.0.1-SNAPSHOT/products")
        .then(function (response) {
            return response.json();
        })
        .then(function (responseAsJson) {
            console.log(responseAsJson);
            buildProducts(responseAsJson);
        })
};

function loadProductsOfCategoryFromBackend(id) {
    categoryId = Number(id);
    fetch(`http://192.168.2.117:8080/webshop-0.0.1-SNAPSHOT/products?category_id=${categoryId}`)
        .then(function (response) {
            return response.json();
        })
        .then(function (responseAsJson) {
            console.log(responseAsJson);
            buildProducts(responseAsJson);
        })
}

function formatCurrency(number) {
    return number.toLocaleString('de-DE', {style: 'currency', currency: 'EUR',});
}

function buildProducts(data) {
    let table = document.getElementById('products');
    table.innerHTML = ""
    for (let product of data) {
        table.innerHTML += 
        `<tr>
            <td>${product.name}</td>
            <td style="text-align: center;">${product.unitsInStock}</td>
            <td style="text-align: right;">${formatCurrency(product.unitPrice)}</td>
            <td style="text-align: center;"><button class="w3-button">add to cart</button></td>
        </tr>`;
    }
}

function loadProductCategoriesFromBackend() {
    fetch("http://192.168.2.117:8080/webshop-0.0.1-SNAPSHOT/product_categories")
        .then(function (response) {
            return response.json();
        })
        .then(function (responseAsJson) {
            console.log(responseAsJson);
            buildProductCategories(responseAsJson);
        })
};

function buildProductCategories(data) {
    let table = document.getElementById('productCategories');
    table.innerHTML = `<li id="0">Show all</li>`
    for (let category of data) {
        table.innerHTML += `<li id="${category.id}">${category.name}</li>`
    }
}

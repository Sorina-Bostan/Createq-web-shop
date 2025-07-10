$(document).ready(function() {

    const contentContainer = $('#product-list-container');
    const body = $('body');
    let cart = JSON.parse(localStorage.getItem('webshopCart')) || [];

    // events
    body.on('click', '.category-link', function(event) {
        event.preventDefault();
        const categoryId = $(this).data('category-id');
        loadProducts(categoryId);
    });

    body.on('click', '.product-link', function(event) {
        event.preventDefault();
        const productId = $(this).data('product-id');
        loadProductDetails(productId);
    });
    body.on('click', '.add-to-cart-btn', function() {
        const productId = $(this).data('product-id');
        const productName = $(this).data('product-name');
        const productPrice = parseFloat($(this).data('product-price'));

        addToCart(productId, productName, productPrice);
    });
    body.on('click', '#delete-cart', function(){
        localStorage.removeItem('webshopCart');
        cart = [];
        updateCartSummary();
        alert("The cart is empty!");
    });
    body.on('click', '#all-products-link', function(event) {
        event.preventDefault();
        const url = "/products";
        contentContainer.html('<p>Loading...</p>');
        contentContainer.load(url, function(response, status, xhr) {
            if (status !== "success") {
                contentContainer.html("<p>Error.</p>");
            }
        });
    });

    // functions
    function loadProducts(categoryId) {
        contentContainer.html('<p>Loading the products...</p>');
        const url = `/products/category/${categoryId}`;

        contentContainer.load(url, function(response, status, xhr) {
            if (status !== "success") {
                console.error("Error loading the products:", xhr.status, xhr.statusText);
                contentContainer.html("<p>Error loading the products.</p>");
            }
        });
    }

    function loadProductDetails(productId) {
        contentContainer.html('<p>Loading the details...</p>');
        const url = `/products/${productId}`;

        contentContainer.load(url, function(response, status, xhr) {
            if (status !== "success") {
                console.error("Error loading the products:", xhr.status, xhr.statusText);
                contentContainer.html("<p>Error loading the products.</p>");
            }
        });
    }
    function updateCartSummary() {
        let itemCount = 0;
        let totalPrice = 0;

        cart.forEach(item => {
            itemCount += item.quantity;
            totalPrice += item.price * item.quantity;
        });

        $('#cart-item-count').text(itemCount);
        $('#cart-total-price').text(totalPrice.toFixed(2));
    }

    function saveCart() {
        localStorage.setItem('webshopCart', JSON.stringify(cart));
    }

    function addToCart(productId, productName, productPrice) {
        const existingItem = cart.find(item => item.id === productId);

        if (existingItem) {
            existingItem.quantity++;
        } else {
            cart.push({
                id: productId,
                name: productName,
                price: productPrice,
                quantity: 1
            });
        }
        saveCart();
        updateCartSummary();
        alert(`"${productName}" was added to cart!`);
    }

});
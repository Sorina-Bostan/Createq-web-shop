$(document).ready(function() {

    const contentContainer = $('#product-list-container');
    const body = $('body');
    let cart = JSON.parse(localStorage.getItem('webshopCart')) || [];
    let bannerTimeout;
    let productToRemoveId = null;
    let currentProductList = [];

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

    // for cart page
    body.on('click', '.add-to-cart-btn', function() {
        const productData = {
            id: $(this).data('product-id'),
            name: $(this).data('product-name'),
            price: parseFloat($(this).data('product-price')),
            imageUrl: $(this).data('product-image-url'),
            stock: parseInt($(this).data('product-stock'))
        };
        addToCart(productData, 1);
    });

    // for details page
    body.on('click', '#add-to-cart-button', function() {
        const quantityToAdd = parseInt($('#pdp-quantity-display').text()) || 1;
        const productData = {
            id: $(this).data('product-id'),
            name: $(this).data('product-name'),
            price: parseFloat($(this).data('product-price')),
            imageUrl: $(this).data('product-image-url'),
            stock: parseInt($(this).data('product-stock'))
        };
        addToCart(productData, quantityToAdd);
    });

    body.on('click', '#clear-cart-btn', function(){
        cart = [];
        saveCart();
        updateCartSummary();
        loadCartPage();
        alert("The cart has been emptied!");
    });
    body.on('click', '.remove-item-btn', function() {
        productToRemoveId = parseInt($(this).data('product-id'));
        $('#confirm-modal-overlay').addClass('show');
    });
    body.on('click', '#modal-confirm-btn', function() {
        if (productToRemoveId !== null) {
            cart = cart.filter(item => item.id !== productToRemoveId);
            saveCart();
            updateCartSummary();
            loadCartPage();
            productToRemoveId = null;
            $('#confirm-modal-overlay').removeClass('show');
        }
    });
    body.on('click', '#modal-cancel-btn', function() {
        productToRemoveId = null;
        $('#confirm-modal-overlay').removeClass('show');
    });
    body.on('click', '#confirm-modal-overlay', function(event) {
        if (event.target.id === 'confirm-modal-overlay') {
            productToRemoveId = null;
            $(this).removeClass('show');
        }
    });
    body.on('click', '#all-products-link', function(event) {
        event.preventDefault();
        loadAllProducts();
    });
    $('body').on('click', '#back-to-list-btn', function() {
        history.back();
    });
    $('body').on('click', '#view-cart-link', function(event) {
        event.preventDefault();
        loadCartPage();
    });

    //for cart page
    body.on('click', '.cart-item-row .increase-btn', function() { updateQuantityInCart($(this).data('product-id'), 1); });
    body.on('click', '.cart-item-row .decrease-btn', function() { updateQuantityInCart($(this).data('product-id'), -1); });

    // for details page
    body.on('click', '.product-details-info .increase-btn', function() { updateQuantityOnPDP(1); });
    body.on('click', '.product-details-info .decrease-btn', function() { updateQuantityOnPDP(-1); });
    $(window).on('popstate', function(event) {
        routePage();
    });

    body.on('change', '#sort-select', function() { sortAndRenderProducts($(this).val()); });
    body.on('click', '#sort-button', function() { sortAndRenderProducts($('#sort-select').val()); });

    // functions

    //loading the products
    function fetchAndDisplayProducts(apiUrl) {
        contentContainer.html('<p>Loading...</p>');
        $.ajax({
            url: apiUrl,
            type: 'GET',
            dataType: 'json',
            success: function(products) {
                currentProductList = products;
                renderProductPage(products);
            },
            error: function() {
                contentContainer.html('<p>Error loading products.</p>');
            }
        });
    }
    function loadProducts(categoryId) {
        $('.forVideo').hide();
        const pageUrl = `/products/category/${categoryId}`;
        const apiUrl = `/api/products/category/${categoryId}`;
        history.pushState({ view: 'category', id: categoryId }, `Category ${categoryId}`, pageUrl);
        fetchAndDisplayProducts(apiUrl);
    }

    function loadAllProducts() {
            const pageUrl = "/products";
            const apiUrl = "/api/products";

            history.pushState({ view: 'allProducts' }, "All Products", pageUrl);
            fetchAndDisplayProducts(apiUrl);
    }
    function renderProductPage(products) {
        const sortingHtml = `
            <div id="sorting-controls">
                <label for="sort-select">Sort by:</label>
                <select id="sort-select">
                    <option value="default">Default</option>
                    <option value="price-asc">Price (Low to High)</option>
                    <option value="price-desc">Price (High to Low)</option>
                </select>
            </div>
            <br>
            <div class="product-gallery"></div>
        `;
        contentContainer.html(sortingHtml);
        renderProductGallery(products);
    }

    //loading the product details
    function loadProductDetails(productId) {
        $('.forVideo').hide();
        const pageUrl = `/products/${productId}`;
        const apiUrl = `/api/products/${productId}`;

        history.pushState({ view: 'product', id: productId }, `Product ${productId}`, pageUrl);
        contentContainer.html('<p>Loading...</p>');
        contentContainer.load(apiUrl, function(response, status, xhr) {
            if (status !== "success") {
                console.error("Error loading the products:", xhr.status, xhr.statusText);
                contentContainer.html("<p>Error loading the products.</p>");
            }
        });
    }

    // cart
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

    function addToCart(productData, quantity) {
        const quantityToAdd = quantity || 1;
        const existingItem = cart.find(item => item.id === productData.id);
        const currentQuantityInCart = existingItem ? existingItem.quantity : 0;
        if (currentQuantityInCart + quantityToAdd > productData.stock) {
            alert(`Sorry, only ${productData.stock} items are available.`);
            return;
        }

        if (existingItem) {
            existingItem.quantity += quantityToAdd;
        } else {
            cart.push({
                id: productData.id, name: productData.name, price: productData.price,
                imageUrl: productData.imageUrl, stock: productData.stock, quantity: quantityToAdd
            });
        }
        saveCart();
        updateCartSummary();
        showBanner(`${quantityToAdd} x "${productData.name}" was added to cart!`);
    }
    function showBanner(message) {
        const banner = $('#cart-banner');
        const bannerMessage = $('#banner-message');
        bannerMessage.text(message);
        banner.addClass('show');
        clearTimeout(bannerTimeout);
        bannerTimeout = setTimeout(function() { banner.removeClass('show'); }, 3000);
    }
    function updateQuantityOnPDP(change) {
        const display = $('#pdp-quantity-display');
        if (display.length === 0) return;

        const maxStock = parseInt($('.product-details-info .quantity-adjuster').data('stock'));
        let currentValue = parseInt(display.text());
        let newValue = currentValue + change;

        if (newValue >= 1 && newValue <= maxStock) {
            display.text(newValue);
        }
    }
    function updateQuantityInCart(productId, change) {
        const itemToUpdate = cart.find(item => item.id === productId);
        if (!itemToUpdate) return;

        let newQuantity = itemToUpdate.quantity + change;
        if (change > 0 && newQuantity > itemToUpdate.stock) {
            alert(`Sorry, only ${itemToUpdate.stock} items are available.`);
            return;
        }
        if (newQuantity <= 0) {
            showRemoveConfirm(productId);
        } else {
            itemToUpdate.quantity = newQuantity;
            saveCart();
            updateCartSummary();
            loadCartPage();
        }
    }


    function loadCartPage() {
        $('.forVideo').hide();

        const pageUrl = "/cart";
        const apiUrl = "api/cart";
        history.pushState({ view: 'cart' }, "Shopping Cart", pageUrl);

        contentContainer.html('<p>Loading cart...</p>');
        contentContainer.load(apiUrl, function(response, status, xhr) {
            if (status !== "success") {
                contentContainer.html("<p>An error occurred while loading the cart.</p>");
                return;
            }
            const cartItemsContainer = $('#cart-items-container');
            const template = $('#cart-item-template').html();
            if (!template) {
                console.error("Cart item template not found!");
                return;
            }
            cartItemsContainer.empty();

            let totalPrice = 0;
            let totalItems = 0;

            cart.forEach(item => {
                const itemRow = $(template);
                const imageUrl = `/static/images/${item.imageUrl}`;
                itemRow.find('.cart-item-name').text(item.name);
                itemRow.find('.cart-item-price').text(item.price.toFixed(2) + ' RON');
                itemRow.find('.quantity-display').text(item.quantity);
                itemRow.find('.cart-item-total').text((item.price * item.quantity).toFixed(2) + ' RON');
                itemRow.find('.cart-item-image').attr('src', imageUrl).attr('alt', item.name);
                itemRow.find('.remove-item-btn').attr('data-product-id', item.id);
                itemRow.find('.quantity-btn').attr('data-product-id', item.id);
                cartItemsContainer.append(itemRow);

                totalItems += item.quantity;
                totalPrice += item.price * item.quantity;
            });

            $('#cart-item-count-summary').text(totalItems);
            $('#cart-total-price-summary').text(totalPrice.toFixed(2));
        });
    }

    // this function is used to synchronize the page content with the new URL
    function routePage() {
        const path = window.location.pathname;
        let categoryMatch = path.match(/^\/products\/category\/(\d+)$/);
        if (categoryMatch) {
            loadProducts(parseInt(categoryMatch[1]));
            return;
        }
        let productMatch = path.match(/^\/products\/(\d+)$/);
        if (productMatch) {
            loadProductDetails(parseInt(productMatch[1]));
            return;
        }
        if (path === '/cart') {
            loadCartPage();
            return;
        }
        loadAllProducts();
    }

    //sort

    function sortAndRenderProducts(sortType) {
        let sortedProducts = [...currentProductList];
        if (sortType === 'price-asc') {
            sortedProducts.sort((a, b) => a.price - b.price);
        } else if (sortType === 'price-desc') {
            sortedProducts.sort((a, b) => b.price - a.price);
        }
        renderProductGallery(sortedProducts);
    }


    function renderProductGallery(products) {
        const gallery = $('.product-gallery');
        gallery.empty();

        if (!products || products.length === 0) {
            gallery.html('<p>No products found.</p>');
            return;
        }

        products.forEach(product => {
            const productCardHtml = `
                <a href="#" class="product-link" data-product-id="${product.id}">
                    <div class="product-card">
                        <div class="product-image-container">
                            <img src="/static/images/${product.imageUrl}" alt="${product.name}">
                        </div>
                        <div class="product-info">
                            <h3 class="product-name">${product.name}</h3>
                            <p class="product-price">${product.price.toFixed(2)} RON</p>
                        </div>
                        <button class="add-to-cart-btn"
                                data-product-id="${product.id}"
                                data-product-name="${product.name}"
                                data-product-price="${product.price}"
                                data-product-image-url="${product.imageUrl}">
                            Add to cart
                        </button>
                    </div>
                </a>
            `;
            gallery.append(productCardHtml);
        });
    }
    function showRemoveConfirm(productId) { productToRemoveId = productId; $('#confirm-modal-overlay').addClass('show');}
    updateCartSummary()
    routePage();
});
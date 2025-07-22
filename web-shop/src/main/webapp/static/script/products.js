let currentProductList = [];

export function loadAllProducts(container) {
    $('.forVideo').show();
    const pageUrl = "/products";
    const apiUrl = "/api/products";
    history.pushState({ view: 'allProducts' }, "All Products", pageUrl);
    fetchAndDisplayProducts(apiUrl, container);
}

export function loadProducts(categoryId, container) {
    $('.forVideo').hide();
    const pageUrl = `/products/category/${categoryId}`;
    const apiUrl = `/api/products/category/${categoryId}`;
    history.pushState({ view: 'category', id: categoryId }, `Category ${categoryId}`, pageUrl);
    fetchAndDisplayProducts(apiUrl, container);
}

export function loadProductDetails(productId, container) {
    $('.forVideo').hide();
    const pageUrl = `/products/${productId}`;
    const apiUrl = `/api/products/${productId}`;
    history.pushState({ view: 'product', id: productId }, `Product ${productId}`, pageUrl);
    container.html('<p>Loading...</p>');
    container.load(apiUrl);
}

export function sortAndRenderProducts(sortType) {
    let sortedProducts = [...currentProductList];
    if (sortType === 'price-asc') sortedProducts.sort((a, b) => a.price - b.price);
    else if (sortType === 'price-desc') sortedProducts.sort((a, b) => b.price - a.price);
    renderProductGallery(sortedProducts);
}

export function updateQuantityOnPDP(change) {
    const display = $('#pdp-quantity-display');
    if (display.length === 0) return;
    const maxStock = parseInt($('.product-details-info .quantity-adjuster').data('stock'));
    let currentValue = parseInt(display.text());
    let newValue = currentValue + change;
    if (newValue >= 1 && newValue <= maxStock) {
        display.text(newValue);
    }
}

function fetchAndDisplayProducts(apiUrl, container) {
    container.html('<p>Loading...</p>');
    $.getJSON(apiUrl)
        .done(function(products) {
            currentProductList = products;
            renderProductPage(products, container);
        })
        .fail(function() { container.html('<p>Error loading products.</p>'); });
}

function renderProductPage(products, container) {
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
    container.html(sortingHtml);
    renderProductGallery(products);
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
                            data-product-image-url="${product.imageUrl}"
                            data-product-stock="${product.stockQuantity}">
                        Add to cart
                    </button>
                </div>
            </a>
        `;
        gallery.append(productCardHtml);
    });
}
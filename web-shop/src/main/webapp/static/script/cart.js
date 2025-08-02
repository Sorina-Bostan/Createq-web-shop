export let cart = JSON.parse(localStorage.getItem('webshopCart')) || [];

function saveCart() {
    localStorage.setItem('webshopCart', JSON.stringify(cart));
}

export function addToCart(productData, quantity) {
    const quantityToAdd = quantity || 1;
    const existingItem = cart.find(item => item.id === productData.id);
    const currentQuantityInCart = existingItem ? existingItem.quantity : 0;

    if (currentQuantityInCart + quantityToAdd > productData.stock) {
        showBanner(`Sorry, only ${productData.stock} items are available.`, 'error',3000);
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
    showBanner(`${quantityToAdd} x "${productData.name}" was added to cart!`,3000);
}

export function mergeLocalCartToServer() {
    return new Promise((resolve, reject) => {
        const localCart = JSON.parse(localStorage.getItem('webshopCart')) || [];
        if (localCart.length === 0) {
            resolve();
            return;
        }
        const itemsToMerge = localCart.map(item => ({
            productId: item.id,
            quantity: item.quantity
        }));

        $.ajax({
            url: '/api/cart/merge',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(itemsToMerge),
            xhrFields: {
                withCredentials: true
            },
            success: function(response) {
                cart.length = 0;
                saveCart();
                resolve(response);
            },
            error: function(xhr) {
                reject(xhr);
            }
        });
    });
}


export function updateCartSummary() {
    let itemCount = 0;
    cart.forEach(item => { itemCount += item.quantity; });
    $('#cart-item-count').text(itemCount);
}

export function loadCartPage(container) {
    $('.forVideo').hide();
    history.pushState({ view: 'cart' }, "Shopping Cart", "/cart");
    container.load('/api/cart/view', function() {
        populateCartPage();
        if (!window.isAuthenticated) {
            $('#auth-prompt-box').show();
        }
    });
}

export function updateQuantityInCart(itemId, change, container) {
    const itemToUpdate = cart.find(item => item.id === itemId);
    if (!itemToUpdate) return;
    const newQuantity = itemToUpdate.quantity + change;

    if (change > 0 && newQuantity > itemToUpdate.stock) {
        showBanner(`Sorry, only ${itemToUpdate.stock} items are available.`, 'error',3000);
        return;
    }
    if (newQuantity <= 0) {
        removeItemFromCart(itemId);
    } else {
        itemToUpdate.quantity = newQuantity;
        saveCart();
    }
    updateCartSummary();
    loadCartPage(container);
}
export function removeItemFromCart(itemId) {
    cart = cart.filter(item => item.id !== itemId);
    saveCart();
    updateCartSummary();
}

export function clearCart() {
    cart.length = 0;
    saveCart();
    updateCartSummary();
}

function populateCartPage() {
    const cartItemsContainer = $('#cart-items-container');
    const template = $('#cart-item-template').html();
    if (!template) return;
    cartItemsContainer.empty();
    let totalPrice = 0;
    let totalItems = 0;

    cart.forEach(item => {
        const itemRow = $(template);
        itemRow.find('.cart-item-name').text(item.name);
        itemRow.find('.cart-item-price').text(item.price.toFixed(2) + ' RON');
        itemRow.find('.quantity-display').text(item.quantity);
        itemRow.find('.cart-item-total').text((item.price * item.quantity).toFixed(2) + ' RON');
        itemRow.find('.cart-item-image').attr('src', `/static/images/${item.imageUrl}`).attr('alt', item.name);
        itemRow.find('.remove-item-btn, .quantity-btn').attr('data-product-id', item.id);
        itemRow.find('.product-link').attr('data-product-id', item.id);
        cartItemsContainer.append(itemRow);
        totalItems += item.quantity;
        totalPrice += item.price * item.quantity;
    });

    $('#cart-item-count-summary').text(totalItems);
    $('#cart-total-price-summary').text(totalPrice.toFixed(2) + ' RON');
}

export function showBanner(message, type = 'success',timeout) {
    const banner = $('#cart-banner');
    const bannerMessage = $('#banner-message');
    banner.removeClass('error success').addClass(type);
    bannerMessage.text(message);
    banner.addClass('show');
    clearTimeout(window.bannerTimeout);
    window.bannerTimeout = setTimeout(() => { banner.removeClass('show'); }, timeout);
}
function updateCartSummaryFromServer(cartDto) {
    let itemCount = 0;
    if (cartDto && cartDto.items) {
        cartDto.items.forEach(item => { itemCount += item.quantity; });
    }
    $('#cart-item-count').text(itemCount);
}
function populateCartPageFromServer(cartDto) {
    const cartItemsContainer = $('#cart-items-container');
    const template = $('#cart-item-template').html();
    if (!template) return;
    cartItemsContainer.empty();
    let totalPrice = 0;
    let totalItems = 0;

    if (cartDto && cartDto.items) {
        cartDto.items.forEach(item => {
            const itemRow = $(template);
            itemRow.find('.cart-item-name').text(item.product.name);
            itemRow.find('.cart-item-price').text(item.product.price.toFixed(2) + ' RON');
            itemRow.find('.quantity-display').attr('data-item-id', item.id).text(item.quantity);
            itemRow.find('.cart-item-total').text((item.product.price * item.quantity).toFixed(2) + ' RON');
            itemRow.find('.cart-item-image').attr('src', `/static/images/${item.product.imageUrl}`).attr('alt', item.product.name);
            itemRow.find('.remove-item-btn, .quantity-btn').attr('data-product-id', item.id);
            itemRow.find('.product-link').attr('data-product-id', item.product.id);
            cartItemsContainer.append(itemRow);
            totalItems += item.quantity;
            totalPrice += item.product.price * item.quantity;
        });
    }

    $('#cart-item-count-summary').text(totalItems);
    $('#cart-total-price-summary').text(totalPrice.toFixed(2) + ' RON');
}
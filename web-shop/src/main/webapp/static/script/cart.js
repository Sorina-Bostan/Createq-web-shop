export let cart = JSON.parse(localStorage.getItem('webshopCart')) || [];

function saveCart() {
    localStorage.setItem('webshopCart', JSON.stringify(cart));
}

export function addToCart(productData, quantity) {
    const quantityToAdd = quantity || 1;
    const existingItem = cart.find(item => item.id === productData.id);
    const currentQuantityInCart = existingItem ? existingItem.quantity : 0;

    if (currentQuantityInCart + quantityToAdd > productData.stock) {
        const remainingStock = productData.stock - currentQuantityInCart;
        let message = (remainingStock > 0)
            ? `Sorry, you can only add ${remainingStock} more item(s).`
            : "Sorry, no more items are available.";
        showBanner(message, 'error',3000);
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
    showBanner(`${quantityToAdd} x "${productData.name}" was added to cart!`, 'success',3000);
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
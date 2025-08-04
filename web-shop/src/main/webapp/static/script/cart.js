export let cart = JSON.parse(localStorage.getItem('webshopCart')) || [];

function saveCart() {
    localStorage.setItem('webshopCart', JSON.stringify(cart));
}
export function addToCart(productData, quantity) {
    if (window.isAuthenticated) {
        addToServerCart(productData, quantity);
    } else {
        addToLocalCart(productData, quantity);
    }
}
function addToLocalCart(productData, quantity) {
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
        showBanner(`${quantity} x "${productData.name}" was added to cart!`, 'success', 3000);
    }


function addToServerCart(productData, quantity) {
    const quantityToAdd = quantity || 1;

    $.ajax({
        url: '/api/cart/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ productId: productData.id, quantity: quantityToAdd }),
        success: function() {
            showBanner(`${quantityToAdd} x "${productData.name}" was added to cart!`, 'success', 3000);
            fetchAndUpdateCartSummary();
        },
        error: function(xhr) {
            let errorMessage = 'Could not add the item to your cart.';
            try {
                const errorResponse = JSON.parse(xhr.responseText);
                if (errorResponse && typeof errorResponse.remainingStock !== 'undefined') {
                    const remainingStock = errorResponse.remainingStock;

                    if (remainingStock > 0) {
                        errorMessage = `Sorry, you can only add ${remainingStock} more item(s).`;
                    } else {
                        errorMessage = "Sorry, no more items are available.";
                    }
                } else if (errorResponse && errorResponse.message) {
                    errorMessage = errorResponse.message;
                }
            } catch (e) {
                if (xhr.responseText) {
                    errorMessage = xhr.responseText;
                }
                console.error("Could not parse error response as JSON:", e);
            }

            showBanner(errorMessage, 'error', 4000);
        }
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
        if (!window.isAuthenticated) {
            populateCartPage();
            $('#auth-prompt-box').show();
        }
    });
}

export function updateQuantityInCart(productId, change, container) {
    if (window.isAuthenticated) {
        const row = $(`button[data-product-id='${productId}']`).closest('.cart-item-row');
        const quantitySpan = row.find('.quantity-display');
        const currentQuantity = parseInt(quantitySpan.text());

        const newQuantity = currentQuantity + change;

        if (newQuantity <= 0) {
            removeItemFromCart(productId, container);
        } else {
            $.ajax({
                url: `/api/cart/update/${productId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ newQuantity: newQuantity }),
                success: function() {
                    console.log(`Quantity for product ${productId} updated to ${newQuantity}.`);
                    loadCartPage(container);
                    fetchAndUpdateCartSummary();
                },
                error: function(xhr) {
                    // noinspection JSUnresolvedReference
                    showBanner(xhr.responseJSON?.message || 'Could not update quantity.', 'error', 4000);
                }
            });
        }
    } else {
        const itemToUpdate = cart.find(item => item.id === productId);
        if (!itemToUpdate) return;

        const newQuantity = itemToUpdate.quantity + change;

        if (change > 0 && newQuantity > itemToUpdate.stock) {
            showBanner(`Sorry, only ${itemToUpdate.stock} items are available.`, 'error', 3000);
            return;
        }

        if (newQuantity <= 0) {
            removeItemFromCart(productId, container);
        } else {
            itemToUpdate.quantity = newQuantity;
            saveCart();
            updateCartSummary();
            loadCartPage(container);
        }
    }
}
export function removeItemFromCart(productId, container) {
    if (window.isAuthenticated) {
        $.ajax({
            url: `/api/cart/remove/${productId}`,
            type: 'DELETE',
            success: function() {
                showBanner('Item removed from cart.', 'success', 3000);
                if (window.location.pathname === '/cart') {
                    loadCartPage(container);
                }
                fetchAndUpdateCartSummary();
            },
            error: () => showBanner('Could not remove item.', 'error')
        });
    } else {
        cart = cart.filter(item => item.id !== productId);
        saveCart();
        updateCartSummary();
        if (window.location.pathname === '/cart') {
            loadCartPage(container);
        }
    }
}
export function clearCart(container) {
    if (window.isAuthenticated) {
        $.ajax({
            url: '/api/cart/clear',
            type: 'DELETE',
            success: function() {
                showBanner('Cart has been cleared.', 'success', 3000);
                loadCartPage(container);
                fetchAndUpdateCartSummary();
            },
            error: () => showBanner('Could not clear the cart.', 'error')
        });
    } else {
        cart.length = 0;
        saveCart();
        updateCartSummary();
        loadCartPage(container);
    }
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
export function mergeLocalCartWithServer() {
    const localCart = cart;

    if (localCart && localCart.length > 0) {
        const itemsToMerge = localCart.map(item => ({
            productId: item.id,
            quantity: item.quantity
        }));

        console.log("Merging cart...", itemsToMerge);

        $.ajax({
            url: '/api/cart/merge',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(itemsToMerge),
            success: function() {
                console.log("Cart merged successfully!");
                clearLocalCartOnly();
                showBanner('Your local cart has been merged!', 'success', 3000);
                fetchAndUpdateCartSummary();
            },
            error: function(xhr) {
                console.error("Failed to merge cart:", xhr.responseText);
                showBanner('Could not merge your shopping cart.', 'error', 5000);
            }
        });
    }
}
export function fetchAndUpdateCartSummary() {
    if (!window.isAuthenticated) {
        updateCartSummary();
        return;
    }
    $.ajax({
        url: '/api/cart/summary',
        type: 'GET',
        success: function(summary) {
            // noinspection JSUnresolvedReference
            $('#cart-item-count').text(summary.itemCount);
        }
    });
}
function clearLocalCartOnly() {
    cart.length = 0;
    saveCart();
    updateCartSummary();
}
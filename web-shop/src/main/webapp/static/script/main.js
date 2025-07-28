import * as cartService from './cart.js';
import * as productService from './products.js';

$(document).ready(function() {

    const contentContainer = $('#product-list-container');
    const body = $('body');
    let productToRemoveId = null;
    let actionToConfirm = null;

    body.on('click', '.category-link', function(e) { e.preventDefault(); productService.loadProducts($(this).data('category-id'), contentContainer); });
    body.on('click', '.product-link', function(e) { e.preventDefault(); productService.loadProductDetails($(this).data('product-id'), contentContainer); });
    body.on('click', '#all-products-link', function(e) { e.preventDefault(); productService.loadAllProducts(contentContainer); });
    body.on('click', '#view-cart-link', function(e) { e.preventDefault(); cartService.loadCartPage(contentContainer); });
    body.on('click', '#back-to-list-btn', function() { history.back(); });

    // add to cart
    body.on('click', '.add-to-cart-btn', function() {
        const productData = {
            id: $(this).data('product-id'),
            name: $(this).data('product-name'),
            price: parseFloat($(this).data('product-price')),
            imageUrl: $(this).data('product-image-url'),
            stock: parseInt($(this).data('product-stock'))
        };
        cartService.addToCart(productData, 1);
    });

    body.on('click', '#add-to-cart-button', function() {
        const quantityToAdd = parseInt($('#pdp-quantity-display').text()) || 1;
        const productData = {
            id: $(this).data('product-id'),
            name: $(this).data('product-name'),
            price: parseFloat($(this).data('product-price')),
            imageUrl: $(this).data('product-image-url'),
            stock: parseInt($(this).data('product-stock'))
        };
        cartService.addToCart(productData, quantityToAdd);
    });

    // cart actions
    body.on('click', '#clear-cart-btn', function() {
        actionToConfirm = 'clear-cart';
        $('#modal-title').text('Empty Cart');
        $('#modal-message').text('Are you sure you want to remove all items from your cart?');
        $('#modal-confirm-btn').text('Yes, Empty Cart');
        $('#confirm-modal-overlay').addClass('show');
    });
    body.on('click', '.remove-item-btn', function() {
        actionToConfirm = 'remove-item';
        productToRemoveId = parseInt($(this).data('product-id'));
        $('#modal-title').text('Remove Item');
        $('#modal-message').text('Are you sure you want to remove this item from your cart?');
        $('#modal-confirm-btn').text('Yes, Remove');
        $('#confirm-modal-overlay').addClass('show');
    });
    body.on('click', '#modal-confirm-btn', function() {
        if (actionToConfirm === 'clear-cart') {
            cartService.clearCart();
            cartService.loadCartPage(contentContainer);
        }
        else if (actionToConfirm === 'remove-item' && productToRemoveId !== null) {
            cartService.removeItemFromCart(productToRemoveId);
            cartService.loadCartPage(contentContainer);
        }
        productToRemoveId = null;
        actionToConfirm = null;
        $('#confirm-modal-overlay').removeClass('show');
    });
    body.on('click', '#modal-cancel-btn', function() {
        productToRemoveId = null;
        actionToConfirm = null;
        $('#confirm-modal-overlay').removeClass('show');
    });

    // quantity
    body.on('click', '.cart-item-row .increase-btn', function() { cartService.updateQuantityInCart($(this).data('product-id'), 1, contentContainer); });
    body.on('click', '.cart-item-row .decrease-btn', function() { cartService.updateQuantityInCart($(this).data('product-id'), -1, contentContainer); });
    body.on('click', '.product-details-info .increase-btn', function() { productService.updateQuantityOnPDP(1); });
    body.on('click', '.product-details-info .decrease-btn', function() { productService.updateQuantityOnPDP(-1); });

    // sort
    body.on('change', '#sort-select', function() { productService.sortAndRenderProducts($(this).val()); });

    $(window).on('popstate', function() { routePage(); });

    function routePage() {
        const path = window.location.pathname;
        let categoryMatch = path.match(/^\/products\/category\/(\d+)$/);
        let productMatch = path.match(/^\/products\/(\d+)$/);

        if (categoryMatch) {
            productService.loadProducts(parseInt(categoryMatch[1]), contentContainer);
            return;
        }
        if (productMatch) {
            productService.loadProductDetails(parseInt(productMatch[1]), contentContainer);
            return;
        }
        if (path === '/cart') {
            cartService.loadCartPage(contentContainer);
            return;
        }
        productService.loadAllProducts(contentContainer);
    }

    cartService.updateCartSummary();
    routePage();
});
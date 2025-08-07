import * as cartService from './cart.js';
import * as productService from './products.js';
import * as authService from './auth.js';
import * as adminService from './admin.js';

$(document).ready(function() {

    const contentContainer = $('#product-list-container');
    const body = $('body');
    window.productToRemoveId = null;
    window.actionToConfirm = null;
    window.userToRemoveId = null;
    window.userToUpdateId = null;
    window.roleSelectElement = null;

    body.on('click', '.category-link', function (e) {
        e.preventDefault();
        productService.loadProducts($(this).data('category-id'), contentContainer);
    });
    body.on('click', '.product-link', function (e) {
        e.preventDefault();
        productService.loadProductDetails($(this).data('product-id'), contentContainer);
    });
    body.on('click', '#all-products-link', function (e) {
        e.preventDefault();
        productService.loadAllProducts(contentContainer);
    });
    body.on('click', '#view-cart-link', function (e) {
        e.preventDefault();
        cartService.loadCartPage(contentContainer);
    });
    body.on('click', '#back-to-list-btn', function () {
        history.back();
    });

    // add to cart
    body.on('click', '.add-to-cart-btn', function () {
        const productData = {
            id: $(this).data('product-id'),
            name: $(this).data('product-name'),
            price: parseFloat($(this).data('product-price')),
            imageUrl: $(this).data('product-image-url'),
            stock: parseInt($(this).data('product-stock'))
        };
        cartService.addToCart(productData, 1);
    });

    body.on('click', '#add-to-cart-button', function () {
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
    body.on('click', '#clear-cart-btn', function () {
        window.actionToConfirm = 'clear-cart';
        $('#modal-title').text('Empty Cart');
        $('#modal-message').text('Are you sure you want to remove all items from your cart?');
        $('#modal-confirm-btn').text('Yes, Empty Cart');
        $('#confirm-modal-overlay').addClass('show');
    });
    body.on('click', '.remove-item-btn', function () {
        window.actionToConfirm = 'remove-item';
        window.productToRemoveId = parseInt($(this).data('product-id'));
        $('#modal-title').text('Remove Item');
        $('#modal-message').text('Are you sure you want to remove this item from your cart?');
        $('#modal-confirm-btn').text('Yes, Remove');
        $('#confirm-modal-overlay').addClass('show');
    });
    body.on('click', '#modal-confirm-btn', function () {
        const contentContainer = $('#product-list-container');
        if (window.actionToConfirm === 'clear-cart') {
            cartService.clearCart(contentContainer);
        } else if (window.actionToConfirm === 'remove-item' && window.productToRemoveId !== null) {
            cartService.removeItemFromCart(window.productToRemoveId, contentContainer);
        }
        else if (window.actionToConfirm === 'delete-product' && window.productToRemoveId !== null) {
            adminService.deleteProductById(window.productToRemoveId);
        }
        else if (window.actionToConfirm === 'delete-user' && window.userToRemoveId !== null) {
            adminService.deleteUserById(window.userToRemoveId);
        }
        else if (window.actionToConfirm === 'update-role' && window.userToUpdateId !== null) {
            const select = window.roleSelectElement;
            const newRole = select.val();
            adminService.updateUserRole(window.userToUpdateId, newRole, select);
        }
        window.productToRemoveId = null;
        window.userToRemoveId = null;
        window.userToUpdateId = null;
        window.roleSelectElement = null;
        window.actionToConfirm = null;
        $('#confirm-modal-overlay').removeClass('show');
    });
    body.on('click', '#modal-cancel-btn', function () {
        if (window.actionToConfirm === 'update-role' && window.roleSelectElement) {
            const select = window.roleSelectElement;
            select.val(select.data('previous-role'));
        }
        window.productToRemoveId = null;
        window.userToRemoveId = null;
        window.userToUpdateId = null;
        window.roleSelectElement = null;
        window.actionToConfirm = null;
        $('#confirm-modal-overlay').removeClass('show');
    });

    //login actions
    body.on('click', '#login-link', function (e) {
        e.preventDefault();
        authService.loadLoginPage(contentContainer);
    });
    body.on('click', '#login-link-from-cart', function (e) {
        e.preventDefault();
        authService.loadLoginPage(contentContainer);
    });

    body.on('submit', '#login-form', function(e) {
        e.preventDefault();
        const loginData = { username: $('#username').val(), password: $('#password').val() };
        const errorContainer = $('#login-error-container');
        errorContainer.hide();

        $.ajax({
            url: '/api/auth/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(loginData),

            success: function(response) {
                cartService.showBanner('Welcome, ' + response.username + '!', 'welcome', 2000);
                if (JSON.parse(localStorage.getItem('webshopCart'))?.length > 0) {
                    cartService.mergeLocalCartWithServer();
                }
                setTimeout(() => {
                    window.location.reload();
                }, 1500);
            },
            error: function(xhr) {
                const errorMessage = xhr.responseText || "An unknown error occurred.";
                errorContainer.text(errorMessage);
                errorContainer.show();
            }
        });
    });
    body.on('click','#go-to-login',function(e){
        e.preventDefault();
        authService.loadLoginPage(contentContainer);
    })

    //register actions
    body.on('click', '#go-to-register', function(e) {
        e.preventDefault();
        authService.loadRegisterPage(contentContainer);
    });
    body.on('click','#register-link-from-cart', function(e) {
        e.preventDefault();
        authService.loadRegisterPage(contentContainer);
    });
    body.on('submit', '#register-form', function(e) {
        e.preventDefault();

        if (authService.validateAndSubmitRegistration()) {
            const registerData = {
                firstName: $('#reg-firstname').val(),
                lastName: $('#reg-lastname').val(),
                email: $('#reg-email').val(),
                username: $('#reg-username').val(),
                password: $('#reg-password').val()
            };

            $.ajax({
                url: '/api/auth/register',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(registerData),
                success: function(response) {
                    alert(response);
                    authService.loadLoginPage(contentContainer);
                },
                error: function(xhr) {
                    let errorMessage = "An unknown server error occurred. Please try again later.";
                    // noinspection JSUnresolvedVariable
                    if (xhr.responseJSON && xhr.responseJSON.message) {
                        // noinspection JSUnresolvedVariable
                        errorMessage = xhr.responseJSON.message;
                    }
                    else if (xhr.responseText) {
                        errorMessage = xhr.responseText;
                    }
                    $('#register-error-message').text(errorMessage).show();
                }
            });
        }
    });

    //admin actions
    body.on('click', '#admin-panel-link', function(e) {
        e.preventDefault();
        $('.forVideo').hide();
        history.pushState({}, "Admin Panel", "/admin/view");
        adminService.loadAdminPage(contentContainer);
    });

    body.on('keyup', '#reg-password', function() {
        authService.validatePasswordStrength($(this));
    });
    body.on('keyup', '#reg-confirm-password', function() {
        const passwordVal = $('#reg-password').val();
        const confirmPasswordInput = $(this);
        if (passwordVal !== confirmPasswordInput.val()) {
            confirmPasswordInput.addClass('input-error');
            confirmPasswordInput.next('.error-message-inline').text('Passwords do not match.').show();
        } else {
            confirmPasswordInput.removeClass('input-error');
            confirmPasswordInput.next('.error-message-inline').hide();
        }
    });


    // quantity
    body.on('click', '.cart-item-row .increase-btn', function() { cartService.updateQuantityInCart($(this).data('product-id'), 1, contentContainer); });
    body.on('click', '.cart-item-row .decrease-btn', function() { cartService.updateQuantityInCart($(this).data('product-id'), -1, contentContainer); });
    body.on('click', '.product-details-info .increase-btn', function() { productService.updateQuantityOnPDP(1); });
    body.on('click', '.product-details-info .decrease-btn', function() { productService.updateQuantityOnPDP(-1); });

    // sort
    body.on('change', '#sort-select', function() { productService.sortAndRenderProducts($(this).val()); });

    $(window).on('popstate', function() { routePage(); });

    function updateUserStatusUI() {
        if (window.isAuthenticated) {
            $('#welcome-message-container').show();
            $('#username-display').text(window.currentUsername);
        } else {
            $('#welcome-message-container').hide();
        }
    }

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
        if(path === '/login'){
            authService.loadLoginPage(contentContainer)
            return;
        }
        if (path === '/register') {
            authService.loadRegisterPage(contentContainer);
            return;
        }
        if (path === '/admin/view') {
            adminService.loadAdminPage(contentContainer);
            return;
        }
        productService.loadAllProducts(contentContainer);
    }
    updateUserStatusUI()
    cartService.fetchAndUpdateCartSummary();
    cartService.updateCartSummary();
    routePage();
});
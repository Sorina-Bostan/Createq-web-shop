<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<div class="cart-page-container">
    <h1 class="cart-title">Your shopping cart</h1>
    <div id="auth-prompt-box" class="auth-prompt-box" style="display: none;">
        <p>Already have an account? <a href="#" id="login-link-from-cart">Login</a> to see your saved items.</p>
        <p>New to ModernWalk? <a href="#" id="register-link-from-cart">Create an account</a> to save your cart for later!</p>
    </div>
    <div class="cart-content-wrapper">
        <table class="cart-table">
            <thead>
            <tr>
                <th class="product-column">Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th></th>
                <th>Total</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="cart-items-container">
                <c:if test="${not empty cart and not empty cart.cartItems}">
                    <c:forEach items="${cart.cartItems}" var="item">
                        <tr class="cart-item-row">
                        <td>
                        <div class="cart-product-details">
                        <a href="#" class="product-link" data-product-id="${item.productId}">
                        <img class="cart-item-image" src="/static/images/${item.imageUrl}" alt="${item.name}">
                        <span class="cart-item-name">${item.name}</span>
                        </a>
                        </div>
                        </td>
                            <td class="cart-item-price">${String.format("%.2f", item.price)} RON</td>
                            <td class="cart-item-quantity">
                                <span class="quantity-display">${item.quantity}</span>
                            </td>
                            <td class="quantity-adjuster-cell">
                                <div class="quantity-adjuster">
                                    <button class="quantity-btn decrease-btn" data-product-id="${item.productId}">-</button>
                                    <button class="quantity-btn increase-btn" data-product-id="${item.productId}">+</button>
                                </div>
                            </td>
                            <td class="cart-item-total">${String.format("%.2f", item.price * item.quantity)} RON</td>
                            <td>
                                <button class="remove-item-btn" data-product-id="${item.productId}">X</button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
        <div class="cart-summary">
            <h3>Cart summary</h3>
            <div class="summary-line">
                <span>Total Items:</span>
                <span id="cart-item-count-summary">${not empty cart ? cart.totalItems : 0}</span>
            </div>
            <div class="summary-line">
                <span>Total Price:</span>
                <strong id="cart-total-price-summary">${not empty cart ? String.format("%.2f", cart.totalPrice) : '0.00'} RON</strong>
            </div>
            <button id="checkout-btn" class="checkout-button">Proceed to Checkout</button>
            <button id="clear-cart-btn" class="clear-cart-button">Clear</button>
        </div>
    </div>
</div>
<template id="cart-item-template">
    <tr class="cart-item-row">
        <td>
            <div class="cart-product-details">
                <a href="#" class="product-link" data-product-id="">
                    <img class="cart-item-image" src="" alt="Product Image">
                    <span class="cart-item-name">Product Name</span>
                </a>
            </div>
        </td>

        <td class="cart-item-price">0.00 RON</td>
        <td class="cart-item-quantity">
            <span class="quantity-display">1</span>
        </td>
        <td class="quantity-adjuster-cell">
            <div class="quantity-adjuster">
                <button class="quantity-btn decrease-btn" data-product-id="">-</button>
                <button class="quantity-btn increase-btn" data-product-id="">+</button>
            </div>
        </td>
        <td class="cart-item-total">0.00 RON</td>
        <td>
            <button class="remove-item-btn" data-product-id="">X</button>
        </td>
    </tr>
</template>
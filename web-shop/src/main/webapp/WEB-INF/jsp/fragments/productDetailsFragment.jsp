<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="product-details">
    <div class="product-details-image">
        <img src="<c:url value='${contextPath}/static/images/${product.imageUrl}'/>" alt="${product.name}">
    </div>
    <div class="product-details-info">
        <h2>${product.name}</h2>
        <p class="stock-info ${product.stockQuantity > 0 ? 'in-stock' : 'out-of-stock'}">
            <strong>Availability:</strong>
            <c:choose>
                <c:when test="${product.stockQuantity > 0}">In Stock</c:when>
                <c:otherwise>Out of Stock</c:otherwise>
            </c:choose>
        </p>
        <p><strong>Price:</strong> ${product.price} RON</p>
        <div class="quantity-selector-container">
            <label><strong>Quantity:</strong></label>
            <div class="quantity-adjuster" data-stock="${product.stockQuantity}">
                <button class="quantity-btn decrease-btn">-</button>
                <span id="pdp-quantity-display" class="quantity-display">1</span>
                <button class="quantity-btn increase-btn">+</button>
            </div>
        </div>
        <div class="product-buttons-container">
            <div class="product-details-button">
                <button id="back-to-list-btn">Back</button>
            </div>
            <div class="product-details-button">
                <button id="add-to-cart-button"
                        data-product-id="${product.id}"
                        data-product-name="${product.name}"
                        data-product-price="${product.price}"
                        data-product-image-url="${product.imageUrl}"
                        data-product-stock="${product.stockQuantity}"
                         ${product.stockQuantity <= 0 ? 'disabled' : ''}>
                    Add to cart</button>
            </div>
        </div>
    </div>

    <div class="product-description">
        <p><strong>Description:</strong> ${product.description}</p>
    </div>
</div>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="product-gallery">
    <c:if test="${not empty products}">
        <c:forEach items="${products}" var="product">
            <a href="#" class="product-link" data-product-id="${product.id}">
                <div class="product-card">
                    <div class="product-image-container">
                        <img src="<c:url value='static/images/${product.imageUrl}'/>" alt="${product.name}">
                    </div>
                    <div class="product-info">
                        <h3 class="product-name">${product.name}</h3>
                        <p class="product-price">${product.price} RON</p>
                    </div>
                    <button class="add-to-cart-btn"
                            data-product-id="${product.id}"
                            data-product-name="${product.name}"
                            data-product-price="${product.price}">
                        Add to cart
                    </button>
                </div>
            </a>
        </c:forEach>
    </c:if>

    <c:if test="${empty products}">
        <p>No products.</p>
    </c:if>
</div>
<%-- TODO: add in common.js the functionality for sorting the products--%>
<%--<div id="sorting-controls" >
    <label for="sort-select">Sort by:</label>
    <select id="sort-select">
        <option value="default">Implicit</option>
        <option value="price-asc">Price (ascending)</option>
        <option value="price-desc">Price (descending)</option>
    </select>
    <button id="sort-button">Sort</button>
</div>--%>
<br>
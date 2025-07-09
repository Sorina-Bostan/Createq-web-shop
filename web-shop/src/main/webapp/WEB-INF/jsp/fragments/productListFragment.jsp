<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h2>Products</h2>
<c:if test="${not empty products}">
    <ul class="product-list-items">
        <c:forEach items="${products}" var="product">
            <li data-price="${product.price}">
                <a href="#" class="product-link" data-product-id="${product.id}">
                        ${product.name}
                </a> - ${product.price} RON
                <button class="add-to-cart-btn"
                        data-product-id="${product.id}"
                        data-product-name="${product.name}"
                        data-product-price="${product.price}">
                    Add to cart
                </button>
            </li>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty products}">
    <p>No products.</p>
</c:if>
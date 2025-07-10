<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/common.css'/>">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <header>
        <strong>Cart: </strong>
        <span id="cart-item-count">0</span> products |
        <strong>Total: </strong>
        <span id="cart-total-price">0.00</span> RON
    </header>
    <nav>
        <h1>Categories</h1>
        <c:if test="${not empty categories}">
            <ul id="category-menu">
                <c:forEach items="${categories}" var="category">
                    <li>
                        <a href="#" class="category-link" data-category-id="${category.id}">
                            <strong>${category.name}</strong>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <br>
        <a href="#" id="all-products-link">See all products</a>
    </nav>
    <br>
    <%-- TODO: add in common.js the functionality for sorting the products--%>
    <div id="sorting-controls" >
        <label for="sort-select">Sort by:</label>
        <select id="sort-select">
            <option value="default">Implicit</option>
            <option value="price-asc">Price (ascending)</option>
            <option value="price-desc">Price (descending)</option>
        </select>
        <button id="sort-button">Sort</button>
    </div>


    <main id="content-container" >
        <div id="product-list-container"></div>
    </main>
    <button id="delete-cart">Empty the cart</button>



<script src="<c:url value='static/script/common.js'/>"></script>
</body>
</html>
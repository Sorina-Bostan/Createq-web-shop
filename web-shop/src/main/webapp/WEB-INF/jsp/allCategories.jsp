<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ModernWalk</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/common.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <div id="cart-banner">
        <span id="banner-message"></span>
    </div>
    <header class="main-header">
    <nav class="main-navigation">
        <c:if test="${not empty categories}">
            <div class="logo-container">
                <img src="${contextPath}/static/images/font.png" alt="ModernWalk Logo"/>
            </div>
            <ul id="category-menu">
                <li><a href="<c:url value='/categories'/>"><strong>Home</strong></a></li>
                <c:forEach items="${categories}" var="category">
                    <li>
                        <a href="#" class="category-link" data-category-id="${category.id}">
                            <strong>${category.name}</strong>
                        </a>
                    </li>
                </c:forEach>
                <li><a href="#" id="all-products-link"><strong>All</strong></a></li>
                <li>
                    <a href="#" id="view-cart-link" class="cart-icon-link">
                        <div class="cart-icon-container">
                            <svg
                                    width="50"
                                    height="50"
                                    viewBox="0 0 64 64"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    stroke="black"
                                    stroke-width="6"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    class="cart-icon-image">
                                <path d="M 8 28 L 12 60 H 52 L 56 28 Z" />
                                <path d="M 20 28 V 16 A 12 12 0 0 1 44 16 V 28" />

                            </svg>
                            <span id="cart-item-count" class="cart-item-badge">0</span>
                        </div>
                    </a>
                </li>
            </ul>
        </c:if>
        <br>
    </nav>
    </header>
    <br>
    <div class="forVideo">
        <video width="600" autoplay muted loop playsinline>
            <source src="${contextPath}/static/video/vintage_video.mp4" type="video/mp4">
            Your browser does not support the video tag.
        </video>
    </div>
    <br>
    <main id="content-container" >
        <div id="product-list-container">
        </div>
    </main>
    <footer>
        <h3>© 2025 Createq Web-Shop - Bostan Sorina-Gabriela</h3>
    </footer>
    <div id="confirm-modal-overlay">
        <div id="confirm-modal">
            <h3 id="modal-title">Confirm Deletion</h3>
            <p id="modal-message">Are you sure you want to remove this item from your cart?</p>
            <div class="modal-buttons">
                <button id="modal-confirm-btn">Yes, Remove</button>
                <button id="modal-cancel-btn">Cancel</button>
            </div>
        </div>
    </div>
    <script type="module" src="<c:url value='${contextPath}/static/script/main.js'/>"></script>
</body>
</html>
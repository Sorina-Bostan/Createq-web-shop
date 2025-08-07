<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>ModernWalk</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/common.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/login.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/admin.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        window.isAuthenticated = <sec:authorize access="isAuthenticated()">true</sec:authorize><sec:authorize access="isAnonymous()">false</sec:authorize>;
        <sec:authorize access="isAuthenticated()">
        window.currentUsername = "<sec:authentication property='principal.username'/>";
        </sec:authorize>
        window.isAdmin = <sec:authorize access="hasAuthority('ROLE_ADMIN')">true</sec:authorize><sec:authorize access="!hasAuthority('ROLE_ADMIN')">false</sec:authorize>;
    </script>
    <meta name="_csrf" content="<spring:eval expression='_csrf.token' />"/>
    <meta name="_csrf_header" content="<spring:eval expression='_csrf.headerName' />"/>
</head>
<body>
    <div id="cart-banner">
        <span id="banner-message"></span>
    </div>
    <header class="main-header">
        <div class="top-section">
            <div class="logo-container">
                <a href="<c:url value='/categories'/>">
                    <img src="${contextPath}/static/images/font.png" alt="ModernWalk Logo"/>
                </a>
            </div>
            <div id="welcome-message-container" style="display: none;">
                <h3><span class="welcome-message">Hello, <span id="username-display"></span>!</span></h3>
            </div>
        </div>
    <nav class="main-navigation">
        <c:if test="${not empty categories}">
            <ul id="category-menu">
                <sec:authorize access="hasAuthority('ROLE_ADMIN')">
                    <li id="admin-menu-item">
                        <a href="#" id="admin-panel-link"><strong>Admin</strong></a>
                    </li>
                </sec:authorize>
                <li><a href="<c:url value='/categories'/>"><strong>Home</strong></a></li>
                <c:forEach items="${categories}" var="category">
                    <li>
                        <a href="#" class="category-link" data-category-id="${category.id}">
                            <strong>${category.name}</strong>
                        </a>
                    </li>
                </c:forEach>
                <li>
                    <a href="#" id="all-products-link"><strong>All</strong></a>
                </li>
                <sec:authorize access="isAnonymous()">
                    <li id="login-menu-item">
                        <a href="#" id="login-link"><strong>Login</strong></a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li id="logout-menu-item">
                        <form action="<c:url value='/perform_logout'/>" method="post" class="logout-form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="logout-button-link"><strong>Logout</strong></button>
                        </form>
                    </li>
                </sec:authorize>
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
            <h3 id="modal-title">Confirm Action</h3>
            <p id="modal-message">Are you sure?</p>
            <div class="modal-buttons">
                <button id="modal-confirm-btn">Yes, Proceed</button>
                <button id="modal-cancel-btn">Cancel</button>
            </div>
        </div>
    </div>
    <script type="module" src="<c:url value='${contextPath}/static/script/main.js'/>"></script>
</body>
</html>
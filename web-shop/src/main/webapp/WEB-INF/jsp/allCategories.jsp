<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ModernWalk</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/common.css'/>">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <header class="main-header">
    <nav class="main-navigation">
        <c:if test="${not empty categories}">
            <div class="logo-container">
                <img src="<c:url value='/static/images/font.png' />" alt="ModernWalk Logo"/>
            </div>
            <ul id="category-menu">
                <c:forEach items="${categories}" var="category">
                    <li>
                        <a href="#" class="category-link" data-category-id="${category.id}">
                            <strong>${category.name}</strong>
                        </a>
                    </li>
                </c:forEach>
                <li><a href="#" id="all-products-link"><strong>All</strong></a></li>
            </ul>
        </c:if>
        <br>
    </nav>
    </header>
    <br>
    <div class="forVideo">
        <video width="600" autoplay muted loop playsinline>
            <source src="<c:url value='static/video/vintage_video.mp4'/>" type="video/mp4">
            Your browser does not support the video tag.
        </video>
    </div>
    <br>
    <main id="content-container" >
        <div id="product-list-container">
            <jsp:include page="/WEB-INF/jsp/fragments/productListFragment.jsp" />
        </div>
    </main>
    <footer>
        <h3>© Createq Web-Shop - Bostan Sorina-Gabriela</h3>
    </footer>
<script src="<c:url value='static/script/common.js'/>"></script>
</body>
</html>
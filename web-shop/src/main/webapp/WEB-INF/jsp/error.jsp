<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
  <title>Error - ModernWalk</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/common.css'/>">
  <style>
    .error-container {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      text-align: center;
      height: 80vh;
      padding: 20px;
    }
    .error-icon {
      font-size: 5em;
      color: #dc3545;
      line-height: 1;
    }
    .error-title {
      font-size: 2.5em;
      color:  #b5764a;
      margin: 20px 0 10px 0;
    }
    .error-message {
      font-size: 1.2em;
      color: #6c757d;
      max-width: 600px;
    }
    .back-link {
      display: inline-block;
      margin-top: 30px;
      padding: 10px 25px;
      background-color: #e81958;
      color:  white;
      text-decoration: none;
      border-radius: 5px;
      font-weight: bold;
      transition: background-color 0.2s ease;
    }

    .back-link:hover {
      background-color: #aa826a;
    }
  </style>
</head>
<body>

<div class="error-container">
  <div class="error-icon">❌</div>

  <h1 class="error-title">Oops! It is a problem.</h1>

  <p class="error-message">
    <c:out value="${errorMessage}"/>
  </p>
  <a href="<c:url value='/categories'/>" class="back-link">Back to the main page.</a>
</div>
</body>
</html>
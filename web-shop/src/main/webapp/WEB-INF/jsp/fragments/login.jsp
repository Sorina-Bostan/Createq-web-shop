<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="auth-container">
  <form id="login-form">
    <h2>Login to ModernWalk</h2>
    <div id="login-error-container" class="error-message"></div>

    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" id="username" name="username" required>
    </div>
    <div class="form-group">
      <label for="email">Email</label>
      <input type="text" id="email" name="email" required>
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" id="password" name="password" required>
    </div>
    <button type="submit" id="login-submit-btn" class="submit-btn">Login</button>
    <p class="redirect-link">
      Don't have an account? <a href="#" id="go-to-register">Sign Up</a>
    </p>
  </form>
</div>
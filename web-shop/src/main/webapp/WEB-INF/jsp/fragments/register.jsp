<div class="auth-container">
  <form id="register-form" novalidate>
    <h2>Create Account</h2>
    <div id="register-error-message" class="error-message" style="display: none;"></div>

    <div class="form-group">
      <label for="reg-firstname">First Name</label>
      <input type="text" id="reg-firstname" name="firstName" required maxlength="30">
      <div class="error-message-inline"></div>
    </div>
    <div class="form-group">
      <label for="reg-lastname">Last Name</label>
      <input type="text" id="reg-lastname" name="lastName" required maxlength="30">
      <div class="error-message-inline"></div>
    </div>
    <div class="form-group" >
      <label for="reg-username">Username</label>
      <input type="text" id="reg-username" required>
    </div>
    <div class="form-group">
      <label for="reg-email">Email</label>
      <input type="email" id="reg-email" required>
    </div>
    <div class="form-group">
      <label for="reg-password">Password</label>
      <input type="password" id="reg-password" required>
    </div>
    <div class="form-group">
      <label for="reg-confirm-password">Confirm Password</label>
      <input type="password" id="reg-confirm-password" required>
      <div class="error-message-inline"></div>
    </div>
    <button type="submit">Register</button>
    <p>Already have an account? <a href="#" id="go-to-login">Login</a></p>
  </form>
</div>
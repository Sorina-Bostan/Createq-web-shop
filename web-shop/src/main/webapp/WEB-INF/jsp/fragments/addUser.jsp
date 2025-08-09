<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="admin-form-container">
  <h2 id="user-form-title">Add New User</h2>
  <form id="user-admin-form">
    <input type="hidden" id="user-id-input">

    <div class="form-group">
      <label for="user-firstname">First Name</label>
      <input type="text" id="user-firstname" required maxlength="30">
    </div>

    <div class="form-group">
      <label for="user-lastname">Last Name</label>
      <input type="text" id="user-lastname" required maxlength="30">
    </div>

    <div class="form-group">
      <label for="user-email">Email</label>
      <input type="email" id="user-email" required>
    </div>

    <div class="form-group">
      <label for="user-username">Username</label>
      <input type="text" id="user-username" required maxlength="30">
    </div>

    <div class="form-group">
      <label for="user-password">Password</label>
      <input type="password" id="user-password" required placeholder="Min. 8 characters, 1 uppercase, 1 digit, 1 special">
    </div>
    <div class="form-group">
      <label for="user-role">Role</label>
      <select id="user-role" required>
        <option value="CUSTOMER" selected>Customer</option>
        <option value="ADMIN">Admin</option>
      </select>
    </div>

    <button type="submit">Create User</button>
    <button type="button" id="cancel-add-user-btn" style="background-color: #777;">Cancel</button>
  </form>
</div>
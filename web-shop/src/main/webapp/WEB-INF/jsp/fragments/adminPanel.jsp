<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="admin-panel-container">
  <h1>Product Management</h1>
  <div class="admin-form-container">
    <h2 id="form-title">Add New Product</h2>
    <form id="product-admin-form">
      <input type="hidden" id="product-id-input">

      <div class="form-group">
        <label for="product-name">Product Name</label>
        <input type="text" id="product-name" required>
      </div>

      <div class="form-group">
        <label for="product-price">Price (RON)</label>
        <input type="number" id="product-price" step="0.01" min="0" required>
      </div>

      <div class="form-group">
        <label for="product-stock">Stock Quantity</label>
        <input type="number" id="product-stock" min="0" required>
      </div>

      <div class="form-group">
        <label for="product-category">Category</label>
        <select id="product-category" required>
          <option value="" disabled selected>Select a category...</option>
        </select>
      </div>

      <div class="form-group">
        <label for="product-image-url">Image Filename (e.g., "jordan.jpg")</label>
        <input type="text" id="product-image-url" required>
      </div>

      <div class="form-group">
        <label for="product-description">Description</label>
        <textarea id="product-description" rows="4" required></textarea>
      </div>

      <button type="submit">Save Product</button>
      <button type="button" id="clear-form-btn" style="background-color: #777;">Clear Form</button>
    </form>
  </div>
  <div class="admin-product-list">
    <h2>Existing Products</h2>
    <table class="admin-table">
      <thead>
      <tr>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody id="admin-products-tbody">
      </tbody>
    </table>
  </div>
</div>
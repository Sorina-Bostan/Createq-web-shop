import { showBanner } from './cart.js';
export function loadAdminPage(container) {
    history.pushState({}, "Admin Panel", "/admin/view");
    container.load('/admin/view', function() {
        loadProductsIntoTable();
        loadCategoriesIntoSelect();
    });
}
export function deleteProductById(productId) {
    $.ajax({
        url: `/api/admin/products/${productId}`,
        type: 'DELETE',
        success: function() {
            showBanner('Product deleted successfully!', 'success', 3000);
            loadProductsIntoTable();
        },
        error: () => showBanner('Could not delete the product.', 'error', 3000)
    });
}
$(document).on('submit', '#product-admin-form', function(event) {
    event.preventDefault();

    const productId = $('#product-id-input').val();
    const productData = {
        name: $('#product-name').val(),
        price: parseFloat($('#product-price').val()),
        stockQuantity: parseInt($('#product-stock').val()),
        categoryId: parseInt($('#product-category').val()),
        imageUrl: $('#product-image-url').val(),
        description: $('#product-description').val()
    };

    const isUpdate = !!productId;
    const url = isUpdate ? `/api/admin/products/${productId}` : '/api/admin/products';
    const method = isUpdate ? 'PUT' : 'POST';

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        data: JSON.stringify(productData),
        success: function() {
            showBanner(`Product ${isUpdate ? 'updated' : 'created'} successfully!`, "success", 3000);
            clearProductForm();
            loadProductsIntoTable();
        },
        error: () => showBanner('An error occurred. Please check the data.', "error", 3000)
    });
});
$(document).on('click', '#clear-form-btn', clearProductForm);
$(document).on('click', '.edit-btn', function() {
    const productId = $(this).closest('tr').data('product-id');

    $.ajax({
        url: `/api/products/${productId}`,
        type: 'GET',
        success: function(product) {
            $('#form-title').text('Edit Product');
            $('#product-id-input').val(product.id);
            $('#product-name').val(product.name);
            $('#product-price').val(product.price);
            $('#product-stock').val(product.stockQuantity);
            $('#product-category').val(product.categoryId);
            $('#product-image-url').val(product.imageUrl);
            $('#product-description').val(product.description);

            window.scrollTo(0, 0);
        },
        error: () => showBanner('Could not fetch product details.', "error", 3000)
    });
});
$(document).on('click', '.delete-btn', function() {
    const productId = $(this).closest('tr').data('product-id');
    const productName = $(this).closest('tr').find('td[data-field="name"]').text();
    window.productToRemoveId = productId;
    window.actionToConfirm = 'delete-product';
    $('#modal-title').text('Delete Product');
    $('#modal-message').text(`Are you sure you want to delete the product "${productName}"?`);
    $('#modal-confirm-btn').text('Yes, Delete');
    $('#confirm-modal-overlay').addClass('show');
});
function loadProductsIntoTable() {
    $.ajax({
        url: '/api/admin/products',
        type: 'GET',
        success: function(products) {
            const tbody = $('#admin-products-tbody');
            tbody.empty();
            products.forEach(product => {
                const row = `
                    <tr data-product-id="${product.id}">
                        <td data-field="name">${product.name}</td>
                        <td data-field="categoryName">${product.categoryName}</td>
                        <td data-field="price">${product.price.toFixed(2)}</td>
                        <td data-field="stockQuantity">${product.stockQuantity}</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: () => alert("Could not load products.")
    });
}

function loadCategoriesIntoSelect() {
    $.ajax({
        url: '/api/categories',
        type: 'GET',
        success: function(categories) {
            const select = $('#product-category');
            select.find('option:gt(0)').remove();
            categories.forEach(category => {
                select.append(`<option value="${category.id}">${category.name}</option>`);
            });
        },
        error: () => alert("Error: Could not load categories for the form.")
    });
}

function clearProductForm() {
    $('#product-admin-form')[0].reset();
    $('#product-id-input').val('');
    $('#form-title').text('Add New Product');
}
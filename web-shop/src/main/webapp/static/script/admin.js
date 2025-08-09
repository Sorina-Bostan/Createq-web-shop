import { showBanner } from './cart.js';

//events
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
        success: () => {
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
        url: `/api/products/product/${productId}`,
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
$(document).on('click', '.admin-tab-btn', function() {
    const targetId = $(this).data('target');
    $('.admin-tab-content').hide();
    $('.admin-tab-btn').removeClass('active');
    $('#' + targetId).show();
    $(this).addClass('active');
    if (targetId === 'user-management') {
        loadUsersIntoTable();
    } else if (targetId === 'product-management') {
        loadProductsIntoTable();
    }
});
$(document).on('change', '.role-select', function() {
    const select = $(this);
    const userId = select.closest('tr').data('user-id');
    const username = select.closest('tr').find('td:first').text();
    const newRole = select.val();
    window.roleSelectElement = select;
    window.userToUpdateId = userId;
    window.actionToConfirm = 'update-role';
    $('#modal-title').text('Change User Role');
    $('#modal-message').text(`Are you sure you want to change the role for user "${username}" to ${newRole}?`);
    $('#modal-confirm-btn').text('Yes, Change Role');
    $('#confirm-modal-overlay').addClass('show');
});
$(document).on('focus', '.role-select', function() {
    $(this).data('previous-role', $(this).val());
});


$(document).on('click', '.delete-user-btn', function() {
    const row = $(this).closest('tr');
    const userId = row.data('user-id');
    const username = row.find('td:first').text();
    window.userToRemoveId = userId;
    window.actionToConfirm = 'delete-user';
    $('#modal-title').text('Delete User');
    $('#modal-message').text(`Are you sure you want to delete the user "${username}"? This action cannot be undone.`);
    $('#modal-confirm-btn').text('Yes, Delete User');
    $('#confirm-modal-overlay').addClass('show');
});
$(document).on('click', '#add-user-btn', function() {
    const formContainer = $('#add-user-form-container');
    formContainer.load('/admin/add-user-form', function() {
        formContainer.show();
    });
});
$(document).on('click', '#cancel-add-user-btn', function() {
    const formContainer = $('#add-user-form-container');
    formContainer.hide();
    formContainer.empty();
});

$(document).on('submit', '#user-admin-form', function(event) {
    event.preventDefault();

    const userData = {
        firstName: $('#user-firstname').val(),
        lastName: $('#user-lastname').val(),
        email: $('#user-email').val(),
        username: $('#user-username').val(),
        password: $('#user-password').val(),
        role: $('#user-role').val()
    };

    $.ajax({
        url: '/api/admin/users',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function() {
            showBanner('User created successfully!', 'success', 3000);
            const formContainer = $('#add-user-form-container');
            formContainer.hide();
            formContainer.empty();
            loadUsersIntoTable();
        },
        error: function(xhr) {
            // noinspection JSUnresolvedReference
            const errorMessage = xhr.responseJSON?.message || 'Could not create the user.';
            showBanner(errorMessage, 'error', 4000);
        }
    });
});

//functions
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

function loadUsersIntoTable() {
    $.ajax({
        url: '/api/admin/users',
        type: 'GET',
        success: function(users) {
            const tbody = $('#admin-users-tbody');
            tbody.empty();

            users.forEach(user => {
                const customerSelected = user.role === 'CUSTOMER' ? 'selected' : '';
                const adminSelected = user.role === 'ADMIN' ? 'selected' : '';
                const row = `
                    <tr data-user-id="${user.id}">
                        <td>${user.username}</td>
                        <td>${user.firstName} ${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>
                            <select class="role-select">
                                <option value="CUSTOMER" ${customerSelected}>Customer</option>
                                <option value="ADMIN" ${adminSelected}>Admin</option>
                            </select>
                        </td>
                        <td>
                            <button class="delete-user-btn">Delete</button>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function() {
            alert('Could not load the list of users.');
        }
    });
}

//export functions
export function updateUserRole(userId, newRole, selectElement) {
    $.ajax({
        url: `/api/admin/users/${userId}/role`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ newRole: newRole }),
        success: function() {
            showBanner('User role updated successfully!', 'success', 3000);
            selectElement.data('previous-role', newRole);
        },
        error: function() {
            showBanner('Failed to update user role.', 'error', 4000);
            selectElement.val(selectElement.data('previous-role'));
        }
    });
}

export function deleteUserById(userId) {
    $.ajax({
        url: `/api/admin/users/${userId}`,
        type: 'DELETE',
        success: function() {
            showBanner('User deleted successfully!', 'success', 3000);
            loadUsersIntoTable();
        },
        error: function(xhr) {
            // noinspection JSUnresolvedReference
            const errorMessage = xhr.responseJSON?.message || 'Could not delete the user.';
            showBanner(errorMessage, 'error', 4000);
        }
    });
}

export function loadAdminPage(container) {
    container.load('/admin/view-content', function() {
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
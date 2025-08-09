export function loadLoginPage(container) {
    $('.forVideo').hide();
    $('.product-gallery').hide();

    const pageUrl = "/login";
    const apiUrl = "/api/auth/login";
    history.pushState({}, "Login", pageUrl);
    container.load(apiUrl);
}
export function loadRegisterPage(container) {
    const pageUrl = "/register";
    const apiUrl = "/api/auth/register";
    history.pushState({}, "Register", pageUrl);
    container.load(apiUrl);
}
function showError(input, message) {
    input.addClass('input-error');
    const errorContainer = input.siblings('.error-message-inline, #password-strength-meter').first().parent().find('.error-message-inline');
    errorContainer.text(message).show();
}

function clearError(input) {
    input.removeClass('input-error');
    const errorContainer = input.siblings('.error-message-inline, #password-strength-meter').first().parent().find('.error-message-inline');
    errorContainer.hide().text('');
}
export function validateAndSubmitRegistration() {
    let isFormValid = true;

    const fields = {
        firstName: $('#reg-firstname'),
        lastName: $('#reg-lastname'),
        email: $('#reg-email'),
        username: $('#reg-username'),
        password: $('#reg-password'),
        confirmPassword: $('#reg-confirm-password')
    };
    Object.values(fields).forEach(field => clearError(field));
    if (fields.firstName.val().trim().length === 0) { showError(fields.firstName, 'First name is required.'); isFormValid = false; }
    if (fields.lastName.val().trim().length === 0) { showError(fields.lastName, 'Last name is required.'); isFormValid = false; }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(fields.email.val())) { showError(fields.email, 'Please enter a valid email address.'); isFormValid = false; }

    const usernameRegex = /^[a-zA-Z0-9_.-]*$/;
    if (!usernameRegex.test(fields.username.val())) { showError(fields.username, 'Username contains invalid characters.'); isFormValid = false; }
    const password = fields.password.val();
    let rulesMet = 0;
    if (password.length >= 8) rulesMet++;
    if (/[A-Z]/.test(password)) rulesMet++;
    if (/[0-9]/.test(password)) rulesMet++;
    if (/[^A-Za-z0-9]/.test(password)) rulesMet++;

    if (rulesMet < 4) {
        showError(fields.password, 'Password must meet all 4 criteria.');
        isFormValid = false;
    }
    if (fields.password.val() !== fields.confirmPassword.val()) {
        showError(fields.confirmPassword, 'Passwords do not match.');
        isFormValid = false;
    } else if (fields.confirmPassword.val().length === 0) {
        showError(fields.confirmPassword, 'Please confirm your password.');
        isFormValid = false;
    }

    return isFormValid;
}
export function validatePasswordStrength(passwordInput) {
    const password = passwordInput.val();
    const strengthBar = $('#strength-bar');
    const strengthText = $('#strength-text');

    let rulesMet = 0;
    if (password.length >= 8) rulesMet++;
    if (/[A-Z]/.test(password)) rulesMet++;
    if (/[0-9]/.test(password)) rulesMet++;
    if (/[^A-Za-z0-9]/.test(password)) rulesMet++;

    strengthBar.removeClass('weak medium strong');
    strengthText.text('');
    clearError(passwordInput);

    if (password.length > 0) {
        if (rulesMet <= 1) { strengthBar.addClass('weak'); strengthText.text('Weak'); }
        else if (rulesMet <= 3) { strengthBar.addClass('medium'); strengthText.text('Medium'); }
        else if (rulesMet === 4) { strengthBar.addClass('strong'); strengthText.text('Strong'); }

        if (rulesMet < 4) {
            showError(passwordInput, 'Must contain 8+ chars, uppercase, digit, special char.');
        }
    }
}


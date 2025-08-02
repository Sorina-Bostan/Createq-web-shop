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
export function validateAndSubmitRegistration() {
    let isFormValid = true;
    function showError(input, message) {
        input.addClass('input-error');
        input.next('.error-message-inline').text(message).show();
        isFormValid = false;
    }
    function clearError(input) {
        input.removeClass('input-error');
        input.next('.error-message-inline').hide();
    }
    const fields = {
        firstName: $('#reg-firstname'),
        lastName: $('#reg-lastname'),
        email: $('#reg-email'),
        username: $('#reg-username'),
        password: $('#reg-password'),
        confirmPassword: $('#reg-confirm-password')
    };
    for (const field in fields) {
        clearError(fields[field]);
    }
    if (fields.firstName.val().trim().length === 0) showError(fields.firstName, 'First name is required.');
    if (fields.lastName.val().trim().length === 0) showError(fields.lastName, 'Last name is required.');

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(fields.email.val())) showError(fields.email, 'Please enter a valid email address.');

    const usernameRegex = /^[a-zA-Z0-9_.-]*$/;
    if (!usernameRegex.test(fields.username.val())) showError(fields.username, 'Username contains invalid characters.');

    validatePasswordStrength(fields.password, true);

    if (fields.password.val() !== fields.confirmPassword.val()) {
        showError(fields.confirmPassword, 'Passwords do not match.');
    } else if (fields.confirmPassword.val().length === 0) {
        showError(fields.confirmPassword, 'Please confirm your password.');
    }

    return isFormValid;
}
export function validatePasswordStrength(passwordInput, isSubmit = false) {
    const password = passwordInput.val();
    const strengthBar = $('#strength-bar');
    const strengthText = $('#strength-text');

    let rulesMet = 0;
    if (password.length >= 8) rulesMet++;
    if (/[A-Z]/.test(password)) rulesMet++;
    if (/[0-9]/.test(password)) rulesMet++;
    if (/[^A-Za-z0-9]/.test(password)) rulesMet++;

    strengthBar.removeClass('weak medium strong');
    if (password.length === 0) {
        strengthText.text('');
    } else if (rulesMet <= 1) {
        strengthBar.addClass('weak');
        strengthText.text('Weak');
    } else if (rulesMet > 1 && rulesMet <= 3) {
        strengthBar.addClass('medium');
        strengthText.text('Medium');
    } else if (rulesMet === 4) {
        strengthBar.addClass('strong');
        strengthText.text('Strong');
    }
    if (isSubmit && rulesMet < 4) {
        passwordInput.addClass('input-error');
        passwordInput.nextAll('.error-message-inline').first().text('Password must meet all criteria.').show();
        return false;
    }
    return true;
}
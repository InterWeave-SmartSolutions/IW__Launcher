/**
 * InterWeave Form Validation Library
 *
 * Provides client-side validation for form inputs with real-time feedback
 * and clear error messages. Designed for accessibility and user-friendly
 * error reporting.
 *
 * Usage:
 *   1. Include this script and validation.css in your JSP page
 *   2. Add data-validate attributes to form inputs
 *   3. Call IWValidation.init() on page load
 *
 * Example:
 *   <input type="text" name="email" data-validate="required email" />
 *   <script>IWValidation.init();</script>
 *
 * Supported validation types:
 *   - required: Field must not be empty
 *   - email: Must be valid email format
 *   - password: Checks password strength
 *   - minlength-N: Minimum N characters (e.g., minlength-8)
 *   - maxlength-N: Maximum N characters
 *   - match-ID: Must match field with given ID
 */

var IWValidation = (function() {
    'use strict';

    var config = {
        validateOnInput: true,
        validateOnBlur: true,
        showSuccessIndicator: true,
        debounceDelay: 300
    };

    var validators = {
        /**
         * Check if field has a value
         */
        required: function(value, element) {
            var isValid = value.trim().length > 0;
            return {
                valid: isValid,
                message: isValid ? '' : 'This field is required'
            };
        },

        /**
         * Validate email format
         */
        email: function(value, element) {
            if (!value) return { valid: true, message: '' };

            var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            var isValid = emailRegex.test(value);

            return {
                valid: isValid,
                message: isValid ? '' : 'Please enter a valid email address (e.g., user@example.com)'
            };
        },

        /**
         * Validate password strength
         */
        password: function(value, element) {
            if (!value) return { valid: true, message: '' };

            var strength = {
                length: value.length >= 8,
                hasUpper: /[A-Z]/.test(value),
                hasLower: /[a-z]/.test(value),
                hasNumber: /[0-9]/.test(value),
                hasSpecial: /[!@#$%^&*(),.?":{}|<>]/.test(value)
            };

            var strengthCount = Object.keys(strength).filter(function(key) {
                return strength[key];
            }).length;

            if (!strength.length) {
                return {
                    valid: false,
                    message: 'Password must be at least 8 characters long',
                    strength: 'weak'
                };
            }

            if (strengthCount < 3) {
                return {
                    valid: true,
                    message: 'Password is weak. Consider adding uppercase, lowercase, numbers, or special characters',
                    strength: 'weak',
                    warning: true
                };
            }

            if (strengthCount < 4) {
                return {
                    valid: true,
                    message: 'Password strength: Medium',
                    strength: 'medium',
                    info: true
                };
            }

            return {
                valid: true,
                message: 'Password strength: Strong',
                strength: 'strong',
                info: true
            };
        },

        /**
         * Validate minimum length
         */
        minlength: function(value, element, param) {
            if (!value) return { valid: true, message: '' };

            var minLength = parseInt(param, 10);
            var isValid = value.length >= minLength;

            return {
                valid: isValid,
                message: isValid ? '' : 'Must be at least ' + minLength + ' characters (current: ' + value.length + ')'
            };
        },

        /**
         * Validate maximum length
         */
        maxlength: function(value, element, param) {
            if (!value) return { valid: true, message: '' };

            var maxLength = parseInt(param, 10);
            var isValid = value.length <= maxLength;

            return {
                valid: isValid,
                message: isValid ? '' : 'Must be at most ' + maxLength + ' characters (current: ' + value.length + ')'
            };
        },

        /**
         * Validate that field matches another field
         */
        match: function(value, element, param) {
            if (!value) return { valid: true, message: '' };

            var matchElement = document.getElementById(param);
            if (!matchElement) {
                return {
                    valid: false,
                    message: 'Configuration error: match target not found'
                };
            }

            var isValid = value === matchElement.value;
            var fieldName = matchElement.getAttribute('data-field-name') || 'password';

            return {
                valid: isValid,
                message: isValid ? '' : 'Does not match ' + fieldName
            };
        }
    };

    var debounceTimers = {};

    /**
     * Debounce function to limit validation frequency
     */
    function debounce(func, delay, key) {
        return function() {
            var context = this;
            var args = arguments;

            clearTimeout(debounceTimers[key]);
            debounceTimers[key] = setTimeout(function() {
                func.apply(context, args);
            }, delay);
        };
    }

    /**
     * Parse validation rules from data-validate attribute
     */
    function parseValidationRules(element) {
        var rulesAttr = element.getAttribute('data-validate');
        if (!rulesAttr) return [];

        var rules = [];
        var ruleStrings = rulesAttr.split(' ');

        for (var i = 0; i < ruleStrings.length; i++) {
            var ruleString = ruleStrings[i].trim();
            if (!ruleString) continue;

            var parts = ruleString.split('-');
            var ruleName = parts[0];
            var param = parts.slice(1).join('-');

            rules.push({
                name: ruleName,
                param: param
            });
        }

        return rules;
    }

    /**
     * Validate a single field
     */
    function validateField(element) {
        var rules = parseValidationRules(element);
        if (rules.length === 0) return { valid: true, messages: [] };

        var value = element.value || '';
        var results = [];
        var isValid = true;

        for (var i = 0; i < rules.length; i++) {
            var rule = rules[i];
            var validator = validators[rule.name];

            if (!validator) continue;

            var result = validator(value, element, rule.param);

            if (!result.valid) {
                isValid = false;
            }

            if (result.message) {
                results.push({
                    message: result.message,
                    valid: result.valid,
                    warning: result.warning || false,
                    info: result.info || false,
                    strength: result.strength
                });
            }
        }

        return {
            valid: isValid,
            messages: results
        };
    }

    /**
     * Get or create error container for a field
     */
    function getErrorContainer(element) {
        var containerId = 'validation-error-' + (element.id || element.name);
        var container = document.getElementById(containerId);

        if (!container) {
            container = document.createElement('div');
            container.id = containerId;
            container.className = 'validation-error';
            container.setAttribute('role', 'alert');
            container.setAttribute('aria-live', 'polite');

            var parent = element.parentNode;
            if (parent.nextSibling) {
                parent.parentNode.insertBefore(container, parent.nextSibling);
            } else {
                parent.parentNode.appendChild(container);
            }
        }

        return container;
    }

    /**
     * Display validation result for a field
     */
    function displayValidationResult(element, result) {
        var container = getErrorContainer(element);

        element.classList.remove('validation-error-field', 'validation-success-field', 'validation-warning-field');
        container.className = 'validation-error';
        container.innerHTML = '';

        if (result.messages.length === 0) {
            if (config.showSuccessIndicator && element.value) {
                element.classList.add('validation-success-field');
                element.setAttribute('aria-invalid', 'false');
            }
            return;
        }

        var hasError = false;
        var hasWarning = false;

        for (var i = 0; i < result.messages.length; i++) {
            var msg = result.messages[i];

            var messageDiv = document.createElement('div');
            messageDiv.className = 'validation-message';

            if (!msg.valid) {
                messageDiv.className += ' validation-message-error';
                hasError = true;
            } else if (msg.warning) {
                messageDiv.className += ' validation-message-warning';
                hasWarning = true;
            } else if (msg.info) {
                messageDiv.className += ' validation-message-info';

                if (msg.strength) {
                    messageDiv.className += ' validation-strength-' + msg.strength;
                }
            }

            messageDiv.textContent = msg.message;
            container.appendChild(messageDiv);
        }

        if (hasError) {
            element.classList.add('validation-error-field');
            element.setAttribute('aria-invalid', 'true');
            container.className += ' validation-error-active';
        } else if (hasWarning) {
            element.classList.add('validation-warning-field');
            element.setAttribute('aria-invalid', 'false');
        } else {
            if (config.showSuccessIndicator) {
                element.classList.add('validation-success-field');
            }
            element.setAttribute('aria-invalid', 'false');
        }
    }

    /**
     * Handle input event (real-time validation)
     */
    function handleInput(event) {
        var element = event.target;

        if (!config.validateOnInput) return;

        var key = element.id || element.name;
        debounce(function() {
            var result = validateField(element);
            displayValidationResult(element, result);
        }, config.debounceDelay, key)();
    }

    /**
     * Handle blur event (validation when leaving field)
     */
    function handleBlur(event) {
        var element = event.target;

        if (!config.validateOnBlur) return;

        var result = validateField(element);
        displayValidationResult(element, result);
    }

    /**
     * Validate entire form
     */
    function validateForm(form) {
        var elements = form.querySelectorAll('[data-validate]');
        var isValid = true;
        var firstInvalidElement = null;

        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            var result = validateField(element);
            displayValidationResult(element, result);

            if (!result.valid) {
                isValid = false;
                if (!firstInvalidElement) {
                    firstInvalidElement = element;
                }
            }
        }

        if (!isValid && firstInvalidElement) {
            firstInvalidElement.focus();
        }

        return isValid;
    }

    /**
     * Handle form submit event
     */
    function handleSubmit(event) {
        var form = event.target;

        if (!validateForm(form)) {
            event.preventDefault();
            event.stopPropagation();
            return false;
        }

        return true;
    }

    /**
     * Initialize validation for a form
     */
    function initializeForm(form) {
        var elements = form.querySelectorAll('[data-validate]');

        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];

            element.setAttribute('aria-required', 'true');

            element.addEventListener('input', handleInput);
            element.addEventListener('blur', handleBlur);
        }

        form.addEventListener('submit', handleSubmit);

        form.setAttribute('novalidate', 'novalidate');
    }

    /**
     * Initialize validation for all forms
     */
    function init(options) {
        if (options) {
            for (var key in options) {
                if (options.hasOwnProperty(key)) {
                    config[key] = options[key];
                }
            }
        }

        var forms = document.querySelectorAll('form');
        for (var i = 0; i < forms.length; i++) {
            initializeForm(forms[i]);
        }
    }

    /**
     * Add a custom validator
     */
    function addValidator(name, validatorFunc) {
        validators[name] = validatorFunc;
    }

    /**
     * Manually validate a field
     */
    function validate(element) {
        if (typeof element === 'string') {
            element = document.getElementById(element) || document.querySelector('[name="' + element + '"]');
        }

        if (!element) return { valid: false, messages: [] };

        var result = validateField(element);
        displayValidationResult(element, result);
        return result;
    }

    /**
     * Clear validation for a field
     */
    function clearValidation(element) {
        if (typeof element === 'string') {
            element = document.getElementById(element) || document.querySelector('[name="' + element + '"]');
        }

        if (!element) return;

        element.classList.remove('validation-error-field', 'validation-success-field', 'validation-warning-field');
        element.removeAttribute('aria-invalid');

        var container = getErrorContainer(element);
        if (container) {
            container.innerHTML = '';
            container.className = 'validation-error';
        }
    }

    return {
        init: init,
        validate: validate,
        validateForm: validateForm,
        clearValidation: clearValidation,
        addValidator: addValidator,
        config: config
    };
})();

if (typeof module !== 'undefined' && module.exports) {
    module.exports = IWValidation;
}

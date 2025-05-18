document.addEventListener('DOMContentLoaded', function() {
    // Quantity input and checkbox handling
    const quantityInputs = document.querySelectorAll('.quantity-input');
    const checkboxes = document.querySelectorAll('.item-checkbox');
    const placeOrderBtn = document.getElementById('place-order-btn');

    // Update checkboxes when quantity changes
    quantityInputs.forEach(input => {
        input.addEventListener('change', function() {
            const itemId = this.dataset.itemId;
            const checkbox = document.querySelector(`#item-${itemId}`);
            const subtotalCell = this.closest('tr').querySelector('.subtotal');

            // Update checkbox
            checkbox.checked = this.value > 0;

            // Update subtotal
            const price = parseFloat(this.closest('tr').querySelector('td:nth-child(2)').textContent.replace('$', ''));
            const quantity = parseInt(this.value);
            const subtotal = price * quantity;
            subtotalCell.textContent = `$${subtotal.toFixed(2)}`;

            // Update total
            updateOrderTotal();
        });
    });

    // Update quantities when checkbox changes
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const itemId = this.value;
            const quantityInput = document.querySelector(`.quantity-input[data-item-id="${itemId}"]`);

            if (this.checked && quantityInput.value === 0) {
                quantityInput.value = 1;
                quantityInput.dispatchEvent(new Event('change'));
            } else if (!this.checked) {
                quantityInput.value = 0;
                quantityInput.dispatchEvent(new Event('change'));
            }
        });
    });

    // Update order total
    function updateOrderTotal() {
        let total = 0;
        document.querySelectorAll('.subtotal').forEach(cell => {
            total += parseFloat(cell.textContent.replace('$', ''));
        });

        document.getElementById('order-total').textContent = `$${total.toFixed(2)}`;

        // Enable/disable place order button
        placeOrderBtn.disabled = total <= 0;
    }

    // Form validation
    const orderForm = document.querySelector('form[action*="/orders/create"]');
    if (orderForm) {
        orderForm.addEventListener('submit', function(e) {
            const checkedItems = document.querySelectorAll('.item-checkbox:checked');
            if (checkedItems.length === 0) {
                e.preventDefault();
                alert('Please select at least one item to order');
            }
        });
    }

    // Initialize tracking status if on track.jsp
    const orderStatus = document.querySelector('.order-status');
    if (orderStatus) {
        updateTrackingProgress(orderStatus.dataset.status);
    }

    function updateTrackingProgress(status) {
        const steps = document.querySelectorAll('.tracking-step');
        const progressBar = document.querySelector('.tracking-progress-bar');

        // Reset all steps
        steps.forEach(step => {
            step.classList.remove('active', 'completed');
        });

        // Update based on status
        let progressWidth = 0;
        switch(status) {
            case 'PENDING':
                progressWidth = 0;
                steps[0].classList.add('active');
                break;
            case 'PREPARING':
                progressWidth = 33;
                steps[0].classList.add('completed');
                steps[1].classList.add('active');
                break;
            case 'ON_THE_WAY':
                progressWidth = 66;
                steps[0].classList.add('completed');
                steps[1].classList.add('completed');
                steps[2].classList.add('active');
                break;
            case 'DELIVERED':
                progressWidth = 100;
                steps.forEach(step => step.classList.add('completed'));
                break;
            case 'CANCELLED':
                progressWidth = 0;
                break;
        }

        if (progressBar) {
            progressBar.style.width = `${progressWidth}%`;
        }
    }
});

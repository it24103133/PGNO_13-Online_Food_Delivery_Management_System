document.addEventListener('DOMContentLoaded', function() {
    // Star rating functionality for review form
    document.querySelectorAll('.rating-container').forEach(container => {
        const stars = container.querySelectorAll('.stars i');
        const hiddenInput = container.querySelector('input[type="hidden"]');
        const ratingType = container.getAttribute('data-rating-type');

        stars.forEach(star => {
            // Hover effect
            star.addEventListener('mouseover', function() {
                const value = parseInt(this.getAttribute('data-value'));
                highlightStars(stars, value);
            });

            star.addEventListener('mouseout', function() {
                const currentValue = hiddenInput.value ? parseInt(hiddenInput.value) : 0;
                highlightStars(stars, currentValue);
            });

            // Click to select
            star.addEventListener('click', function() {
                const value = parseInt(this.getAttribute('data-value'));
                hiddenInput.value = value;
                highlightStars(stars, value);

                // Update the name for form submission
                hiddenInput.name = `${ratingType}Rating`;
            });
        });
    });

    // Star rating functionality for edit modal
    document.querySelectorAll('.edit-food-rating i, .edit-delivery-rating i').forEach(star => {
        star.addEventListener('click', function() {
            const container = this.closest('.edit-food-rating, .edit-delivery-rating');
            const stars = container.querySelectorAll('i');
            const hiddenInput = container.querySelector('input[type="hidden"]');
            const value = parseInt(this.getAttribute('data-value'));

            hiddenInput.value = value;
            highlightStars(stars, value);
        });
    });

    // Highlight stars up to the given value
    function highlightStars(stars, value) {
        stars.forEach(star => {
            const starValue = parseInt(star.getAttribute('data-value'));
            if (starValue <= value) {
                star.classList.add('active');
                star.classList.remove('far');
                star.classList.add('fas');
            } else {
                star.classList.remove('active');
                star.classList.add('far');
                star.classList.remove('fas');
            }
        });
    }

    // Confirm before deleting review
    document.querySelectorAll('form[action*="/reviews/delete"]').forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!confirm('Are you sure you want to delete this review?')) {
                e.preventDefault();
            }
        });
    });
});

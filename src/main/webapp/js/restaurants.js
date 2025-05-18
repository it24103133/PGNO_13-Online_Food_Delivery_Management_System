document.addEventListener('DOMContentLoaded', function() {
    // Confirm before deleting
    const deleteForms = document.querySelectorAll('form[action*="/restaurants/delete"]');
    deleteForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!confirm('Are you sure you want to delete this restaurant?')) {
                e.preventDefault();
            }
        });
    });

    const searchInput = document.getElementById('restaurantSearch');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            const cards = document.querySelectorAll('.restaurant-card');

            cards.forEach(card => {
                const name = card.querySelector('.card-title').textContent.toLowerCase();
                const cuisine = card.querySelector('.card-text').textContent.toLowerCase();

                if (name.includes(searchTerm) || cuisine.includes(searchTerm)) {
                    card.parentElement.style.display = 'block';
                } else {
                    card.parentElement.style.display = 'none';
                }
            });
        });
    }
});
window.addEventListener('DOMContentLoaded', event => {// Get references to modal elements
    const modal = document.getElementById('warning-myModal');
    const openModalButton = document.getElementById('warning-openModalButton');
    const closeModalIcon = document.getElementById('warning-closeModalIcon');
    const yesButton = document.getElementById('warning-yesButton');
    const noButton = document.getElementById('warning-noButton');
    const warningModalForm = document.getElementById('warning-modal-form');

    if (modal) {
        // Open modal
        if (openModalButton) {
            openModalButton.addEventListener('click', () => {
                const userId = openModalButton.getAttribute('data-user-id');
                const product = openModalButton.getAttribute('data-product-id');
                if (userId) {
                    warningModalForm.action = `/admin/user/delete/${userId}`;
                }
                if (product) {
                    warningModalForm.action = `/admin/product/delete/${product}`;
                }
                modal.style.display = 'flex'; // Show modal
            });

            // Close modal (x icon)
            closeModalIcon.addEventListener('click', () => {
                modal.style.display = 'none'; // Hide modal
            });

            // Handle Yes button click
            yesButton.addEventListener('click', () => {
                modal.style.display = 'none'; // Hide modal
            });

            // Handle No button click
            noButton.addEventListener('click', () => {
                modal.style.display = 'none'; // Hide modal
            });
        }

        // Close modal when clicking outside the modal content
        window.addEventListener('click', (event) => {
            if (event.target === modal) {
                modal.style.display = 'none'; // Hide modal
            }
        });
    }
})
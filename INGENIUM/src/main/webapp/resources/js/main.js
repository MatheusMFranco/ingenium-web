/**
 * Ingenium JS Helper
 */
(function() {
    'use strict';

    const setupMenu = () => {
        const menuToggle = document.querySelector('.menu-toggle');
        if (menuToggle) {
            menuToggle.addEventListener('click', function() {
                document.body.classList.toggle('sidebar-active');
            });
        }
    };

    const setupModals = () => {
        // Lógica de modais nativos se houver
    };

    // Executar ao carregar
    document.addEventListener('DOMContentLoaded', () => {
        setupMenu();
        setupModals();
    });

})();

// PrimeFaces helpers
function handleConfirm(message) {
    const confirmed = confirm(message);
    return confirmed;
}

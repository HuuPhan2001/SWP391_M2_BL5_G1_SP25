document.querySelectorAll('[data-bs-toggle="modal"]').forEach(trigger => {
    trigger.addEventListener('click', function () {
        const categoryId = this.getAttribute('data-whatever');
        document.getElementById('categoryIdDelete').setAttribute("value", categoryId);
        document.getElementById('buttonDelete').setAttribute("href", "delete-category?id=" + categoryId);
    });
});


document.addEventListener('DOMContentLoaded', function () {
    function autoCloseMessage(messageType) {
        const message = document.getElementById('message');
        if (message) {
            setTimeout(() => {
                message.classList.add('fade-out');
                setTimeout(() => message.remove(), 500);
            }, 5000);
        }
    }

    function setupMessageCloseButtons() {
        document.querySelectorAll('.alert .btn-close').forEach(button => {
            button.addEventListener('click', function () {
                const alert = this.closest('.alert');
                alert.classList.add('fade-out');
                setTimeout(() => alert.remove(), 500);
            });
        });
    }

    function restoreFormValues() {
        const failedCategoryJsonInput = document.getElementById('failedCategoryJson');

        if (failedCategoryJsonInput && failedCategoryJsonInput.value) {
            const failedCategory = JSON.parse(failedCategoryJsonInput.value);
            document.getElementById('name').value = failedCategory.name || '';
            document.getElementById('banner').value = failedCategory.banner || '';
            document.getElementById('typeId').value = failedCategory.typeId || '';
            document.getElementById('description').value = failedCategory.description || '';
            document.getElementById('status').value = failedCategory.status || '';

            sessionStorage.removeItem('failedCategory');
        }
    }

    function saveFormValuesBeforeSubmit() {
        const formData = {
            name: document.getElementById('name').value,
            banner: document.getElementById('banner').value,
            typeId: document.getElementById('typeId').value,
            description: document.getElementById('description').value,
            status: document.getElementById('status').value
        };

        sessionStorage.setItem('failedCategory', JSON.stringify(formData));
    }

    const form = document.getElementById('categoryForm');
    if (form) {
        form.addEventListener('submit', saveFormValuesBeforeSubmit);
    }

    autoCloseMessage('success');
    autoCloseMessage('danger');
    setupMessageCloseButtons();
    restoreFormValues();

});


function openDeleteModal(type, id) {
    const iframe = document.getElementById('modalFrame');
    iframe.src = 'DeleteModal.jsp';
    iframe.style.display = 'block';

    iframe.onload = function () {
        iframe.contentWindow.postMessage({type: type, id: id}, '*');
    };
}

window.addEventListener("message", function (event) {
    if (event.data === 'close-modal') {
        document.getElementById('modalFrame').style.display = 'none';
    }
});


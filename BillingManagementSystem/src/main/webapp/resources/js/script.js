$(document).ready(function() {
    if ($('#salesTable').length) {
        $('#salesTable').DataTable({
            order: [[0, 'desc']],
            pageLength: 10,
            lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]]
        });
    }

    if ($('#purchaseTable').length) {
        $('#purchaseTable').DataTable({
            order: [[0, 'desc']],
            pageLength: 10,
            lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]]
        });
    }
});

function toggleTheme() {
    document.body.classList.toggle('dark-theme');
    const isDark = document.body.classList.contains('dark-theme');
    localStorage.setItem('theme', isDark ? 'dark' : 'light');
}

document.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
    }
});

function printBill() {
    window.print();
}

function validateForm(formId) {
    const form = document.getElementById(formId);
    if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
    }
    form.classList.add('was-validated');
}

function updateTotal(row) {
    const quantity = row.querySelector('input[name="quantity"]').value;
    const price = row.querySelector('input[name="price"]').value;
    const total = quantity * price;
    row.querySelector('.total').textContent = total.toFixed(2);

    updateGrandTotal();
}

function updateGrandTotal() {
    const totals = Array.from(document.querySelectorAll('.total'))
        .map(el => parseFloat(el.textContent) || 0);
    const grandTotal = totals.reduce((a, b) => a + b, 0);
    document.getElementById('grandTotal').textContent = grandTotal.toFixed(2);
}
function handleError(message) {
    const alertDiv = document.createElement('div');
    alertDiv.className = 'alert alert-danger alert-dismissible fade show';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.querySelector('.container').prepend(alertDiv);
}
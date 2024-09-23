function toggleTableVisibility() {
    const tableContainer = document.getElementById('tableContainer');
    const flatNumber = document.getElementById('flatNumber').value;
    tableContainer.style.display = flatNumber !== 'default' ? 'block' : 'none';
}


function getDetailsByIdOnChange() {
	
	const flatNumberSelect = document.getElementById('flatNumber');
	const tableContainer = document.getElementById('tableContainer');
    // Check if a flat number is selected
    if (flatNumberSelect.value !== 'default') {
        tableContainer.style.display = 'block'; // Show the container
    } else {
        tableContainer.style.display = 'none'; // Hide the container
    }
    resetFormFields();
    var selectElement = document.getElementById('flatNumber');
    var selectedValue = selectElement.value;
    var tooltip = document.getElementById('tooltip');

    // Hide tooltip initially
    tooltip.style.display = 'none';

    // Check if the selected value is not empty and not "Select"
    if (selectedValue && selectedValue !== "Select") {
        fetch(`/getEntry/${selectedValue}`)
            .then(response => response.json())
            .then(data => {
                if (data) {
                    // Map the response data to the HTML fields
                    document.getElementById('flatType').innerText = data.flatType || '-';
                    document.getElementById('annualMaintenance').innerText = data.annualMaintenance || '0.0';
                    document.getElementById('totaloutstandingAmount').innerText = data.totaloutstandingAmount || '0.0';
                    document.getElementById('paidTillYear').innerText = data.paidTillYear || '0.0';
                    document.getElementById('receivedTillNow').innerText = data.receivedTillNow || '0.0';
                } else {
                    document.getElementById('tooltip').style.display = 'inline';
                }
            })
            .catch((error) => {
                console.error('Error:', error);
                document.getElementById('tooltip').style.display = 'inline';
            });
    } else {
        document.getElementById('tooltip').style.display = 'none';
    }
}

function resetFormFields() {
    document.getElementById("receivedTillNow").value = "0.0";
    document.getElementById("transactionDate").value = "";
    document.getElementById("paymentMethod").value = "";
    document.getElementById("transactionId").value = "";
    document.getElementById("amount").value = "0.0";
}

function formatDateInput(input) {
    const value = input.value.replace(/[^0-9]/g, '');
    const formattedDate = value.replace(/(\d{2})(\d{2})(\d{4})/, '$1-$2-$3');
    input.value = formattedDate;
}
function showAlert(message) {
    const alertContainer = document.getElementById('alertContainer');
    alertContainer.innerHTML = `
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `;
}

function validateAndSubmit() {
    const flatNumber = document.getElementById('flatNumber').value;
    const transactionDate = document.getElementById('transactionDate').value;
    const paymentMethod = document.getElementById('paymentMethod').value;
    const transactionId = document.getElementById('transactionId').value;
    const amount = document.getElementById('amount').value;

    // Validation
    if (flatNumber === 'default') {
        showAlert('Please select a valid flat number.');
        return;
    }
    if (!transactionDate) {
        showAlert('Please enter the transaction date.');
        return;
    }
    if (paymentMethod === 'default') {
        showAlert('Please select a payment method.');
        return;
    }
    if (!transactionId) {
        showAlert('Please enter the transaction ID.');
        return;
    }
    if (!amount || amount <= 0) {
        showAlert('Please enter a valid amount.');
        return;
    }

    submitPaymentEntryForm();
}

function submitPaymentEntryForm() {
	
	
    // Get form values
    const flatNumber = document.getElementById('flatNumber').value;
    const transactionDate = document.getElementById('transactionDate').value;
    const paymentMethod = document.getElementById('paymentMethod').value;
    const transactionId = document.getElementById('transactionId').value;
    const amount = parseFloat(document.getElementById('amount').value);

    const formattedTransactionDate = convertToISODate(transactionDate);
    
    const jsonData = {
        flatNumber: parseInt(flatNumber),
        date: formattedTransactionDate, 
        paymentMethod: paymentMethod,
        transactionId: transactionId,
        amount: amount,
    };


    fetch('/savePaymentForApproval', { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    })
    .then(async response => {
        // Check if the response is OK (status 200-299)
        if (response.ok) {
            return response.json(); // Parse the JSON only if the response is OK
        }

        // Handle specific error status codes
        if (response.status === 409) {
            alert('This entry already exists. Please check your submission.');
            return; // Exit early
        }

        // If we reach here, it's some other error
        const errorData = await response.text(); // Use text() for non-JSON responses
        throw new Error(`Error ${response.status}: ${errorData}`);
    })
    .then(data => {
        console.log('Success:', data);
        showSuccessAlert('Maintenance entry submitted! Your Payment reference id: ' + data.id);

        // Clear the alert if present
        const alertContainer = document.getElementById('alertContainer');
        if (alertContainer) {
            alertContainer.innerHTML = '';
        }

        // Safely update the elements if they exist
        const annualMaintenanceElem = document.getElementById('annualMaintenance');
        if (annualMaintenanceElem) {
            annualMaintenanceElem.innerText = data.annualMaintenance || '0.0';
        }

        const paidTillYearElem = document.getElementById('paidTillYear');
        if (paidTillYearElem) {
            paidTillYearElem.innerText = data.paidTillYear || '-';
        }

        const receivedTillNowElem = document.getElementById('receivedTillNow');
        if (receivedTillNowElem) {
            receivedTillNowElem.innerText = data.receivedTillNow || '-';
        }

        const paidInYearElem = document.getElementById('paidInYear');
        if (paidInYearElem) {
            paidInYearElem.innerText = data.paidInYear || '0.0';
        }

        const transactionDateElem = document.getElementById('transactionDate');
        if (transactionDateElem) {
            transactionDateElem.value = '';
        }

        const paymentMethodElem = document.getElementById('paymentMethod');
        if (paymentMethodElem) {
            paymentMethodElem.value = '';
        }

        const transactionIdElem = document.getElementById('transactionId');
        if (transactionIdElem) {
            transactionIdElem.value = '';
        }

        const amountElem = document.getElementById('amount');
        if (amountElem) {
            amountElem.value = '0.0';
        }
    })
    .catch((error) => {
        console.error('Error:', error);
        alert('Failed to submit maintenance entry');
    });



}

function convertToISODate(inputDate) {
    if (!inputDate) return null;

    // Split the input date by "-"
    const [day, month, year] = inputDate.split('-');

    // Reformat the date to "YYYY-MM-DD"
    const formattedDate = `${year}-${month}-${day}`;

    // Create a Date object
    const dateObject = new Date(formattedDate);

    // Check if the date is valid
    if (isNaN(dateObject.getTime())) {
        console.error('Invalid date');
        return null;
    }

    // Return the ISO string format
    return dateObject.toISOString();
}
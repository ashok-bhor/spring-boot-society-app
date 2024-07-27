// Initialize formData object to store entered data
let formData = {
    ownerName: ''
};


function getPaidAmountForYear() {
    const flatNumber = document.getElementById('flatNumber').value;
    const year = document.getElementById('year').value;

    if (flatNumber && year) {
        fetch(`http://localhost:8080/getPaidAmountForYear?flatNumber=${flatNumber}&year=${year}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('paidInYear').textContent = data.amount;
            })
            .catch(error => {
                console.error('Error fetching paid amount:', error);
            });
    } else {
        document.getElementById('paidInYear').textContent = '0.0';
    }
}

function handleSelectionChange() {
    var selectElement = document.getElementById('flatNumber');
    var selectedValue = selectElement.value;
    var tooltip = document.getElementById('tooltip');

    // Hide tooltip initially
    tooltip.style.display = 'none';

    // Check if the selected value is not empty and not "Select"
    if (selectedValue && selectedValue !== "Select") {
        fetch(`http://localhost:8080/getSme/${selectedValue}`)
            .then(response => response.json())
            .then(data => {
                if (data) {
                    // Map the response data to the HTML fields
                    document.getElementById("year").value = data.year;
                    document.getElementById('flatType').innerText = data.flatType || '-';
                    document.getElementById('annualMaintenance').innerText = data.annualMaintenance || '0.0';
                    document.getElementById('previousOutstanding').innerText = data.previousOutstanding || '0.0';
                    document.getElementById('paidTillYear').innerText = data.paidTillYear || '0.0';
                    document.getElementById('receivedTillNow').innerText = data.receivedTillNow || '0.0';
                    document.getElementById('paidInYear').innerText = data.paidInYear || '0.0';
                    document.getElementById('date').value = data.date || '';
                    document.getElementById('paymentMethod').value = data.paymentMethod || '';
                    document.getElementById('transactionId').value = data.transactionId || '';
                    document.getElementById('amount').value = data.amount || '0.0';
                    document.getElementById('verified').value = data.verified ? 'true' : 'false';
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

function handleSelectionChangeOldWorking() {
    var selectElement = document.getElementById('flatNumber');
    var selectedValue = selectElement.value;
    var tooltip = document.getElementById('tooltip');

    // Hide tooltip initially
    tooltip.style.display = 'none';

    // Check if the selected value is not empty and not "Select"
    if (selectedValue && selectedValue !== "Select") {
        fetch(`http://localhost:8080/getSme/${selectedValue}`)
            .then(response => response.json())
            .then(data => {
                if (data) {
                    // Map the response data to the HTML fields
                    document.getElementById("year").value = data.year;
                    document.getElementById('flatType').innerText = data.flatType || '-';
                    document.getElementById('annualMaintenance').innerText = data.annualMaintenance || '0.0';
                    document.getElementById('previousOutstanding').innerText = data.previousOutstanding || '0.0';
                    document.getElementById('paidTillYear').innerText = data.paidTillYear || '0.0';
                    document.getElementById('receivedTillNow').innerText = data.receivedTillNow || '0.0';
                    document.getElementById('paidInYear').innerText = data.paidInYear || '0.0';
                    document.getElementById('date').value = data.date || '';
                    document.getElementById('paymentMethod').value = data.paymentMethod || '';
                    document.getElementById('transactionId').value = data.transactionId || '';
                    document.getElementById('amount').value = data.amount || '0.0';
                    document.getElementById('verified').value = data.verified ? 'true' : 'false';
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


function formatDate(dateString) {
    // Format date to dd-MM-yyyy
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
}

function resetFormFields() {
    document.getElementById("year").value = "";
    document.getElementById("previousOutstanding").value = "0.0";
    document.getElementById("receivedTillNow").value = "0.0";
    document.getElementById("date").value = "";
    document.getElementById("paymentMethod").value = "";
    document.getElementById("transactionId").value = "";
    document.getElementById("amount").value = "0.0";
    document.getElementById("verified").value = "false";
}

function submitForm() {

    // Get form values
    const flatNumber = document.getElementById('flatNumber').value;
    const year = document.getElementById('year').value;
    const flatType = document.getElementById('flatType').value;
    const annualMaintenance = document.getElementById('annualMaintenance').value;
    const previousOutstanding = document.getElementById('previousOutstanding').value;
    const receivedTillNow = document.getElementById('receivedTillNow').value;
    const paidInYear = document.getElementById('paidInYear').value;
    const paidTillYear = document.getElementById('paidTillYear').value;
    const date = document.getElementById('date').value;
    const paymentMethod = document.getElementById('paymentMethod').value;
    const transactionId = document.getElementById('transactionId').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const verified = document.getElementById('verified').value === 'true';

    let dateObject = null;
    let formattedDate = null;

    if (!date) {
        console.log('Date variable is null or undefined or falsy.');
    } else {
        // Split the date string into day, month, and year parts
        let parts = date.split('-');
        
        // Ensure parts array has day, month, and year
        if (parts.length === 3) {
            // Construct Date object (months are 0-based in JavaScript)
            let dateObject = new Date(parts[2], parts[1] - 1, parts[0]);
    
            // Validate if the dateObject is valid
            if (isNaN(dateObject.getTime())) {
                console.log('Invalid date');
            } else {
                // Extract day, month, and year
                let day = dateObject.getDate().toString().padStart(2, '0'); // Ensure two digits for day
                let month = (dateObject.getMonth() + 1).toString().padStart(2, '0'); // Month is zero-based
                let year = dateObject.getFullYear();
    
                // Create formatted date string
                 formattedDate = `${day}-${month}-${year}`;
                            }
        } else {
            console.log('Invalid date format');
        }
    }
    const jsonData = {
        flatNumber: parseInt(flatNumber),
        year: year,
        flatType: flatType, // Make sure to provide the flatType value
        previousOutstanding: previousOutstanding,
        annualMaintenance: annualMaintenance,
        receivedTillNow: receivedTillNow,
        paidTillYear: paidTillYear,
        paidInYear: paidInYear,
        date: formattedDate, // Ensure date is in the correct format "dd-MM-yyyy"
        paymentMethod: paymentMethod,
        transactionId: transactionId,
        amount: amount,
        verified: verified
    };

    alert(JSON.stringify(jsonData));
    fetch('http://localhost:8080/smeSave', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    })
        .then(async response => {
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(`Error ${response.status}: ${errorData.message}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            alert('Maintenance entry submitted successfully!');

            // Map the response data to the HTML fields        
            const yearDropdown = document.getElementById('year');
            for (let i = 0; i < yearDropdown.options.length; i++) {
                if (yearDropdown.options[i].value === data.paidTillYear) {
                    yearDropdown.selectedIndex = i;
                    break;
                }
            }
            document.getElementById('flatType').innerText = data.flatType || '-';
            //  document.getElementById('year').innerText = data.paidTillYear || '-';
            document.getElementById('annualMaintenance').innerText = data.annualMaintenance || '0.0';
            document.getElementById('previousOutstanding').innerText = data.previousOutstanding || '0.0';
            document.getElementById('paidTillYear').innerText = data.paidTillYear || '-';
            document.getElementById('receivedTillNow').innerText = data.receivedTillNow || '-';
            document.getElementById('paidInYear').innerText = data.paidInYear || '0.0';
            document.getElementById('date').value = data.date || '';
            document.getElementById('paymentMethod').value = '';
            document.getElementById('transactionId').value = '';
            document.getElementById('amount').value = '0.0';
            document.getElementById('verified').value = data.verified || 'false';
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Failed to submit maintenance entry');
        });
}


function submitExtraCharges() {
    // Get form values
    const flatNumber = document.getElementById('flatNumber').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const reason = document.getElementById('reason').value;
    const comments = document.getElementById('comments').value;

    // Construct JSON object
    const jsonData = {
        flatNumber: parseInt(flatNumber),
        amount: amount,
        reason: reason,
        comments: comments
    };

    // Send data to Spring Boot backend via AJAX
    fetch('http://localhost:8080/saveExtraCharges', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            showAlert('Extra charges added successfully!', 'success');
            // Optionally clear form fields or perform other actions
        })
        .catch((error) => {
            console.error('Error:', error);
            showAlert('Failed to add extra charges', 'danger');
        });
}

function showAlert(message, type) {
    const alertPlaceholder = document.getElementById('alertPlaceholder');
    const alertHTML = `<div class="alert alert-tooltip alert-${type} fade show" role="alert">
    ${message}
</div>`;
    
    alertPlaceholder.innerHTML = alertHTML;

    // Automatically close alert after 3 seconds
    setTimeout(() => {
        const alertElement = alertPlaceholder.querySelector('.alert');
        if (alertElement) {
            const alert = new bootstrap.Alert(alertElement);
            alert.close();
        }
    }, 3000);
}




// Function to export data
function exportData() {
    // Perform export action (e.g., export to Excel)
    console.log('Exporting data:', formData);

    // Clear formData object after export, keeping ownerName
    formData = {
        ownerName: formData.ownerName
    };

    // Clear display or show confirmation message
    alert('Data exported successfully!');
}

// Function to preview entered data (show in modal or alert)
function previewData() {
    let previewContent = '';

    // Check if there are entries to preview
    if (formData.entries && formData.entries.length > 0) {
        formData.entries.forEach((entry, index) => {
            previewContent += `Entry ${index + 1}: \n`;
            previewContent += `Year: ${entry.year}\n`;
            previewContent += `Flat Type: ${entry.flatType}\n`;
            previewContent += `Annual Maintenance: ${entry.annualMaintenance}\n`;
            previewContent += `Previous Outstanding: ${entry.previousOutstanding}\n`;
            previewContent += `Total Outstanding: ${entry.totalOutstanding}\n`;
            previewContent += `Actual Outstanding: ${entry.actualOutstanding}\n`;
            previewContent += `Date: ${entry.date}\n`;
            previewContent += `Payment Method: ${entry.paymentMethod}\n`;
            previewContent += `Transaction ID: ${entry.transactionId}\n`;
            previewContent += `Verified: ${entry.verified}\n\n`;
        });
    } else {
        previewContent = 'No entries to preview.';
    }

    // Display preview content (could be shown in a modal or alert)
    alert(previewContent);
}

// Define an array of year ranges
let yearRanges = [
    "2016-17",
    "2017-18",
    "2018-19",
    "2019-20",
    "2020-21",
    "2021-22",
    "2022-23",
    "2023-24",
    "2024-25"
];



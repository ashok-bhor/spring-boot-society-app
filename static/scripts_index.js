document.addEventListener('DOMContentLoaded', function () {
    // Get the username from the span
    window.username = document.getElementById('userName').textContent.trim();

    // Access the link element
    var userLink = document.getElementById('userLink');

    // Update the link text and title attribute with the username
    userLink.textContent = window.username;
    userLink.setAttribute('title', window.username);
});

const flatNumbers = [101, 102, 103, 104, 201, 202, 203, 204, 301, 302, 303, 304, 401, 402, 403, 404, 501, 502, 503, 504, 601, 602, 603, 604, 701, 702, 703, 704];

// Define an array of year ranges
const yearRanges = ["2016-17", "2017-18", "2018-19", "2019-20", "2020-21", "2021-22", "2022-23", "2023-24", "2024-25"];
const verfiedArray = ["Yes", "No"];
const paymentMethods = ["Cheque", "IMPS", "UPI", "Cash"];


// Initialize formData object to store entered data
let formData = {
    ownerName: ''
};

function showSuccessAlert(msg) {
    Swal.fire({
        title: 'Success!',
        text: msg,
        icon: 'success',
        confirmButtonText: 'OK'
    });
}
function showErrorAlert(msg) {
    Swal.fire({
        title: 'Error!',
        text: msg,
        icon: 'error',	
        confirmButtonText: 'OK'
    });
}

function populateDropdown() {
    // Get the select element by ID
    const flatNumber = document.getElementById('flatNumber');

    // Check if the select element exists
    if (flatNumber) {
        // Clear existing options except the first one
        flatNumber.innerHTML = '<option value="default">Select Flat Number</option>';

        // Create options and append them to the select element
        flatNumbers.forEach(number => {
            const option = document.createElement('option');
            option.value = number;
            option.textContent = number;
            flatNumber.appendChild(option);
        });
    } else {
        console.error('Dropdown element with ID "flatNumber" not found.');
    }
}
function showDashboardRejectedPayments() {
	  // Get the selected flat number from the dropdown
	  const flatNumber = document.getElementById('flatNumberSelect').value;

	  // Check if a flat number is selected
	  if (flatNumber === 'default') {
	    alert('Please select a flat number.');
	    return;
	  }

	  // Fetch rejected payments based on the selected flat number
	  fetch(`/getRejectedPayments/${flatNumber}`)
	    .then(response => {
	      if (!response.ok) {
	        throw new Error('Network response was not ok');
	      }
	      return response.text(); // Get response as text
	    })
	    .then(text => {
	      try {
	        const data = JSON.parse(text); // Try to parse JSON
	        const container = document.getElementById('dashboardRejectedPaymentTableContainer');

	        // Clear previous content
	        container.innerHTML = '';

	        if (data.length === 0) {
	          container.innerHTML = '<p>No rejected payments available.</p>';
	        } else {
	          // Create a table and populate it with data
	          const table = document.createElement('table');
	          table.className = 'table table-bordered'; // Apply Bootstrap table styling
	          table.innerHTML = `
	            <thead>
	              <tr>
	                <th>Payment Id</th>
	                <th>Date</th>
	                <th>Payment Method</th>
	                <th>Transaction ID</th>
	                <th>Amount</th>
	                <th>Reject Reason</th>
	              </tr>
	            </thead>
	            <tbody>
	              ${data.map(payment => `
	                <tr>
	                  <td>${payment.id}</td>
	                  <td>${new Date(payment.date).toLocaleDateString()}</td>
	                  <td>${payment.paymentMethod}</td>
	                  <td>${payment.transactionId}</td>
	                  <td>${payment.amount.toFixed(2)}</td>
	                  <td>${payment.rejectReason}</td>
	                </tr>
	              `).join('')}
	            </tbody>
	          `;
	          container.appendChild(table);
	        }

	        // Show the modal using Bootstrap 5's modal method
	        const dashboardRejectedPaymentModal = new bootstrap.Modal(document.getElementById('dashboardRejectedPaymentModal'));
	        dashboardRejectedPaymentModal.show();
	      } catch (error) {
	        console.error('Error parsing JSON:', error);
	        document.getElementById('dashboardRejectedPaymentTableContainer').innerHTML = '<p>Error parsing rejected payments data.</p>';
	      }
	    })
	    .catch(error => {
	      console.error('Error fetching rejected payments:', error);
	      document.getElementById('dashboardRejectedPaymentTableContainer').innerHTML = '<p>Error loading rejected payments.</p>';
	    });
	}
function downloadReciept() {
    fetch('/downloadPdf')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.blob(); // Get the response as a blob (binary large object)
        })
        .then(blob => {
            // Create a link element
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob); // Create a URL for the blob
            link.download = 'filled_Receipt_format.pdf'; // Specify the download filename
            link.click(); // Trigger the download
            URL.revokeObjectURL(link.href); // Clean up the URL object
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}



function getPaidAmountForYear() {
    const flatNumber = document.getElementById('flatNumber').value;
    const year = document.getElementById('year').value;

    if (flatNumber && year) {
        fetch(`/getPaidAmountForYear?flatNumber=${flatNumber}&year=${year}`)
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

function getFlatType() {
    const flatNumber = document.getElementById('flatNumber').value;
    const url = `/getFlatType/${flatNumber}`;

    try {
        const response = fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const flatType = response.text();
        console.log("Flat type is:", flatType);
        return flatType;
    } catch (error) {
        console.error('Error fetching flat type:', error);
    }
    getPaidAmountForYear();
}


function validateMaintenanceEntry() {
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
                let day = dateObject.getDate().toString().padStart(2, '0'); // Ensure
                // two
                // digits
                // for
                // day
                let month = (dateObject.getMonth() + 1).toString().padStart(2, '0'); // Month
                // is
                // zero-based
                let year = dateObject.getFullYear();

                // Create formatted date string
                formattedDate = `${day}-${month}-${year}`;
            }
        } else {
            console.log('Invalid date format');
        }
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






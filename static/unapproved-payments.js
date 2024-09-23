//Function to fetch unapproved payments and update the table
function getPaymentsByIdOnChange() {


	if (!toggleTableVisibility()) {
		// Return from the main function if isVisible is false
		return;
	}
	console.log('Fetching payments...');
	var flatNumber = document.getElementById('flatNumberSelectUp').value;
	fetch(`/getUnapprovedPayments?flatNumber=${flatNumber}`)
		.then(response => response.json())
		.then(data => {
			const tableBody = document.querySelector('#paymentsTable');
			tableBody.innerHTML = ''; // Clear existing rows

			if (data.length === 0) {
				// Display the message if no payments are returned
				tableBody.innerHTML = `
				<tr>
				<td colspan="5" style="text-align: center; font-weight: bold; background-color:light-green">
				No payments to approve
				</td>
				</tr>
				`;
			} else {
				// Populate the table with payment data
				data.forEach(payment => {
					const row = document.createElement('tr');
					row.innerHTML = `
					<input type="hidden" name="paymentId" id="paymentId" value=${payment.id}>
					<td>${payment.id}</td>
					<td>${payment.flatNumber}</td>
					<td>${new Intl.DateTimeFormat('en-GB').format(new Date(payment.date))}</td>
					<td>${payment.paymentMethod}</td>
					<td>${payment.transactionId}</td>
					<td>${payment.amount}</td>
					<td>
					<button class="btn btn-success" onclick="approvePayment(${payment.id})">Approve</button>
					<button class="btn btn-danger" onclick="openRejectModal(${payment.id})">Reject</button>
					</td>
					`;
					tableBody.appendChild(row);
				});
			}
		})
		.catch(error => {
			console.error('Error fetching payments:', error);
		});
}

function showUnapprovedPayments() {
	turnOnUnapprovedTable();

	console.log('Fetching payments...');
	var flatNumber = document.getElementById('flatNumberSelectUp').value;
	fetch(`/getAllUnapprovedPayments`)
		.then(response => response.json())
		.then(data => {
			const tableBody = document.querySelector('#paymentsTable');
			tableBody.innerHTML = ''; // Clear existing rows
			if (data.length === 0) {
				// Display the message if no payments are returned
				tableBody.innerHTML = `
				<tr>
				<td colspan="5" style="text-align: center; font-weight: bold; background-color:light-green">
				No payments to approve
				</td>
				</tr>
				`;
			} else {
				// Populate the table with payment data
				data.forEach(payment => {
					const row = document.createElement('tr');
					row.innerHTML = `
					<input type="hidden" name="paymentId" id="paymentId" value=${payment.id}>
					<td>${payment.id}</td>
					<td>${payment.flatNumber}</td>
					<td>${new Intl.DateTimeFormat('en-GB').format(new Date(payment.date))}</td>
					<td>${payment.paymentMethod}</td>
					<td>${payment.transactionId}</td>
					<td>${payment.amount}</td>
					<td>
					<button class="btn btn-success" onclick="approvePayment(${payment.id})">Approve</button>
					<button class="btn btn-danger" onclick="openRejectModal(${payment.id})">Reject</button>
					</td>
					`;
					tableBody.appendChild(row);
				});
			}
		})
		.catch(error => {
			console.error('Error fetching payments:', error);
		});
}


function approvePayment(paymentId) {
	const actionDetails = getActionDetails(username);

	// Extract details from actionDetails
	const { actionedBy, actionedTimestamp, actionersUserAgentDetails } = actionDetails;
	if (confirm('Are you sure you want to approve this payment?')) {
		fetch(`/approvePayment/${paymentId}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				action: 'Approved',
				actionedBy: actionedBy,
				actionedTimestamp: actionedTimestamp,
				actionersUserAgentDetails: actionersUserAgentDetails
			})
		})
			.then(response => {
				if (response.ok) {
					showSuccessAlert('Payment approved successfully!');
					getPaymentsByIdOnChange(); // Refresh the payments list
				} else {
					// Handle specific error status codes
			        if (response.status === 409) {
			        	showErrorAlert('This entry already exists. Please check your submission.');
			            return; // Exit early
			        }
					showErrorAlert('Failed to approve payment.');
				}
			})
			.catch(error => {
				console.error('Error approving payment:', error);
			});
	}
}

function openRejectModal(paymentId) {
	$('#rejectModal').modal('show');
	document.getElementById('rejectModal').setAttribute('data-payment-id', paymentId);
}

function handleRejectReasonChange() {
	const reasonDropdown = document.getElementById('rejectReasonDropdown');
	const otherReasonContainer = document.getElementById('otherReasonContainer');
	const otherRejectReason = document.getElementById('otherRejectReason');

	if (reasonDropdown.value === 'Other') {
		otherReasonContainer.style.display = 'block';
		otherRejectReason.setAttribute('required', 'required'); // Set required
		// attribute
	} else {
		otherReasonContainer.style.display = 'none';
		otherRejectReason.removeAttribute('required'); // Remove required
		// attribute
	}
}

function submitReject() {
	const paymentId = document.getElementById('rejectModal').getAttribute('data-payment-id');
	const reasonDropdown = document.getElementById('rejectReasonDropdown');
	const otherRejectReason = document.getElementById('otherRejectReason').value;
	const selectedReason = reasonDropdown.value;

	// Use the otherRejectReason if "Other" is selected
	const rejectReason = selectedReason === 'Other' ? otherRejectReason : selectedReason;

	// Validate the form
	if (!selectedReason) {
		showErrorAlert('Please select a reason for rejection.');
		return;
	}
	if (selectedReason === 'Other' && !otherRejectReason) {
		showErrorAlert('Please specify the reason.');
		return;
	}

	const actionDetails = getActionDetails(username);

	// Extract details from actionDetails
	const { actionedBy, actionedTimestamp, actionersUserAgentDetails } = actionDetails;

	fetch(`/rejectPayment/${paymentId}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			action: 'Rejected',
			rejectReason: rejectReason,
			actionedBy: actionedBy,
			actionedTimestamp: actionedTimestamp,
			actionersUserAgentDetails: actionersUserAgentDetails
		})
	})
		.then(response => {
			if (response.ok) {
				showSuccessAlert('Payment rejected successfully!');
				$('#rejectModal').modal('hide');
				getPaymentsByIdOnChange(); // Refresh the payments list
			} else {
				showErrorAlert('Failed to reject payment.');
			}
		})
		.catch(error => {
			console.error('Error rejecting payment:', error);
		});
}

//Ensure you include Bootstrap 5's JavaScript library in your HTML

function showRejectedPayments() {
  const flatNumber = document.getElementById('flatNumberSelectUp').value;

  if (flatNumber === "default") {
    alert('Please select a flat number.');
    return;
  }

  // Fetch rejected payments based on flat number
  fetch(`/getRejectedPayments?flatNumber=${flatNumber}`)
    .then(response => response.json())
    .then(data => {
      if (data.length === 0) {
        alert('No rejected payments found for this flat number.');
        return;
      }

      // Assuming you only need to show details of the first rejected payment
      const rejectedPayment = data[0];
      document.getElementById('modalFlatNumber').textContent = rejectedPayment.flatNumber;
      document.getElementById('modalDate').textContent = new Date(rejectedPayment.date).toLocaleDateString();
      document.getElementById('modalPaymentMethod').textContent = rejectedPayment.paymentMethod;
      document.getElementById('modalTransactionId').textContent = rejectedPayment.transactionId;
      document.getElementById('modalAmount').textContent = rejectedPayment.amount.toFixed(2);
      document.getElementById('modalRejectReason').textContent = rejectedPayment.rejectReason;

      // Show the modal using Bootstrap 5's modal method
      var myModal = new bootstrap.Modal(document.getElementById('rejectedPaymentModal'));
      myModal.show();
    })
    .catch(error => {
      console.error('Error fetching rejected payments:', error);
    });
}


function toggleTableVisibility() {
	const tableContainer = document.getElementById('unApprovedTable');
	const buttonShowRejected = document.getElementById('buttonShowRejected');
	const flatNumber = document.getElementById('flatNumberSelectUp').value;
	let isVisible;

	if (flatNumber !== 'default') {
		tableContainer.style.visibility = 'visible';
		tableContainer.style.height = 'auto';
		buttonShowRejected.style.display = 'block';
		isVisible = true;
	} else {
		tableContainer.style.visibility = 'hidden';
		tableContainer.style.height = '0';
		buttonShowRejected.style.display = 'none';
		isVisible = false;
	}

	return isVisible;
}

function turnOnUnapprovedTable() {
	const tableContainer = document.getElementById('unApprovedTable');
	let isVisible;

		tableContainer.style.visibility = 'visible';
		tableContainer.style.height = 'auto';
		isVisible = true;

	return isVisible;
}

function showRejectedPayments() {
	const flatNumber = document.getElementById('flatNumberSelectUp').value;
	if (flatNumber !== 'default') {
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
					const container = document.getElementById('rejectedPaymentTableContainer');
					if (data.length === 0) {
						container.innerHTML = '<p>No rejected payments available.</p>';
					} else {
						const table = document.createElement('table');
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
						container.innerHTML = ''; // Clear previous content
						container.appendChild(table);
					}
					$('#rejectedPaymentModal').modal('show');
				} catch (error) {
					console.error('Error parsing JSON:', error);
					document.getElementById('rejectedPaymentTableContainer').innerHTML = '<p>Error parsing rejected payments data.</p>';
				}
			})
			.catch(error => {
				console.error('Error fetching rejected payments:', error);
				document.getElementById('rejectedPaymentTableContainer').innerHTML = '<p>Error loading rejected payments.</p>';
			});
	} else {
		showErrorAlert('Please select a flat number.');
	}
}

function formatCaptureDate(date) {
	const day = String(date.getDate()).padStart(2, '0');
	const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are
	// 0-based
	const year = date.getFullYear();

	const hours = String(date.getHours()).padStart(2, '0');
	const minutes = String(date.getMinutes()).padStart(2, '0');
	const seconds = String(date.getSeconds()).padStart(2, '0');

	return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
}


// Function to get action details
function getActionDetails(username) {
	// Get the current date and format it
	const currentDate = new Date();
	const actionedTimestamp = formatDate(currentDate);

	// Get the User-Agent details
	const actionersUserAgentDetails = navigator.userAgent;

	// Return the action details object
	return {
		actionedBy: username,
		actionedTimestamp: actionedTimestamp,
		actionersUserAgentDetails: actionersUserAgentDetails
	};
}


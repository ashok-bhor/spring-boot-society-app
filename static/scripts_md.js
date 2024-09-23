if (typeof window.rowsPerPage === 'undefined') {
        window.rowsPerPage = 10; // Initialize only if not already defined
    }

document.addEventListener('DOMContentLoaded', () => {
    const initialFlatNumber = document.querySelector('#flatNumberSelect').value;
    
    if (initialFlatNumber !== 'default') {
        initializePagination(initialFlatNumber, rowsPerPage);
    }
});


function fetchDashBoard() {
	const selectedValue = flatNumberSelect.value;
    const contentDiv = document.getElementById('content');
    const paymentHistoryDiv = document.getElementById('paymentHistoryContent');
    const buttonShowRejectedOnMD = document.getElementById('buttonShowRejectedOnMD');
    
    if (selectedValue === 'default') {
        contentDiv.style.display = 'none'; // Hide content if 'default' is
											// selected
        paymentHistoryDiv.style.display='none';
        buttonShowRejectedOnMD.style.display='none';
    } else {
        contentDiv.style.display = 'block'; // Show content if any valid value
											// is selected
        paymentHistoryDiv.style.display='block';
        buttonShowRejectedOnMD.style.display='block';
        fetchData(); // Call fetchData function if needed
    }
}

// Function to fetch data based on selected flat number
function fetchData() {
    var flatNumber = $('#flatNumberSelect').val();
    if (flatNumber === "default") {
        $('#maintenanceDashboardBody').empty().append(
            `<tr>
                <td colspan="7" class="text-center">Select flat number to see data</td>
            </tr>`
        );
        $('#paymentHistoryBody').empty().append(
            `<tr>
                <td colspan="9" class="text-center">Select flat number to see data</td>
            </tr>`
        );
        return; // Exit the function early
    }

    // Call functions to fetch payment history and maintenance dashboard data
    // fetchPaymentHistory(flatNumber);
    fetchMaintenanceDashboard(flatNumber);
}

function displayRows(data, page = 1) {
	 const table = document.querySelector("#paymentHistoryBody");
    table.innerHTML = '';
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const rows = (page === 'all') ? data : data.slice(start, end);

    if (rows.length === 0) {
        table.innerHTML = `<tr><td colspan="9" class="text-center">No data available</td></tr>`;
        return;
    }

    rows.forEach(row => {
        const actionButtons = userRole === 'ADMIN' ? `
            <td>
                <button class="btn btn-primary btn-sm edit-btn" data-id="${row.id}">Edit</button>
                <button class="btn btn-danger btn-sm delete-btn" onclick="openDeleteModalOnHistory(${row.id})">Delete</button>
                <button class="btn btn-secondary btn-sm cancel-btn" style="display:none;">Cancel</button>
            </td>` : '';
        const tr = document.createElement('tr');
        tr.setAttribute('data-id', row.id);
        tr.innerHTML = `
        	<td>${row.id}</td>
            <td>${row.year}</td>
            <td>${row.amount}</td>
            <td>${row.flatType}</td>
            <td>${row.annualMaintenance}</td>
            <td>${new Intl.DateTimeFormat('en-GB').format(new Date(row.date))}</td>
            <td>${row.paymentMethod}</td>
            <td>${row.transactionId}</td>
            <td style="text-align: center;"> <button class="btn btn-sm download-btn"><i class="fas fa-file-download" style="font-size: 24px;"></i></button></td>
            ${actionButtons}
        `;
        table.appendChild(tr);
    });
}

function displayPagination(data) {
    pagination.innerHTML = '';
    const pageCount = Math.ceil(data.length / rowsPerPage);

    for (let i = 1; i <= pageCount; i++) {
        const li = document.createElement('li');
        li.classList.add('page-item');
        if (i === 1) li.classList.add('active');
        li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
        pagination.appendChild(li);

        li.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelectorAll('.pagination li').forEach(li => li.classList.remove('active'));
            li.classList.add('active');
            displayRows(data, i);
        });
    }

    // Add "Load All" option
    const liAll = document.createElement('li');
    liAll.classList.add('page-item');
    liAll.innerHTML = `<a class="page-link" href="#">Load All</a>`;
    pagination.appendChild(liAll);

    liAll.addEventListener('click', function(e) {
        e.preventDefault();
        document.querySelectorAll('.pagination li').forEach(li => li.classList.remove('active'));
        liAll.classList.add('active');
        displayRows(data, 'all');
    });
}



// Function to fetch payment history
function fetchPaymentHistory(flatNumber, callback) {
    $.ajax({
        url: '/getPaidHistory/' + flatNumber,
        method: 'GET',
        success: function(data) {
            if (data.length > 0) {
                callback(data); // Call the callback function with the data
            } else {
                $('#paymentHistoryBody').empty().append(
                    `<tr>
                        <td colspan="9" class="text-center text-danger">No payment history entries found</td>
                    </tr>`
                );
            }
        },
        error: function(error) {
            console.error('Error fetching payment history:', error);
            $('#paymentHistoryBody').empty().append(
                `<tr>
                    <td colspan="9" class="text-center text-danger">Error fetching data</td>
                </tr>`
            );
        }
    });
}

function initializePagination(flatNumber, rowsPerPage = 10) {
    // Fetch payment history and initialize pagination
    fetchPaymentHistory(flatNumber, function(data) {
        displayRows(data);
        displayPagination(data);
    });
}

// Event listener for flat number selection change
document.querySelector('#flatNumberSelect').addEventListener('change', function() {
    const flatNumber = this.value;
    if (flatNumber !== 'default') {
        initializePagination(flatNumber); // Use the selected flat number
    }
});


// Helper function to format currency values
function formatCurrency(amount) {
    return amount.toLocaleString('en-IN', { style: 'currency', currency: 'INR' }).replace('₹', '');
}

function fetchMaintenanceDashboard(flatNumber) {
	console.log("fetchMaintenanceDashboard");
    $.ajax({
        url: '/getMaintenanceDashboardCard/' + flatNumber,
        method: 'GET',
        success: function(data) {
            // Clear existing content
            $('#content').empty();

            // Ensure data is an array
            if (!Array.isArray(data)) {
                data = [data]; // Wrap in an array if it's not
            }

            // Check if data is empty
            if (data.length === 0) {
                $('#content').append(
                    `<div class="alert alert-danger">No maintenance data found</div>`
                );
                return;
            }

            // Append cards to the content
            const rows = [[], []]; // Two rows for the layout
            console.log('data:'+data)
            data.forEach((card, index) => {
                const rowIndex = Math.floor(index / 3); // Distribute cards into
														// rows
                rows[rowIndex].push(card);
            });

            
            console.log('rows:'+rows[0]);
            // Append rows to the content
            rows.forEach(row => {
                const rowHtml = row.map(card => `
                    <div class="col card-container"> 
                        <div class="card rounded-3 shadow-sm">
                            <div class="card-header text-center" style="background-color: ${card.cardColor}; color: #ffffff;">
                                ${card.header}
                            </div>
                            <div class="card-body text-center">
                               
                                ${card.extraChargesLink ? `<a href="#" class="extra-charges-link" data-flat-number="${flatNumber}">${card.value}</a>` : `${card.value}`}
                            </div>
                        </div>
                    </div>
                `).join('');

                $('#content').append(`<div class="row mb-4">${rowHtml}</div>`);
            });

            // Show content
            $('#content').show();

            // Add event listener for extra charges link if applicable
            $('.extra-charges-link').click(function(e) {
                e.preventDefault();
                const flatNumber = $(this).data('flat-number');
                fetchExtraCharges(flatNumber);
                $('#extraChargesModal').modal('show');
            });
        },
        error: function(error) {
            console.error('Error fetching maintenance dashboard data:', error);
            $('#content').empty().append(
                `<div class="alert alert-danger">Error fetching data</div>`
            );
        }
    });
}

function fetchExtraCharges(flatNumber) {
    // Example AJAX call to fetch extra charges data
    $.ajax({
        url: '/getExtraChargeEntry?flatNumber=' + flatNumber,
        method: 'GET',
        success: function (data) {
            populateModal(data, flatNumber);
        },
        error: function (error) {
            console.error('Error fetching extra charges:', error);
        }
    });
}

function populateModal(data, flatNumber) {

    $('#modalFlatNumber').text(flatNumber);
    var chargesBody = $('#extraChargesBody');
    chargesBody.empty();
    var totalCharges = 0;

    data.forEach(function (entry) {
        var row = '<tr>' +
            '<td>' + entry.chargedAmount + '</td>' +
            '<td>' + entry.reason + '</td>' +
            '<td>' + entry.comments + '</td>' +
            '</tr>';
        chargesBody.append(row);
        totalCharges += entry.chargedAmount;
    });

    $('#totalCharges').text(totalCharges);
    $('#extraChargesModal').modal('show');
}


$(document).ready(function() {
    $('#paymentHistoryBody').on('click', '.edit-btn', function() {
        const row = $(this).closest('tr'); // Get the clicked row
        openEditModalOnHistory(row);
    });
});

function formatDateForServer(dateString) {
    // Assuming the input format is "dd/MM/yyyy"
    var parts = dateString.split('/');
    // Convert to ISO format "yyyy-MM-dd"
    return `${parts[2]}-${parts[1]}-${parts[0]}`;
}
function formatDateToDDMMYYYY(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are
																// zero-based
    const year = date.getFullYear();
    
    return `${day}/${month}/${year}`;
}

function openEditModalOnHistory(row) {
	// Debugging: log the text to ensure it matches
    console.log('Row text:', row.find('td:eq(6)').text().trim());
    
    const modal = $('#editModal1'); // Reference to the modal

    // Fill modal fields with data from the row
    modal.find('#editYear').text(row.find('td:eq(1)').text());
    modal.find('#editAmount').val(row.find('td:eq(2)').text());
    modal.find('#editFlatType').text(row.find('td:eq(3)').text());
    modal.find('#editAnnualMaintenance').text(row.find('td:eq(4)').text());
    modal.find('#editDate').val(row.find('td:eq(5)').text());
    
    // Populate Payment Method dropdown
    var paymentMethods = ["Cheque", "IMPS", "UPI", "Cash"];
    var paymentMethodSelect = modal.find('#editPaymentMethod');
    paymentMethodSelect.empty();
    paymentMethods.forEach(function(method) {
        paymentMethodSelect.append(new Option(method, method));
    });

    // Set the selected value, ensure it matches the dropdown options
    var selectedMethod = row.find('td:eq(6)').text().trim();
    console.log('Selected method:', selectedMethod);
    
    // Use .val() with .prop() to ensure proper selection
    paymentMethodSelect.val(selectedMethod).prop('selected', true).change();

    modal.find('#editTransactionId').val(row.find('td:eq(7)').text());

    // Show the modal
    modal.modal('show');

    // Ensure that only one click handler is attached
    $('#saveChanges').off('click').on('click', function() {
        // Validate form data
        if (!entryValidator()) {
            return; // Exit function if validation fails
        }

        var formattedDate = formatDateForServer($('#editDate').val());

        var data = {
            id: row.data('id'),
            flatNumber: $('#flatNumberSelect').val(),
            year: $('#editYear').text(),
            amount: $('#editAmount').val(),
            flatType: $('#editFlatType').text(),
            annualMaintenance: $('#editAnnualMaintenance').text(),
            date: formattedDate, // Use the formatted date here
            paymentMethod: $('#editPaymentMethod').val(),
            transactionId: $('#editTransactionId').val(),
        };

        // Call the API to save the data
        $.ajax({
            url: '/editPaymentHistory/',
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
            	showSuccessAlert('Data saved successfully!');

                // Update the row with new data
            	row.find('td:eq(0)').text(data.id);
                row.find('td:eq(1)').text(data.year);
                row.find('td:eq(2)').text(data.amount);
                row.find('td:eq(3)').text(data.flatType);
                row.find('td:eq(4)').text(data.annualMaintenance);
                
                // Format the date for the modal input
                const dateObj = new Date(data.date);
                const formattedDate = formatDateToDDMMYYYY(dateObj);
                row.find('td:eq(5)').text(formattedDate);
                row.find('td:eq(6)').text(data.paymentMethod);
                row.find('td:eq(7)').text(data.transactionId);

                // Hide the modal after saving
                modal.modal('hide');

                // Fetch updated data
                fetchMaintenanceDashboard(data.flatNumber);
            },
            error: function(error) {
                console.error('Error saving data:', error);
                showErrorAlert('Error saving data.');
            }
        });
    });
}


function entryValidator(){

    var amount = $('#editAmount').val();
    var date = $('#editDate').val();
    var paymentMethod = $('#editPaymentMethod').val();
    var transactionId = $('#editTransactionId').val();

    var isValid = true;
    var errorMsg = '';


    if (!amount || isNaN(amount)) {
        isValid = false;
        errorMsg += 'Amount must be a valid number.\n';
    }

    if (!date) {
        isValid = false;
        errorMsg += 'Date is required.\n';
    }

    if (!paymentMethod) {
        isValid = false;
        errorMsg += 'Payment Method is required.\n';
    }

    if (!transactionId) {
        isValid = false;
        errorMsg += 'Transaction ID is required.\n';
    }

    if (!isValid) {
    	showErrorAlert('Please correct the following errors:\n' + errorMsg);
    }

    return isValid;
}


function openDeleteModalOnHistory(rowid) {

    if (rowid === undefined || rowid === null) {
        console.error('ID is not defined or is null.');
        return;
    }

    // Show delete confirmation modal
    $('#deleteModal').modal('show');

    // Handle confirm delete button click
    $('#confirmDeleteBtn').off().on('click', function () {
        var reason = $('#deleteReasonSelect').val();
        var otherReason = $('#otherReasonText').val().trim();

        if (reason === 'Other' && otherReason === '') {
            alert('Please enter a reason for deletion.');
            $('#otherReasonInput').show();
            return;
        }

        // Call the API to delete the data
        $.ajax({
            url: '/deletePaymentHistory/' + rowid,
            method: 'DELETE',
            data: { deleteReason: reason },
            success: function (response) {
                alert('Data deleted successfully!');
                $('#deleteModal').modal('hide');
                // Remove the row from the table
                $('#row-' + rowid).remove();
                
                // fetchData();
                fetchDashBoard();
            },
            error: function (error) {
                console.error('Error deleting data:', error);
                alert('Error deleting data.');
            }
        });

    });
}



$(document).ready(function () {
    // Event delegation for edit, save, cancel, and delete buttons
	$(document).on('click', '.edit-btn', function () {});


    // Delete row
    $(document).on('click', '.delete-btn', function () {});
    
 // Download receipt
    $(document).on('click', '.download-btn', function () {
        var row = $(this).closest('tr');
        var id = row.data('id');
        // Correct URL with backticks for template literals
        fetch(`/downloadPdf/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.blob(); // Get the response as a blob (binary
										// large object)
            })
            .then(blob => {
                // Create a link element
                const link = document.createElement('a');
                link.href = URL.createObjectURL(blob); // Create a URL for the
														// blob
                link.download = 'filled_Receipt_format.pdf'; // Specify the
																// download
																// filename
                link.click(); // Trigger the download
                URL.revokeObjectURL(link.href); // Clean up the URL object

                // Show download confirmation modal
                $('#downloadModal').modal('show');
            })
            .catch(error => {
                alert('Could not download receipt. Please try again later.');
                console.error('There was a problem with the fetch operation:', error);
            });
    });

    
fetchData();
});

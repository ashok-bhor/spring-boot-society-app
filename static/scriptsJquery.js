
function fetchDashBoard() {
	const selectedValue = selectElement.value;
    const contentDiv = document.getElementById('content');

    if (selectedValue === 'default') {
        contentDiv.style.display = 'none'; // Hide content if 'default' is selected
    } else {
        contentDiv.style.display = 'block'; // Show content if any valid value is selected
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
    fetchPaymentHistory(flatNumber);
    fetchMaintenanceDashboard(flatNumber);
}

// Function to fetch payment history
function fetchPaymentHistory(flatNumber) {
    $.ajax({
        url: 'http://localhost:8080/getPaidHistory/' + flatNumber,
        method: 'GET',
        success: function (data) {
            // Clear existing rows
            $('#paymentHistoryBody').empty();
            if (data.length > 0) {
                // Populate rows
                data.forEach(function (item) {
                    var actionButtons = '';
                    if (userRole === 'ADMIN') {
                        actionButtons = `
                           <td>
                            <button class="btn btn-primary btn-sm edit-btn">Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn">Delete</button>
                        	<button class="btn btn-secondary btn-sm cancel-btn" style="display:none;">Cancel</button>
                            </td>`;
                        
                    }
                    $('#paymentHistoryBody').append(
                        `<tr data-id="${item.id}">
                            <td>${item.year}</td>
                            <td>${item.amount}</td>
                            <td>${item.flatType}</td>
                            <td>${item.annualMaintenance}</td>
                            <td>${item.date}</td>
                            <td>${item.paymentMethod}</td>
                            <td>${item.transactionId}</td>
                            <td>${item.verified}</td>
                            ${actionButtons}    
                        </tr>`
                    );
                });
            } else {
                $('#paymentHistoryBody').append(
                    `<tr>
                        <td colspan="9" class="text-center text-danger">No payment history entries found</td>
                    </tr>`
                );
            }
        },
        error: function (error) {
            console.error('Error fetching payment history:', error);
            $('#paymentHistoryBody').empty().append(
                `<tr>
                    <td colspan="9" class="text-center text-danger">Error fetching data</td>
                </tr>`
            );
        }
    });
}


// Helper function to format currency values
function formatCurrency(amount) {
    return amount.toLocaleString('en-IN', { style: 'currency', currency: 'INR' }).replace('₹', '');
}

function fetchMaintenanceDashboard(flatNumber) {
    $.ajax({
        url: 'http://localhost:8080/getMaintenanceDashboardCard/' + flatNumber,
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
                const rowIndex = Math.floor(index / 3); // Distribute cards into rows
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
        url: 'http://localhost:8080/getExtraChargeEntry?flatNumber=' + flatNumber,
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
            '<td>' + entry.amount + '</td>' +
            '<td>' + entry.reason + '</td>' +
            '<td>' + entry.comments + '</td>' +
            '</tr>';
        chargesBody.append(row);
        totalCharges += entry.amount;
    });

    $('#totalCharges').text(totalCharges);
    $('#extraChargesModal').modal('show');
}

$(document).ready(function () {
    // Event delegation for edit, save, cancel, and delete buttons
	$(document).on('click', '.edit-btn', function () {
	    var row = $(this).closest('tr');
	    var modal = $('#editModal');

	    // Fill modal with data from the row
	    modal.find('#editYear').text(row.find('td:eq(0)').text());
	    modal.find('#editAmount').val(row.find('td:eq(1)').text());
	    modal.find('#editFlatType').text(row.find('td:eq(2)').text());
	    modal.find('#editAnnualMaintenance').text(row.find('td:eq(3)').text());
	    modal.find('#editDate').val(row.find('td:eq(4)').text());
	    
	    // Populate Payment Method dropdown
	    var paymentMethods = ["cheque", "imps", "upi", "cash"];
	    var paymentMethodSelect = modal.find('#editPaymentMethod');
	    paymentMethodSelect.empty();
	    paymentMethods.forEach(function(method) {
	        paymentMethodSelect.append(new Option(method, method));
	    });
	    paymentMethodSelect.val(row.find('td:eq(5)').text());
	    
	    modal.find('#editTransactionId').val(row.find('td:eq(6)').text());
	    
	    // Populate Verified dropdown
	    var verifiedArray = ["Yes", "No"];
	    var verifiedSelect = modal.find('#editVerified');
	    verifiedSelect.empty();
	    verifiedArray.forEach(function(verified) {
	        verifiedSelect.append(new Option(verified, verified));
	    });
	    verifiedSelect.val(row.find('td:eq(7)').text());

	    // Show modal
	    modal.modal('show');

	    // Save button click event inside modal
	    $('#saveChanges').off().on('click', function () {
	        var data = {
	            id: row.data('id'),
	            flatNumber: $('#flatNumberSelect').val(),
	            year: $('#editYear').text(),
	            amount: $('#editAmount').val(),
	            flatType: $('#editFlatType').text(),
	            annualMaintenance: $('#editAnnualMaintenance').text(),
	            date: $('#editDate').val(),
	            paymentMethod: $('#editPaymentMethod').val(),
	            transactionId: $('#editTransactionId').val(),
	            verified: $('#editVerified').val()
	        };

	        // Call the API to save the data
	        $.ajax({
	            url: 'http://localhost:8080/editPaymentHistory/',
	            method: 'PUT',
	            contentType: 'application/json',
	            data: JSON.stringify(data),
	            success: function (response) {
	                let flatNumber = data.flatNumber;
	                alert('Data saved successfully!');
	                
	                // Update the row with new data
	                row.find('td:eq(0)').text(data.year);
	                row.find('td:eq(1)').text(data.amount);
	                row.find('td:eq(2)').text(data.flatType);
	                row.find('td:eq(3)').text(data.annualMaintenance);
	                row.find('td:eq(4)').text(data.date);
	                row.find('td:eq(5)').text(data.paymentMethod);
	                row.find('td:eq(6)').text(data.transactionId);
	                row.find('td:eq(7)').text(data.verified);
	                
	                modal.modal('hide');
	                fetchMaintenanceDashboard(flatNumber);
	            },
	            error: function (error) {
	                console.error('Error saving data:', error);
	                alert('Error saving data.');
	            }
	        });
	    });
	});


    // Delete row
    $(document).on('click', '.delete-btn', function () {
        var row = $(this).closest('tr');
        var id = row.data('id');

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
                url: 'http://localhost:8080/deletePaymentHistory/' + id,
                method: 'DELETE',
                data: { deleteReason: reason },
                success: function (response) {
                    alert('Data deleted successfully!');
                    $('#deleteModal').modal('hide');
                    fetchData();
                },
                error: function (error) {
                    console.error('Error deleting data:', error);
                    alert('Error deleting data.');
                }
            });

        });
    });

    fetchData();
});




/*$(document).ready(function () {
    var currentRow; // Variable to store the current row being edited

    // Event delegation for edit, save, cancel, and delete buttons
    $(document).on('click', '.edit-btn', function () {
        currentRow = $(this).closest('tr');
        
        // Populate the modal fields with the current row data
        $('#editYear').val(currentRow.find('td:eq(0)').text());
        $('#editAmount').val(currentRow.find('td:eq(1)').text());
        $('#editFlatType').val(currentRow.find('td:eq(2)').text());
        $('#editAnnualMaintenance').val(currentRow.find('td:eq(3)').text());
        $('#editDate').val(currentRow.find('td:eq(4)').text());
        $('#editPaymentMethod').val(currentRow.find('td:eq(5)').text());
        $('#editTransactionId').val(currentRow.find('td:eq(6)').text());
        $('#editVerified').val(currentRow.find('td:eq(7)').text());

        // Show the modal
        $('#editModal').modal('show');
    });

    // Save the edited data from the modal
    $('#saveEditBtn').click(function () {
        var data = {
            id: currentRow.data('id'),
            flatNumber: $('#flatNumberSelect').val(),
            year: $('#editYear').val(),
            amount: $('#editAmount').val(),
            flatType: $('#editFlatType').val(),
            annualMaintenance: $('#editAnnualMaintenance').val(),
            date: $('#editDate').val(),
            paymentMethod: $('#editPaymentMethod').val(),
            transactionId: $('#editTransactionId').val(),
            verified: $('#editVerified').val()
        };

        // Call the API to save the data
        $.ajax({
            url: 'http://localhost:8080/editPaymentHistory/',
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                let flatNumber = data.flatNumber;

                console.log(data);
                alert('Data saved successfully!');
                
                // Update the current row with the new data
                currentRow.find('td:eq(0)').text(data.year);
                currentRow.find('td:eq(1)').text(data.amount);
                currentRow.find('td:eq(2)').text(data.flatType);
                currentRow.find('td:eq(3)').text(data.annualMaintenance);
                currentRow.find('td:eq(4)').text(data.date);
                currentRow.find('td:eq(5)').text(data.paymentMethod);
                currentRow.find('td:eq(6)').text(data.transactionId);
                currentRow.find('td:eq(7)').text(data.verified);

                // Hide the modal
                $('#editModal').modal('hide');

                fetchMaintenanceDashboard(flatNumber);
            },
            error: function (error) {
                console.error('Error saving data:', error);
                alert('Error saving data.');
            }
        });
    });

    // Cancel edit mode
    $(document).on('click', '.cancel-btn', function () {
        var row = $(this).closest('tr');
        var editBtn = row.find('.edit-btn');
        // Revert to original values
        var original = row.data('original');
        row.find('td:eq(0)').text(original.year);
        row.find('td:eq(1)').text(original.amount);
        row.find('td:eq(2)').text(original.flatType);
        row.find('td:eq(3)').text(original.annualMaintenance);
        row.find('td:eq(4)').text(original.date);
        row.find('td:eq(5)').text(original.paymentMethod);
        row.find('td:eq(6)').text(original.transactionId);
        row.find('td:eq(7)').text(original.verified);
        editBtn.text('Edit');
        $(this).hide();
    });

    // Delete row
    $(document).on('click', '.delete-btn', function () {
        var row = $(this).closest('tr');
        var id = row.data('id');

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
                url: 'http://localhost:8080/deletePaymentHistory/' + id,
                method: 'DELETE',
                data: { deleteReason: reason }, // Replace 'reason' with the actual reason value
                success: function (response) {
                    alert('Data deleted successfully!');
                    $('#deleteModal').modal('hide');
                    fetchData();
                },
                error: function (error) {
                    console.error('Error deleting data:', error);
                    alert('Error deleting data.');
                }
            });

        });
    });

    fetchData();
});

*/
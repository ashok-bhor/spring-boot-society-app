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
                            <td>
                                <button class="btn btn-primary btn-sm edit-btn">Edit</button>
                                <button class="btn btn-danger btn-sm delete-btn">Delete</button>
                                <button class="btn btn-secondary btn-sm cancel-btn" style="display:none;">Cancel</button>
                            </td>
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

function fetchMaintenanceDashboard(flatNumber) {
    $.ajax({
        url: 'http://localhost:8080/getMaintenanceDashboardData/' + flatNumber,
        method: 'GET',
        success: function (data) {

            // Clear existing rows
            $('#maintenanceDashboardBody').empty();

            // Check if data is null, undefined, or an empty array
            if (!data) {
                $('#maintenanceDashboardBody').append(
                    `<tr>
                        <td colspan="7" class="text-center text-danger">No maintenance data found</td>
                    </tr>`
                );
                return;
            }

            // Normalize data to be an array
            var dataArray = Array.isArray(data) ? data : [data];

            if (dataArray.length === 0) {
                $('#maintenanceDashboardBody').append(
                    `<tr>
                        <td colspan="7" class="text-center text-danger">No maintenance data found</td>
                    </tr>`
                );
                return;
            }

            // Populate rows if data is valid
            dataArray.forEach(function (item) {
                $('#maintenanceDashboardBody').append(
                    `<tr>
                        <td>${item.flatType}</td>
                        <td>${item.totalPaid}</td>
                        <td>${item.paidTillYear}</td>
                        <td>${item.annualMaintenance}</td>
                        <td>${item.maintenanceOutstanding}</td>
                        <td><a href="#" class="extra-charges-link" data-flat-number="${flatNumber}">${item.extraCharges}</a></td>
                        <td>${item.totalOutstanding}</td>
                    </tr>`
                );
            });

            // Attach click event listener to extra charges links
            $('.extra-charges-link').click(function (e) {
                e.preventDefault();
                var flatNumber = $(this).data('flat-number');
                fetchExtraCharges(flatNumber);
                $('#extraChargesModal').modal('show');
            });
        },
        error: function (error) {
            console.error('Error fetching maintenance dashboard data:', error);
            $('#maintenanceDashboardBody').empty().append(
                `<tr>
                    <td colspan="7" class="text-center text-danger">Error fetching data</td>
                </tr>`
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
        var cancelBtn = row.find('.cancel-btn');
        if ($(this).text() === 'Edit') {
            // Store original values
            row.data('original', {
                year: row.find('td:eq(0)').text(),
                amount: row.find('td:eq(1)').text(),
                flatType: row.find('td:eq(2)').text(),
                annualMaintenance: row.find('td:eq(3)').text(),
                date: row.find('td:eq(4)').text(),
                paymentMethod: row.find('td:eq(5)').text(),
                transactionId: row.find('td:eq(6)').text(),
                verified: row.find('td:eq(7)').text()
            });
            // Convert row cells to text fields for editing
            row.find('td').each(function (index, td) {
                if (index < 8) { // Exclude the action buttons cell
                    var text = $(td).text();
                    $(td).html(`<input type="text" class="form-control" value="${text}">`);
                }
            });
            $(this).text('Save');
            cancelBtn.show();
        } else {
            // Save the edited data
            var data = {
                id: row.data('id'),
                flatNumber: $('#flatNumberSelect').val(),
                year: row.find('td:eq(0) input').val(),
                amount: row.find('td:eq(1) input').val(),
                flatType: row.find('td:eq(2) input').val(),
                annualMaintenance: row.find('td:eq(3) input').val(),
                date: row.find('td:eq(4) input').val(),
                paymentMethod: row.find('td:eq(5) input').val(),
                transactionId: row.find('td:eq(6) input').val(),
                verified: row.find('td:eq(7) input').val()
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
                    // Convert text fields back to plain text
                    row.find('td').each(function (index, td) {
                        if (index < 8) {
                            var input = $(td).find('input');
                            $(td).text(input.val());
                        }
                    });
                    row.find('.edit-btn').text('Edit');
                    cancelBtn.hide();
                    fetchMaintenanceDashboard(flatNumber);
                    
                },
                error: function (error) {
                    console.error('Error saving data:', error);
                    alert('Error saving data.');
                }
            });
        }
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

$(document).ready(function() {
    // Fetch flat numbers and outstanding amounts from API
    var flatNumbers = [
        { number: 101, outstanding: 500 },
        { number: 102, outstanding: 250 },
        { number: 103, outstanding: 300 },
        { number: 104, outstanding: 150 },
        { number: 201, outstanding: 400 },
        { number: 202, outstanding: 600 },
        { number: 203, outstanding: 200 },
        { number: 204, outstanding: 350 },
        { number: 301, outstanding: 450 },
        { number: 302, outstanding: 100 },
        { number: 303, outstanding: 275 },
        { number: 304, outstanding: 225 },
        { number: 401, outstanding: 175 },
        { number: 402, outstanding: 325 },
        { number: 403, outstanding: 275 },
        { number: 404, outstanding: 200 },
        { number: 501, outstanding: 350 },
        { number: 502, outstanding: 150 },
        { number: 503, outstanding: 400 },
        { number: 504, outstanding: 300 },
        { number: 601, outstanding: 250 },
        { number: 602, outstanding: 450 },
        { number: 603, outstanding: 175 },
        { number: 604, outstanding: 225 },
        { number: 701, outstanding: 200 },
        { number: 702, outstanding: 300 },
        { number: 703, outstanding: 350 },
        { number: 704, outstanding: 150 }
    ];

    // Function to populate flat squares dynamically
    function populateFlatGrid() {
        var flatGrid = $('#flatGrid');
        flatGrid.empty();

        flatNumbers.forEach(function(flat) {
            var squareHtml = '<div class="flat-square">' +
                '<div class="flat-number">' + flat.number + '</div>' +
                '<div class="outstanding-amount">$' + flat.outstanding + '</div>' +
                '</div>';
            flatGrid.append(squareHtml);
        });
    }

    // Call function to populate the grid on page load
    populateFlatGrid();
});

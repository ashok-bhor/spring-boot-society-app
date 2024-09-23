$(document).ready(function() {
	
    fetch('/getOutstandingList')
        .then(response => response.json())
        .then(data => {
            //console.log(data); // Log the data to inspect its structure
            populateFlatGrid(data);
        })
        .catch(error => console.error('Error fetching grid data:', error));

    function populateFlatGrid(flatData) {
        const gridContainer = document.getElementById('flatGrid');
        

        flatNumbers.forEach(flatNumber => {
            const flat = flatData.find(item => item.flatNumber === flatNumber);
            let className = 'grid-item'; // Default class for grid item

            if (flat) {
                if (flat.outstandingAmount === 0) {
                    className += ' zero-outstanding';
                } else {
                    className += ' non-zero-outstanding';
                }
            }

            const gridItem = document.createElement('div');
            gridItem.className = className;
            gridItem.innerHTML = `
                <span class="flat-number">Flat ${flatNumber}</span>
                <span class="outstanding-amount">₹${flat ? flat.outstandingAmount.toFixed(2) : '0.00'}</span>
            `;

            gridContainer.appendChild(gridItem);
        });
    }
});

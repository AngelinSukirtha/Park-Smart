$(document).ready(function() {
	const checkboxes = $('.checkbox-cell');
	const parkingForm = $('#parkingForm');

	parkingForm.on('submit', function(event) {
		event.preventDefault();

		let selectedCells = [];

		checkboxes.each(function() {
			if ($(this).prop('checked')) {
				let spotId = $(this).attr('id');
				selectedCells.push(spotId);
			}
		});

		if (selectedCells.length === 0) {
			alert("Please select at least one spot.");
			return;
		}

		$('<input>').attr({
			type: 'hidden',
			name: 'selectedSpots',
			value: JSON.stringify(selectedCells)
		}).appendTo(parkingForm);

		parkingForm.get(0).submit();
	});
});
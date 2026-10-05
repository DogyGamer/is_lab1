loadCoordinates();

$('vehicleForm').addEventListener('submit', async event => {
    event.preventDefault();
    if (await sendForm('POST', '/api/vehicles')) {
        location.href = '/';
    }
});

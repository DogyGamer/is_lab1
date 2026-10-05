function showMessage(kind, text) {
    $('message').className = 'alert alert-' + kind;
    $('message').textContent = text;
}

function showVehicles(list) {
    showMessage('info', 'Найдено объектов: ' + list.length);
    $('resultBody').innerHTML = list.map(v => '<tr>' + vehicleCells(v) + '</tr>').join('');
    $('resultTable').classList.toggle('d-none', list.length === 0);
}

function onSubmit(formId, action) {
    $(formId).addEventListener('submit', async event => {
        event.preventDefault();
        try {
            await action();
        } catch (errors) {
            $('resultTable').classList.add('d-none');
            showMessage('danger', errors.join('. '));
        }
    });
}

onSubmit('maxIdForm', async () => {
    showVehicles([await api('GET', '/api/vehicles/max-id')]);
});

onSubmit('countForm', async () => {
    const result = await api('GET', '/api/vehicles/count-fuel-type-greater?fuelType=' + $('countFuelType').value);
    $('resultTable').classList.add('d-none');
    showMessage('info', 'Количество объектов: ' + result.count);
});

onSubmit('nameForm', async () => {
    showVehicles(await api('GET', '/api/vehicles/by-name?substring=' + encodeURIComponent($('substring').value)));
});

onSubmit('typeForm', async () => {
    showVehicles(await api('GET', '/api/vehicles/by-type?type=' + $('vehicleType').value));
});

onSubmit('powerForm', async () => {
    showVehicles(await api('GET', '/api/vehicles/by-engine-power?min=' + $('minPower').value + '&max=' + $('maxPower').value));
});

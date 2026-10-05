async function loadCoordinates(selectedId) {
    const list = await api('GET', '/api/coordinates');
    $('coordinatesId').innerHTML = '<option value="">Новые координаты</option>'
        + list.map(c => `<option value="${c.id}">#${c.id}: (${c.x}; ${c.y})</option>`).join('');
    $('coordinatesId').value = selectedId ?? '';
    toggleCoordinates();
}

function toggleCoordinates() {
    const isNew = $('coordinatesId').value === '';
    $('newCoordinates').classList.toggle('d-none', !isNew);
    $('x').disabled = !isNew;
    $('y').disabled = !isNew;
}

function fillForm(v) {
    $('name').value = v.name;
    $('type').value = v.type;
    $('enginePower').value = v.enginePower;
    $('numberOfWheels').value = v.numberOfWheels;
    $('capacity').value = v.capacity;
    $('distanceTravelled').value = v.distanceTravelled;
    $('fuelConsumption').value = v.fuelConsumption ?? '';
    $('fuelType').value = v.fuelType ?? '';
    $('x').value = '';
    $('y').value = '';
}

function readForm() {
    const text = id => $(id).value;
    const number = id => $(id).value === '' ? null : Number($(id).value);
    return {
        name: text('name'),
        type: text('type') || null,
        enginePower: number('enginePower'),
        numberOfWheels: number('numberOfWheels'),
        capacity: number('capacity'),
        distanceTravelled: number('distanceTravelled'),
        fuelConsumption: number('fuelConsumption'),
        fuelType: text('fuelType') || null,
        coordinatesId: number('coordinatesId'),
        x: number('x'),
        y: number('y')
    };
}

async function sendForm(method, url) {
    try {
        await api(method, url, readForm());
        return true;
    } catch (errors) {
        showErrors($('formErrors'), errors);
        return false;
    }
}

async function openEditModal(id, onSaved) {
    let vehicle;
    try {
        vehicle = await api('GET', '/api/vehicles/' + id);
        await loadCoordinates(vehicle.coordinates.id);
    } catch (errors) {
        alert(errors.join('\n'));
        return;
    }
    fillForm(vehicle);
    hideErrors($('formErrors'));

    const modal = bootstrap.Modal.getOrCreateInstance($('editModal'));
    $('vehicleForm').onsubmit = async event => {
        event.preventDefault();
        if (await sendForm('PUT', '/api/vehicles/' + id)) {
            modal.hide();
            onSaved();
        }
    };
    modal.show();
}

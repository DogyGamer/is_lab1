let currentId = null;

function showVehicle(v) {
    $('vehicleCard').innerHTML = `
        <div class="card mb-3">
            <div class="card-header">Транспортное средство #${v.id}</div>
            <div class="card-body">
                <dl class="row mb-0">
                    <dt class="col-sm-4">Название</dt><dd class="col-sm-8">${esc(v.name)}</dd>
                    <dt class="col-sm-4">Дата создания</dt><dd class="col-sm-8">${new Date(v.creationDate).toLocaleString('ru-RU')}</dd>
                    <dt class="col-sm-4">Тип</dt><dd class="col-sm-8">${v.type}</dd>
                    <dt class="col-sm-4">Мощность двигателя</dt><dd class="col-sm-8">${v.enginePower}</dd>
                    <dt class="col-sm-4">Количество колёс</dt><dd class="col-sm-8">${v.numberOfWheels}</dd>
                    <dt class="col-sm-4">Вместимость</dt><dd class="col-sm-8">${v.capacity}</dd>
                    <dt class="col-sm-4">Пройденное расстояние</dt><dd class="col-sm-8">${v.distanceTravelled}</dd>
                    <dt class="col-sm-4">Расход топлива</dt><dd class="col-sm-8">${v.fuelConsumption ?? '—'}</dd>
                    <dt class="col-sm-4">Тип топлива</dt><dd class="col-sm-8">${v.fuelType ?? '—'}</dd>
                </dl>
            </div>
            <div class="card-footer">
                <button class="btn btn-sm btn-outline-primary" onclick="openEditModal(${v.id}, loadVehicle)">Изменить</button>
                <button class="btn btn-sm btn-outline-danger" onclick="removeVehicle(${v.id}, loadVehicle)">Удалить</button>
            </div>
        </div>
        <div class="card">
            <div class="card-header">Связанный объект: координаты #${v.coordinates.id}</div>
            <div class="card-body">
                <dl class="row mb-0">
                    <dt class="col-sm-4">X</dt><dd class="col-sm-8">${v.coordinates.x}</dd>
                    <dt class="col-sm-4">Y</dt><dd class="col-sm-8">${v.coordinates.y}</dd>
                </dl>
            </div>
        </div>`;
    $('vehicleCard').classList.remove('d-none');
}

async function loadVehicle() {
    if (currentId === null) {
        return;
    }
    try {
        showVehicle(await api('GET', '/api/vehicles/' + currentId));
        hideErrors($('error'));
    } catch (errors) {
        $('vehicleCard').classList.add('d-none');
        showErrors($('error'), errors);
    }
}

$('searchForm').addEventListener('submit', event => {
    event.preventDefault();
    currentId = Number($('vehicleId').value);
    loadVehicle();
});

setInterval(loadVehicle, POLL_INTERVAL);

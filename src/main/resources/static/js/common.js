const POLL_INTERVAL = 3000;

const $ = id => document.getElementById(id);

async function api(method, url, body) {
    let response;
    try {
        response = await fetch(url, {
            method: method,
            headers: {'Content-Type': 'application/json'},
            body: body ? JSON.stringify(body) : undefined
        });
    } catch (e) {
        throw ['Нет связи с сервером'];
    }
    const text = await response.text();
    const data = text ? JSON.parse(text) : null;
    if (!response.ok) {
        throw (data && data.errors) || ['Ошибка сервера (' + response.status + ')'];
    }
    return data;
}

function esc(value) {
    const div = document.createElement('div');
    div.textContent = value;
    return div.innerHTML;
}

function showErrors(box, errors) {
    box.innerHTML = errors.map(e => '<div>' + esc(e) + '</div>').join('');
    box.classList.remove('d-none');
}

function hideErrors(box) {
    box.classList.add('d-none');
}

function vehicleCells(v) {
    return `<td>${v.id}</td>
        <td>${esc(v.name)}</td>
        <td>#${v.coordinates.id} (${v.coordinates.x}; ${v.coordinates.y})</td>
        <td>${new Date(v.creationDate).toLocaleString('ru-RU')}</td>
        <td>${v.type}</td>
        <td>${v.enginePower}</td>
        <td>${v.numberOfWheels}</td>
        <td>${v.capacity}</td>
        <td>${v.distanceTravelled}</td>
        <td>${v.fuelConsumption ?? ''}</td>
        <td>${v.fuelType ?? ''}</td>`;
}

async function removeVehicle(id, onRemoved) {
    if (!confirm('Удалить транспортное средство ' + id + '?')) {
        return;
    }
    try {
        await api('DELETE', '/api/vehicles/' + id);
    } catch (errors) {
        alert(errors.join('\n'));
    }
    onRemoved();
}

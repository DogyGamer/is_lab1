async function loadTable() {
    let list;
    try {
        list = await api('GET', '/api/coordinates');
    } catch (errors) {
        return;
    }
    $('tableBody').innerHTML = list.length === 0
        ? '<tr><td colspan="4" class="text-center text-muted">Координат нет</td></tr>'
        : list.map(c => `<tr>
            <td>${c.id}</td>
            <td>${c.x}</td>
            <td>${c.y}</td>
            <td><button class="btn btn-sm btn-outline-danger" onclick="removeCoordinates(${c.id})">Удалить</button></td>
        </tr>`).join('');
}

async function removeCoordinates(id) {
    hideErrors($('error'));
    try {
        await api('DELETE', '/api/coordinates/' + id);
    } catch (errors) {
        showErrors($('error'), errors);
    }
    loadTable();
}

loadTable();
setInterval(loadTable, POLL_INTERVAL);

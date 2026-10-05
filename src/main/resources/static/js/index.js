let page = 0;
let totalPages = 1;
let sort = 'id';
let direction = 'asc';
let filter = {};

async function loadTable() {
    const params = new URLSearchParams({page, sort, direction});
    for (const key in filter) {
        if (filter[key]) {
            params.set(key, filter[key]);
        }
    }

    let data;
    try {
        data = await api('GET', '/api/vehicles?' + params);
    } catch (errors) {
        return;
    }

    if (data.items.length === 0 && page > 0) {
        page = Math.max(data.totalPages - 1, 0);
        return loadTable();
    }

    totalPages = data.totalPages;
    $('tableBody').innerHTML = data.items.length === 0
        ? '<tr><td colspan="12" class="text-center text-muted">Объектов нет</td></tr>'
        : data.items.map(v => `<tr>${vehicleCells(v)}
            <td class="text-nowrap">
                <button class="btn btn-sm btn-outline-primary" onclick="openEditModal(${v.id}, loadTable)">Изменить</button>
                <button class="btn btn-sm btn-outline-danger" onclick="removeVehicle(${v.id}, loadTable)">Удалить</button>
            </td></tr>`).join('');

    $('pageInfo').textContent = 'Страница ' + (page + 1) + ' из ' + Math.max(totalPages, 1);
    $('prevPage').disabled = page === 0;
    $('nextPage').disabled = page >= totalPages - 1;
    showSortArrows();
}

function showSortArrows() {
    document.querySelectorAll('th[data-sort]').forEach(th => {
        const label = th.textContent.replace(/ [▲▼]$/, '');
        th.textContent = th.dataset.sort === sort ? label + (direction === 'asc' ? ' ▲' : ' ▼') : label;
    });
}

document.querySelectorAll('th[data-sort]').forEach(th => {
    th.addEventListener('click', () => {
        if (sort === th.dataset.sort) {
            direction = direction === 'asc' ? 'desc' : 'asc';
        } else {
            sort = th.dataset.sort;
            direction = 'asc';
        }
        page = 0;
        loadTable();
    });
});

$('filterForm').addEventListener('submit', event => {
    event.preventDefault();
    filter = {
        name: $('filterName').value,
        type: $('filterType').value,
        fuelType: $('filterFuelType').value
    };
    page = 0;
    loadTable();
});

$('resetFilter').addEventListener('click', () => {
    $('filterForm').reset();
    filter = {};
    page = 0;
    loadTable();
});

$('prevPage').addEventListener('click', () => {
    page--;
    loadTable();
});

$('nextPage').addEventListener('click', () => {
    page++;
    loadTable();
});

loadTable();
setInterval(loadTable, POLL_INTERVAL);

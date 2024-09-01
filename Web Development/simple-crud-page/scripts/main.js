document.addEventListener('DOMContentLoaded', function() {
    const itemsPerPage = 5;
    let currentPage = 1;
    let events = [];

    // Función para cargar eventos desde localStorage y renderizarlos en la tabla
    function loadEvents(filterDate = null) {
        events = JSON.parse(localStorage.getItem('events')) || [];
        if (filterDate) {
            events = events.filter(event => event.fecha === filterDate);
            currentPage = 1; // Establecer la página actual de vuelta a la primera página después de aplicar el filtro
        }
        renderEvents();
    }

    // Función para renderizar eventos en la tabla con paginación
    function renderEvents() {
        const tbody = document.querySelector('#lista tbody');
        tbody.innerHTML = ''; // Limpiar el contenido existente

        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const paginatedEvents = events.slice(start, end);

        paginatedEvents.forEach((event, index) => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${event.nombre}</td>
                <td>${event.fecha}</td>
                <td>${event.hora}</td>
                <td>${event.lugar}</td>
                <td>${event.descripcion}</td>
                <td class="event-buttons">
                    <button class="edit" data-index="${start + index}">Editar</button>
                    <button class="delete" data-index="${start + index}">Eliminar</button>
                </td>
            `;

            tbody.appendChild(row);
        });

        // Agregar listeners a los botones de edición y eliminación
        document.querySelectorAll('.edit').forEach(button => {
            button.addEventListener('click', function() {
                const index = this.getAttribute('data-index');
                editEvent(index);
            });
        });

        document.querySelectorAll('.delete').forEach(button => {
            button.addEventListener('click', function() {
                const index = this.getAttribute('data-index');
                deleteEvent(index);
            });
        });

        renderPagination();
    }

    // Función para editar un evento
    function editEvent(index) {
        window.location.href = `edit-event.html?index=${index}`;
    }

    // Función para eliminar un evento
    function deleteEvent(index) {
        if (confirm("¿Estás seguro de que deseas eliminar este evento?")) {
            events.splice(index, 1);
            localStorage.setItem('events', JSON.stringify(events));
            loadEvents(); // Cargar eventos después de eliminar
        }
    }

    // Función para renderizar los controles de paginación
    function renderPagination() {
        const pagination = document.querySelector('#pagination-buttons'); // Seleccionar el contenedor de botones de paginación
        pagination.innerHTML = ''; // Limpiar el contenido existente

        const pageCount = Math.ceil(events.length / itemsPerPage);

        for (let i = 1; i <= pageCount; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            pageButton.classList.add('page-btn');
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            pageButton.addEventListener('click', () => {
                currentPage = i;
                renderEvents();
            });
            pagination.appendChild(pageButton);
        }

        // Actualizar el número de página mostrado
        document.getElementById('page-info').textContent = `Página ${currentPage}`;
    }

    // Añadir evento al botón de "Anterior"
    document.getElementById('prev-page').addEventListener('click', function() {
        if (currentPage > 1) {
            currentPage--;
            renderEvents();
        }
    });

    // Añadir evento al botón de "Siguiente"
    document.getElementById('next-page').addEventListener('click', function() {
        const pageCount = Math.ceil(events.length / itemsPerPage);
        if (currentPage < pageCount) {
            currentPage++;
            renderEvents();
        }
    });

    // Manejo del filtro por fecha
    document.getElementById('filter-date').addEventListener('change', function() {
        const filterDate = this.value;
        loadEvents(filterDate);
    });

    // Redireccionamiento btn add
    function redireccionAdd() {
        window.location.href = 'add-event.html';
    }

    // Añadir el evento de click al botón de añadir
    document.getElementById('add-event-btn').addEventListener('click', redireccionAdd);

    // Cargar los eventos al cargar la página
    loadEvents();
});
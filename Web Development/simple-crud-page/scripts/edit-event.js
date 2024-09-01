document.addEventListener('DOMContentLoaded', function() {
    // Función para cargar las ciudades en el campo de selección
    function loadCities() {
        const lugarSelect = document.getElementById('lugar');

        // Realizar la solicitud al archivo JSON
        fetch('datos/lugares.json')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al cargar el archivo JSON');
                }
                return response.json();
            })
            .then(data => {
                // Limpiar el campo de selección por si había opciones anteriores
                lugarSelect.innerHTML = '';

                // Extraer y procesar los datos de las ciudades
                const cities = data.lugares.map(lugar => lugar.name);

                // Crear opciones para cada ciudad en los datos
                cities.forEach(city => {
                    const option = document.createElement('option');
                    option.value = city;
                    option.textContent = city;
                    lugarSelect.appendChild(option);
                });

                // Llamar a la función para cargar el evento después de cargar las ciudades
                loadEvent();
            })
            .catch(error => {
                console.error('Error al cargar las ciudades:', error);
            });
    }

    // Obtener el índice del evento a editar de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const eventIndex = urlParams.get('index');

    // Cargar los datos del evento en el formulario
    function loadEvent() {
        const events = JSON.parse(localStorage.getItem('events')) || [];
        const event = events[eventIndex];

        document.getElementById('nombre').value = event.nombre;
        document.getElementById('fecha').value = event.fecha;
        document.getElementById('hora').value = event.hora;
        document.getElementById('lugar').value = event.lugar;
        document.getElementById('descripcion').value = event.descripcion;
    }

    // Llamar a la función para cargar las ciudades al cargar la página
    loadCities();

    // Guardar los cambios del evento editado
    document.getElementById('edit-form').addEventListener('submit', function(e) {
        e.preventDefault();

        const events = JSON.parse(localStorage.getItem('events')) || [];
        const updatedEvent = {
            nombre: document.getElementById('nombre').value,
            fecha: document.getElementById('fecha').value,
            hora: document.getElementById('hora').value,
            lugar: document.getElementById('lugar').value,
            descripcion: document.getElementById('descripcion').value
        };

        // Validar que la fecha esté en el formato YYYY-MM-DD
        const fechaPattern = /^\d{4}-\d{2}-\d{2}$/;
        if (!fechaPattern.test(updatedEvent.fecha)) {
            alert("Por favor, introduce una fecha válida en el formato YYYY-MM-DD.");
            return;
        }
        
        // Validar que la hora esté en el formato HH:MM
        const hora = updatedEvent.hora;
        const horaPattern = /^([0-1]\d|2[0-3]):([0-5]\d)$/;
        if (!horaPattern.test(hora)) {
            alert("Por favor, introduce una hora válida en el formato HH:MM.");
            return;
        }

        if (confirm("¿Estás seguro de que deseas guardar los cambios del evento?")) {
            events[eventIndex] = updatedEvent;
            localStorage.setItem('events', JSON.stringify(events));
            alert("Evento editado exitosamente.");

            // Redirigir a la página principal
            window.location.href = 'index.html';
        }
    });

    // Redireccionamiento btn back
    document.getElementById('back-btn').addEventListener('click', function() {
        if (confirm("¿Estás seguro de que deseas volver a la página principal sin guardar los cambios?")) {
            window.location.href = 'index.html';
        }
    });
});
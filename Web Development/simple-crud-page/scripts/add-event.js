document.addEventListener('DOMContentLoaded', function() {
    // Función para cargar las ciudades en el campo de selección
    function loadCities() {
        const lugarSelect = document.getElementById('lugar');

        // Realizar la solicitud al archivo JSON
        fetch('datos/lugares.json')
            .then(response => response.json())
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
            })
            .catch(error => {
                console.error('Error al cargar las ciudades:', error);
            });
    }

    // Llamar a la función para cargar las ciudades al cargar la página
    loadCities();

    // Obtener el formulario y agregar el evento de submit
    document.getElementById('addevent-form').addEventListener('submit', function(e) {
        e.preventDefault();

        // Obtener los valores del formulario
        const nombre = document.getElementById('nombre').value;
        const fecha = document.getElementById('fecha').value;
        const hora = document.getElementById('hora').value;
        const lugar = document.getElementById('lugar').value;
        const descripcion = document.getElementById('descripcion').value;

        // Validar que la fecha esté en el formato YYYY-MM-DD
        const fechaPattern = /^\d{4}-\d{2}-\d{2}$/;
        if (!fechaPattern.test(fecha)) {
            alert("Por favor, introduce una fecha válida en el formato YYYY-MM-DD.");
            return;
        }
        
        // Validar que la hora esté en el formato HH:MM
        const horaPattern = /^([0-1]\d|2[0-3]):([0-5]\d)$/;
        if (!horaPattern.test(hora)) {
            alert("Por favor, introduce una hora válida en el formato HH:MM.");
            return;
        }

        if (confirm("¿Estás seguro de que deseas agregar el evento?")) {
            // Crear un nuevo evento
            const newEvent = {
                nombre: nombre,
                fecha: fecha,
                hora: hora,
                lugar: lugar,
                descripcion: descripcion
            };

            // Obtener los eventos existentes de localStorage
            let events = JSON.parse(localStorage.getItem('events')) || [];

            // Agregar el nuevo evento a la lista
            events.push(newEvent);

            // Guardar los eventos actualizados en localStorage
            localStorage.setItem('events', JSON.stringify(events));

            alert("Evento agregado correctamente.");

            // Redirigir a la página principal
            window.location.href = 'index.html';
        }
    });

    // Redireccionamiento btn back
    function volverIndex(){
        if (confirm("¿Estás seguro de que deseas volver a la página principal sin guardar el evento?")) {
            window.location.href = 'index.html';
        }
    }
});
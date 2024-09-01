package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.ModeloDTO;
import cfc.servidor.DTOs.MovimientoDTO;
import cfc.servidor.entidades.Equipo;
import cfc.servidor.entidades.Movimiento;
import cfc.servidor.entidades.Ubicacion;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.IMovimientoBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de movimiento.
 */
@Stateless
public class MovimientoBean implements IMovimientoBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public MovimientoDTO registrar(MovimientoDTO movimientoDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(movimientoDTO);

        // Crea una nueva entidad de movimiento donde se cargarán los datos
        Movimiento movimiento = new Movimiento();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(movimiento, movimientoDTO);

        // Registra el movimiento en la base de datos
        this.daoCrudGeneral.registrar(movimiento);

        // Obtiene el equipo asociado al movimiento y actualiza
        // su ubicación actual en la base de datos
        Equipo equipo = this.daoCrudGeneral.obtenerPorID(Equipo.class, movimientoDTO.getIdEquipo());
        equipo.setUbicacion(movimiento.getUbicacion());
        this.daoCrudGeneral.actualizar(equipo);

        // Actualiza el DTO con el ID generado
        movimientoDTO.setId(movimiento.getId());

        // Retorna el DTO actualizado
        return movimientoDTO;
    }

    @Override
    public void desactivar(Integer id) {
        // Obtiene la entidad de movimiento por su ID
        Movimiento movimiento = this.daoCrudGeneral.obtenerPorID(Movimiento.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (movimiento != null) {
            movimiento.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(movimiento);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de movimiento por su ID
        Movimiento movimiento = this.daoCrudGeneral.obtenerPorID(Movimiento.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (movimiento != null) {
            movimiento.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(movimiento);
        }
    }

    @Override
    public MovimientoDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de movimiento por su ID
        Movimiento movimiento = this.daoCrudGeneral.obtenerPorID(Movimiento.class, id);

        // Si no existe, retorna null
        if (movimiento == null) return null;

        // Crea y retorna un DTO con los datos del movimiento encontrado
        return new MovimientoDTO(movimiento);
    }

    @Override
    public List<MovimientoDTO> obtenerTodo() {
        // Obtiene la lista de todos los movimientos registrados
        List<MovimientoDTO> movimientoDTOS = new ArrayList<>();

        // Convierte cada entidad de movimiento a un DTO y la agrega a la lista
        for (Movimiento movimiento : this.daoCrudGeneral.obtenerTodo(Movimiento.class)) {
            MovimientoDTO movimientoDTO = new MovimientoDTO(movimiento);
            movimientoDTOS.add(movimientoDTO);
        }

        // Retorna la lista de DTOs
        return movimientoDTOS;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param movimiento    Entidad a la que se le cargarán los datos
     * @param movimientoDTO DTO con los datos a cargar
     */
    public void cargarDatosDTO(Movimiento movimiento, MovimientoDTO movimientoDTO) {
        movimiento.setId(movimientoDTO.getId());
        movimiento.setComentario(movimientoDTO.getComentario());
        movimiento.setEstado(movimientoDTO.getEstado());
        movimiento.setFechaDelRegistro(movimientoDTO.getFechaDelRegistro());
        movimiento.setUsername(movimientoDTO.getUsername());

        Ubicacion ubicacion = this.daoCrudGeneral.obtenerPorID(Ubicacion.class, movimientoDTO.getIdUbicacion());
        movimiento.setUbicacion(ubicacion);

        Equipo equipo = this.daoCrudGeneral.obtenerPorID(Equipo.class, movimientoDTO.getIdEquipo());
        movimiento.setEquipo(equipo);
    }

    private void controlarRestricciones(MovimientoDTO movimientoDTO) throws EntityException {
        if (movimientoDTO.getEstado() == null) {
            throw new EntityException("El estado del movimiento no puede ser nulo");
        }
        if (movimientoDTO.getComentario() == null || movimientoDTO.getComentario().isEmpty()) {
            throw new EntityException("El comentario del movimiento no puede ser nulo o vacío");
        }
        if (movimientoDTO.getComentario().length() > 250) {
            throw new EntityException("El comentario del movimiento no puede superar los 100 caracteres");
        }
        if (movimientoDTO.getComentario().length() < 3) {
            throw new EntityException("El comentario del movimiento no puede tener menos de 3 caracteres");
        }
        if (movimientoDTO.getIdUbicacion() == null) {
            throw new EntityException("El ID de la ubicación no puede ser nulo");
        }
        if (movimientoDTO.getIdEquipo() == null) {
            throw new EntityException("El ID del equipo no puede ser nulo");
        }
        if (movimientoDTO.getUsername() == null || movimientoDTO.getUsername().isEmpty()) {
            throw new EntityException("El nombre de usuario no puede ser nulo o vacío");
        }
        if (movimientoDTO.getUsername().length() > 100) {
            throw new EntityException("El nombre de usuario no puede superar los 100 caracteres");
        }
        if (movimientoDTO.getUsername().length() < 5) {
            throw new EntityException("El nombre de usuario no puede tener menos de 5 caracteres");
        }
        if (movimientoDTO.getFechaDelRegistro() == null) {
            throw new EntityException("La fecha del registro no puede ser nula");
        }
        if(movimientoDTO.getFechaDelRegistro().isAfter(LocalDateTime.now())){
            throw new EntityException("La fecha del registro no puede ser mayor a la fecha actual");
        }





//        if (modeloDTO.getEstado() == null) {
//            throw new EntityException("El estado del modelo no puede ser nulo");
//        }
//        if (modeloDTO.getNombre() == null || modeloDTO.getNombre().isEmpty()) {
//            throw new EntityException("El nombre del modelo no puede ser nulo o vacío");
//        }
//        if (modeloDTO.getNombre().length() > 50) {
//            throw new EntityException("El nombre del modelo no puede superar los 100 caracteres");
//        }
//        if (modeloDTO.getNombre().length() < 3) {
//            throw new EntityException("El nombre del modelo no puede tener menos de 3 caracteres");
//        }
//        if (modeloDTO.getIdMarca() == null) {
//            throw new EntityException("El ID de la marca no puede ser nulo");
//        }
    }
}

package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.IntervencionDTO;
import cfc.servidor.entidades.Equipo;
import cfc.servidor.entidades.Intervencion;
import cfc.servidor.entidades.TipoIntervencion;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.IIntervencionBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de intervención.
 */
@Stateless
public class IntervencionBean implements IIntervencionBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public IntervencionDTO registrar(IntervencionDTO intervencionDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(intervencionDTO);

        // Crea una nueva entidad de intervención donde se cargarán los datos
        Intervencion intervencion = new Intervencion();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(intervencion, intervencionDTO);

        // Registra la intervención en la base de datos
        this.daoCrudGeneral.registrar(intervencion);

        // Actualiza el DTO con el ID generado
        intervencionDTO.setId(intervencion.getId());

        // Retorna el DTO actualizado
        return intervencionDTO;
    }

    @Override
    public void actualizar(IntervencionDTO intervencionDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(intervencionDTO);
        // Obtiene la entidad de intervención por su ID
        Intervencion intervencion = this.daoCrudGeneral.obtenerPorID(Intervencion.class, intervencionDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (intervencion != null) {
            this.cargarDatosDTO(intervencion, intervencionDTO);
            this.daoCrudGeneral.actualizar(intervencion);
        }
    }

    @Override
    public IntervencionDTO obtenerPorID(Integer id) {

        // Obtiene la entidad de intervención por su ID
        Intervencion intervencion = this.daoCrudGeneral.obtenerPorID(Intervencion.class, id);

        // Si no existe, retorna null
        if (intervencion == null) return null;

        // Crea y retorna un DTO con los datos de la intervención encontrada
        return new IntervencionDTO(intervencion);
    }


    @Override
    public List<IntervencionDTO> obtenerTodo() {
        // Obtiene la lista de todas las intervenciones registradas
        List<Intervencion> intervenciones = this.daoCrudGeneral.obtenerTodo(Intervencion.class);

        // Crea una lista de DTOs donde se guardarán los datos de las intervenciones.
        List<IntervencionDTO> intervencionesDTOS = new ArrayList<>();

        // Convierte cada entidad de intervención a un DTO y la agrega a la lista
        for (Intervencion intervencion : intervenciones) {
            intervencionesDTOS.add(new IntervencionDTO(intervencion));
        }

        // Retorna la lista de DTOs
        return intervencionesDTOS;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param intervencion    Entidad a la que se le cargarán los datos
     * @param intervencionDTO DTO con los datos a cargar
     */
    public void cargarDatosDTO(Intervencion intervencion, IntervencionDTO intervencionDTO) {
        intervencion.setId(intervencionDTO.getId());
        intervencion.setMotivo(intervencionDTO.getMotivo());
        intervencion.setObservacion(intervencionDTO.getObservacion());
        intervencion.setFechaYHora(intervencionDTO.getFechaYHora());

        TipoIntervencion tipoIntervencion = this.daoCrudGeneral.obtenerPorID(TipoIntervencion.class, intervencionDTO.getIdTipoIntervencion());
        intervencion.setTipoIntervencion(tipoIntervencion);

        Equipo equipo = this.daoCrudGeneral.obtenerPorID(Equipo.class, intervencionDTO.getIdEquipoIntervenido());
        intervencion.setEquipoIntervenido(equipo);
    }

    private void controlarRestricciones(IntervencionDTO intervencionDTO) throws EntityException {

        if(intervencionDTO.getObservacion().length() > 100) {
            throw new IllegalArgumentException("El campo observación no puede superar los 100 caracteres");
        }
        if(intervencionDTO.getFechaYHora().isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("La fecha y hora no puede ser posterior a la actual");
        }
        if (intervencionDTO.getFechaYHora() == null) {
            throw new IllegalArgumentException("La fecha y hora no puede ser nula");
        }
        if(intervencionDTO.getMotivo() == null) {
            throw new IllegalArgumentException("El motivo no puede ser nulo");
        }
        if(intervencionDTO.getObservacion() == null) {
            throw new IllegalArgumentException("La observación no puede ser nula");
        }
        if(intervencionDTO.getIdTipoIntervencion() == null) {
            throw new IllegalArgumentException("El tipo de intervención no puede ser nulo");
        }
        if(intervencionDTO.getIdEquipoIntervenido() == null) {
            throw new IllegalArgumentException("El equipo intervenido no puede ser nulo");
        }
    }

}
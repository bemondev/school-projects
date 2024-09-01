package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.TipoIntervencionDTO;
import cfc.servidor.entidades.TipoIntervencion;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.ITipoIntervencionBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de tipo de intervención.
 */
@Stateless
public class TipoIntervencionBean implements ITipoIntervencionBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public TipoIntervencionDTO registrar(TipoIntervencionDTO tipoIntervencionDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(tipoIntervencionDTO);

        // Crea una nueva entidad de tipo de intervención donde se cargarán los datos
        TipoIntervencion tipoIntervencion = new TipoIntervencion();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(tipoIntervencion, tipoIntervencionDTO);

        // Registra el tipo de intervención en la base de datos
        this.daoCrudGeneral.registrar(tipoIntervencion);

        // Actualiza el DTO con el ID generado
        tipoIntervencionDTO.setId(tipoIntervencion.getId());

        // Retorna el DTO actualizado
        return tipoIntervencionDTO;
    }

    @Override
    public void actualizar(TipoIntervencionDTO tipoIntervencionDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(tipoIntervencionDTO);

        // Obtiene la entidad de tipo de intervención por su ID
        TipoIntervencion tipoIntervencion = this.daoCrudGeneral.obtenerPorID(TipoIntervencion.class, tipoIntervencionDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (tipoIntervencion != null) {
            this.cargarDatosDTO(tipoIntervencion, tipoIntervencionDTO);
            this.daoCrudGeneral.actualizar(tipoIntervencion);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de tipo de intervención por su ID
        TipoIntervencion tipoIntervencion = this.daoCrudGeneral.obtenerPorID(TipoIntervencion.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (tipoIntervencion != null) {
            tipoIntervencion.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(tipoIntervencion);
        }
    }

    @Override
    public void desactivar(Integer id) {
        // Obtiene la entidad de tipo de intervención por su ID
        TipoIntervencion tipoIntervencion = this.daoCrudGeneral.obtenerPorID(TipoIntervencion.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (tipoIntervencion != null) {
            tipoIntervencion.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(tipoIntervencion);
        }
    }

    @Override
    public TipoIntervencionDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de tipo de intervención por su ID
        TipoIntervencion tipoIntervencion = this.daoCrudGeneral.obtenerPorID(TipoIntervencion.class, id);

        // Si no existe, retorna null
        if (tipoIntervencion == null) return null;

        // Crea y retorna un DTO con los datos del tipo de intervención encontrado
        return new TipoIntervencionDTO(tipoIntervencion);
    }

    @Override
    public TipoIntervencionDTO obtenerPorNombre(String nombre) {
        // Obtiene la entidad de tipo de intervención por su nombre
        TipoIntervencion tipoIntervencion = this.daoCrudGeneral.obtenerPorNombre(TipoIntervencion.class, nombre);

        // Si no existe, retorna null
        if (tipoIntervencion == null) return null;

        // Crea y retorna un DTO con los datos del tipo de intervención encontrado
        return new TipoIntervencionDTO(tipoIntervencion);
    }

    @Override
    public List<TipoIntervencionDTO> obtenerTodo() {
        // Obtiene la lista de todos los tipos de intervención registrados
        List<TipoIntervencionDTO> tiposIntervencionDTOs = new ArrayList<>();

        // Convierte cada entidad de tipo de intervención a un DTO y la agrega a la lista
        for (TipoIntervencion tipoIntervencion : this.daoCrudGeneral.obtenerTodo(TipoIntervencion.class)) {
            TipoIntervencionDTO dto = new TipoIntervencionDTO(tipoIntervencion);
            tiposIntervencionDTOs.add(dto);
        }

        // Retorna la lista de DTOs
        return tiposIntervencionDTOs;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param tipoIntervencion    Entidad a la que se le cargarán los datos
     * @param tipoIntervencionDTO DTO con los datos a cargar
     */
    private void cargarDatosDTO(TipoIntervencion tipoIntervencion, TipoIntervencionDTO tipoIntervencionDTO) {
        tipoIntervencion.setId(tipoIntervencionDTO.getId());
        tipoIntervencion.setNombre(tipoIntervencionDTO.getNombre());
        tipoIntervencion.setEstado(tipoIntervencionDTO.getEstado());
    }

    private void controlarRestricciones(TipoIntervencionDTO tipoIntervencionDTO) throws EntityException {
        if (tipoIntervencionDTO.getEstado() == null) {
            throw new EntityException("El estado del tipo de intervención no puede ser nulo");
        }
        if (tipoIntervencionDTO.getNombre() == null || tipoIntervencionDTO.getNombre().isEmpty()) {
            throw new EntityException("El nombre del tipo de intervención no puede ser nulo o vacío");
        }
        if (tipoIntervencionDTO.getNombre().length() > 100) {
            throw new EntityException("El nombre del tipo de intervención no puede superar los 100 caracteres");
        }
        if (tipoIntervencionDTO.getNombre().length() < 3) {
            throw new EntityException("El nombre del tipo de intervención no puede tener menos de 3 caracteres");
        }


    }
}

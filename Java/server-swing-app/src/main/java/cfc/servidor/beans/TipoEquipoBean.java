package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.TipoEquipoDTO;
import cfc.servidor.entidades.TipoEquipo;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.ITipoEquipoBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de tipo de equipo.
 */
@Stateless
public class TipoEquipoBean implements ITipoEquipoBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================

    @Override
    public TipoEquipoDTO registrar(TipoEquipoDTO tipoEquipoDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(tipoEquipoDTO);

        // Crea una nueva entidad de tipo de equipo donde se cargarán los datos
        TipoEquipo tipoEquipo = new TipoEquipo();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(tipoEquipo, tipoEquipoDTO);

        // Registra el tipo de equipo en la base de datos
        this.daoCrudGeneral.registrar(tipoEquipo);

        // Actualiza el DTO con el ID generado
        tipoEquipoDTO.setId(tipoEquipo.getId());

        // Retorna el DTO actualizado
        return tipoEquipoDTO;
    }


    @Override
    public void actualizar(TipoEquipoDTO tipoEquipoDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(tipoEquipoDTO);

        // Obtiene la entidad de tipo de equipo por su ID
        TipoEquipo tipoEquipo = this.daoCrudGeneral.obtenerPorID(TipoEquipo.class, tipoEquipoDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (tipoEquipo != null) {
            this.cargarDatosDTO(tipoEquipo, tipoEquipoDTO);
            this.daoCrudGeneral.actualizar(tipoEquipo);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de tipo de equipo por su ID
        TipoEquipo tipoEquipo = this.daoCrudGeneral.obtenerPorID(TipoEquipo.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (tipoEquipo != null) {
            tipoEquipo.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(tipoEquipo);
        }
    }

    @Override
    public void desactivar(Integer id) {
        // Obtiene la entidad de tipo de equipo por su ID
        TipoEquipo tipoEquipo = this.daoCrudGeneral.obtenerPorID(TipoEquipo.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (tipoEquipo != null) {
            tipoEquipo.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(tipoEquipo);
        }
    }

    @Override
    public TipoEquipoDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de tipo de equipo por su ID
        TipoEquipo tipoEquipo = this.daoCrudGeneral.obtenerPorID(TipoEquipo.class, id);

        // Si no existe, retorna null
        if (tipoEquipo == null) return null;

        // Crea y retorna un DTO con los datos del tipo de equipo encontrado
        return new TipoEquipoDTO(tipoEquipo);
    }

    @Override
    public TipoEquipoDTO obtenerPorNombre(String nombre) {
        // Obtiene la entidad de tipo de equipo por su nombre
        TipoEquipo tipoEquipo = this.daoCrudGeneral.obtenerPorNombre(TipoEquipo.class, nombre);

        // Si no existe, retorna null
        if (tipoEquipo == null) return null;

        // Crea y retorna un DTO con los datos del tipo de equipo encontrado
        return new TipoEquipoDTO(tipoEquipo);
    }

    @Override
    public List<TipoEquipoDTO> obtenerTodo() {
        // Obtiene la lista de todos los tipos de equipo registrados
        List<TipoEquipoDTO> listaDTOs = new ArrayList<>();

        // Convierte cada entidad de tipo de equipo a un DTO y la agrega a la lista
        for (TipoEquipo tipoEquipo : this.daoCrudGeneral.obtenerTodo(TipoEquipo.class)) {
            TipoEquipoDTO tipoEquipoDTO = new TipoEquipoDTO(tipoEquipo);
            listaDTOs.add(tipoEquipoDTO);
        }

        // Retorna la lista de DTOs
        return listaDTOs;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param tipoEquipo    Entidad a la que se le cargarán los datos
     * @param tipoEquipoDTO DTO con los datos a cargar
     */
    private void cargarDatosDTO(TipoEquipo tipoEquipo, TipoEquipoDTO tipoEquipoDTO) {
        tipoEquipo.setId(tipoEquipoDTO.getId());
        tipoEquipo.setNombre(tipoEquipoDTO.getNombre());
        tipoEquipo.setEstado(tipoEquipoDTO.getEstado());
    }

    private void controlarRestricciones(TipoEquipoDTO tipoEquipoDTO) throws EntityException {
        if (tipoEquipoDTO.getEstado() == null) {
            throw new EntityException("El estado del tipo de equipo no puede ser nulo");
        }
        if (tipoEquipoDTO.getNombre() == null || tipoEquipoDTO.getNombre().isEmpty()) {
            throw new EntityException("El nombre del tipo de equipo no puede ser nulo o vacío");
        }
        if (tipoEquipoDTO.getNombre().length() > 100) {
            throw new EntityException("El nombre del tipo de equipo no puede superar los 100 caracteres");
        }
        if (tipoEquipoDTO.getNombre().length() < 3) {
            throw new EntityException("El nombre del tipo de equipo no puede tener menos de 3 caracteres");
        }
    }
}

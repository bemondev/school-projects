package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.entidades.Perfil;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.IPerfilBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de perfil.
 */
@Stateless
public class PerfilBean implements IPerfilBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================

    @Override
    public PerfilDTO registrar(PerfilDTO perfilDTO) throws EntityException {

        // controla las restricciones de los datos
        controlarRestricciones(perfilDTO);

        // Crea una nueva entidad de perfil donde se cargarán los datos
        Perfil perfil = new Perfil();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(perfil, perfilDTO);

        // Registra el perfil en la base de datos
        this.daoCrudGeneral.registrar(perfil);

        // Actualiza el DTO con el ID generado
        perfilDTO.setId(perfil.getId());

        // Retorna el DTO actualizado
        return perfilDTO;
    }

    @Override
    public void actualizar(PerfilDTO perfilDTO) throws EntityException {

        // controla las restricciones de los datos
        controlarRestricciones(perfilDTO);

        // Obtiene la entidad de perfil por su ID
        Perfil perfil = this.daoCrudGeneral.obtenerPorID(Perfil.class, perfilDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (perfil != null) {
            this.cargarDatosDTO(perfil, perfilDTO);
            this.daoCrudGeneral.actualizar(perfil);
        }
    }

    @Override
    public PerfilDTO obtenerPorNombre(String nombre) {
        // Obtiene la entidad de perfil por su nombre
        Perfil perfil = this.daoCrudGeneral.obtenerPorNombre(Perfil.class, nombre);

        // Si no existe, retorna null
        if (perfil == null) return null;

        // Crea y retorna un DTO con los datos del perfil encontrado
        return new PerfilDTO(perfil);
    }

    @Override
    public PerfilDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de perfil por su ID
        Perfil perfil = this.daoCrudGeneral.obtenerPorID(Perfil.class, id);

        // Si no existe, retorna null
        if (perfil == null) return null;

        // Crea y retorna un DTO con los datos del perfil encontrado
        return new PerfilDTO(perfil);
    }

    @Override
    public void desactivar(Integer id) {
        // Obtiene la entidad de perfil por su ID
        Perfil perfil = this.daoCrudGeneral.obtenerPorID(Perfil.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (perfil != null) {
            perfil.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(perfil);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de perfil por su ID
        Perfil perfil = this.daoCrudGeneral.obtenerPorID(Perfil.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (perfil != null) {
            perfil.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(perfil);
        }
    }

    @Override
    public List<PerfilDTO> obtenerTodo() {
        // Crea una lista de DTOs donde se guardarán los datos de los perfiles.
        List<PerfilDTO> perfilDTOS = new ArrayList<>();

        // Convierte cada entidad de perfil a un DTO y la agrega a la lista
        for (Perfil perfil : this.daoCrudGeneral.obtenerTodo(Perfil.class)) {
            PerfilDTO perfilDTO = new PerfilDTO(perfil);
            perfilDTOS.add(perfilDTO);
        }

        // Retorna la lista de DTOs
        return perfilDTOS;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param perfil    Entidad a la que se le cargarán los datos
     * @param perfilDTO DTO con los datos a cargar
     */
    private void cargarDatosDTO(Perfil perfil, PerfilDTO perfilDTO) {
        perfil.setId(perfilDTO.getId());
        perfil.setNombre(perfilDTO.getNombre());
        perfil.setEstado(perfilDTO.getEstado());
        perfil.setPermisos(perfilDTO.getPermisos());
    }

    private void controlarRestricciones(PerfilDTO perfilDTO) throws EntityException {

        if (perfilDTO.getEstado() == null) {
            throw new EntityException("El estado del perfil no puede ser nulo");
        }
        if (perfilDTO.getNombre() == null || perfilDTO.getNombre().isEmpty()) {
            throw new EntityException("El nombre del perfil no puede ser nulo o vacío");
        }
        if (perfilDTO.getNombre().length() > 100) {
            throw new EntityException("El nombre del perfil no puede superar los 100 caracteres");
        }
        if (perfilDTO.getNombre().length() < 3) {
            throw new EntityException("El nombre del perfil no puede tener menos de 3 caracteres");
        }
        if (perfilDTO.getPermisos() == null || perfilDTO.getPermisos().isEmpty()) {
            throw new EntityException("El perfil debe tener al menos un permiso");
        }
    }

}

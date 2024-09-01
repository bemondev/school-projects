package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.PaisDTO;
import cfc.servidor.entidades.Pais;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.IPaisBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de país.
 */
@Stateless
public class PaisBean implements IPaisBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public PaisDTO registrar(PaisDTO paisDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(paisDTO);

        // Crea una nueva entidad de país donde se cargarán los datos
        Pais pais = new Pais();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(pais, paisDTO);

        // Registra el país en la base de datos
        this.daoCrudGeneral.registrar(pais);

        // Actualiza el DTO con el ID generado
        paisDTO.setId(pais.getId());

        // Retorna el DTO actualizado
        return paisDTO;
    }

    @Override
    public void actualizar(PaisDTO paisDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(paisDTO);

        // Obtiene la entidad de país por su ID
        Pais pais = this.daoCrudGeneral.obtenerPorID(Pais.class, paisDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (pais != null) {
            this.cargarDatosDTO(pais, paisDTO);
            this.daoCrudGeneral.actualizar(pais);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de país por su ID
        Pais pais = this.daoCrudGeneral.obtenerPorID(Pais.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (pais != null) {
            pais.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(pais);
        }
    }

    @Override
    public void desactivar(Integer id) {
        // Obtiene la entidad de país por su ID
        Pais pais = this.daoCrudGeneral.obtenerPorID(Pais.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (pais != null) {
            pais.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(pais);
        }
    }

    @Override
    public List<PaisDTO> obtenerTodo() {
        // Obtiene la lista de todos los países registrados
        List<PaisDTO> listaDTOs = new ArrayList<>();

        // Convierte cada entidad de país a un DTO y la agrega a la lista
        for (Pais pais : this.daoCrudGeneral.obtenerTodo(Pais.class)) {
            PaisDTO paisDTO = new PaisDTO(pais);
            listaDTOs.add(paisDTO);
        }

        // Retorna la lista de DTOs
        return listaDTOs;
    }

    @Override
    public PaisDTO obtenerPorNombre(String nombre) {
        // Obtiene la entidad de país por su nombre
        Pais pais = this.daoCrudGeneral.obtenerPorNombre(Pais.class, nombre);

        // Si no existe, retorna null
        if (pais == null) return null;

        // Crea y retorna un DTO con los datos del país encontrado
        return new PaisDTO(pais);
    }

    @Override
    public PaisDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de país por su ID
        Pais pais = this.daoCrudGeneral.obtenerPorID(Pais.class, id);

        // Si no existe, retorna null
        if (pais == null) return null;

        // Crea y retorna un DTO con los datos del país encontrado
        return new PaisDTO(pais);
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param pais    Entidad a la que se le cargarán los datos
     * @param paisDTO DTO con los datos a cargar
     */
    public void cargarDatosDTO(Pais pais, PaisDTO paisDTO) {
        pais.setId(paisDTO.getId());
        pais.setNombre(paisDTO.getNombre());
        pais.setEstado(paisDTO.getEstado());
    }

    private void controlarRestricciones(PaisDTO paisDTO) throws EntityException {
        if (paisDTO.getEstado() == null) {
            throw new EntityException("El estado del país no puede ser nulo");
        }
        if (paisDTO.getNombre() == null || paisDTO.getNombre().isEmpty()) {
            throw new EntityException("El nombre del país no puede ser nulo o vacío");
        }
        if (paisDTO.getNombre().length() > 100) {
            throw new EntityException("El nombre del país no puede superar los 100 caracteres");
        }
        if (paisDTO.getNombre().length() < 3) {
            throw new EntityException("El nombre del país no puede tener menos de 3 caracteres");
        }
    }
}

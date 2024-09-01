package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.MarcaDTO;
import cfc.servidor.entidades.Marca;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.IMarcaBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de marca.
 */
@Stateless
public class MarcaBean implements IMarcaBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public MarcaDTO registrar(MarcaDTO marcaDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(marcaDTO);

        // Crea una nueva entidad de marca donde se cargarán los datos
        Marca marca = new Marca();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(marca, marcaDTO);

        // Registra la marca en la base de datos
        this.daoCrudGeneral.registrar(marca);

        // Actualiza el DTO con el ID generado
        marcaDTO.setId(marca.getId());

        // Retorna el DTO actualizado
        return marcaDTO;
    }

    @Override
    public void actualizar(MarcaDTO marcaDTO) throws EntityException {

        // Obtiene la entidad de marca por su ID
        controlarRestricciones(marcaDTO);

        // Obtiene la entidad de marca por su ID
        Marca marca = this.daoCrudGeneral.obtenerPorID(Marca.class, marcaDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (marca != null) {
            this.cargarDatosDTO(marca, marcaDTO);
            this.daoCrudGeneral.actualizar(marca);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de marca por su ID
        Marca marca = this.daoCrudGeneral.obtenerPorID(Marca.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (marca != null) {
            marca.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(marca);
        }
    }

    @Override
    public void desactivar(Integer id) {
        // Obtiene la entidad de marca por su ID
        Marca marca = this.daoCrudGeneral.obtenerPorID(Marca.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (marca != null) {
            marca.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(marca);
        }
    }

    @Override
    public List<MarcaDTO> obtenerTodo() {
        // Crea una lista de DTOs donde se guardarán los datos de las marcas.
        List<MarcaDTO> listaDTOs = new ArrayList<>();

        // Convierte cada entidad de marca a un DTO y la agrega a la lista
        for (Marca marca : this.daoCrudGeneral.obtenerTodo(Marca.class)) {
            MarcaDTO marcaDTO = new MarcaDTO(marca);
            listaDTOs.add(marcaDTO);
        }

        // Retorna la lista de DTOs
        return listaDTOs;
    }

    @Override
    public MarcaDTO obtenerPorNombre(String nombre) {
        // Obtiene la entidad de marca por su nombre
        Marca marca = this.daoCrudGeneral.obtenerPorNombre(Marca.class, nombre);

        // Si no existe, retorna null
        if (marca == null) return null;

        // Crea y retorna un DTO con los datos de la marca encontrada
        return new MarcaDTO(marca);
    }

    @Override
    public MarcaDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de marca por su ID
        Marca marca = this.daoCrudGeneral.obtenerPorID(Marca.class, id);

        // Si no existe, retorna null
        if (marca == null) return null;

        // Crea y retorna un DTO con los datos de la marca encontrada
        return new MarcaDTO(marca);
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param marca    Entidad a la que se le cargarán los datos
     * @param marcaDTO DTO con los datos a cargar
     */
    public void cargarDatosDTO(Marca marca, MarcaDTO marcaDTO) {
        marca.setId(marcaDTO.getId());
        marca.setNombre(marcaDTO.getNombre());
        marca.setEstado(marcaDTO.getEstado());
    }

    private void controlarRestricciones(MarcaDTO marcaDTO) throws EntityException {
        if (marcaDTO.getEstado() == null) {
            throw new EntityException("El estado de la marca no puede ser nulo");
        }
        if (marcaDTO.getNombre() == null || marcaDTO.getNombre().isEmpty()) {
            throw new EntityException("El nombre de la marca no puede ser nulo o vacío");
        }
        if (marcaDTO.getNombre().length() > 100) {
            throw new EntityException("El nombre de la marca no puede superar los 100 caracteres");
        }
        if (marcaDTO.getNombre().length() < 3) {
            throw new EntityException("El nombre de la marca no puede tener menos de 3 caracteres");
        }


    }
}

package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DAOs.DAOModelo;
import cfc.servidor.DTOs.MarcaDTO;
import cfc.servidor.DTOs.ModeloDTO;
import cfc.servidor.entidades.Marca;
import cfc.servidor.entidades.Modelo;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.IModeloBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class ModeloBean implements IModeloBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    @EJB
    private DAOModelo daoModelo;

    // ======================= Métodos remotos =======================

    @Override
    public ModeloDTO registrar(ModeloDTO modeloDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(modeloDTO);

        // Crea una nueva entidad de modelo donde se cargarán los datos
        Modelo modelo = new Modelo();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(modelo, modeloDTO);

        // Registra el modelo en la base de datos
        this.daoCrudGeneral.registrar(modelo);

        // Actualiza el DTO con el ID generado
        modeloDTO.setId(modelo.getId());

        // Retorna el DTO actualizado
        return modeloDTO;
    }

    @Override
    public void actualizar(ModeloDTO modeloDTO) throws EntityException {

        // Controla las restricciones de los datos
        controlarRestricciones(modeloDTO);

        // Obtiene la entidad de modelo por su ID
        Modelo modelo = this.daoCrudGeneral.obtenerPorID(Modelo.class, modeloDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (modelo != null) {
            this.cargarDatosDTO(modelo, modeloDTO);
            this.daoCrudGeneral.actualizar(modelo);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de modelo por su ID
        Modelo modelo = this.daoCrudGeneral.obtenerPorID(Modelo.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (modelo != null) {
            modelo.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(modelo);
        }
    }

    @Override
    public void desactivar(Integer idModelo) {
        // Obtiene la entidad de modelo por su ID
        Modelo modelo = this.daoCrudGeneral.obtenerPorID(Modelo.class, idModelo);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (modelo != null) {
            modelo.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(modelo);
        }
    }

    @Override
    public List<ModeloDTO> obtenerTodo() {
        // Crea una lista de DTOs donde se guardarán los datos de los modelos.
        List<ModeloDTO> listaDTOs = new ArrayList<>();

        // Convierte cada entidad de modelo a un DTO y la agrega a la lista
        for (Modelo modelo : this.daoCrudGeneral.obtenerTodo(Modelo.class)) {
            ModeloDTO modeloDTO = new ModeloDTO(modelo);
            listaDTOs.add(modeloDTO);
        }

        // Retorna la lista de DTOs
        return listaDTOs;
    }

    @Override
    public ModeloDTO obtenerPorNombre(String nombre) {
        // Obtiene la entidad de modelo por su nombre
        Modelo modelo = this.daoCrudGeneral.obtenerPorNombre(Modelo.class, nombre);

        // Si no existe, retorna null
        if (modelo == null) return null;

        // Crea y retorna un DTO con los datos del modelo encontrado
        return new ModeloDTO(modelo);
    }

    @Override
    public ModeloDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de modelo por su ID
        Modelo modelo = this.daoCrudGeneral.obtenerPorID(Modelo.class, id);

        // Si no existe, retorna null
        if (modelo == null) return null;

        // Crea y retorna un DTO con los datos del modelo encontrado
        return new ModeloDTO(modelo);
    }

    @Override
    public List<ModeloDTO> obtenerPorMarca(Integer id) {
        // Crea una lista de DTOs donde se guardarán los datos de los modelos.
        List<ModeloDTO> listaDTOs = new ArrayList<>();

        // Convierte cada entidad de modelo a un DTO y la agrega a la lista
        for (Modelo modelo : this.daoModelo.obtenerPorMarca(id)) {
            ModeloDTO modeloDTO = new ModeloDTO(modelo);
            listaDTOs.add(modeloDTO);
        }

        // Retorna la lista de DTOs
        return listaDTOs;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param modelo    Entidad a la que se le cargarán los datos
     * @param modeloDTO DTO con los datos a cargar
     */
    public void cargarDatosDTO(Modelo modelo, ModeloDTO modeloDTO) {
        modelo.setId(modeloDTO.getId());
        modelo.setNombre(modeloDTO.getNombre());
        modelo.setEstado(modeloDTO.getEstado());

        Marca marca = this.daoCrudGeneral.obtenerPorID(Marca.class, modeloDTO.getIdMarca());
        modelo.setMarca(marca);
    }

    private void controlarRestricciones(ModeloDTO modeloDTO) throws EntityException {
        if (modeloDTO.getEstado() == null) {
            throw new EntityException("El estado del modelo no puede ser nulo");
        }
        if (modeloDTO.getNombre() == null || modeloDTO.getNombre().isEmpty()) {
            throw new EntityException("El nombre del modelo no puede ser nulo o vacío");
        }
        if (modeloDTO.getNombre().length() > 50) {
            throw new EntityException("El nombre del modelo no puede superar los 100 caracteres");
        }
        if (modeloDTO.getNombre().length() < 3) {
            throw new EntityException("El nombre del modelo no puede tener menos de 3 caracteres");
        }
        if (modeloDTO.getIdMarca() == null) {
            throw new EntityException("El ID de la marca no puede ser nulo");
        }
    }
}

package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.UbicacionDTO;
import cfc.servidor.entidades.Ubicacion;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.servicios.IUbicacionBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de ubicación.
 */
@Stateless
public class UbicacionBean implements IUbicacionBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public UbicacionDTO registrar(UbicacionDTO ubicacionDTO) throws EntityException {

        //Controla las restricciones de los datos
        controlarRestricciones(ubicacionDTO);

        // Crea una nueva entidad de ubicación donde se cargarán los datos
        Ubicacion ubicacion = new Ubicacion();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(ubicacion, ubicacionDTO);

        // Registra la ubicación en la base de datos
        this.daoCrudGeneral.registrar(ubicacion);

        // Actualiza el DTO con el ID generado
        ubicacionDTO.setId(ubicacion.getId());

        // Retorna el DTO actualizado
        return ubicacionDTO;
    }

    @Override
    public void actualizar(UbicacionDTO ubicacionDTO) throws EntityException {

        //Controla las restricciones de los datos
        controlarRestricciones(ubicacionDTO);

        // Obtiene la entidad de ubicación por su ID
        Ubicacion ubicacion = this.daoCrudGeneral.obtenerPorID(Ubicacion.class, ubicacionDTO.getId());

        // Si existe, carga los datos del DTO a la entidad y la actualiza en la base de datos
        if (ubicacion != null) {
            this.cargarDatosDTO(ubicacion, ubicacionDTO);
            this.daoCrudGeneral.actualizar(ubicacion);
        }
    }

    @Override
    public UbicacionDTO obtenerPorNombre(String nombre) {
        // Obtiene la entidad de ubicación por su nombre
        Ubicacion ubicacion = this.daoCrudGeneral.obtenerPorNombre(Ubicacion.class, nombre);

        // Si no existe, retorna null
        if (ubicacion == null) return null;

        // Crea y retorna un DTO con los datos de la ubicación encontrada
        return new UbicacionDTO(ubicacion);
    }

    @Override
    public UbicacionDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de ubicación por su ID
        Ubicacion ubicacion = this.daoCrudGeneral.obtenerPorID(Ubicacion.class, id);

        // Si no existe, retorna null
        if (ubicacion == null) return null;

        // Crea y retorna un DTO con los datos de la ubicación encontrada
        return new UbicacionDTO(ubicacion);
    }


    @Override
    public List<UbicacionDTO> obtenerTodo() {
        // Obtiene la lista de todas las ubicaciones registradas
        List<UbicacionDTO> ubicacionesDTO = new ArrayList<>();

        // Convierte cada entidad de ubicación a un DTO y la agrega a la lista
        List<Ubicacion> ubicaciones = this.daoCrudGeneral.obtenerTodo(Ubicacion.class);

        // Convierte cada entidad de ubicación a un DTO y la agrega a la lista
        for (Ubicacion ubicacion : ubicaciones) {
            UbicacionDTO ubicacionDTO = new UbicacionDTO(ubicacion);
            ubicacionesDTO.add(ubicacionDTO);
        }

        // Retorna la lista de DTOs
        return ubicacionesDTO;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param ubicacion    Entidad a la que se le cargarán los datos
     * @param ubicaciondto DTO con los datos a cargar
     */
    public void cargarDatosDTO(Ubicacion ubicacion, UbicacionDTO ubicaciondto) {
        ubicacion.setId(ubicaciondto.getId());
        ubicacion.setCama(ubicaciondto.getCama());
        ubicacion.setEstado(ubicaciondto.getEstado());
        ubicacion.setNombre(ubicaciondto.getNombre());
        ubicacion.setInstitucion(ubicaciondto.getInstitucion());
        ubicacion.setSector(ubicaciondto.getSector());
        ubicacion.setPiso(ubicaciondto.getPiso());
        ubicacion.setNumero(ubicaciondto.getNumero());
    }

    private void controlarRestricciones(UbicacionDTO ubicacionDTO) throws EntityException {
        if(ubicacionDTO.getNombre() == null || ubicacionDTO.getNombre().isEmpty()) {
            throw new EntityException("El nombre de la ubicación no puede estar vacío");
        }
        if (ubicacionDTO.getNombre().length() > 45) {
            throw new EntityException("El nombre de la ubicación no puede superar los 45 caracteres");
        }
        if (ubicacionDTO.getEstado() == null) {
            throw new EntityException("El estado de la ubicación no puede estar vacío");
        }
        if (ubicacionDTO.getInstitucion() == null) {
            throw new EntityException("La institución de la ubicación no puede estar vacía");
        }
        if (ubicacionDTO.getSector() == null) {
            throw new EntityException("El sector de la ubicación no puede estar vacío");
        }
        if (ubicacionDTO.getPiso() == null) {
            throw new EntityException("El piso de la ubicación no puede estar vacío");
        }
        if (ubicacionDTO.getPiso().length() > 3) {
            throw new EntityException("el piso de la ubicación no puede superar los 3 caracteres");
        }
        if (ubicacionDTO.getCama() == null) {
            throw new EntityException("La cama de la ubicación no puede estar vacía");
        }
        if (ubicacionDTO.getCama().length() > 3) {
            throw new EntityException("La cama de la ubicación no puede superar los 3 caracteres");
        }

        if (String.valueOf(ubicacionDTO.getNumero()) == null){
            throw new EntityException("El número de la ubicación no puede estar vacío");
        }



    }

}

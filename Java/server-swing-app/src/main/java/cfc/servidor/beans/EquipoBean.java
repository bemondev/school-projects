package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DAOs.DAOEquipo;
import cfc.servidor.DTOs.EquipoDTO;
import cfc.servidor.entidades.*;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.local.IEquipoBeanLocal;
import cfc.servidor.servicios.IBajaEquipoBeanRemote;
import cfc.servidor.servicios.IEquipoBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de equipo.
 */
@Stateless
public class EquipoBean implements IEquipoBeanRemote, IEquipoBeanLocal {
    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    @EJB
    private DAOEquipo daoEquipo;

    // ======================= Métodos remotos =======================
    @Override
    public EquipoDTO registrar(EquipoDTO equipoDTO) throws EntityException {
        // Controla las restricciones de los datos
        controlarRestricciones(equipoDTO);
        // Crea una nueva entidad de equipo donde se cargarán los datos
        Equipo equipo = new Equipo();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(equipo, equipoDTO);

        // Registra el equipo en la base de datos
        this.daoCrudGeneral.registrar(equipo);

        // Actualiza el DTO con el ID generado
        equipoDTO.setId(equipo.getId());

        // Retorna el DTO actualizado
        return equipoDTO;
    }

    @Override
    public void actualizar(EquipoDTO equipoDTO) throws EntityException {
        // Controla las restricciones de los datos
        controlarRestricciones(equipoDTO);

        // Obtiene la entidad de equipo por su ID
        Equipo equipo = this.daoCrudGeneral.obtenerPorID(Equipo.class, equipoDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (equipo != null) {
            this.cargarDatosDTO(equipo, equipoDTO);
            this.daoCrudGeneral.actualizar(equipo);
        }
    }

    @Override
    public void desactivar(Integer id) {

        // Obtiene la entidad de equipo por su ID
        Equipo equipo = this.daoCrudGeneral.obtenerPorID(Equipo.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (equipo != null) {
            equipo.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(equipo);
        }
    }

    @Override
    public void activar(Integer id) {

        // Obtiene la entidad de equipo por su ID
        Equipo equipo = this.daoCrudGeneral.obtenerPorID(Equipo.class, id);

        // Si existe, cambia su estado a Activo y lo actualiza en la base de datos
        if (equipo != null) {
            equipo.setEstado(EstadosEnum.ACTIVO);
            this.daoCrudGeneral.actualizar(equipo);
        }
    }

    @Override
    public EquipoDTO obtenerPorNombre(String name) {

        // Obtiene la entidad de equipo por su nombre
        Equipo equipo = this.daoCrudGeneral.obtenerPorNombre(Equipo.class, name);

        // Si no existe, retorna null
        if (equipo == null) return null;

        // Crea y retorna un DTO con los datos del equipo encontrado
        return new EquipoDTO(equipo);
    }

    @Override
    public EquipoDTO obtenerPorID(Integer id) {

        // Obtiene la entidad de equipo por su ID
        Equipo equipo = this.daoCrudGeneral.obtenerPorID(Equipo.class, id);

        // Si no existe, retorna null
        if (equipo == null) return null;

        // Crea y retorna un DTO con los datos del equipo encontrado
        return new EquipoDTO(equipo);
    }

    @Override
    public EquipoDTO obtenerPorIDInterna(Integer idInterno) {
        // Obtiene la entidad de equipo por su ID
        Equipo equipo = this.daoEquipo.obtenerPorIDInterna(idInterno);

        // Si no existe, retorna null
        if (equipo == null) return null;

        // Crea y retorna un DTO con los datos del equipo encontrado
        return new EquipoDTO(equipo);
    }

    @Override
    public EquipoDTO obtenerPorNumeroDeSerie(String numero) {
        // Obtiene la entidad de equipo por su ID
        Equipo equipo = this.daoEquipo.obtenerPorNumeroDeSerie(numero);

        // Si no existe, retorna null
        if (equipo == null) return null;

        // Crea y retorna un DTO con los datos del equipo encontrado
        return new EquipoDTO(equipo);
    }

    @Override
    public List<EquipoDTO> obtenerTodo() {
        // Crea una lista de DTOs donde se guardarán los datos de los equipos.
        List<EquipoDTO> equipoDTOS = new ArrayList<>();

        // Convierte cada entidad de equipo a un DTO y la agrega a la lista
        for (Equipo equipo : this.daoCrudGeneral.obtenerTodo(Equipo.class)) {
            EquipoDTO equipoDTO = new EquipoDTO(equipo);
            equipoDTOS.add(equipoDTO);
        }

        // Retorna la lista de DTOs
        return equipoDTOS;
    }

    @Override
    public List<Equipo> obtenerPorMarca(Integer idMarca) {
        return daoEquipo.obtenerPorMarca(idMarca);
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param equipo    Entidad a la que se le cargarán los datos
     * @param equipoDTO DTO con los datos a cargar
     */
    private void cargarDatosDTO(Equipo equipo, EquipoDTO equipoDTO) {
        equipo.setId(equipoDTO.getId());
        equipo.setIdInterna(equipoDTO.getIdInterna());
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setNumeroSerie(equipoDTO.getNumeroSerie());
        equipo.setFechaAdquisicion(equipoDTO.getFechaAdquisicion());
        equipo.setGarantia(equipoDTO.getGarantia());
        equipo.setProveedor(equipoDTO.getProveedor());
        equipo.setEstado(equipoDTO.getEstado());

        TipoEquipo tipoEquipoDTO = this.daoCrudGeneral.obtenerPorID(TipoEquipo.class, equipoDTO.getIdTipoEquipo());
        equipo.setTipoEquipo(tipoEquipoDTO);

        Imagen imagenDTO = this.daoCrudGeneral.obtenerPorID(Imagen.class, equipoDTO.getIdImagen());
        equipo.setImagen(imagenDTO);

        Modelo modeloDTO = this.daoCrudGeneral.obtenerPorID(Modelo.class, equipoDTO.getIdModeloEquipo());
        equipo.setModeloEquipo(modeloDTO);

        Pais paisDto = this.daoCrudGeneral.obtenerPorID(Pais.class, equipoDTO.getIdPaisDeOrigen());
        equipo.setPaisDeOrigen(paisDto);

        Ubicacion ubicacion = this.daoCrudGeneral.obtenerPorID(Ubicacion.class, equipoDTO.getIdUbicacion());
        equipo.setUbicacion(ubicacion);
    }

    private void controlarRestricciones(EquipoDTO equipoDTO) throws EntityException {
        if (equipoDTO.getIdInterna() == null) {
            throw new EntityException("El id interno no puede ser nulo");
        }
        if (equipoDTO.getNumeroSerie() == null) {
            throw new EntityException("El número de serie no puede ser nulo");
        }
        if (equipoDTO.getNumeroSerie().length() > 50) {
            throw new EntityException("El número de serie no puede ser mayor a 50 caracteres");
        }
        if (equipoDTO.getGarantia() == null) {
            throw new EntityException("La garantia no puede ser nula");
        }
        if (equipoDTO.getGarantia().length() > 50) {
            throw new EntityException("La garantia no puede ser mayor a 50 caracteres");
        }
        if (equipoDTO.getProveedor() == null) {
            throw new EntityException("El proveedor no puede ser nulo");
        }
        if (equipoDTO.getProveedor().length() > 50) {
            throw new EntityException("El proveedor no puede ser mayor a 50 caracteres");
        }
        if (equipoDTO.getIdTipoEquipo() == null) {
            throw new EntityException("El tipo de equipo no puede ser nulo");
        }
        if (equipoDTO.getIdImagen() == null) {
            throw new EntityException("La imagen no puede ser nula");
        }
        if (equipoDTO.getIdModeloEquipo() == null) {
            throw new EntityException("El modelo de equipo no puede ser nulo");
        }
        if (equipoDTO.getIdPaisDeOrigen() == null) {
            throw new EntityException("El país de origen no puede ser nulo");
        }
        if (equipoDTO.getIdUbicacion() == null) {
            throw new EntityException("La ubicación no puede ser nula");
        }
        if (equipoDTO.getEstado() == null) {
            throw new EntityException("El estado no puede ser nulo");
        }
        if(equipoDTO.getFechaAdquisicion().isAfter(LocalDate.now())){
            throw new EntityException("La fecha de adquisición no puede ser mayor a la fecha actual");
        }
    }
}



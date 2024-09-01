package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.BajaEquipoDTO;
import cfc.servidor.entidades.BajaEquipo;
import cfc.servidor.entidades.Equipo;
import cfc.servidor.entidades.Usuario;
import cfc.servidor.servicios.IBajaEquipoBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de BajaEquipo.
 */
@Stateless
public class BajaEquipoBean implements IBajaEquipoBeanRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public BajaEquipoDTO registrar(BajaEquipoDTO bajaEquipoDTO) {
        // Control de restricciones

        // Crear una nueva entidad de BajaEquipo donde se cargarán los datos
        BajaEquipo bajaEquipo = new BajaEquipo();

        // Cargar los datos del DTO a la entidad
        cargarDatosDTO(bajaEquipo, bajaEquipoDTO);

        // Registrar la baja de equipo en la base de datos
        daoCrudGeneral.registrar(bajaEquipo);

        // Actualizar el DTO con el ID generado
        bajaEquipoDTO.setId(bajaEquipo.getId());

        // Retornar el DTO actualizado
        return bajaEquipoDTO;
    }

    @Override
    public void actualizar(BajaEquipoDTO bajaEquipoDTO) {

        // Obtener la entidad de BajaEquipo por su ID
        BajaEquipo bajaEquipo = daoCrudGeneral.obtenerPorID(BajaEquipo.class, bajaEquipoDTO.getId());

        // Si existe, actualizar sus datos y actualizar en la base de datos
        if (bajaEquipo != null) {
            cargarDatosDTO(bajaEquipo, bajaEquipoDTO);
            daoCrudGeneral.actualizar(bajaEquipo);
        }
    }

    @Override
    public BajaEquipoDTO obtenerPorNombre(String nombre) {
        // Obtener la entidad de BajaEquipo por su nombre
        BajaEquipo bajaEquipo = daoCrudGeneral.obtenerPorNombre(BajaEquipo.class, nombre);

        // Si no existe, retornar null
        if (bajaEquipo == null) return null;

        // Crear y retornar un DTO con los datos de la baja de equipo encontrada
        return new BajaEquipoDTO(bajaEquipo);
    }

    @Override
    public BajaEquipoDTO obtenerPorID(Integer id) {
        // Obtener la entidad de BajaEquipo por su ID
        BajaEquipo bajaEquipo = daoCrudGeneral.obtenerPorID(BajaEquipo.class, id);

        // Si no existe, retornar null
        if (bajaEquipo == null) return null;

        // Crear y retornar un DTO con los datos de la baja de equipo encontrada
        return new BajaEquipoDTO(bajaEquipo);
    }

    @Override
    public List<BajaEquipoDTO> obtenerTodo() {
        // Crear una lista de DTOs donde se guardarán de las bajas de equipo encontradas
        List<BajaEquipoDTO> bajaEquipoDTOS = new ArrayList<>();

        // Convertir cada entidad de BajaEquipo a un DTO y agregarlo a la lista
        for (BajaEquipo bajaEquipo : daoCrudGeneral.obtenerTodo(BajaEquipo.class)) {
            BajaEquipoDTO bajaEquipoDTO = new BajaEquipoDTO(bajaEquipo);
            bajaEquipoDTOS.add(bajaEquipoDTO);
        }

        // Retornar la lista de DTOs
        return bajaEquipoDTOS;
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     */
    private void cargarDatosDTO(BajaEquipo bajaEquipo, BajaEquipoDTO bajaEquipoDTO) {
        bajaEquipo.setId(bajaEquipoDTO.getId());
        Equipo equipo = daoCrudGeneral.obtenerPorID(Equipo.class, bajaEquipoDTO.getIdEquipo());
        bajaEquipo.setEquipo(equipo);
        bajaEquipo.setFechaBaja(bajaEquipoDTO.getFechaBaja());

        Usuario usuario = daoCrudGeneral.obtenerPorID(Usuario.class, bajaEquipoDTO.getIdUsuario());
        bajaEquipo.setUsuario(usuario);
        bajaEquipo.setMotivo(bajaEquipoDTO.getMotivo());
        bajaEquipo.setObservaciones(bajaEquipoDTO.getObservaciones());
    }
}



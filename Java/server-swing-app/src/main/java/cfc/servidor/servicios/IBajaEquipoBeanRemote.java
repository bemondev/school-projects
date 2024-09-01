package cfc.servidor.servicios;

import cfc.servidor.DTOs.BajaEquipoDTO;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de baja de equipo.
 * Define los métodos de negocio sobre la entidad BajaEquipo.
 */
@Remote
public interface IBajaEquipoBeanRemote {

    /**
     * Registra una baja de equipo en el sistema.
     *
     * @param bajaEquipoDTO DTO con los datos de la baja de equipo a registrar.
     * @return DTO actualizado con el ID generado.
     */
    BajaEquipoDTO registrar(BajaEquipoDTO bajaEquipoDTO);

    /**
     * Actualiza los datos de una baja de equipo en el sistema.
     *
     * @param bajaEquipoDTO DTO con los datos de la baja de equipo a actualizar.
     */
    void actualizar(BajaEquipoDTO bajaEquipoDTO);

    /**
     * Obtiene una baja de equipo por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre de la baja de equipo a obtener.
     * @return DTO con los datos de la baja de equipo encontrada.
     */
    BajaEquipoDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene una baja de equipo por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id de la baja de equipo a obtener.
     * @return DTO con los datos de la baja de equipo encontrada.
     */
    BajaEquipoDTO obtenerPorID(Integer id);

    /**
     * Obtiene todas las bajas de equipo registradas en el sistema.
     *
     * @return Lista de DTO con los datos de las bajas de equipo.
     */
    List<BajaEquipoDTO> obtenerTodo();


}

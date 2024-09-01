package cfc.servidor.servicios;

import cfc.servidor.DTOs.EquipoDTO;
import cfc.servidor.entidades.Equipo;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de equipo.<br>
 * Define los métodos de negocio sobre la entidad Equipo.
 */
@Remote
public interface IEquipoBeanRemote {

    /**
     * Registra un equipo en el sistema.
     *
     * @param equipoDTO DTO con los datos del equipo a registrar.
     * @return DTO actualizado con el ID generado.
     */
    EquipoDTO registrar(EquipoDTO equipoDTO) throws EntityException;

    /**
     * Actualiza los datos de un equipo en el sistema.
     *
     * @param equipoDTO DTO con los datos del equipo a actualizar.
     */
    void actualizar(EquipoDTO equipoDTO) throws EntityException;

    /**
     * Desactiva (Da de baja) un equipo en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del equipo a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) un equipo desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del equipo a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene un equipo por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre del equipo a obtener.
     * @return DTO con los datos del equipo encontrado.
     */
    EquipoDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene un equipo por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id del equipo a obtener.
     * @return DTO con los datos del equipo encontrado.
     */
    EquipoDTO obtenerPorID(Integer id);

    /**
     * Obtiene un equipo por su ID interno.<br>
     * En caso de no existir, devuelve null.
     *
     * @param idInterno Id interno del equipo a obtener.
     * @return DTO con los datos del equipo encontrado.
     */
    EquipoDTO obtenerPorIDInterna(Integer idInterno);

    /**
     * Obtiene todos los equipos registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los equipos.
     */
    List<EquipoDTO> obtenerTodo();


    List<Equipo> obtenerPorMarca(Integer idMarca);

    EquipoDTO obtenerPorNumeroDeSerie(String numero);
}
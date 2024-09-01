package cfc.servidor.servicios;

import cfc.servidor.DTOs.MovimientoDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de movimiento.
 * Define los métodos de negocio sobre la entidad Movimiento.
 */
@Remote
public interface IMovimientoBeanRemote {

    /**
     * Registra un movimiento en el sistema.
     *
     * @param movimientoDTO DTO con los datos del movimiento a registrar.
     * @return DTO actualizado con el ID generado.
     */
    MovimientoDTO registrar(MovimientoDTO movimientoDTO) throws EntityException;

    /**
     * Desactiva (Da de baja) un movimiento en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del movimiento a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) un movimiento desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del movimiento a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene un movimiento por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id del movimiento a obtener.
     * @return DTO con los datos del movimiento encontrado.
     */
    MovimientoDTO obtenerPorID(Integer id);

    /**
     * Obtiene todos los movimientos registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los movimientos.
     */
    List<MovimientoDTO> obtenerTodo();
}


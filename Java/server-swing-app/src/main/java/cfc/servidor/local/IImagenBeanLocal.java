package cfc.servidor.local;

import cfc.servidor.DTOs.ImagenDTO;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de imagen.<br>
 * Define los métodos de negocio sobre la entidad Imagen.
 */
@Local
public interface IImagenBeanLocal {

    /**
     * Registra una imagen en el sistema.
     *
     * @param imagenDTO DTO con los datos de la imagen a registrar.
     * @return DTO actualizado con el ID generado.
     */
    ImagenDTO registrar(ImagenDTO imagenDTO);

    /**
     * Actualiza los datos de una imagen en el sistema.
     *
     * @param imagenDTO DTO con los datos de la imagen a actualizar.
     */
    void actualizar(ImagenDTO imagenDTO);

    /**
     * Elimina una imagen del sistema.
     *
     * @param id ID de la imagen a eliminar.
     */
    void eliminar(Integer id);

    /**
     * Obtiene todas las imágenes registradas en el sistema.
     *
     * @return Lista de DTO con los datos de las imágenes.
     */
    List<ImagenDTO> obtenerTodo();

    /**
     * Obtiene una imagen por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre de la imagen a obtener.
     * @return DTO con los datos de la imagen encontrada.
     */
    ImagenDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene una imagen por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id ID de la imagen a obtener.
     * @return DTO con los datos de la imagen encontrada.
     */
    ImagenDTO obtenerPorID(Integer id);
}
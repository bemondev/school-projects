package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DTOs.ImagenDTO;
import cfc.servidor.entidades.Imagen;
import cfc.servidor.local.IImagenBeanLocal;
import cfc.servidor.servicios.IImagenBeanRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de imagen.
 */
@Stateless
public class ImagenBean implements IImagenBeanRemote, IImagenBeanLocal {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOCRUDGeneral daoCrudGeneral;

    // ======================= Métodos remotos =======================
    @Override
    public ImagenDTO registrar(ImagenDTO imagenDTO) {

        // Crea una nueva entidad de imagen donde se cargarán los datos
        Imagen imagen = new Imagen();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(imagen, imagenDTO);

        // Registra la imagen en la base de datos
        this.daoCrudGeneral.registrar(imagen);

        // Actualiza el DTO con el ID generado
        imagenDTO.setId(imagen.getId());

        // Retorna el DTO actualizado
        return imagenDTO;
    }

    @Override
    public void actualizar(ImagenDTO imagenDTO) {

        // Obtiene la entidad de imagen por su ID
        Imagen imagen = this.daoCrudGeneral.obtenerPorID(Imagen.class, imagenDTO.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (imagen != null) {
            this.cargarDatosDTO(imagen, imagenDTO);
            this.daoCrudGeneral.actualizar(imagen);
        }
    }

    @Override
    public void eliminar(Integer id) {

        // Obtiene la entidad de imagen por su ID
        Imagen imagen = this.daoCrudGeneral.obtenerPorID(Imagen.class, id);

        // Si existe, lo elimina de la base de datos
        if (imagen != null) {
            this.daoCrudGeneral.eliminar(imagen);
        }
    }

    @Override
    public List<ImagenDTO> obtenerTodo() {

        // Crea una lista de DTOs donde se guardarán los datos de las imágenes.
        List<ImagenDTO> lista = new ArrayList<>();

        // Convierte cada entidad de imagen a un DTO y la agrega a la lista
        for (Imagen img : this.daoCrudGeneral.obtenerTodo(Imagen.class)) {
            ImagenDTO imagenDTO = new ImagenDTO(img);
            lista.add(imagenDTO);
        }

        // Retorna la lista de DTOs
        return lista;
    }

    @Override
    public ImagenDTO obtenerPorNombre(String nombre) {

        // Obtiene la entidad de imagen por su nombre
        Imagen imagen = this.daoCrudGeneral.obtenerPorNombre(Imagen.class, nombre);

        // Si no existe, retorna null
        if (imagen == null) return null;

        // Crea y retorna un DTO con los datos de la imagen encontrada
        return new ImagenDTO(imagen);
    }

    @Override
    public ImagenDTO obtenerPorID(Integer id) {

        // Obtiene la entidad de imagen por su ID
        Imagen imagen = this.daoCrudGeneral.obtenerPorID(Imagen.class, id);

        // Si no existe, retorna null
        if (imagen == null) return null;

        // Crea y retorna un DTO con los datos de la imagen encontrada
        return new ImagenDTO(imagen);
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param imagen    Entidad a la que se le cargarán los datos
     * @param imagenDTO DTO con los datos a cargar
     */
    public void cargarDatosDTO(Imagen imagen, ImagenDTO imagenDTO) {
        imagen.setId(imagenDTO.getId());
        imagen.setNombre(imagenDTO.getNombre());
        imagen.setUrl(imagenDTO.getUrl());
    }
}

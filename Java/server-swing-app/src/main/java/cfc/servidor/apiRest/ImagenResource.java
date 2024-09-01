package cfc.servidor.apiRest;

import cfc.servidor.DTOs.ImagenDTO;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.local.IImagenBeanLocal;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
@Path("/image")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagenResource {
    @EJB
    private IImagenBeanLocal bean;

    @POST
    public ImagenDTO crearImagen(ImagenDTO imagen) throws EntityException {
        bean.registrar(imagen);
        return imagen;
    }

    @GET
    public List<ImagenDTO> obtenerTodos() {
        return bean.obtenerTodo();
    }

    @GET
    @Path("/{nombreImg}")
    public ImagenDTO obtenerImagen(@PathParam("nombreImg") String nombreImg) {
        return bean.obtenerPorNombre(nombreImg);
    }

    @PUT
    @Path("/{id}")
    public ImagenDTO actualizarEquipo(@PathParam("id") int id, ImagenDTO imagen) throws EntityException {
        ImagenDTO e;
        e = bean.obtenerPorID(id);
        if (imagen.getNombre() != null) {
            e.setNombre(imagen.getNombre());
        }
        if (imagen.getUrl() != null) {
            e.setUrl(imagen.getUrl());
        }
        bean.actualizar(e);
        return e;
    }

    @DELETE
    @Path("/{id}")
    public void eliminarEquipo(@PathParam("id") int id) throws EntityException {
        ImagenDTO e = bean.obtenerPorID(id);
        if (e != null) {
            System.out.println("No podemo eliminar papi, no tiene estado una imagen ;)" );
        } else {
            System.out.println("No se pudo eliminar una imagen inexistente");
        }
    }
}

package cfc.servidor.apiRest;

import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.local.IUsuarioBeanLocal;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @EJB
    private IUsuarioBeanLocal bean;

    @POST
    public UsuarioDTO crearUsuario(UsuarioDTO usuario, String password) throws EntityException {
        bean.registrar(usuario, password);
        return usuario;
    }

    @GET
    public List<UsuarioDTO> obtenerTodos() {
        List<UsuarioDTO> usuarios = bean.obtenerTodo();
        return usuarios;
    }

    @GET
    @Path("/{id}")
    public UsuarioDTO obtenerUsuario(@PathParam("id") int id) {
        UsuarioDTO u = bean.obtenerPorID(id);
        return u;
    }

    @PUT
    @Path("/{id}")
    public UsuarioDTO actualizarUsuario(@PathParam("id") int id, UsuarioDTO usuario) throws EntityException {
        UsuarioDTO u;
        u = bean.obtenerPorID(id);
        if (usuario.getNombre() != null) {
            u.setNombre(usuario.getNombre());
        }
        if (usuario.getApellido() != null) {
            u.setApellido(usuario.getApellido());
        }
        if (usuario.getCedula() != null) {
            u.setCedula(usuario.getCedula());
        }
        if (usuario.getFechaNacimiento() != null) {
            u.setFechaNacimiento(usuario.getFechaNacimiento());
        }
        if (usuario.getTelefono() != null) {
            u.setTelefono(usuario.getTelefono());
        }
        if (usuario.getEmail() != null) {
            u.setEmail(usuario.getEmail());
        }
        if (usuario.getIdPerfil() != null) {
            u.setIdPerfil(usuario.getIdPerfil());
        }
        if (usuario.getEstado() != null) {
            u.setEstado(usuario.getEstado());
        }
        bean.actualizar(u);
        return u;
    }

    @DELETE
    @Path("/{id}")
    public void eliminarPersona(@PathParam("id") int id) {
        UsuarioDTO u = bean.obtenerPorID(id);
        if (u != null) {
            bean.desactivar(u.getId());
        } else {
            System.out.println("No se pudo eliminar un usuario inexistente");
        }
    }
}
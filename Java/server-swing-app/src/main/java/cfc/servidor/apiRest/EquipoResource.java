package cfc.servidor.apiRest;

import cfc.servidor.DTOs.EquipoDTO;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.local.IEquipoBeanLocal;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.List;

@Path("/equipment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EquipoResource {
    @EJB
    private IEquipoBeanLocal bean;

    @POST
    public EquipoDTO crearEquipo(EquipoDTO equipo) throws EntityException {
        System.out.println(equipo);
        bean.registrar(equipo);
        return equipo;
    }

    @GET
    public List<EquipoDTO> obtenerTodos() {
        return bean.obtenerTodo();
    }

    @GET
    @Path("/{id}")
    public EquipoDTO obtenerEquipo(@PathParam("id") int id) {
        return bean.obtenerPorID(id);
    }

    @PUT
    @Path("/{id}")
    public EquipoDTO actualizarEquipo(@PathParam("id") int id, EquipoDTO equipo) throws EntityException {
        EquipoDTO e;
        e = bean.obtenerPorID(id);
        if (equipo.getNombre() != null) {
            e.setNombre(equipo.getNombre());
        }
        if (equipo.getIdInterna() != null) {
            e.setIdInterna(equipo.getIdInterna());
        }
        if (equipo.getNumeroSerie() != null) {
            e.setNumeroSerie(equipo.getNumeroSerie());
        }
        if (equipo.getFechaAdquisicion() != null) {
            e.setFechaAdquisicion(equipo.getFechaAdquisicion());
        }
        if (equipo.getGarantia() != null) {
            e.setGarantia(equipo.getGarantia());
        }
        if (equipo.getProveedor() != null) {
            e.setProveedor(equipo.getProveedor());
        }
        if (equipo.getIdTipoEquipo() != null) {
            e.setIdTipoEquipo(equipo.getIdTipoEquipo());
        }
        if (equipo.getIdImagen() != null) {
            e.setIdImagen(equipo.getIdImagen());
        }
        if (equipo.getIdModeloEquipo() != null) {
            e.setIdModeloEquipo(equipo.getIdModeloEquipo());
        }
        if (equipo.getEstado() != null) {
            e.setEstado(equipo.getEstado());
        }
        if (equipo.getIdUbicacion() != null) {
            e.setIdUbicacion(equipo.getIdUbicacion());
        }
        if (equipo.getIdPaisDeOrigen() != null) {
            e.setIdPaisDeOrigen(equipo.getIdPaisDeOrigen());
        }
        bean.actualizar(e);
        return e;
    }


    @DELETE
    @Path("/{id}")
    public void eliminarEquipo(@PathParam("id") int id) throws EntityException {
        EquipoDTO e = bean.obtenerPorID(id);
        if (e != null) {
            bean.desactivar(e.getId());
        } else {
            System.out.println("No se pudo eliminar un equipo inexistente");
        }
    }
}

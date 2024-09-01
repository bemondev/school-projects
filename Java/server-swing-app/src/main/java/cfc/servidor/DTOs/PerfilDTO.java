package cfc.servidor.DTOs;

import cfc.servidor.entidades.Perfil;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.enumerados.PermisosEnum;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * DTO de {@link Perfil}
 */
public class PerfilDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private String nombre;
    private EstadosEnum estado;
    private List<PermisosEnum> permisos = new LinkedList<>();

    // ===================== Constructores =======================

    public PerfilDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de perfil.
     *
     * @param perfil Entidad de perfil a convertir.
     */
    public PerfilDTO(Perfil perfil) {
        this.setId(perfil.getId());
        this.setNombre(perfil.getNombre());
        this.setEstado(perfil.getEstado());
        this.permisos.addAll(perfil.getPermisos());
    }

    // ===================== Getters y Setters =======================


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    public List<PermisosEnum> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisosEnum> permisos) {
        this.permisos = permisos;
    }

    // ===================== Métodos de Object =======================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilDTO entity = (PerfilDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.permisos, entity.permisos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, estado, permisos);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ", " +
                "estado = " + estado + ", " +
                "permisos = " + permisos + ")";
    }
}
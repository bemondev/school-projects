package cfc.servidor.DTOs;

import cfc.servidor.entidades.TipoEquipo;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO de {@link TipoEquipo}
 */
public class TipoEquipoDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private String nombre;
    private EstadosEnum estado;

    // ===================== Constructores =======================
    public TipoEquipoDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de tipo de equipo.
     *
     * @param tipoEquipo Entidad de tipo de equipo a convertir.
     */
    public TipoEquipoDTO(TipoEquipo tipoEquipo) {
        this.setId(tipoEquipo.getId());
        this.setEstado(tipoEquipo.getEstado());
        this.setNombre(tipoEquipo.getNombre());
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

    // ===================== Métodos de Object =======================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoEquipoDTO entity = (TipoEquipoDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, estado);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ", " +
                "estado = " + estado + ")";
    }
} 
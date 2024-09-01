package cfc.servidor.DTOs;

import cfc.servidor.entidades.Marca;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO de {@link Marca}
 */
public class MarcaDTO implements Serializable {
    // ======================== Atributos ========================
    private Integer id;
    private String nombre;
    private EstadosEnum estado;

    // ===================== Constructores =======================
    public MarcaDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de marca.
     *
     * @param marca Entidad de marca a convertir.
     */
    public MarcaDTO(Marca marca) {
        this.setId(marca.getId());
        this.setEstado(marca.getEstado());
        this.setNombre(marca.getNombre());
    }

    // ===================== Getters y Setters =======================
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    // ===================== Métodos de Object =======================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarcaDTO entity = (MarcaDTO) o;
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
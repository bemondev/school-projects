package cfc.servidor.DTOs;

import cfc.servidor.entidades.Pais;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO de {@link Pais}
 */
public class PaisDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private String nombre;
    private EstadosEnum estado;

    // ===================== Constructores =======================
    public PaisDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de pais.
     *
     * @param pais Entidad de pais a convertir.
     */
    public PaisDTO(Pais pais) {
        this.setId(pais.getId());
        this.setEstado(pais.getEstado());
        this.setNombre(pais.getNombre());
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
        PaisDTO paisDTO = (PaisDTO) o;
        return Objects.equals(id, paisDTO.id) && Objects.equals(nombre, paisDTO.nombre) && estado == paisDTO.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, estado);
    }

    @Override
    public String toString() {
        return "PaisDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}
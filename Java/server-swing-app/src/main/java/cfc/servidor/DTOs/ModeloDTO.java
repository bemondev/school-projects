package cfc.servidor.DTOs;

import cfc.servidor.entidades.Modelo;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO de {@link Modelo}
 */
public class ModeloDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private String nombre;
    private EstadosEnum estado;
    private Integer idMarca;

    // ===================== Constructores =======================

    public ModeloDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de modelo.
     *
     * @param modelo Entidad de modelo a convertir.
     */
    public ModeloDTO(Modelo modelo) {
        this.setId(modelo.getId());
        this.setEstado(modelo.getEstado());
        this.setNombre(modelo.getNombre());
        this.setIdMarca(modelo.getMarca().getId());
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

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    // ===================== Métodos de Object =======================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloDTO modeloDTO = (ModeloDTO) o;
        return Objects.equals(id, modeloDTO.id) && Objects.equals(nombre, modeloDTO.nombre) && estado == modeloDTO.estado && Objects.equals(idMarca, modeloDTO.idMarca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, estado, idMarca);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ", " +
                "estado = " + estado + ", " +
                "idMarca = " + idMarca + ")";
    }
}
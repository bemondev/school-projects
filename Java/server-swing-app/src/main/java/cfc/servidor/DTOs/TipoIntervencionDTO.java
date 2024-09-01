package cfc.servidor.DTOs;

import cfc.servidor.entidades.TipoIntervencion;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO de {@link TipoIntervencion}
 */
public class TipoIntervencionDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private String nombre;
    private EstadosEnum estado;

    // ===================== Constructores =======================
    public TipoIntervencionDTO(){

    }

    /**
     * Crea un DTO a partir de una entidad de tipo de intervención.
     *
     * @param tipoIntervencion Entidad de tipo de intervención a convertir.
     */
    public TipoIntervencionDTO(TipoIntervencion tipoIntervencion){
        this.setId(tipoIntervencion.getId());
        this.setEstado(tipoIntervencion.getEstado());
        this.setNombre(tipoIntervencion.getNombre());
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
        TipoIntervencionDTO that = (TipoIntervencionDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && estado == that.estado;
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
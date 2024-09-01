package cfc.servidor.DTOs;

import cfc.servidor.entidades.Ubicacion;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.enumerados.InstitucionesEnum;
import cfc.servidor.enumerados.SectoresEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO de {@link Ubicacion}
 */
public class UbicacionDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private InstitucionesEnum institucion;
    private String nombre;
    private SectoresEnum sector;
    private String piso;
    private String cama;
    private int numero;
    private EstadosEnum estado;

    // ===================== Constructores =======================

    public UbicacionDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de ubicación.
     *
     * @param ubicacion Entidad de ubicación a convertir.
     */
    public UbicacionDTO(Ubicacion ubicacion) {
        this.setId(ubicacion.getId());
        this.setCama(ubicacion.getCama());
        this.setEstado(ubicacion.getEstado());
        this.setNombre(ubicacion.getNombre());
        this.setInstitucion(ubicacion.getInstitucion());
        this.setSector(ubicacion.getSector());
        this.setPiso(ubicacion.getPiso());
        this.setNumero(ubicacion.getNumero());

    }

    // ===================== Getters y Setters =======================
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InstitucionesEnum getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitucionesEnum institucion) {
        this.institucion = institucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public SectoresEnum getSector() {
        return sector;
    }

    public void setSector(SectoresEnum sector) {
        this.sector = sector;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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
        UbicacionDTO entity = (UbicacionDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.institucion, entity.institucion) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.sector, entity.sector) &&
                Objects.equals(this.piso, entity.piso) &&
                Objects.equals(this.cama, entity.cama) &&
                Objects.equals(this.numero, entity.numero) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, institucion, nombre, sector, piso, cama, numero, estado);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "institucion = " + institucion + ", " +
                "nombre = " + nombre + ", " +
                "sector = " + sector + ", " +
                "piso = " + piso + ", " +
                "cama = " + cama + ", " +
                "numero = " + numero + ", " +
                "estado = " + estado + ")";
    }
}
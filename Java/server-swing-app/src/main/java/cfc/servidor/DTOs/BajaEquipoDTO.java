package cfc.servidor.DTOs;

import cfc.servidor.entidades.BajaEquipo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO de {@link BajaEquipo}
 */

public class BajaEquipoDTO implements Serializable {

    // ======================== Atributos ========================

    private Integer id;
    private Integer idUsuario;

    private Integer idEquipo;
    private LocalDate fechaBaja;
    private String motivo;
    private String observaciones;

    // ===================== Constructores =======================
    public BajaEquipoDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de baja de equipo.
     *
     * @param bajaEquipo Entidad de baja de equipo a convertir.
     */
    public BajaEquipoDTO(BajaEquipo bajaEquipo) {
        this.setId(bajaEquipo.getId());
        this.setFechaBaja(bajaEquipo.getFechaBaja());
        this.setIdUsuario(bajaEquipo.getUsuario().getId());
        this.setMotivo(bajaEquipo.getMotivo());
        this.setObservaciones(bajaEquipo.getObservaciones());
        this.setIdEquipo(bajaEquipo.getEquipo().getId());
    }

    // ===================== Getters y Setters =======================


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // ===================== Métodos de Object =======================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BajaEquipoDTO that = (BajaEquipoDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idEquipo, that.idEquipo) && Objects.equals(fechaBaja, that.fechaBaja) && Objects.equals(motivo, that.motivo) && Objects.equals(observaciones, that.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, idEquipo, fechaBaja, motivo, observaciones);
    }

    @Override
    public String toString() {
        return "BajaEquipoDTO{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idEquipo=" + idEquipo +
                ", fechaBaja=" + fechaBaja +
                ", motivo='" + motivo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}

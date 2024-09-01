package cfc.servidor.DTOs;

import cfc.servidor.entidades.Intervencion;
import cfc.servidor.enumerados.MotivosEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO de {@link Intervencion}
 */
public class IntervencionDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private String observacion;
    private LocalDateTime fechaYHora;
    private MotivosEnum motivo;
    private Integer idEquipoIntervenido;
    private Integer idTipoIntervencion;

    // ===================== Constructores =======================
    public IntervencionDTO() {

    }

    /**
     * Crea un DTO a partir de una entidad de intervención.
     *
     * @param intervencion Entidad de intervención a convertir.
     */
    public IntervencionDTO(Intervencion intervencion) {
        this.setId(intervencion.getId());
        this.setMotivo(intervencion.getMotivo());
        this.setObservacion(intervencion.getObservacion());
        this.setFechaYHora(intervencion.getFechaYHora());
        this.setIdTipoIntervencion(intervencion.getTipoIntervencion().getId());
        this.setIdEquipoIntervenido(intervencion.getEquipoIntervenido().getId());
    }

    // ===================== Getters y Setters =======================
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public MotivosEnum getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivosEnum motivo) {
        this.motivo = motivo;
    }

    public Integer getIdEquipoIntervenido() {
        return idEquipoIntervenido;
    }

    public void setIdEquipoIntervenido(Integer idEquipoIntervenido) {
        this.idEquipoIntervenido = idEquipoIntervenido;
    }

    public Integer getIdTipoIntervencion() {
        return idTipoIntervencion;
    }

    public void setIdTipoIntervencion(Integer idTipoIntervencion) {
        this.idTipoIntervencion = idTipoIntervencion;
    }

    // ===================== Métodos de Object =======================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntervencionDTO that = (IntervencionDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(observacion, that.observacion) && Objects.equals(fechaYHora, that.fechaYHora) && motivo == that.motivo && Objects.equals(idEquipoIntervenido, that.idEquipoIntervenido) && Objects.equals(idTipoIntervencion, that.idTipoIntervencion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, observacion, fechaYHora, motivo, idEquipoIntervenido, idTipoIntervencion);
    }

    @Override
    public String toString() {
        return "IntervencionDTO{" +
                "id=" + id +
                ", observacion='" + observacion + '\'' +
                ", fechaYHora=" + fechaYHora +
                ", motivo=" + motivo +
                ", idEquipoIntervenido=" + idEquipoIntervenido +
                ", idTipoIntervencion=" + idTipoIntervencion +
                '}';
    }
}


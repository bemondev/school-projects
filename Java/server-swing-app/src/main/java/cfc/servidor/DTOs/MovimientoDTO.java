package cfc.servidor.DTOs;

import cfc.servidor.entidades.Movimiento;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO de {@link Movimiento}
 */
public class MovimientoDTO implements Serializable {

    // ======================== Atributos ========================

    private Integer id;
    private String Comentario;
    private EstadosEnum estado;
    private LocalDateTime fechaDelRegistro;
    private Integer idUbicacion;
    private Integer idEquipo;
    private String username;

    // ===================== Constructores =======================

    public MovimientoDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de movimiento.
     *
     * @param movimiento Entidad de movimiento a convertir.
     */
    public MovimientoDTO(Movimiento movimiento) {
        this.setId(movimiento.getId());
        this.setComentario(movimiento.getComentario());
        this.setEstado(movimiento.getEstado());
        this.setFechaDelRegistro(movimiento.getFechaDelRegistro());
        this.setIdUbicacion(movimiento.getUbicacion().getId());
        this.setUsername(movimiento.getUsername());
        this.setIdEquipo(movimiento.getEquipo().getId());
    }

    // ===================== Getters y Setters =======================
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaDelRegistro() {
        return fechaDelRegistro;
    }

    public void setFechaDelRegistro(LocalDateTime fechaDelRegistro) {
        this.fechaDelRegistro = fechaDelRegistro;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // ===================== Métodos de Object =======================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoDTO that = (MovimientoDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(Comentario, that.Comentario) && estado == that.estado && Objects.equals(fechaDelRegistro, that.fechaDelRegistro) && Objects.equals(idUbicacion, that.idUbicacion) && Objects.equals(idEquipo, that.idEquipo) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Comentario, estado, fechaDelRegistro, idUbicacion, idEquipo, username);
    }

    @Override
    public String toString() {
        return "MovimientoDTO{" +
                "id=" + id +
                ", Comentario='" + Comentario + '\'' +
                ", estado=" + estado +
                ", fechaDelRegistro=" + fechaDelRegistro +
                ", idUbicacion=" + idUbicacion +
                ", idEquipo=" + idEquipo +
                ", username='" + username + '\'' +
                '}';
    }
}
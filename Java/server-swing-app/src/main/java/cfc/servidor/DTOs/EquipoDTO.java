package cfc.servidor.DTOs;

import cfc.servidor.entidades.Equipo;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO de {@link Equipo}
 */
public class EquipoDTO implements Serializable {

    // ======================== Atributos ========================

    private Integer id;
    private Integer idInterna;
    private String nombre;
    private String numeroSerie;
    private LocalDate fechaAdquisicion;
    private String garantia;
    private String proveedor;
    private Integer idTipoEquipo;
    private Integer idImagen;
    private Integer idModeloEquipo;
    private EstadosEnum estado;
    private Integer idUbicacion;
    private Integer idPaisDeOrigen;

    // ===================== Constructores =======================
    public EquipoDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de equipo.
     *
     * @param equipo Entidad de equipo a convertir.
     */
    public EquipoDTO(Equipo equipo) {
        this.setId(equipo.getId());
        this.setIdInterna(equipo.getIdInterna());
        this.setNombre(equipo.getNombre());
        this.setNumeroSerie(equipo.getNumeroSerie());
        this.setFechaAdquisicion(equipo.getFechaAdquisicion());
        this.setGarantia(equipo.getGarantia());
        this.setProveedor(equipo.getProveedor());
        this.setIdTipoEquipo(equipo.getTipoEquipo().getId());
        this.setIdImagen(equipo.getImagen().getId());
        this.setIdModeloEquipo(equipo.getModeloEquipo().getId());
        this.setEstado(equipo.getEstado());
        if (equipo.getUbicacion() != null)
            this.setIdUbicacion(equipo.getUbicacion().getId());
        if (equipo.getPaisDeOrigen() != null)
            this.setIdPaisDeOrigen(equipo.getPaisDeOrigen().getId());
    }

    // ===================== Getters y Setters =======================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdInterna() {
        return idInterna;
    }

    public void setIdInterna(Integer idInterna) {
        this.idInterna = idInterna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getIdTipoEquipo() {
        return idTipoEquipo;
    }

    public void setIdTipoEquipo(Integer idTipoEquipo) {
        this.idTipoEquipo = idTipoEquipo;
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public Integer getIdModeloEquipo() {
        return idModeloEquipo;
    }

    public void setIdModeloEquipo(Integer idModeloEquipo) {
        this.idModeloEquipo = idModeloEquipo;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Integer getIdPaisDeOrigen() {
        return idPaisDeOrigen;
    }

    public void setIdPaisDeOrigen(Integer idPaisDeOrigen) {
        this.idPaisDeOrigen = idPaisDeOrigen;
    }

    // ===================== Métodos de Object =======================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipoDTO equipoDTO = (EquipoDTO) o;
        return Objects.equals(id, equipoDTO.id) && Objects.equals(idInterna, equipoDTO.idInterna) && Objects.equals(nombre, equipoDTO.nombre) && Objects.equals(numeroSerie, equipoDTO.numeroSerie) && Objects.equals(fechaAdquisicion, equipoDTO.fechaAdquisicion) && Objects.equals(garantia, equipoDTO.garantia) && Objects.equals(proveedor, equipoDTO.proveedor) && Objects.equals(idTipoEquipo, equipoDTO.idTipoEquipo) && Objects.equals(idImagen, equipoDTO.idImagen) && Objects.equals(idModeloEquipo, equipoDTO.idModeloEquipo) && estado == equipoDTO.estado && Objects.equals(idUbicacion, equipoDTO.idUbicacion) && Objects.equals(idPaisDeOrigen, equipoDTO.idPaisDeOrigen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idInterna, nombre, numeroSerie, fechaAdquisicion, garantia, proveedor, idTipoEquipo, idImagen, idModeloEquipo, estado, idUbicacion, idPaisDeOrigen);
    }

    @Override
    public String toString() {
        return "EquipoDTO{" +
                "id=" + id +
                ", idInterna=" + idInterna +
                ", nombre='" + nombre + '\'' +
                ", numeroSerie=" + numeroSerie +
                ", fechaAdquisicion=" + fechaAdquisicion +
                ", garantia='" + garantia + '\'' +
                ", proveedor='" + proveedor + '\'' +
                ", idTipoEquipo=" + idTipoEquipo +
                ", idImagen=" + idImagen +
                ", idModeloEquipo=" + idModeloEquipo +
                ", estado=" + estado +
                ", idUbicacion=" + idUbicacion +
                ", idPaisDeOrigen=" + idPaisDeOrigen +
                '}';
    }
}


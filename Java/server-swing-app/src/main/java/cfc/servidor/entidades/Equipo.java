package cfc.servidor.entidades;

import cfc.servidor.enumerados.EstadosEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "EQUIPOS")
public class Equipo {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "EQUIPOS_SEQ", sequenceName = "EQUIPOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EQUIPOS_SEQ")
    @Column(name = "ID_EQUIPO", nullable = false)
    private Integer id;
    @Column(nullable = false, length = 50)
    private String nombre;
    @Column(name = "ID_INTERNA", nullable = false, unique = true)
    private Integer idInterna;
    @Column(name = "NUM_SERIE", nullable = false, unique = true, length = 50)
    private String numeroSerie;
    @Column(name = "FEC_ADQUISICION")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaAdquisicion;
    @Column(length = 50)
    private String garantia;
    @Column(length = 50)
    private String proveedor;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_EQUIPO", nullable = false)
    private TipoEquipo tipoEquipo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_IMAGEN")
    private Imagen imagen;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_MODELO_EQUIPO", nullable = false)
    private Modelo modeloEquipo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadosEnum estado;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_UBICACION")
    private Ubicacion ubicacion;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PAIS_ORIGEN")
    private Pais paisDeOrigen;

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

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Modelo getModeloEquipo() {
        return modeloEquipo;
    }

    public void setModeloEquipo(Modelo modeloEquipo) {
        this.modeloEquipo = modeloEquipo;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Pais getPaisDeOrigen() {
        return paisDeOrigen;
    }

    public void setPaisDeOrigen(Pais paisDeOrigen) {
        this.paisDeOrigen = paisDeOrigen;
    }

    // ===================== Métodos de Object =======================

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", idInterna=" + idInterna +
                ", nombre='" + nombre + '\'' +
                ", numeroSerie=" + numeroSerie +
                ", fechaAdquisicion=" + fechaAdquisicion +
                ", garantia='" + garantia + '\'' +
                ", proveedor='" + proveedor + '\'' +
                ", tipoEquipo=" + tipoEquipo +
                ", imagen=" + imagen +
                ", modeloEquipo=" + modeloEquipo +
                ", estado=" + estado +
                ", ubicacion=" + ubicacion +
                ", paisDeOrigen=" + paisDeOrigen +
                '}';
    }
}
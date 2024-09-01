package cfc.servidor.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "BAJA_EQUIPOS")
public class BajaEquipo {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "BAJAEQUIPOS_SEQ", sequenceName = "BAJAEQUIPOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAJAEQUIPOS_SEQ")
    @Column(name = "ID_BAJAEQUIPO", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_EQUIPO", nullable = false)
    private Equipo equipo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fechaBaja;
    @Column(name = "MOTIVO", nullable = false, length = 100)
    private String motivo;
    @Column(name = "OBSERVACIONES", length = 100)
    private String observaciones;

    // ===================== Getters y Setters =======================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    public String toString() {
        return "BajaEquipo{" +
                "id=" + id +
                ", equipo=" + equipo +
                ", usuario=" + usuario +
                ", fechaBaja=" + fechaBaja +
                ", motivo='" + motivo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}

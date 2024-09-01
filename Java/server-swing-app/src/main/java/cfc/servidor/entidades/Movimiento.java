package cfc.servidor.entidades;

import cfc.servidor.enumerados.EstadosEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MOVIMIENTOS")
public class Movimiento {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "MOVIMIENTOS_SEQ", sequenceName = "MOVIMIENTOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIMIENTOS_SEQ")
    @Column(nullable = false, name = "ID_MOVIMIENTO")
    private Integer id;

    @Column(nullable = false, length = 250)
    private String Comentario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadosEnum estado;

    @Column(nullable = false, name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaDelRegistro;

    @Column(nullable = false)
    private String username;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_UBICACION", nullable = false)
    private Ubicacion ubicacion;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_EQUIPO", nullable = false)
    private Equipo equipo;

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

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // ===================== Métodos de Object =======================
    @Override
    public String toString() {
        return "Movimiento{" +
                "id=" + id +
                ", Comentario='" + Comentario + '\'' +
                ", estado=" + estado +
                ", fechaDelRegistro=" + fechaDelRegistro +
                ", username='" + username + '\'' +
                ", ubicacion=" + ubicacion +
                ", equipo=" + equipo +
                '}';
    }
}
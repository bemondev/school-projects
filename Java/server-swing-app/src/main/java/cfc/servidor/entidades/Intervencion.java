package cfc.servidor.entidades;

import cfc.servidor.enumerados.MotivosEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "INTERVENCIONES")
public class Intervencion {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "INTERVENCIONES_SEQ", sequenceName = "INTERVENCIONES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INTERVENCIONES_SEQ")
    @Column(name = "ID_INTERVENCION")
    private Integer id;

    @Column(length = 100)
    private String observacion;

    @Column(nullable = false, name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaYHora;

    @Enumerated(EnumType.STRING)
    private MotivosEnum motivo;

    //esto luego representara un combobox con todos los equipamientos disponibles a intervenir
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_EQUIPO_INTERVENIDO", nullable = false)
    private Equipo equipoIntervenido;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_INTERVENCION", nullable = false)
    private TipoIntervencion tipoIntervencion;

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

    public Equipo getEquipoIntervenido() {
        return equipoIntervenido;
    }

    public void setEquipoIntervenido(Equipo equipoIntervenido) {
        this.equipoIntervenido = equipoIntervenido;
    }

    public TipoIntervencion getTipoIntervencion() {
        return tipoIntervencion;
    }

    public void setTipoIntervencion(TipoIntervencion tipoIntervencion) {
        this.tipoIntervencion = tipoIntervencion;
    }

    // ===================== Métodos de Object =======================

    @Override
    public String toString() {
        return "Intervencion{" +
                "id=" + id +
                ", observacion='" + observacion + '\'' +
                ", fechaYHora=" + fechaYHora +
                ", motivo=" + motivo +
                ", equipoIntervenido=" + equipoIntervenido +
                ", tipoIntervencion=" + tipoIntervencion +
                '}';
    }
}

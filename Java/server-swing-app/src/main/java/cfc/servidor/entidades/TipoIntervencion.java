package cfc.servidor.entidades;

import cfc.servidor.enumerados.EstadosEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "TIPOS_INTERVENCION")
public class TipoIntervencion {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "TIPO_INTERVENCIONES_SEQ", sequenceName = "TIPO_INTERVENCIONES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_INTERVENCIONES_SEQ")
    @Column(nullable = false, name = "ID_TIPO_INTERVENCION")
    private Integer id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private EstadosEnum estado;

    // ===================== Getters y Setters =======================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    // ===================== Métodos de Object =======================

    @Override
    public String toString() {
        return "TipoIntervencion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}

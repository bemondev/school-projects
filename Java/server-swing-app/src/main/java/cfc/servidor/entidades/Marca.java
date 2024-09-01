package cfc.servidor.entidades;

import cfc.servidor.enumerados.EstadosEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "MARCAS")
public class Marca {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "MARCAS_SEQ", sequenceName = "MARCAS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MARCAS_SEQ")
    @Column(nullable = false, name = "ID_MARCA")
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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
        return "Marca{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}
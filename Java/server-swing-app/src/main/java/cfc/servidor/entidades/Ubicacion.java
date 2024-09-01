package cfc.servidor.entidades;

import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.enumerados.InstitucionesEnum;
import cfc.servidor.enumerados.SectoresEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "UBICACIONES")
public class Ubicacion {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "UBICACIONES_SEQ", sequenceName = "UBICACIONES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UBICACIONES_SEQ")
    @Column(nullable = false, name = "ID_UBICACION")
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InstitucionesEnum institucion;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SectoresEnum sector;

    @Column(nullable = false, length = 3)
    private String piso;

    @Column(length = 5)
    private String cama;

    @Column(nullable = false)
    private Integer numero;

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

    public InstitucionesEnum getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitucionesEnum institucion) {
        this.institucion = institucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public SectoresEnum getSector() {
        return sector;
    }

    public void setSector(SectoresEnum sector) {
        this.sector = sector;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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
        return "Ubicacion{" +
                "id=" + id +
                ", institucion=" + institucion +
                ", nombre='" + nombre + '\'' +
                ", sector=" + sector +
                ", piso='" + piso + '\'' +
                ", cama='" + cama + '\'' +
                ", numero=" + numero +
                ", estado=" + estado +
                '}';
    }
}


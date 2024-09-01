package cfc.servidor.entidades;

import jakarta.persistence.*;

import java.sql.Clob;

@Entity
@Table(name = "IMAGENES")
public class Imagen {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "IMAGENES_SEQ", sequenceName = "IMAGENES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGENES_SEQ")
    @Column(nullable = false, name = "ID_IMAGEN")
    private Integer id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, length = 4000)
    private String nombre;

    // ===================== Getters y Setters =======================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // ===================== Métodos de Object =======================

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

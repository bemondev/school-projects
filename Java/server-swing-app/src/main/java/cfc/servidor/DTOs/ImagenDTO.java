package cfc.servidor.DTOs;

import cfc.servidor.entidades.Imagen;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO de {@link Imagen}
 */
public class ImagenDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;
    private String url;
    private String nombre;

    // ===================== Constructores =======================
    public ImagenDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de imagen.
     *
     * @param imagen Entidad de imagen a convertir.
     */
    public ImagenDTO(Imagen imagen) {
        this.setId(imagen.getId());
        this.setUrl(imagen.getUrl());
        this.setNombre(imagen.getNombre());
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagenDTO entity = (ImagenDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.url, entity.url) &&
                Objects.equals(this.nombre, entity.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, nombre);
    }

    @Override
    public String toString() {
        return "ImagenDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
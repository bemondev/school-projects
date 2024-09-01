package com.ale.servidorappmobilecoffee.entity;

import com.ale.servidorappmobilecoffee.enums.Category;

public class Producto {

    private int id;
    private String name;
    private String description;
    private double price;
    private String urlImagen;
    private Category categoria;

    public Producto() {
    }

    public Producto(int id, String name, String description, double price, String urlImagen, Category categoria) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", urlImagen='" + urlImagen + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}

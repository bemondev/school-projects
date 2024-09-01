package cfc.servidor.entidades;

import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.enumerados.PermisosEnum;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "PERFILES")
public class Perfil {

    // ======================== Atributos ========================

    @Id
    @SequenceGenerator(name = "PERFILES_SEQ", sequenceName = "PERFILES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERFILES_SEQ")
    @Column(nullable = false, name = "ID_PERFIL")
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadosEnum estado;

    @ElementCollection(targetClass = PermisosEnum.class) // indica a JPA que la propiedad permisos es una colección de elementos.
    @Enumerated(EnumType.STRING) // especifica que el tipo de enumeración se debe tratar como un string al ser almacenado en la base de datos.
    @JoinTable(name = "PERMISOS_PERFILES", joinColumns = @JoinColumn(name = "ID_PERFIL")) // define la tabla que se utilizará para almacenar los elementos de la lista de permisos. Puedes especificar un nombre de tabla personalizado y configurar las columnas mediante @JoinColumn.
    private List<PermisosEnum> permisos = new LinkedList<>();

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

    public List<PermisosEnum> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisosEnum> permisos) {
        this.permisos = permisos;
    }

    // ===================== Métodos de Object =======================

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", permisos=" + permisos +
                '}';
    }

}

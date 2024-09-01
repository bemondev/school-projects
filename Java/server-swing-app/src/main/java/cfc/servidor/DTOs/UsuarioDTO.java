package cfc.servidor.DTOs;

import cfc.servidor.entidades.Usuario;
import cfc.servidor.enumerados.EstadosEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO de {@link Usuario}
 */
public class UsuarioDTO implements Serializable {

    // ======================== Atributos ========================
    private Integer id;

    private String nombre;

    private String apellido;

    private String cedula;

    private LocalDate fechaNacimiento;

    private String telefono;

    private String username;

    private String email;

    private Integer idPerfil;

    private EstadosEnum estado;

    // ===================== Constructores =======================

    public UsuarioDTO() {
    }

    /**
     * Crea un DTO a partir de una entidad de usuario.
     *
     * @param usuario Entidad de usuario a convertir.
     */
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.cedula = usuario.getCedula();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.fechaNacimiento = usuario.getFechaNacimiento();
        this.telefono = usuario.getTelefono();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.idPerfil = usuario.getPerfil().getId();
        this.estado = usuario.getEstado();
    }


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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaNacimiento(Integer anio, Integer mes, Integer dia) {
        this.fechaNacimiento = LocalDate.of(anio, mes, dia);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    // ===================== Métodos de Object =======================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO that = (UsuarioDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(cedula, that.cedula) && Objects.equals(fechaNacimiento, that.fechaNacimiento) && Objects.equals(telefono, that.telefono) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(idPerfil, that.idPerfil) && estado == that.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, cedula, fechaNacimiento, telefono, username, email, idPerfil, estado);
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", idPerfil=" + idPerfil +
                ", estado=" + estado +
                '}';
    }
}

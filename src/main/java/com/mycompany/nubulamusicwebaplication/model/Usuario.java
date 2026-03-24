/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nubulamusicwebaplication.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "contrasenia", nullable = false, length = 255)
    private String contrasenia;

    @Column(name = "pseudonimo", nullable = false, unique = true, length = 50)
    private String pseudonimo;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "cuenta", nullable = false, length = 20)
    private String cuenta;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "terminos_aceptados", nullable = false)
    private boolean terminosAceptados;
    
    @OneToMany(mappedBy = "usuario")
    private Set<Album> albumes;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String contrasenia, String pseudonimo,
                   String estado, String cuenta, LocalDate fechaNacimiento, boolean terminosAceptados, Set<Album> albumes) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.pseudonimo = pseudonimo;
        this.estado = estado;
        this.cuenta = cuenta;
        this.fechaNacimiento = fechaNacimiento;
        this.terminosAceptados = terminosAceptados;
        this.albumes = albumes;
    }

    public Set<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(Set<Album> albumes) {
        this.albumes = albumes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getPseudonimo() {
        return pseudonimo;
    }

    public void setPseudonimo(String pseudonimo) {
        this.pseudonimo = pseudonimo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isTerminosAceptados() {
        return terminosAceptados;
    }

    public void setTerminosAceptados(boolean terminosAceptados) {
        this.terminosAceptados = terminosAceptados;
    }
}

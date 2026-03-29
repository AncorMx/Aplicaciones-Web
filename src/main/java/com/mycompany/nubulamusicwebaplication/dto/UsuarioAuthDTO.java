package com.mycompany.nubulamusicwebaplication.dto;

public class UsuarioAuthDTO {

    private String correo;
    private String contrasenia;

    public UsuarioAuthDTO() {
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
}

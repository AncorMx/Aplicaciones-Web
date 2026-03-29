package com.mycompany.nubulamusicwebaplication.controller;

import com.mycompany.nubulamusicwebaplication.dto.ResponseMessageDTO;
import com.mycompany.nubulamusicwebaplication.dto.UsuarioAuthDTO;
import com.mycompany.nubulamusicwebaplication.model.Usuario;
import com.mycompany.nubulamusicwebaplication.service.IUsuarioService;
import com.mycompany.nubulamusicwebaplication.service.UsuarioService;
import com.mycompany.nubulamusicwebaplication.util.JSONMapper;
import com.mycompany.nubulamusicwebaplication.util.JWTUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuthServletAPI", urlPatterns = {"/api/auth/*"})
public class AuthServletAPI extends HttpServlet {

    private IUsuarioService usuarioService = new UsuarioService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        UsuarioAuthDTO req = JSONMapper.mapper
                .readValue(request.getInputStream(), UsuarioAuthDTO.class);

        Usuario usuario = usuarioService.autenticar(req.getCorreo(), req.getContrasenia());

        if (usuario == null) {
            response.setStatus(401);
            return;
        }

        String token = JWTUtil.generarToken(usuario.getCorreo());

        ResponseMessageDTO mensaje = new ResponseMessageDTO();

        mensaje.setSuccess(true);
        mensaje.setMessage(token);

        response.setContentType("application/json");
        JSONMapper.mapper.writeValue(response.getWriter(), mensaje);
    }
}

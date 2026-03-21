package com.mycompany.nubulamusicwebaplication.controller;

import com.mycompany.nubulamusicwebaplication.model.Album;
import com.mycompany.nubulamusicwebaplication.model.Usuario;
import com.mycompany.nubulamusicwebaplication.service.AlbumService;
import com.mycompany.nubulamusicwebaplication.service.IAbumService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 *
 * @author martinbl
 */
@WebServlet(name = "AlbumServlet", urlPatterns = { "/albums" })
@MultipartConfig(
        // Tamaño máximo que se mantiene en memoria (RAM).
        // Si el archivo supera este tamaño, el servidor lo empieza
        // a escribir temporalmente en disco.
        // 1024 * 1024 = 1MB
        fileSizeThreshold = 1024 * 1024, // 1MB

        // Tamaño máximo permitido para UN archivo subido.
        // Si el usuario intenta subir un archivo mayor a este tamaño,
        // el servidor lanzará una excepción.
        // 1024 * 1024 * 2 = 2MB
        maxFileSize = 1024 * 1024 * 2, // 2MB

        // Tamaño máximo permitido para TODA la petición HTTP.
        // Esto incluye todos los archivos y campos del formulario.
        // Sirve para evitar que alguien envíe múltiples archivos enormes.
        // 1024 * 1024 * 5 = 5MB
        maxRequestSize = 1024 * 1024 * 5 // 5MB
)
public class AlbumServlet extends HttpServlet {

    private final IAbumService albumService = new AlbumService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    request.getRequestDispatcher("/views/admin/album-form.jsp").forward(request, response);
                    break;
                case "edit":
                    Long id = Long.parseLong(request.getParameter("id"));

                    Album album = albumService.obtenerAlbum(id, usuario.getId());

                    request.setAttribute("album", album);

                    request.getRequestDispatcher("/views/admin/album-form.jsp").forward(request, response);
                    break;
                case "delete":
                    Long deleteId = Long.parseLong(request.getParameter("id"));

                    albumService.eliminarAlbum(deleteId, usuario.getId());

                    response.sendRedirect(request.getContextPath() + "/albums");
                    break;
                default:
                    List<Album> albums = albumService.obtenerAlbumsUsuario(usuario.getId());

                    request.setAttribute("albums", albums);

                    request.getRequestDispatcher("/views/admin/mis-albums.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String idParam = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");

        Part filePart = request.getPart("imagen");

        String fileName = filePart.getSubmittedFileName();

        if (!fileName.endsWith(".png")) {
            throw new ServletException("Solo se permiten imagenes en formato .png");
        }

        String newFileName = System.currentTimeMillis() + "_" + fileName;

        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filePath = uploadPath + File.separator + newFileName;
        filePart.write(filePath);

        String imageUrl = "uploads/" + newFileName;

        Album album = new Album();

        album.setTitulo(titulo);
        album.setDescripcion(descripcion);
        album.setImageUrl(imageUrl);
        album.setUsuario(usuario);

        try {
            if (idParam == null || idParam.isEmpty()) {
                albumService.crearAlbum(album);
            } else {
                album.setId(Long.parseLong(idParam));
                albumService.actualizarAlbum(album, usuario.getId());
            }

            response.sendRedirect(request.getContextPath() + "/albums");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("album", album);

            request.getRequestDispatcher("/views/admin/album-form.jsp").forward(request, response);
        }
    }
}

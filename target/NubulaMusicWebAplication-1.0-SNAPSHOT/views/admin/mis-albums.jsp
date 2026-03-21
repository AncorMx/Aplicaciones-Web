<%-- Document : mis-albums Created on : 17 mar 2026 Author : martinbl --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>

            <!DOCTYPE html>
            <html>

            <head>
                <title>Mis Albums - Nébula Music</title>
                <meta charset="UTF-8">
                <link rel="stylesheet" type="text/css"
                    href="${pageContext.request.contextPath}/assets/css/styles.css" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>

            <body>
                <%@include file="/WEB-INF/jsp/fragments/header.jspf" %>
                    <header class="about-header">
                        <img src="${pageContext.request.contextPath}/assets/img/header2.jpg" />
                        <h1>Mis Albums</h1>
                    </header>

                    <main class="about-main">
                        <a href="albums?action=new">Crear Álbum</a>

                        <hr>

                        <c:forEach var="album" items="${albums}">
                            <div style="border:1px solid #ccc;padding:15px;margin-bottom:15px;width:300px">
                                <img src="${album.imageUrl}" width="250">
                                <h3>${album.titulo}</h3>
                                <p>${album.descripcion}</p>
                                <a href="albums?action=edit&id=${album.id}">Editar</a>
                                |
                                <a href="albums?action=delete&id=${album.id}"
                                    onclick="return confirm('¿Eliminar álbum?')">
                                    Eliminar
                                </a>
                            </div>
                        </c:forEach>
                    </main>

                    <%@include file="/WEB-INF/jsp/fragments/footer.jspf" %>
            </body>

            </html>
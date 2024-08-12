<%-- 
    Document   : mostrarforo
    Created on : 13/07/2024, 8:21:31 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Desactivar la caché del navegador, asegurando que siempre se solicite una versión nueva de la página desde el servidor
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies  
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mostrarforo.css">
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
        <title>FORO</title>
    </head>

    <body>
        <c:choose>
            <c:when test="${rol == '1'}">
                <jsp:include page="vistas_estudiante/header_estu.jsp" />
            </c:when>
            <c:when test="${rol == '2'}">
                <jsp:include page="vistas_docente/header_docen.jsp" />
            </c:when>
            <c:when test="${rol == '3' || rol == '4'}">
                <jsp:include page="vistas_admin/header_admin.jsp" />
            </c:when>
        </c:choose>

        <main>
            <section class="container">
                <article class="present">
                    <div class="present__content">
                        <span class="present__fecha">${foro.fecha}</span>
                        <h1 class="present__title">${foro.titulo}</h1>
                        <div class="present__text" id="presentText">
                            ${foro.descripcion}
                        </div>
                        <p class="present__tags">
                            <span>Etiquetas:</span>
                            <a href="${pageContext.request.contextPath}/sv_foros?asignatura=${foro.asignaturaId}" class="tag">${foro.asignatura}</a>
                            <a href="${pageContext.request.contextPath}/sv_foros?tipof=${foro.tipoId}" class="tag">${foro.tipo}</a>
                            <a href="${pageContext.request.contextPath}/sv_foros?idioma=${foro.idiomaId}" class="tag">${foro.idioma}</a>
                        </p>

                        <p class="present__usuario">
                            Publicado por: ${foro.rolUsuario} - ${foro.nombreUsuario}
                        </p>

                        <a href="${pageContext.request.contextPath}/sv_foros" class="present__cerrar"><i class="bi bi-x-circle-fill"></i></a>

                        <c:if test="${foro.usuarioDoc == sessionScope.UserDoc}">
                            <div class="respu__crud margin-bottom">
                                <form action="${pageContext.request.contextPath}/mostrar_foro" method="POST" class="form_eliminar">
                                    <input type="hidden" name="foroId" value="${foro.id}">
                                    <input type="hidden" name="action" value="eliminarForo">
                                    <button type="button" class="eliminarForo"><img src="assets/papelera.png" alt="Eliminar Foro"/></button>
                                </form>
                                <button id="editarForoBtn" class="editarForo"><img src="assets/editar.png" alt="Editar Foro"/></button>
                            </div>
                        </c:if>
                        <c:if test="${(rol == '3' || rol == '4') && foro.usuarioDoc != sessionScope.UserDoc}">
                            <div class="respu__crud margin-bottom">
                                <form action="${pageContext.request.contextPath}/mostrar_foro" method="POST" class="form_eliminar">
                                    <input type="hidden" name="foroId" value="${foro.id}">
                                    <input type="hidden" name="action" value="eliminarForo">
                                    <button type="button" class="eliminarForo"><img src="assets/papelera.png" alt="Eliminar Foro"/></button>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </article>

                <article class="respu">
                    <form action="${pageContext.request.contextPath}/sv_respuestas" class="form__coment" id="respuForm" method="POST" novalidate>
                        <h2 class="form__title">Publica tu respuesta</h2>
                        <div class="form__group">
                            <div id="editor"></div>
                            <textarea class="form__textarea" id="respuesta" name="respuesta" style="display:none;" required></textarea>
                            <input type="hidden" name="foroId" value="${foro.id}">
                            <input type="hidden" name="action" value="enviarRespu">

                        </div>
                        <button class="form__btn" type="submit">Publicar</button>
                    </form>

                    <h2 class="respu__title">RESPUESTAS</h2>

                    <c:forEach var="respuesta" items="${respuestas}" varStatus="status">
                        <div class="respu__card">
                            <div class="card__info">
                                <p class="card__name">${respuesta.rolUsuario}: ${respuesta.nombreUsuario}</p>
                                <p class="card__fecha">${respuesta.fechaPublicacion} <br> <span class="card__num">#${status.index + 1}</span></p>
                            </div>
                            <div class="separador"></div>
                            <div class="contenidoRespuesta" id="contenidoRespuesta${respuesta.id}">
                                ${respuesta.contenido}
                            </div>

                            <c:if test="${respuesta.usuarioId == sessionScope.UserDoc}">
                                <div class="respu__crud">
                                    <form action="${pageContext.request.contextPath}/sv_respuestas" method="POST" class="form_eliminar">
                                        <input type="hidden" name="respuestaId" value="${respuesta.id}">
                                        <input type="hidden" name="foroId" value="${foro.id}">
                                        <input type="hidden" name="action" value="eliminarRespu">
                                        <button type="button" class="eliminarRespu"><img src="assets/papelera.png" alt="alt"/></button>
                                    </form>
                                    <button class="editarRespuBtn editarRespu" data-id="${respuesta.id}"><img src="assets/editar.png" alt="alt"/></button>
                                </div>
                            </c:if>
                            <c:if test="${(rol == '3' || rol == '4') && respuesta.usuarioId != sessionScope.UserDoc}">
                                <div class="respu__crud">
                                    <form action="${pageContext.request.contextPath}/sv_respuestas" method="POST" class="form_eliminar">
                                        <input type="hidden" name="respuestaId" value="${respuesta.id}">
                                        <input type="hidden" name="foroId" value="${foro.id}">
                                        <input type="hidden" name="action" value="eliminarRespu">
                                        <button type="button" class="eliminarRespu"><img src="assets/papelera.png" alt="alt"/></button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </article>
            </section>
        </main>

        <div id="modalEditarForo" class="display--none">
            <div class="modal__content">
                <span class="modal__close">&times;</span>
                <h2 class="modal__title">Editar Foro</h2>
                <form id="editarForoForm" class="modal__form" action="${pageContext.request.contextPath}/mostrar_foro" method="POST" novalidate>
                    <div class="modal__group">
                        <input type="text" class="modal__input" name="tituloForo" value="${foro.titulo}" required>
                    </div>
                    <div class="form__group">
                        <div id="editorForo" class="modal__editor"></div>
                        <textarea id="foroEditado" class="modal__textarea" name="foroEditado" style="display:none;" required></textarea>
                    </div>
                    <input type="hidden" name="foroId" id="foroIdEdit" value="${foro.id}">
                    <input type="hidden" name="action" value="editarForo">
                    <button type="submit" class="form__btn margin-top">Guardar Cambios</button>
                </form>
            </div>
        </div>

        <div id="modalEditarRespuesta" class="display--none">
            <div class="modal__content">
                <span class="modal__close">&times;</span>
                <h2 class="modal__title">Editar Respuesta</h2>
                <form id="editarRespuestaForm" class="modal__form" action="${pageContext.request.contextPath}/sv_respuestas" method="POST" novalidate>
                    <div class="form__group">
                        <div id="editorRespuesta" class="modal__editor"></div>
                        <textarea id="respuestaEditada" class="modal__textarea" name="respuesta" style="display:none;" required></textarea>
                    </div>
                    <input type="hidden" name="respuestaId" id="respuestaIdEdit">
                    <input type="hidden" name="foroId" value="${foro.id}">
                    <input type="hidden" name="action" value="editarRespu">
                    <button type="submit" class="form__btn margin-top">Guardar Cambios</button>
                </form>
            </div>
        </div>

        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            // Mostrar SweetAlert si hay un mensaje de error en la sesión
            window.addEventListener('load', function () {
                const errorMessage = '<%= session.getAttribute("error")%>';
                if (errorMessage && errorMessage !== 'null') {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: errorMessage,
                    });
                    <% session.removeAttribute("error");%>
                }

                const successMessage = '<%= session.getAttribute("success")%>';
                if (successMessage && successMessage !== 'null') {
                    Swal.fire({
                        icon: 'success',
                        title: 'Éxito',
                        text: successMessage,
                    });
                    <% session.removeAttribute("success");%>
                }
            });
        </script>
        <script src="js/foro.js" type="module"></script>
    </body>
</html>

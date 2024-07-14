<%-- 
    Document   : subirforo
    Created on : 11/07/2024, 2:14:37 p. m.
    Author     : Propietario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/subirforo.css"/>
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
        <title>Crear foro</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${rol == '1'}">
                <jsp:include page="vistas_estudiante/header_estu.jsp" />
            </c:when>
            <c:when test="${rol == '2'}">
                <jsp:include page="vistas_docente/header_docen.jsp" />
            </c:when>
        </c:choose>

        <main>
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/subir_foro" method="POST" class="form" id="foroForm">
                    <div class="form__group">
                        <label class="form__label" for="titulo">Título del Foro</label>
                        <input class="form__input" type="text" id="titulo" name="titulo" maxlength="50">
                    </div>
                    <div class="form__group form__group-select">
                        <select class="form__select" name="asignatura" id="asignatura">
                            <option value="">Seleccione la asignatura</option>
                            <c:forEach var="asignatura" items="${asignaturas}">
                                <option value="${asignatura.id}">${asignatura.nombre}</option>
                            </c:forEach>
                        </select>

                        <select class="form__select" name="idioma" id="idioma">
                            <option value="">Seleccione el idioma</option>
                            <c:forEach var="idioma" items="${idiomas}">
                                <option value="${idioma.id}">${idioma.nombre}</option>
                            </c:forEach>
                        </select>

                        <select class="form__select" name="tipof" id="tipof">
                            <option value="">Seleccione el tipo de foro</option>
                            <c:forEach var="tipo" items="${tiposforo}">
                                <option value="${tipo.id}">${tipo.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form__group">
                        <label class="form__label" for="descripcion">Descripción</label>
                        <div id="editor"></div>
                        <textarea class="form__textarea" id="descripcion" name="descripcion" style="display:none;"></textarea>
                    </div>

                    <a class="cerrar" href="${pageContext.request.contextPath}/sv_foros"><i class="bi bi-x-circle-fill"></i></a>

                    <button class="form__button" type="submit" id="submitBtn" name="enviar">Publicar</button>
                </form>
            </div>
        </main>

        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
            var quill = new Quill('#editor', {
                theme: 'snow'
            });

            function valid() {
                let tit = document.getElementById("titulo").value.trim();
                // let descrip = document.getElementById("descripcion").value;
                let asig = document.getElementById("asignatura").value;
                let idioma = document.getElementById("idioma").value;
                let tipo = document.getElementById("tipof").value;
                var descrip = quill.getText().trim();

                if (tit === "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'El título no puede estar vacío',
                    });
                    return false;
                }

                if (descrip === '' || descrip === '<p><br></p>' || descrip.length === 0) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'La descripción no puede estar vacía',
                    });
                    return false;
                }

                if (asig === "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Por favor, seleccione la asignatura',
                    });
                    return false;
                }

                if (idioma === "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Por favor, seleccione el idioma',
                    });
                    return false;
                }

                if (tipo === "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Por favor, seleccione el tipo de foro',
                    });
                    return false;
                }

                return true;
            }


            document.getElementById('foroForm').addEventListener('submit', function () {

                document.getElementById('descripcion').value = quill.root.innerHTML;

                if (!valid()) {
                    event.preventDefault();
                }
            });

            let titulo = document.querySelector(".header__title");
            titulo.textContent = "Crear Nuevo Foro";
        </script>

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
            });
        </script>
    </body>
</html>

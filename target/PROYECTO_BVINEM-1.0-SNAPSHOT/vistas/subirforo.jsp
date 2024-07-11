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
        <title>Realizar foro</title>
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
                <form class="form" id="foroForm">
                    <h2 class="form__title">Crear Nuevo Foro</h2>
                    <div class="form__group">
                        <label class="form__label" for="titulo">Título del Foro</label>
                        <input class="form__input" type="text" id="titulo" name="titulo">
                    </div>
                    <div class="form__group">
                        <label class="form__label" for="descripcion">Descripción</label>
                        <div id="editor"></div>
                        <textarea class="form__textarea" id="descripcion" name="descripcion" style="display:none;"></textarea>
                    </div>
                    <button class="form__button" type="submit" id="submitBtn">Publicar</button>
                </form>
            </div>
        </main>

        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
        <script>
            var quill = new Quill('#editor', {
                theme: 'snow'
            });

            document.getElementById('foroForm').addEventListener('submit', function () {
                document.getElementById('descripcion').value = quill.root.innerHTML;
            });
        </script>
    </body>
</html>

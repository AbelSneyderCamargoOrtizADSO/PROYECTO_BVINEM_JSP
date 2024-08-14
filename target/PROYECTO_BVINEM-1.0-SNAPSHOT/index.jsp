<%-- 
    Document   : index
    Created on : 10/06/2024, 4:22:41 p. m.
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
    <c:choose>
        <c:when test="${not empty sessionScope.logueado}">
            <c:redirect url="/sv_documentos"/>
        </c:when>
    </c:choose>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/login.css">
        <title>LOGIN</title>
    </head>

    <body>
        <main>
            <section class="container">
                <div class="roles roles--pd">
                    <h1 class="roles__title">INGRESAR</h1>
                    <img src="assets/escu.png" alt="" class="roles__img">

                    <button id="btn__admin" class="roles__btn roles__btn--color2">
                        <img src="assets/admin.png" alt="" class="btn__img">
                        <span class="btn__span">Admin</span>
                    </button>

                    <button id="btn__doc" class="roles__btn roles__btn--color1">
                        <img src="assets/profe.png" alt="" class="btn__img">
                        <span class="btn__span">Profe</span>
                    </button>

                    <button id="btn__estu" class="roles__btn select">
                        <img src="assets/estu.png" alt="" class="btn__img">
                        <span class="btn__span">Estud.</span>
                    </button>
                </div>

                <div id="container__estu" class="container__log container__log--estu">
                    <figure class="container__figure">
                        <img src="assets/estu.png" alt="" class="container__img">
                    </figure>

                    <form action="sv_login" method="POST" class="form" novalidate>
                        <h2 class="form__title">ESTUDIANTE</h2>

                        <div class="form__group">
                            <input type="text" class="form__input dni solo-numeros" placeholder="Documento de identidad" name="dni" maxlength="10" required>
                        </div>

                        <div class="form__group">
                            <input type="password" class="form__input pass" placeholder="Contraseña" name="password" required>
                        </div>

                        <button class="btn__principal" name="ingresarEstu">Ingresar</button>

                        <p class="form__reg">¿Aun no te has registrado? <a href="register.jsp" class="form__link">REGISTRATE</a></p>
                    </form>
                </div>

                <!-- DOCENTE -->
                <div id="container__doc" class="container__log container__log--doc disable">
                    <figure class="container__figure container__figure--doc">
                        <img src="assets/profe.png" alt="" class="container__img">
                    </figure>

                    <form action="sv_login" method="POST" class="form form--doc" novalidate>
                        <h2 class="form__title form__title--doc">DOCENTE</h2>

                        <div class="form__group">
                            <input type="text" class="form__input form__input--doc dni solo-numeros" placeholder="Documento de identidad" name="dni" maxlength="10" required>
                        </div>
                        <div class="form__group">
                            <input type="password" class="form__input form__input--doc pass" placeholder="Contraseña" name="password" required>
                        </div>

                        <button class="btn__principal btn__principal--doc" name="ingresarDocen">Ingresar</button>
                    </form>
                </div>

                <!-- ADMIN -->
                <div id="container__admin" class="container__log container__log--admin disable">
                    <figure class="container__figure container__figure--admin">
                        <img src="assets/admin.png" alt="" class="container__img">
                    </figure>

                    <form action="sv_login" method="POST" class="form form--admin" novalidate>
                        <h2 class="form__title form__title--admin">ADMINISTRADOR</h2>

                        <div class="form__group">
                            <input type="text" class="form__input form__input--admin dni solo-numeros" placeholder="Documento de identidad" name="dni" maxlength="10" required>
                        </div>
                        <div class="form__group">
                            <input type="password" class="form__input form__input--admin pass" placeholder="Contraseña" name="password" required>
                        </div>

                        <button class="btn__principal btn__principal--admin" name="ingresarAdmin">Ingresar</button>
                    </form>
                </div>
            </section>

        </main>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            // Mostrar SweetAlert si hay un mensaje de error en la sesión
            window.addEventListener('load', function () {
                const errorMessage = '<%= request.getAttribute("error")%>';
                if (errorMessage && errorMessage !== 'null') {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: errorMessage,
                    });
            <% request.removeAttribute("error");%>
                }

                const successMessage = '<%= request.getAttribute("success")%>';
                if (successMessage && successMessage !== 'null') {
                    Swal.fire({
                        icon: 'success',
                        title: 'Éxito',
                        text: successMessage,
                    });
            <% request.removeAttribute("success");%>
                }
            });
        </script>
        <script src="${pageContext.request.contextPath}/js/index.js" type="module"></script>
    </body>
</html>
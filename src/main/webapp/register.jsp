<%-- 
    Document   : register
    Created on : 10/06/2024, 4:23:24 p. m.
    Author     : Abelito
--%>

<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/register.css">
        <title>REGISTRO</title>
    </head>

    <body>
        <main>
            <section class="container">
                <div class="roles roles--hg">
                    <h1 class="roles__title">REGISTRARSE</h1>
                    <img src="assets/escu.png" alt="" class="roles__img">

                    <button class="roles__btn select">
                        <img src="assets/estu.png" alt="" class="btn__img">
                        <span class="btn__span">Estud.</span>
                    </button>
                </div>

                <div class="container__log container__log--rows">
                    <figure class="container__figure form--pd">
                        <img src="assets/estu.png" alt="" class="container__img">
                    </figure>

                    <form action="${pageContext.request.contextPath}/sv_usuario" method="POST" class="form form--pd" novalidate>
                        <h2 class="form__title">ESTUDIANTE</h2>
                        <input type="hidden" name="tipoUsuario" value="estudiante">
                        <div class="form__group">
                            <input type="text" id="dni" class="form__input solo-numeros" placeholder="Documento de identidad" name="nuevoDoc" required maxlength="10">
                        </div>
                        <div class="form__group">
                            <input type="text" id="nombres" class="form__input solo-letras" placeholder="Nombres" name="nombres" maxlength="40" required>
                        </div>
                        <div class="form__group">
                            <input type="text" id="apellidos" class="form__input solo-letras" placeholder="Apellidos" name="apellidos" maxlength="40" required>
                        </div>
                        <div class="form__group">
                            <input type="text" id="correo" class="form__input" placeholder="Correo electrónico" name="correo" required>
                        </div>
                        <div class="form__group">
                            <input type="password" id="password" class="form__input" placeholder="Contraseña" name="password" required>
                        </div>
                        <div class="form__group">
                            <input type="password" id="confirmPass" class="form__input" name="confirmPass" placeholder="Confirmar contraseña" required>
                        </div>
                        <button class="btn__principal" type="submit" name="regEstudiante">Registrarse</button>

                        <p class="form__reg">¿Ya te has registrado? <a href="index.jsp" class="form__link">INICIAR SESION</a></p>
                    </form>
                </div>
            </section>
        </main>
        
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
            });
        </script>
        <script src="${pageContext.request.contextPath}/js/register.js" type="module"></script>
    </body>

</html>

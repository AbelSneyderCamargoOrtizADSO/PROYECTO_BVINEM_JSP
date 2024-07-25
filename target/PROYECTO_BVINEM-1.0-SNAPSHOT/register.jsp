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
                <div class="roles">
                    <h1 class="roles__title">REGISTRARSE</h1>
                    <img src="assets/escu.png" alt="" class="roles__img">

                    <button class="roles__btn select">
                        <img src="assets/estu.png" alt="" class="btn__img">
                        <span class="btn__span">Estud.</span>
                    </button>
                </div>

                <div class="container__log">
                    <figure class="container__figure">
                        <img src="assets/estu.png" alt="" class="container__img">
                    </figure>

                    <form action="sv_register" method="POST" class="container__form form__valid">
                        <h2 class="form__title">ESTUDIANTE</h2>

                        <input type="text" class="form__input solo-numeros obligatorio" placeholder="Documento de identidad" name="dni" maxlength="10">
                        <input type="text" class="form__input solo-letras obligatorio" placeholder="Nombres" name="nombres">
                        <input type="text" class="form__input solo-letras obligatorio" placeholder="Apellidos" name="apellidos">
                        <input type="text" class="form__input obligatorio" placeholder="Correo electrónico" name="correo">
                        <input type="password" class="form__input obligatorio" placeholder="Contraseña" name="password">
                        <select class="form__input form__select obligatorio" name="grado" placeholder="Grado Académico">
                            <option value="" disabled selected>Seleccione su grado académico</option>
                            <option value="1">Primero</option>
                            <option value="2">Segundo</option>
                            <option value="3">Tercero</option>
                            <option value="4">Cuarto</option>
                            <option value="5">Quinto</option>
                            <option value="6">Sexto</option>
                            <option value="7">Séptimo</option>
                            <option value="8">Octavo</option>
                            <option value="9">Noveno</option>
                            <option value="10">Décimo</option>
                            <option value="11">Undécimo</option>
                        </select>

                        <button class="form__btn" type="submit" name="submit">Registrarse</button>

                        <p class="form__reg">¿Ya te has registrado? <a href="index.jsp" class="form__link">INICIAR SESION</a></p>
                    </form>
                </div>
            </section>
        </main>
        
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="${pageContext.request.contextPath}/js/validaciones.js"></script>

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
            });
        </script>

    </body>

</html>

<%-- 
    Document   : index
    Created on : 10/06/2024, 4:22:41 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/login.css">
        <title>LOGIN</title>
    </head>

    <body>
        <input type="hidden" id="mensajeerror" value="<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>">
        <main>
            <section class="container">
                <div class="roles">
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

                    <form action="sv_login" method="POST" class="container__form form__valid">
                        <h2 class="form__title">ESTUDIANTE</h2>

                        <input type="text" class="form__input solo-numeros obligatorio" placeholder="Documento de identidad" name="dni" maxlength="10">
                        <input type="password" class="form__input obligatorio" placeholder="Contraseña" name="password">

                        <button class="form__btn" name="ingresarEstu">Ingresar</button>

                        <p class="form__reg">¿Aun no te has registrado? <a href="register.jsp" class="form__link">REGISTRATE</a></p>
                    </form>
                </div>

                <!-- DOCENTE -->
                <div id="container__doc" class="container__log container__log--doc disable">
                    <figure class="container__figure container__figure--doc">
                        <img src="assets/profe.png" alt="" class="container__img">
                    </figure>

                    <form action="sv_login" method="POST" class="container__form container__form--doc form__valid">
                        <h2 class="form__title form__title--doc">DOCENTE</h2>
                        
                        <input type="text" class="form__input form__input--doc solo-numeros obligatorio" placeholder="Documento de identidad" name="dni" maxlength="10">
                        <input type="password" class="form__input form__input--doc obligatorio" placeholder="Contraseña" name="password">

                        <button class="form__btn form__btn--doc" name="ingresarDocen">Ingresar</button>
                    </form>
                </div>

                <!-- ADMIN -->
                <div id="container__admin" class="container__log container__log--admin disable">
                    <figure class="container__figure container__figure--admin">
                        <img src="assets/admin.png" alt="" class="container__img">
                    </figure>

                    <form action="sv_login" method="POST" class="container__form container__form--admin form__valid">
                        <h2 class="form__title form__title--admin">ADMINISTRADOR</h2>

                        <input type="text" class="form__input form__input--admin solo-numeros obligatorio" placeholder="Documento de identidad" name="dni" maxlength="10">
                        <input type="password" class="form__input form__input--admin obligatorio" placeholder="Contraseña" name="password">

                        <button class="form__btn form__btn--admin" name="ingresarAdmin">Ingresar</button>
                    </form>
                </div>
            </section>

        </main>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script type="text/javascript">
            var mensajeError = document.getElementById("mensajeerror").value;
            if (mensajeError) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: mensajeError
                });
            }
        </script>

        <script>
            let cestu = document.getElementById("container__estu");
            let cdoc = document.getElementById("container__doc");
            let cadmin = document.getElementById("container__admin");
            let btnadmin = document.getElementById("btn__admin");
            let btndoc = document.getElementById("btn__doc");
            let btnestu = document.getElementById("btn__estu");

            // Escuchador de evento para el botón de administrador
            btnadmin.addEventListener("click", function () {
                cestu.classList.add("disable");
                cdoc.classList.add("disable");
                cadmin.classList.remove("disable");
                btnadmin.classList.add("select");
                btndoc.classList.remove("select");
                btnestu.classList.remove("select");
            });

            // Escuchador de evento para el botón de docente
            btndoc.addEventListener("click", function () {
                cestu.classList.add("disable");
                cdoc.classList.remove("disable");
                cadmin.classList.add("disable");
                btnadmin.classList.remove("select");
                btndoc.classList.add("select");
                btnestu.classList.remove("select");
            });

            // Escuchador de evento para el botón de estudiante
            btnestu.addEventListener("click", function () {
                cestu.classList.remove("disable");
                cdoc.classList.add("disable");
                cadmin.classList.add("disable");
                btnadmin.classList.remove("select");
                btndoc.classList.remove("select");
                btnestu.classList.add("select");
            });
        </script>
    </body>
    
    <script src="${pageContext.request.contextPath}/js/validaciones.js"></script>

</html>
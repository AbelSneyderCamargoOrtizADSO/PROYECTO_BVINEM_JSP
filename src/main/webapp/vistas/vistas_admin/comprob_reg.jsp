<%-- 
    Document   : comprob_reg
    Created on : 4/08/2024, 11:19:41 p. m.
    Author     : Abelito
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comprobante.css"/>
        <title>Comprobante de Registro</title>
    </head>
    <body>
        <main>
            <section class="container">
                <div class="comprobante">
                    <a href="${pageContext.request.contextPath}/sv_usuario" class="form__cerrar"><i class="bi bi-x-circle-fill"></i></a>
                    <div class="comprobante__header">
                        <img src="${pageContext.request.contextPath}/assets/escu.png" alt="Escudo INEM"/>
                    </div>
                    <h1 class="comprobante__title">Comprobante de Registro</h1>
                    <h2 class="comprobante__subtitle">Biblioteca Virtual INEM</h2>
                    <div class="comprobante__info">
                        <p><b>Documento de Identidad:</b> ${usuario.docUsu}</p>
                        <p><b>Nombres:</b> ${usuario.nombre}</p>
                        <p><b>Apellidos:</b> ${usuario.apellido}</p>
                        <p><b>Correo Electrónico:</b> ${usuario.correo}</p>
                        <p><b>Rol:</b> <span id="rol" data-rol="${usuario.rol}">${usuario.rol}</span></p>
                        <p><b>Fecha de Registro:</b> <span id="fechaRegistro"></span></p>
                    </div>
                    <button class="comprobante__button" onclick="window.print()">Imprimir Comprobante</button>
                </div>
            </section>
        </main>
    </body>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        // Mostrar SweetAlert si hay un mensaje de error en la sesión
        window.addEventListener('load', function () {
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
    
    <script src="${pageContext.request.contextPath}/js/comprobante.js"></script>
</html>


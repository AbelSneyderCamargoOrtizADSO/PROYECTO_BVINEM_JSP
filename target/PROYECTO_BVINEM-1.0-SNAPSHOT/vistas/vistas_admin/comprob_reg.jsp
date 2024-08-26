<%-- 
    Document   : comprob_reg
    Created on : 4/08/2024, 11:19:41 p. m.
    Author     : Abelito
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Verifica si la sesión es nula o si no existe un atributo "logueado" en la sesión.
    if (session == null || session.getAttribute("logueado") == null) {
        // Si la sesión no existe o el usuario no está logueado, redirige al usuario a "index.jsp".
        // `request.getContextPath()` asegura que la redirección sea relativa a la raíz de la aplicación web.
        response.sendRedirect(request.getContextPath() + "/index.jsp");

        // Detiene la ejecución del resto del código para evitar que se procese la solicitud después de la redirección.
        return;
    }

    // Desactiva la caché del navegador para esta respuesta, asegurando que siempre se solicite una nueva versión de la página desde el servidor.
    // Esto evita que el navegador almacene en caché esta página y siempre obtenga una versión actualizada del servidor.
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1

    // Asegura compatibilidad con navegadores más antiguos estableciendo el encabezado "Pragma" a "no-cache".
    // Este encabezado es parte del protocolo HTTP 1.0 y se usa para desactivar la caché.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0

    // Establece la fecha de expiración de la página a 0, lo que indica a los proxies y cachés intermedios que la página ya ha expirado
    // y que no deben servirla desde su caché. Esto fuerza a los clientes a solicitar una nueva copia desde el servidor.
    response.setDateHeader("Expires", 0); // Proxies

%>
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


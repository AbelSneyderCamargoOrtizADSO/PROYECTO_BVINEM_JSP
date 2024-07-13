<%-- 
    Document   : header_docen
    Created on : 3/07/2024, 9:21:00 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header">
    <img src="${pageContext.request.contextPath}/assets/escu.png" alt="" class="header__img">
    <input type="search" class="header__search" placeholder="Buscar...">
    <h1 class="header__title"></h1>
    <div class="header__user">
        <div class="header__menu">
            <img src="${pageContext.request.contextPath}/assets/iconouser.png" alt="" class="header__img header__img--display">
            <i class="bi bi-caret-down-fill"></i>
            <nav class="nav">
                <ul class="nav__list">
                    <li class="nav__list">
                        <a href="${pageContext.request.contextPath}/subir_doc" class="nav__link"><i class="bi bi-file-earmark-arrow-up-fill"></i> Subir
                            documento</a>
                    </li>
                    <li class="nav__list">
                        <a href="${pageContext.request.contextPath}/subir_foro" class="nav__link"><i class="bi bi-chat-square-text-fill"></i> Realizar foro</a>
                    </li>
                    <li class="nav__list">
                        <a href="solicitudes/soli.html" class="nav__link"><i class="bi bi-envelope-paper-fill"></i>
                            Solicitudes</a>
                    </li>
                </ul>
            </nav>
        </div>
        <form action="${pageContext.request.contextPath}/cerrar_sesion" method="get">
            <button type="submit" class="header__btn">Cerrar sesión</button>
            <button type="submit" class="header__btn header__btn--resp"><i class="bi bi-box-arrow-right"></i></button>
        </form>
    </div>
</header>


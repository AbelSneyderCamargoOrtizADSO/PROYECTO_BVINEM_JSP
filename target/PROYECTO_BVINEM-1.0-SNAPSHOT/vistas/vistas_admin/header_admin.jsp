<%-- 
    Document   : header_admin
    Created on : 3/07/2024, 9:33:24 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header header-admin">
    <img src="${pageContext.request.contextPath}/assets/escu.png" alt="" class="header__img">
    <input type="search" class="header__search" placeholder="Buscar...">
    <h1 class="header__title"></h1>
    <div class="header__user">
        <div class="header__menu">
            <img src="${pageContext.request.contextPath}/assets/iconoadmin.png" alt="" class="header__img header__img--display">
            <i class="bi bi-caret-down-fill"></i>
            <nav class="nav">
                <ul class="nav__list">
                    <li class="nav__list">
                        <a href="${pageContext.request.contextPath}/sv_usuario" class="nav__link"><i class="bi bi-person-fill-gear"></i> Gestionar docentes</a>
                    </li>

                </ul>
            </nav>
        </div>
        <form action="${pageContext.request.contextPath}/cerrar_sesion" method="get">
            <button type="submit" class="header__btn header__btn-admin">Cerrar sesión</button>
            <button type="submit" class="header__btn header__btn--resp"><i class="bi bi-box-arrow-right"></i></button>
        </form>
    </div>
</header>
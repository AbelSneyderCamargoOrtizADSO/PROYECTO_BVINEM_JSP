<%-- 
    Document   : header_estu
    Created on : 3/07/2024, 8:10:24 p. m.
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
            <img src="${pageContext.request.contextPath}/assets/iconoestu.png" alt="" class="header__img header__img--display"> 
            <i class="bi bi-caret-down-fill"></i>
            <nav class="nav">
                <ul class="nav__list">
                    <li class="nav__list">
                        <a href="${pageContext.request.contextPath}/subir_foro" class="nav__link"><i class="bi bi-chat-square-text-fill"></i> Realizar foro</a>
                    </li>
                    <li class="nav__list">
                        <a href="" class="nav__link"><i class="bi bi-envelope-paper-fill"></i>
                            Realizar solicitud</a>
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

<%-- 
    Document   : header_admin
    Created on : 3/07/2024, 9:33:24 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header">
    <img src="${pageContext.request.contextPath}/assets/escu.png" alt="" class="header__img">
    <input type="search" class="header__search" placeholder="Buscar...">
    <div class="header__user">
        <div class="header__menu">
            <img src="${pageContext.request.contextPath}/assets/iconoadmin.png" alt="" class="header__img header__img--display">
        </div>
        <form action="${pageContext.request.contextPath}/cerrar_sesion" method="get">
            <button type="submit" class="header__btn">Cerrar sesión</button>
            <button type="submit" class="header__btn header__btn--resp"><i class="bi bi-box-arrow-right"></i></button>
        </form>
    </div>
</header>
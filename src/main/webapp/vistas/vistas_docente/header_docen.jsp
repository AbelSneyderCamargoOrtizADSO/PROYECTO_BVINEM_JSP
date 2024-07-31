<%-- 
    Document   : header_docen
    Created on : 3/07/2024, 9:21:00 p. m.
    Author     : Abelito
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header">
    <img src="${pageContext.request.contextPath}/assets/escu.png" alt="" class="header__img">
    <form action="${pageContext.request.contextPath}/sv_busqueda" method="get" class="header__search" onsubmit="return this.query.value.trim().length > 0;">
        <input type="search" name="query" class="search__input" placeholder="Buscar...">
        <select name="tipo" class="search__select">
            <option value="libros">Libros</option>
            <option value="foros">Foros</option>
        </select>
        <button type="submit" class="search___button">
            <svg viewBox="0 0 24 24" aria-hidden="true" class="search__icon">
                <g>
                    <path d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path>
                </g>
            </svg>
        </button>
    </form>

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
                </ul>
            </nav>
        </div>
        <form action="${pageContext.request.contextPath}/cerrar_sesion" method="get">
            <button type="submit" class="header__btn">Cerrar sesión</button>
            <button type="submit" class="header__btn header__btn--resp"><i class="bi bi-box-arrow-right"></i></button>
        </form>
    </div>
</header>

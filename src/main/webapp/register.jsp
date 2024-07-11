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

        <form action="sv_register" method="POST" class="container__form">
          <h2 class="form__title">ESTUDIANTE</h2>

          <input type="text" class="form__input" placeholder="Documento de identidad" name="dni" required="required">
          <input type="text" class="form__input" placeholder="Nombres" name="nombres" required="required">
          <input type="text" class="form__input" placeholder="Apellidos" name="apellidos" required="required">
          <input type="text" class="form__input" placeholder="Correo electrónico" name="correo" required="required">
          <input type="password" class="form__input" placeholder="Contraseña" name="password" required="required">
          <input list="cursos" type="text" class="form__input" placeholder="Grado Académico Ej: 8 (octavo)" name="grado"
            required="required">
          <datalist id="cursos">
            <option value="1" label="primero">
            <option value="2" label="segundo">
            <option value="3" label="tercero">
            <option value="4" label="cuarto">
            <option value="5" label="quinto">
            <option value="6" label="sexto">
            <option value="7" label="septimo">
            <option value="8" label="octavo">
            <option value="9" label="noveno">
            <option value="10" label="decimo">
            <option value="11" label="undecimo">
          </datalist>

          <button class="form__btn" type="submit" name="submit">Registrarse</button>

          <p class="form__reg">¿Ya te has registrado? <a href="index.jsp" class="form__link">INICIAR SESION</a></p>
        </form>
      </div>
    </section>
  </main>

</body>

</html>

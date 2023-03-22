<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registro e Inicio de sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<div class="container">
    <div class="col-6">
        <h2>¡Regístrate!</h2>
        <form:form action="/register" method="post" modelAttribute="nuevoUser">

            <div class="form-group">
                <form:label path="firstName">Nombre:</form:label>
                <form:input path="firstName" class="form-control" />
                <form:errors path="firstName" class="text-danger" />
            </div>

            <div class="form-group">
                <form:label path="lastName">Apellido:</form:label>
                <form:input path="lastName" class="form-control" />
                <form:errors path="lastName" class="text-danger" />
            </div>

            <div class="form-group">
                <form:label path="email">Email:</form:label>
                <form:input path="email" class="form-control" />
                <form:errors path="email" class="text-danger" />
            </div>

            <div class="form-group">
                <form:label path="password">Password:</form:label>
                <form:password path="password" class="form-control" />
                <form:errors path="password" class="text-danger" />
            </div>

            <div class="form-group">
                <form:label path="confirmPassword">Confirmación de Password:</form:label>
                <form:password path="confirmPassword" class="form-control" />
                <form:errors path="confirmPassword" class="text-danger" />
            </div>
            <br>
            <input type="submit" value="Regístrate" class="btn btn-success" />
        </form:form>

    </div>
    <div class="col-6">
        <h2>¡Inicia sesión!</h2>

        <form:form action="/login" method="post" modelAttribute="nuevoLogin">
            <div class="form-group">
                <form:label path="email">Email:</form:label>
                <form:input path="email" class="form-control" />
                <form:errors path="email" class="text-danger" />
            </div>

            <div class="form-group">
                <form:label path="password">Password:</form:label>
                <form:password path="password" class="form-control" />
                <form:errors path="password" class="text-danger" />
            </div>
            <br>
            <input type="submit" value="Inicia Sesión" class="btn btn-success" />
        </form:form>

    </div>
</div>

</body>
</html>
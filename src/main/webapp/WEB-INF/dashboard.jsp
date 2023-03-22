<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<div class="container">
<header class="d-flex justify-content-between align-items-center">
    <h1>Bienvenido ${userSession.firstName}</h1>
    <a class="btn btn-info" href="/projects/new" role="button">Nuevo Proyecto</a>
    <a class="btn btn-danger" href="/logout" role="button">Cerrar Sesión</a>
</header>

    <div class="row">
        <h2>Todos los proyectos</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Proyecto</th>
                <th>Lider de Proyecto</th>
                <th>Fecha Límite</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${otherProjectsList}" var="otherProject">
                <tr>
                    <td>${otherProject.title}</td>
                    <td>${otherProject.lead.firstName}</td>
                    <td>${otherProject.dueDate}</td>
                    <td>
                        <a href="/projects/join/${otherProject.id}" class="btn btn-info">Unirme</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>
    <hr>
    <div class="row">
        <h2>Mis proyectos</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Proyecto</th>
                <th>Lider de Proyecto</th>
                <th>Fecha Límite</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${myProjectsList}" var="myProject">
                <tr>
                    <td>${myProject.title}</td>
                    <td>${myProject.lead.firstName}</td>
                    <td>${myProject.dueDate}</td>
                    <td>
                        <c:if test="${myProject.lead.id == userSession.id}">
                            <a href="/projects/edit/${myProject.id}" class="btn btn-warning">Editar</a>
                        </c:if>

                        <c:if test="${myProject.lead.id != userSession.id}">
                            <a href="/projects/leave/${myProject.id}" class="btn btn-danger">Salir</a>
                        </c:if>

                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </div>

</div>



</body>
</html>
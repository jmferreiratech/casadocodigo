<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de produtos</title>
</head>
<body>
<form:form method="post" action="${spring:mvcUrl('PC#save').build()}" commandName="product">
    <div>
        <label for="title">Título</label>
        <form:input path="title"/>
        <form:errors path="title"/>
    </div>
    <div>
        <label for="description">Descrição</label>
        <form:textarea path="description" rows="10" cols="20"/>
        <form:errors path="description"/>
    </div>
    <div>
        <label for="pages">Número de paginas</label>
        <form:input path="pages"/>
        <form:errors path="pages"/>
    </div>
    <div>
        <c:forEach items="${types}" var="bookType" varStatus="status">
            <div>
                <label for="price_${bookType}">${bookType}</label>
                <input type="text" name="prices[${status.index}].value"
                       id="price_${bookType}"/>
                <input type="hidden"
                       name="prices[${status.index}].bookType"
                       value="${bookType}"/>
            </div>
        </c:forEach>
    </div>
    <div>
        <label for="releaseDate">Data de lançamento</label>
        <input type="date" name="releaseDate"/>
        <form:errors path="releaseDate"/>
    </div>
    <div>
        <input type="submit" value="Enviar">
    </div>
</form:form>
</body>
</html>

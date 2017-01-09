<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de produtos</title>
</head>
<body>
<form method="post" action="/casadocodigo/produtos">
    <div>
        <label for="title">Titulo</label>
        <input type="text" name="title" id="title"/>
    </div>
    <div>
        <label for="description">Descrição</label>
        <textarea rows="10" cols="20" name="description"
                  id="description">
        </textarea>
    </div>
    <div>
        <label for="pages">Número de paginas</label>
        <input type="text" name="pages" id="pages"/>
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
        <input type="submit" value="Enviar">
    </div>
</form>
</body>
</html>

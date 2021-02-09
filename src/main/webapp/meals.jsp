<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
</h3>
<hr>
<h2>Meals</h2>
<table border="1" cellpadding="5">
    <tr>
        <th>Description</th>
        <th>Calories</th>
        <th>Date</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:  ${meal.excess ? 'red' : 'green'}">
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
            <td><fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
</h3>
<hr>
<h2> ${action ? 'Create meal' : 'Update meal'}</h2>

<form method="POST" action='meals' name="createOrUpdateForm">
    <label for="dateTime">DateTime:</label>
    <input id="dateTime" name="dateTime" class="field" type="datetime-local" required
           value="${meal.dateTime}"/>
    <br/>
    <label for="description">Description:</label>
    <input id="description" name="description" class="field" type="text" required
           value="${meal.description}"/>
    <br/>
    <label for="calories">Calories:</label>
    <input id="calories" name="calories" class="field" type="number" required
           value="${meal.calories}"/>
    <br/>
    <input type="hidden" name="id" value="${meal.id}"/>
    <span class="button-block">
        <input type="submit" value="Save"/>
        <a href="meals"><input type="button" value="Cancel"></a>
    </span>
</form>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Ganichev
  Date: 07.11.2017
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="resources/css/bootstrap.css" rel="stylesheet"/>
    <link href="resources/css/topjava.css" rel="stylesheet"/>
    <title></title>
</head>
<body>
    <div class="container">
        <h3><a href="index.html">Home</a></h3>
        <h2>Meals</h2>
        <div class="row">
            <div class="col-sm-12">
                <table class="table table-striped">
                    <thead>
                        <tr role="row">
                            <th>Date/Time</th>
                            <th>Description</th>
                            <th>Calories</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="meal" items="${meals}">
                            <tr class="${meal.exceed?'exceeded':'normal'}">
                                <td>${meal.dateTime.format(dateFormater)}</td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>

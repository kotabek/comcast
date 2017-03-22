<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lng="en">
<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">


    <h1>Comcast test task</h1>
    <div class="row" style="padding-bottom: 20px;">
        <form:form method="post" action="/"
                   modelAttribute="item"
                   cssClass="form-horizontal col-xs-offset-4 col-xs-4">
            <div class="form-group">
                <label for="partnerId">Partner ID</label>
                <form:input path="partnerId"
                            type="text" cssClass="form-control"
                            name="partnerId"/>
            </div>
            <div class="form-group">
                <label for="duration">Duration</label>
                <form:input path="duration"
                            type="number" cssClass="form-control"
                            name="duration"/>
            </div>
            <div class="form-group">
                <label for="content">Content</label>
                <form:input path="content"
                            type="textArea" cssClass="form-control"
                            name="content"/>
            </div>
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    <div class="form-group alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                            ${errorMessage}
                    </div>
                </c:when>
            </c:choose>
            <button type="submit" class="btn btn-default pull-right">Add</button>
        </form:form>
    </div>


    <c:choose>
        <c:when test="${not empty list}">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                    <tr>
                        <th>PartnerId</th>
                        <th>StartTime</th>
                        <th>Duration</th>
                        <th>Content</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${list}">
                        <tr>
                            <td>${item.partnerId}</td>
                            <td><fmt:formatDate
                                    type="both"
                                    value="${item.startTime}"/></td>
                            <td>${item.duration}</td>
                            <td>${item.content}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
    </c:choose>
</div>
</body>
</html>
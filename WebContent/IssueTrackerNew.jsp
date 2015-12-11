<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Issue Tracker New</title>
<style>
table,th,td {
    border: 1px solid black;
}
</style>


</head>
<body>

<h1 align="center"> Accessibility Ticket Tracker</h1>

	<c:if test="${requestScope.error ne null}">
        <strong style="color: red;">
        <c:out value="${requestScope.error}"></c:out></strong>
     </c:if>

	<c:if test="${requestScope.success ne null}">
        <strong style="color: green;">
        <c:out value="${requestScope.success}"></c:out></strong>
     </c:if>

	<c:url value="/AddIssueServlet" var="addURL"></c:url>
	<c:url value="/EditIssueServlet" var="editURL"></c:url>
	
	<!-- Edit Issue Logic  -->
	
	<c:if test="${requestScope.issue ne null}">
        <form action='<c:out value="${editURL}"></c:out>' method="post">
            Issue ID : <input type="text" value="${requestScope.issue.id}"
                readonly="readonly" name="id" size="35"><br> 
            Description : <input type="text" value="${requestScope.issue.description}" name="description" size="50"><br>
            Status : 
            		<select name="status" >
                   <option value="" disabled selected>${requestScope.issue.status}</option> 
					<option value="OPEN">OPEN</option>
					<option value="IN-PROGRESS">IN-PROGRESS</option>
					<option value="RESOLVED">RESOLVED</option>
					</select>
            <br> 
            Severity : 
            		<select name="severity" >
                   <option value="" disabled selected>${requestScope.issue.severity}</option> 
					<option value="HIGH">HIGH</option>
					<option value="MEDIUM">MEDIUM</option>
					<option value="LOW">LOW</option>
					</select>
            
                <input type="submit" value="Edit Ticket">
        </form>
    </c:if>
	
	<!-- Add Issue Logic -->
	<c:if test="${requestScope.issue eq null }">
		<form action='<c:out value="${addURL}"></c:out>' method = "post">
			Description: <input type="text" name="description" size="50">
			<br> 
			Status: <select name="status" >
					<option value="OPEN" selected>OPEN</option>
					<option value="IN-PROGRESS" disabled>IN-PROGRESS</option>
					<option value="RESOLVED" disabled>RESOLVED</option>
					</select>
			Severity : 
            		<select name="severity" >
                   <option value="HIGH" selected>HIGH</option> 
					<option value="MEDIUM" >MEDIUM</option>
					<option value="LOW" >LOW</option>
					</select>	
			<br> 
			<input type="submit"value="Add a new Ticket">	
		</form>
	</c:if>
	
	<!-- Display Table Logic -->
	
	<c:if test="${not empty requestScope.Issues}">
        <table>
            <tbody>
                <tr>
                    <th>ID</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Severity</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${requestScope.Issues}" var="issue">
                    <c:url value="/EditIssueServlet" var="editURL">
                        <c:param name="id" value="${issue.id}"></c:param>
                    </c:url>
                    <c:url value="/DeleteIssueServlet" var="deleteURL">
                        <c:param name="id" value="${issue.id}"></c:param>
                    </c:url>
                    <tr>
                        <td><c:out value="${issue.id}"></c:out></td>
                        <td><c:out value="${issue.description}"></c:out></td>
                        <td><c:out value="${issue.status}"></c:out></td>
                        <td><c:out value="${issue.severity}"></c:out></td>
                        <td><a
                            href='<c:out value="${editURL}" escapeXml="true"></c:out>'>Edit</a></td>
                        <td><a
                            href='<c:out value="${deleteURL}" escapeXml="true"></c:out>'>Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
	


</body>
</html>
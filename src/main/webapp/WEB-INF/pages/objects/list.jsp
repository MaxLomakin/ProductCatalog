<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<body>

	<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>All Objects</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Name</th>
					<th>Object Type</th>
					<th>Description</th>
				</tr>
			</thead>

			<c:forEach var="object" items="${objects}">
				<tr>
					<td>
						${object.objectId}
					</td>
					<td>${object.name}</td>
					<td>${object.objectType.name}</td>
					<td>${object.description}</td>
					<td>
						<spring:url value="/objects/${object.objectId}" var="objectUrl" />
						<spring:url value="/objects/${object.objectId}/delete" var="deleteUrl" />
						<spring:url value="/objects/${object.objectId}/update" var="updateUrl" />

						<button class="btn btn-info" onclick="location.href='${objectUrl}'">Details</button>
						<button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
						<button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
						<button class="btn" onclick="location.href='/files/add/${object.objectId}'">Files</button></td>
				</tr>
			</c:forEach>
		</table>

		<button class="btn" onclick="location.href='/objects/add'">Create Objects</button>

	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
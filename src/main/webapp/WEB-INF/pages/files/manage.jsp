<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<body>

	<div class="container">

		<h1>List of Files</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>No.</th>
					<th>File Name</th>
					<th>Type</th>
					<th>Description</th>
					<th width="80"></th>
					<th width="80"></th>
				</tr>
			</thead>
		    		<tbody>
					<c:forEach items="${files}" var="file" varStatus="counter">
						<tr>
							<td>${counter.index + 1}</td>
							<td>${file.name}</td>
							<td>${file.type}</td>
							<td>${file.description}</td>
							<td>
								<a href="<c:url value='/files/download/${object.objectId}/${file.fileId}' />" class="btn btn-success custom-width">download</a>
							</td>
							<td>
								<a href="<c:url value='/files/delete/${object.objectId}/${file.fileId}' />" class="btn btn-danger custom-width">delete</a>
							</td>

						</tr>
					</c:forEach>
		    		</tbody>
		    	</table>
		    <%--</div>--%>
		<%--</div>--%>
		<%--<div class="panel panel-default">--%>
			
			<div class="panel-heading"><span class="lead">Upload New Document</span></div>
			<div class="uploadcontainer">
				<form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">
			
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="file">Upload a document</label>
							<div class="col-md-7">
								<form:input type="file" path="file" id="file" class="form-control input-sm"/>
								<div class="has-error">
									<form:errors path="file" class="help-inline"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="file">Description</label>
							<div class="col-md-7">
								<form:input type="text" path="description" id="description" class="form-control input-sm"/>
							</div>
							
						</div>
					</div>
			
					<div class="row">
						<div class="form-actions floatRight">
							<input type="submit" value="Upload" class="btn btn-primary btn-sm">
						</div>
					</div>
	
				</form:form>
				</div>
		<%--</div>--%>
   	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
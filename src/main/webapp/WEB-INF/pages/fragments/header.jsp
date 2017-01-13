<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/users" var="users" />
<spring:url value="/objects" var="objects" />
<spring:url value="/products" var="products" />
<spring:url value="/category" var="categories" />
<spring:url value="/uploads" var="uploads" />
<spring:url value="/admin" var="admin" />

<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlHome}">Product Catalog</a>
		</div>
		<div id="collapse navbar-collapse js-navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a id="drop1" href="#" class="dropdown-toggle" data-toggle="dropdown">
						Navigation
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${users}">Users</a></li>
						<li><a href="${objects}">Objects</a></li>
						<li><a href="${products}">Products</a></li>
						<li><a href="${categories}">Categories</a></li>
						<li><a href="${uploads}">Uploads</a></li>
						<li><a href="${admin}">Admin Page</a></li>
					</ul>
				</li>
				<li>
					<c:if test="${not empty _user}">
						<a href="/users/${_user.id}">${_user.name}</a>
					</c:if>
					<c:if test="${empty _user}">
						<a href="#">guest</a>
					</c:if>
				</li>
				<li>
					<c:if test="${not empty _user}">
						<a href="<c:url value="/logout" />">Logout</a>
					</c:if>
					<c:if test="${empty _user}">
						<a href="<c:url value="/login" />">Login</a>
					</c:if>
				</li>
			</ul>
		</div>
	</div>
</nav>
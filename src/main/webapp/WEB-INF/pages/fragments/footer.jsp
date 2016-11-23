<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container footer">
	<hr>
	<footer>
		<p>&copy; Max Lomakin 2016</p>
	</footer>
</div>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<spring:url value="/resources/core/js/hello.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js"
	var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>



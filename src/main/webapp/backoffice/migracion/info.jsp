<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

	<h1>Info Migración</h1>
	
	<hr>
	
	<p>Leidas: <strong>${leidas}</strong> lineas leidas</p>
	
	<p>Correctas: <strong>${correctas}</strong> lineas insertadas correctamente en la BBDD</p>
	
	<p>Erroneas: <strong>${erroneas}</strong> lineas erroneas al insertar en la BBDD</p>
	
	<p>Tiempo de ejecución: <strong>${minutos}</strong> minutos <strong>${segundos}</strong> segundos</p>
	
	<p>Tiempo de ejecución (en milisegundos): <strong>${milisegundos}</strong> milisegundos
	
	<c:forEach items="${errores}" var="e">
		<p>${e}</p>
	</c:forEach>

<%@include file="../../includes/footer.jsp"%>
<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

	<h1>Info Migraci�n</h1>
	
	<hr>
	
	<p>Leidas: <strong>${leidas}</strong> lineas leidas</p>
	
	<p>Correctas: <strong>${correctas}</strong> lineas insertadas correctamente en la BBDD</p>
	
	<p>Erroneas: <strong>${erroneas}</strong> lineas erroneas al insertar en la BBDD</p>
	
	<p>Tiempo de ejecuci�n: <strong>${minutos}</strong> minutos <strong>${segundos}</strong> segundos</p>
	
	<p>Tiempo de ejecuci�n (en milisegundos): <strong>${milisegundos}</strong> milisegundos
	
	<c:forEach items="${errores}" var="e">
		<p>${e}</p>
	</c:forEach>

<%@include file="../../includes/footer.jsp"%>
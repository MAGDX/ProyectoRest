<%@page import="com.ipartek.formacion.controller.backoffice.UsuarioController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

	<h1>Listado Usuarios</h1>
	
	<hr>
	
	<%@include file="../../includes/mensaje.jsp"%>
	
	<a href="backoffice/usuario?op=<%=UsuarioController.OP_NUEVO%>" class="mb-3 btn btn-outline-primary">Nuevo Usuario</a>
	
	<form action="backoffice/usuario" class="form-inline float-right">
		<input type="hidden" name="op" value="<%=UsuarioController.OP_BUSCAR%>">
		<input type="search" class="form-control mr-2" name="nombreBuscar" placeholder="Buscar por nombre" required>
		<button class="btn btn-outline-primary"><i class="fas fa-search"></i></button>
	</form>
	
	<ul class="list-group">
	  <c:forEach items="${usuarios}" var="u">	
	  	<li class="list-group-item">
	  		<a href="backoffice/usuario?op=<%=UsuarioController.OP_DETALLE%>&id=${u.id}">	  			
	  			<p class="h3">${u.id} ${u.nombre}</p>
	  		</a>
	  	</li>
	  </c:forEach>	  	  
	</ul>
	
<%@include file="../../includes/footer.jsp"%>
<%@page import="com.ipartek.formacion.controller.backoffice.RolController"%>
<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

	<h1>Listado Roles</h1>
	
	<hr>	
	
	<%@include file="../../includes/mensaje.jsp"%>
			
	
	<a href="backoffice/rol?op=<%=RolController.OP_NUEVO%>" class="mb-3 btn btn-outline-primary">Nuevo Rol</a>
	
	
	<form action="backoffice/rol" class="form-inline float-right">
		<input type="hidden" name="op" value="<%=UsuarioController.OP_BUSCAR%>">
		<input type="search" class="form-control mr-2" name="nombreBuscar" placeholder="Buscar por nombre" required>
		<button class="btn btn-outline-primary"><i class="fas fa-search"></i></button>
	</form>
	
	
	<ul class="list-group">
	  <c:forEach items="${roles}" var="r">	
	  	<li class="list-group-item">
	  		<a href="backoffice/rol?op=<%=RolController.OP_DETALLE%>&id=${r.id}">	  			
	  			<p class="h3">${r.id} ${r.nombre}</p>
	  		</a>
	  	</li>
	  </c:forEach>	  	  
	</ul>
	
<%@include file="../../includes/footer.jsp"%>
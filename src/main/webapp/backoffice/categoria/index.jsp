<%@page import="com.ipartek.formacion.controller.backoffice.CategoriaController"%>
<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

	<h1>Listado Categorias</h1>
	
	<hr>
	
	<%@include file="../../includes/mensaje.jsp"%>
			
	
	<a href="backoffice/categoria?op=<%=CategoriaController.OP_NUEVO%>" class="mb-3 btn btn-outline-primary">Nuevo Categoria</a>
	
	
	<form action="backoffice/categoria" class="form-inline float-right">
		<input type="hidden" name="op" value="<%=CategoriaController.OP_BUSCAR%>">
		<input type="search" class="form-control mr-2" name="nombreBuscar" placeholder="Buscar por nombre" required>
		<button class="btn btn-outline-primary"><i class="fas fa-search"></i></button>
	</form>
	
	
	<ul class="list-group">
	  <c:forEach items="${categorias}" var="c">	
	  	<li class="list-group-item">
	  		<a href="backoffice/categoria?op=<%=CategoriaController.OP_DETALLE%>&id=${c.id}">
	  			<p class="h3">${c.id} ${c.nombre}</p>
	  		</a>
	  	</li>
	  </c:forEach>	  	  
	</ul>
	
<%@include file="../../includes/footer.jsp"%>
<%@page	import="com.ipartek.formacion.controller.backoffice.UsuarioController"%>
<%@page	import="com.ipartek.formacion.controller.backoffice.VideoController"%>
<%@page import="com.ipartek.formacion.controller.InicioController"%>

<%@include file="includes/header.jsp"%>
<%@include file="includes/navbar.jsp"%>
<%@include file="includes/mensaje.jsp"%>

	<h1>Ranking Videos Más Populares</h1>
	
	
	<hr>
	${nombreBuscar}
	<form action="inicio" class="form-inline float-right">
		<input type="hidden" name="op" value="<%=InicioController.OP_BUSCAR%>">
		<input type="search" class="form-control mr-2" name="nombreBuscar" placeholder="Buscar por nombre video o categoria" required>
		<button class="btn btn-outline-primary">
			<i class="fas fa-search"></i>
		</button>
	</form>
	
	<div class="row">
		<div class="col">
			<ul class="list-group">
				<c:forEach items="${videosLikes}" var="v">
					<li class="list-group-item">
				  		<a href="inicio?op=<%=InicioController.OP_DETALLE%>&id=${v.id}">
				  			<img class="float-left mr-3" src="https://img.youtube.com/vi/${v.codigo}/default.jpg" alt="Imagen destacda del video ${v.nombre}"/>
				  			<p class="h3 mb-4">${v.nombre}</p>
				  		</a>
				  		<div class="d-inline mb-1">
					  		<a href="inicio?op=<%=InicioController.OP_DETALLE%>&uid=${v.usuario.id}">
					  			<span><i class="fas fa-user mr-1"></i>${v.usuario.nombre}</span>
					  		</a>
					  		<span class="ml-3 mr-3"><i class="fas fa-tag mr-1"></i>${v.categoria.nombre}</span>
					  		<span class="mr-3"><i class="fas fa-heart mr-1 text-danger"></i>${v.likes}</span>
				  		</div>
				  	</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
<%@include file="includes/footer.jsp"%>
<%@page import="com.ipartek.formacion.controller.InicioController"%>
<%@include file="includes/header.jsp"%>
<%@include file="includes/navbar.jsp"%>

	<h1>${video.nombre}</h1>
	
	<hr>
	
	<%@include file="includes/mensaje.jsp"%>
	
	<form class="form-inline d-flex justify-content-between mt-3 mb-2" action="inicio" method="post">
		<label class="mr-2" for="categoria_id"><i class="fas fa-tag mr-1"></i>Categoría:</label>
		<c:forEach items="${categorias}" var="c">
			<c:if test="${c.id == video.categoria.id}">
				<input type="text" class="form-control mb-2 mr-5"
					id="categoria_id" placeholder="Categoria Video"
					value="${c.nombre}">
			</c:if>
		</c:forEach>
		
		<label class="mr-2" for="usuario_id"><i class="fas fa-user mr-1"></i>Usuario:</label>
		<c:forEach items="${usuarios}" var="u">
			<c:if test="${u.id == video.usuario.id}">
				<input type="text" class="form-control mb-2 mr-5"
					id="usuario_id" placeholder="Usuario Video"
					value="${u.nombre}">
			</c:if>
		</c:forEach>
		<a href="inicio">
			<span class="d-right"><i class="fas fa-heart mr-1 text-danger"></i>${video.likes}</span>
		</a>
	</form><!-- End Form -->
	
	<div class="embed-responsive embed-responsive-16by9 mb-1">
		<iframe class="embed-responsive-item"
			src="https://www.youtube.com/embed/${video.codigo}" frameborder="0"
			allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
			allowfullscreen></iframe>
	</div>
	
<%@include file="includes/footer.jsp"%>
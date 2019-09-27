<%@page import="com.ipartek.formacion.controller.backoffice.UsuarioController"%>
<%@page import="com.ipartek.formacion.controller.backoffice.VideoController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

	<h1>Listado Videos</h1>
	
	<hr>
		
	<%@include file="../../includes/mensaje.jsp"%>
			
	
	<a href="backoffice/videos?op=<%=VideoController.OP_NUEVO%>" class="mb-3 btn btn-outline-primary">Nuevo Video</a>
	
	
	<div class="row">
		<div class="col">
			<h3>Videos Publicados</h3>
			<ul class="list-group">
	  			<c:forEach items="${videosVisibles}" var="v" >	  
  				  	<li class="list-group-item">
				  		<a href="backoffice/videos?op=<%=VideoController.OP_DETALLE%>&id=${v.id}">
				  			<img class="float-left mr-3" src="https://img.youtube.com/vi/${v.codigo}/default.jpg" alt="Imagen destacda del video ${v.nombre}"/>
				  			<p class="h3 mb-4">${v.nombre}</p>
				  		</a>
				  		<div class="d-inline mb-1">
					  		<a href="backoffice/usuario?op=<%=UsuarioController.OP_DETALLE%>&id=${v.usuario.id}">	
					  			<span class="mr-3"><i class="fas fa-user mr-1"></i>${v.usuario.nombre}</span>
					  		</a>
					  		<span class="mr-3"><i class="fas fa-tag mr-1"></i>${v.categoria.nombre}</span>
					  		<span class="mr-3"><i class="fas fa-heart mr-1 text-danger"></i>${v.likes}</span>
				  		</div>
				  	</li>
				</c:forEach>
			</ul>		
		</div>
		
		<div class="col">
			<h3>Videos Ocultos</h3>
			<ul class="list-group">
	  			<c:forEach items="${videosNoVisibles}" var="v" >	  
  				  	<li class="list-group-item">
				  		<a href="backoffice/videos?op=<%=VideoController.OP_DETALLE%>&id=${v.id}">
				  			<img class="float-left mr-3" src="https://img.youtube.com/vi/${v.codigo}/default.jpg" alt="Imagen destacda del video ${v.nombre}"/>
				  			<p class="h3 mb-4">${v.nombre}</p>
				  		</a>
				  		<div class="d-inline mb-1">
					  		<a href="backoffice/usuario?op=<%=UsuarioController.OP_DETALLE%>&id=${v.usuario.id}">	
					  			<span class="mr-3"><i class="fas fa-user mr-1"></i>${v.usuario.nombre}</span>
					  		</a>				  			
					  		<span class="mr-3"><i class="fas fa-tag mr-1"></i>${v.categoria.nombre}</span>	
					  		<span class="mr-3"><i class="fas fa-heart mr-1 text-danger"></i>${v.likes}</span>
					  	</div>
				  	</li>					
				</c:forEach>
			</ul>		
		</div>
	</div>
	
<%@include file="../../includes/footer.jsp"%>
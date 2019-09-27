<li class="nav-item">
	<a class="nav-link" href="backoffice/inicio">
		<i class="fas fa-user mr-1"></i>${sessionScope.usuario.nombre}
	</a>
</li>
<li class="nav-item">
	<a class="nav-link" href="backoffice/videos">
		<fmt:message key="menu.videos" />
	</a>
</li>
<li class="nav-item">
	<a class="nav-link"
	href="backoffice/categoria">
		<fmt:message key="menu.categorias" />
	</a>
</li>
<li class="nav-item">
	<a class="nav-link" href="backoffice/rol">
		<fmt:message key="menu.roles" />
	</a>
</li>
<li class="nav-item">
	<a class="nav-link" href="logout">
		<i class="fas fa-sign-out-alt"></i><fmt:message key="menu.salir" />
	</a>
</li>
<li class="nav-item">
	<div class="dropdown">
		<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<fmt:message key="menu.usuarios" />
		</button>
		<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
			<a class="dropdown-item" href="backoffice/usuario?op=<%=UsuarioController.OP_LISTAR%>&visible=true">
				<fmt:message key="menu.usuariosactivos" />
			</a>
			<a class="dropdown-item" href="backoffice/usuario?op=<%=UsuarioController.OP_LISTAR%>&visible=false">
				<fmt:message key="menu.usuarioseliminados" />
			</a>
		</div>
	</div>
</li>
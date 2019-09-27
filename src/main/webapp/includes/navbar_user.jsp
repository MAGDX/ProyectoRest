<li class="nav-item">
	<a class="nav-link" href="frontoffice/index.jsp">
		<i class="fas fa-user mr-1"></i>${sessionScope.usuario.nombre}
	</a>
</li>
<li class="nav-item">
	<a class="nav-link" href="frontoffice">
		<fmt:message key="menu.videos" />
	</a>
</li>
<li class="nav-item">
	<a class="nav-link" href="logout">
		<i class="fas fa-sign-out-alt"></i><fmt:message key="menu.salir" />
	</a>
</li>
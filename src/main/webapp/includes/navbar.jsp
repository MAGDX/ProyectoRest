<%@page import="com.ipartek.formacion.controller.backoffice.VideoController"%>
<%@page import="com.ipartek.formacion.controller.backoffice.UsuarioController"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="#">JEE</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
				<a class="nav-link" href="inicio">
					<fmt:message key="menu.inicio" />
				</a>
			</li>
			<c:if test="${usuario == null}">
				<li class="nav-item">
					<a class="nav-link" href="login.jsp">Login</a>
				</li>
			</c:if>

			<c:if test="${usuario != null}">
				<c:if test="${usuario.rol.id == 1}">
					<%@include file="navbar_admin.jsp"%>
				</c:if>
				<c:if test="${usuario.rol.id == 2}">
					<%@include file="navbar_user.jsp"%>
				</c:if>
			</c:if>
			<li class="nav-item">
				<div class="dropdown">
					<button class="btn btn-secondary dropdown-toggle" type="button"
							id="dropdownMenuButton" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
						<fmt:message key="menu.videosvistos" />
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<c:forEach items="${videosVistos}" var="vv">
							<a class="dropdown-item"
								href="backoffice/videos?op=<%=VideoController.OP_DETALLE%>&id=${vv.value.id}">(${vv.key})
								${vv.value.nombre}</a>
						</c:forEach>
					</div>
				</div>
			</li>
		</ul><!-- End Lista Navbar -->
		<div class="navbar-nav flex-row mr-3">
			<a class="mr-1" href="i18n?idiomaSeleccionado=en_EN">
				<img src="resources/img/british.png" alt="" class="${sessionScope.idiomaSeleccionado != 'en_EN' ? 'inactive': ''  }">
			</a>
			<a class="mr-1" href="i18n?idiomaSeleccionado=eu_ES">
				<img src="resources/img/euskadi.png" alt="" class="${sessionScope.idiomaSeleccionado != 'eu_ES' ? 'inactive': ''  }">
			</a>
			<a class="mr-1" href="i18n?idiomaSeleccionado=es_ES">
				<img src="resources/img/Spain.png" alt="" class="${sessionScope.idiomaSeleccionado != 'es_ES' ? 'inactive': ''  }">
			</a>
		</div>
	</div><!-- End collapse navbar-collapse -->
</nav><!-- End navar -->

<!-- <nav class="bg-dark">
	<a href="i18n?idiomaSeleccionado=en_EN">
		<img src="resources/img/british.png" alt="" class="${sessionScope.idiomaSeleccionado != 'en_EN' ? 'inactive': ''  }">
	</a>
	<a href="i18n?idiomaSeleccionado=eu_ES">
		<img src="resources/img/euskadi.png" alt="" class="${sessionScope.idiomaSeleccionado != 'eu_ES' ? 'inactive': ''  }">
	</a>
	<a href="i18n?idiomaSeleccionado=es_ES">
		<img src="resources/img/Spain.png" alt="" class="${sessionScope.idiomaSeleccionado != 'es_ES' ? 'inactive': ''  }">
	</a>
</nav> -->


<main class="container">
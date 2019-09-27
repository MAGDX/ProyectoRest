package com.ipartek.formacion.controller.frontoffice;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;
import javax.validation.Validator;

import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.dao.VideoDAO;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet implementation class FrontofficeController
 */
@WebServlet("/frontoffice/")
public class FrontofficeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "index.jsp";
	public static final String VIEW_FORM_USER = "formulario.jsp";
	public static final String VIEW_INDEX_VIDEOS = "youtube/index.jsp";
	public static final String VIEW_FORM_VIDEO = "youtube/formulario.jsp";
	public static String view = VIEW_INDEX;

	public static final String OP_LISTAR = "0";
	public static final String OP_GUARDAR = "1";
	public static final String OP_BUSCAR = "2";
	public static final String OP_NUEVO = "3";
	public static final String OP_ELIMINAR = "4";
	public static final String OP_DETALLE = "5";

	private static final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	private static final VideoDAO videoDAO = VideoDAO.getInstance();

	private Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if (op == null) {
			op = OP_LISTAR;
		}

		switch (op) {
		case OP_DETALLE:
			detalle(request, response);
			break;

		case OP_BUSCAR:
			buscar(request, response);
			break;

		case OP_GUARDAR:
			guardar(request, response);
			break;

		case OP_ELIMINAR:
			eliminar(request, response);
			break;

		case OP_NUEVO:
			nuevo(request, response);
			break;

		default:
			listarVideos(request, response);
			break;
		}

		request.getRequestDispatcher(view).forward(request, response);
	}

	private void listarVideos(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sesion = request.getSession();

		Usuario u = (Usuario) sesion.getAttribute("usuario");

		request.setAttribute("videos", videoDAO.getVideosByUsuario(u.getId()));

		view = VIEW_INDEX_VIDEOS;
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		// ir al form para crear nuevo video
		view = VIEW_FORM_VIDEO;
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		// eliminar un video o eliminar usuario (si eliminamos usuario, destruir sesion)
		view = VIEW_FORM_VIDEO;
		// view = enviar usuario fuera de la app;
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		// para modificar o crear video
		view = VIEW_INDEX_VIDEOS;
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) {
		// buscar video
		view = VIEW_INDEX_VIDEOS;
	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		// mostrar detalle de un video o del propio usuario
		view = VIEW_FORM_VIDEO;
		view = VIEW_FORM_USER;
	}
}
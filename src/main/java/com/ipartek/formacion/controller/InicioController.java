package com.ipartek.formacion.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.model.dao.CategoriaDAO;
import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.dao.VideoDAO;
import com.ipartek.formacion.model.pojo.Video;

/**
 * Servlet implementation class LikesController
 */
@WebServlet("/inicio")
public class InicioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "index.jsp";
	public static final String VIEW_FORM_VIDEO = "formulario.jsp";
	public static final String VIEW_FORM_USUARIO = "formulario_usuario.jsp";
	public static final String OP_LISTAR = "0";
	public static final String OP_DETALLE = "1";
	public static final String OP_BUSCAR = "2";
	public static final String OP_GUARDAR = "3";
	public static final String OP_ELIMINAR = "4";
	public static String view;

	private static VideoDAO videoDAO;
	private static UsuarioDAO usuarioDAO;
	private static CategoriaDAO categoriaDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		videoDAO = VideoDAO.getInstance();
		usuarioDAO = UsuarioDAO.getInstance();
		categoriaDAO = CategoriaDAO.getInstance();
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

		default:
			listar(request, response);
			break;
		}

		request.getRequestDispatcher(view).forward(request, response);
	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");

		int id = Integer.parseInt(sid);

		Video v = videoDAO.getById(id);
		request.setAttribute("video", v);

		request.setAttribute("usuarios", usuarioDAO.getAll());
		request.setAttribute("categorias", categoriaDAO.getAll());

		view = VIEW_FORM_VIDEO;

		HttpSession session = request.getSession();
		HashMap<Integer, Video> videosVistos = (HashMap<Integer, Video>) session.getAttribute("videosVistos");
		if (videosVistos == null) {
			videosVistos = new HashMap<Integer, Video>();
		}
		videosVistos.put(v.getId(), v);
		session.setAttribute("videosVistos", videosVistos);
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("videosLikes", videoDAO.getAllLikes());

		view = VIEW_INDEX;
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nombreBuscar = request.getParameter("nombreBuscar");
		request.setAttribute("videosLikes", videoDAO.getAllByNombre(nombreBuscar));
		
		view = VIEW_INDEX;
	}
}
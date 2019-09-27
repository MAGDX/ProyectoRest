package com.ipartek.formacion.controller.backoffice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.controller.ControladorCrud;
import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.model.dao.RolDAO;
import com.ipartek.formacion.model.pojo.Rol;

/**
 * Servlet implementation class RolController
 */
@WebServlet("/backoffice/rol")
public class RolController extends ControladorCrud {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "rol/index.jsp";
	public static final String VIEW_FORM = "rol/formulario.jsp";

	private static final RolDAO rolDAO = RolDAO.getInstance();

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

	@Override
	protected void listar(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("roles", rolDAO.getAll());

		view = VIEW_INDEX;
	}

	@Override
	protected void buscar(HttpServletRequest request, HttpServletResponse response) {
		String nombreBuscar = request.getParameter("nombreBuscar");

		request.setAttribute("roles", rolDAO.getByName(nombreBuscar));
		view = VIEW_INDEX;
	}

	@Override
	protected void guardar(HttpServletRequest request, HttpServletResponse response) {

		String nombre = request.getParameter("nombre");
		int id = Integer.parseInt(request.getParameter("id"));

		Rol r = new Rol();
		r.setId(id);
		r.setNombre(nombre);

		try {

			if (r.getId() == -1) {
				rolDAO.crear(r);
			} else {
				rolDAO.modificar(r);
			}
			request.setAttribute("mensaje", new Alert("success", "Rol creado con exito!"));

		} catch (Exception e) {

			request.setAttribute("mensaje", new Alert("danger", "El Rol <b>" + nombre + "</b> ya existe!"));
		}

		request.setAttribute("rol", r);

		view = VIEW_FORM;
	}

	@Override
	protected void detalle(HttpServletRequest request, HttpServletResponse response) {
		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		Rol r = rolDAO.getById(id);
		request.setAttribute("rol", r);

		view = VIEW_FORM;
	}

	@Override
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) {
		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		if (rolDAO.delete(id)) {
			request.setAttribute("mensaje", new Alert("success", "Registro Eliminado"));
		} else {
			request.setAttribute("mensaje", new Alert("danger", "ERROR, no se pudo eliminar"));
		}

		listar(request, response);
	}

	@Override
	protected void nuevo(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("rol", new Rol());

		view = VIEW_FORM;
	}
}
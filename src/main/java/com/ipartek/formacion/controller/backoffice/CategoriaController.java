package com.ipartek.formacion.controller.backoffice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.ipartek.formacion.controller.ControladorCrud;
import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.model.dao.CategoriaDAO;
import com.ipartek.formacion.model.pojo.Categoria;
import com.ipartek.formacion.model.pojo.Usuario;
import com.ipartek.formacion.model.pojo.Video;

/**
 * Servlet implementation class CategoriaController
 */
@WebServlet("/backoffice/categoria")
public class CategoriaController extends ControladorCrud {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "categoria/index.jsp";
	public static final String VIEW_FORM = "categoria/formulario.jsp";
	
	private Validator validator;

	private static final CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

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
		
		request.setAttribute("categorias", categoriaDAO.getAll());
		
		view = VIEW_INDEX;
	}

	@Override
	protected void guardar(HttpServletRequest request, HttpServletResponse response) {		
		
		String nombre = request.getParameter("nombre");
		int id = Integer.parseInt(request.getParameter("id"));

		Categoria c = new Categoria();
		c.setId(id);
		c.setNombre(nombre);
		
		try {
			
			if (c.getId() == -1) {
				categoriaDAO.crear(c);
			} else {
				categoriaDAO.modificar(c);
			}
			request.setAttribute("mensaje", new Alert("success", "Registro creado con exito"));

		} catch (Exception e) {

			request.setAttribute("mensaje", new Alert("danger", "Tenemos un problema, el codigo ya existe"));
		}
		
		request.setAttribute("categoria", c);

		view = VIEW_FORM;
	}

	@Override
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		if (categoriaDAO.delete(id)) {
			request.setAttribute("mensaje", new Alert("success", "Registro Eliminado"));
		} else {
			request.setAttribute("mensaje", new Alert("danger", "ERROR, no se pudo eliminar"));
		}

		listar(request, response);
	}

	@Override
	protected void nuevo(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("categoria", new Categoria());
		view = VIEW_FORM;
	}

	@Override
	protected void detalle(HttpServletRequest request, HttpServletResponse response) {
		
		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);
		
		request.setAttribute("categoria", categoriaDAO.getById(id));
		view = VIEW_FORM;
	}

	@Override
	protected void buscar(HttpServletRequest request, HttpServletResponse response) {
		String nombreBuscar = request.getParameter("nombreBuscar");

		request.setAttribute("categorias", categoriaDAO.getByName(nombreBuscar));
		view = VIEW_INDEX;
	}
}
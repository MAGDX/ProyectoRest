package com.ipartek.formacion.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet implementation class ApiController
 */
@WebServlet({ "/api/usuarios", "/api/usuarios/" })
public class UsuarioRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static UsuarioDAO usuarioDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		usuarioDAO = UsuarioDAO.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Cambiar Contect Type de text/html a application/json y el Character Encoding
		 * a UTF-8
		 */
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// Obtenemos parametro de nombre
		String nombre = request.getParameter("nombre");

		ArrayList<Usuario> lista;

		// Conseguir datos del DAO
		if (nombre == null) {
			lista = usuarioDAO.getAll();
		} else {
			lista = usuarioDAO.getAllByNombre(nombre);
		}

		// Modificar la contraseña de los pojos por asteriscos para no mostrarla
		Iterator<Usuario> it = lista.iterator();

		while (it.hasNext()) {
			it.next().setContrasenya("****");
		}

		// Ordenamos la lista segun la manera indicada por parametro
		String orden = request.getParameter("orden");
		Comparador cASC = new Comparador();

		if (orden != null && orden.equalsIgnoreCase("DESC")) {
			lista.sort(cASC.reversed());
		} else {
			lista.sort(cASC);
		}

		// Convertir POJO en un String de JSON
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(lista);

		// Pintar datos por la salida
		ServletOutputStream out = response.getOutputStream();
		out.print(json);
		out.flush();// si la respuesta es muy grande conviene hacer esto
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
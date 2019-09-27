package com.ipartek.formacion.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.validation.Validator;

public abstract class ControladorCrud extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "";
	public static final String VIEW_FORM = "";
	public static String view = "";

	public static final String OP_LISTAR = "0";
	public static final String OP_GUARDAR = "1";
	public static final String OP_BUSCAR = "2";
	public static final String OP_NUEVO = "3";
	public static final String OP_ELIMINAR = "4";
	public static final String OP_DETALLE = "5";

	private Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
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
			listar(request, response);
			break;
		}

		request.getRequestDispatcher(view).forward(request, response);
	}

	protected abstract void nuevo(HttpServletRequest request, HttpServletResponse response);

	protected abstract void listar(HttpServletRequest request, HttpServletResponse response);

	protected abstract void eliminar(HttpServletRequest request, HttpServletResponse response);

	protected abstract void guardar(HttpServletRequest request, HttpServletResponse response);

	protected abstract void buscar(HttpServletRequest request, HttpServletResponse response);

	protected abstract void detalle(HttpServletRequest request, HttpServletResponse response);
}
package com.ipartek.formacion.controller.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.model.pojo.Rol;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet Filter implementation class FilterFrontoffice
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, urlPatterns = { "/frontoffice/*" })
public class FilterFrontoffice implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		session.setAttribute("callback", req.getRequestURI());
		Usuario u = (Usuario) session.getAttribute("usuario");

		if (u != null) {
			if (u.getRol().getId() == Rol.ROL_USUARIO) {
				// pass the request along the filter chain
				chain.doFilter(request, response);
			} else {
				// response redireccionar a login en caso de que el rol no sea el correcto
				session.setAttribute("mensaje", new Alert("danger", "Usuario incorrecto"));
				res.sendRedirect(req.getContextPath() + "/login.jsp");
			}
		} else {
			// response redireccionar a login en caso de que no este logeado
			session.setAttribute("mensaje", new Alert("danger", "Por favor inicia sesion para poder acceder"));
			res.sendRedirect(req.getContextPath() + "/login.jsp");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

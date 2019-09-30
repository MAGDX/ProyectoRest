package com.ipartek.formacion.controller.backoffice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet implementation class MigracionController
 */
@WebServlet("/backoffice/migracion")
public class MigracionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static Log LOG = LogFactory.getLog(MigracionController.class);

	private UsuarioDAO usuarioDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		BasicConfigurator.configure();
		usuarioDAO = UsuarioDAO.getInstance();
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

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int leidas = 0;
		int correctas = 0;
		int erroneas = 0;
		long inicio = System.currentTimeMillis();
		ArrayList<String> errores = new ArrayList<String>();

		// Leemos el archivo de texto
		try (Connection con = ConnectionManager.getConnection();
				BufferedReader br = new BufferedReader(
						new FileReader("C:\\1713\\eeclipse-workspace\\ProyectoRest\\personas.txt"))) {

			String linea;
			String[] lSpliteada;
			con.setAutoCommit(false);

			// Leemos linea a linea
			while ((linea = br.readLine()) != null) {

				// Leido con exito (Leidas + 1)
				leidas++;
				LOG.debug("Linea " + leidas);

				// Spliteamos la linea
				lSpliteada = linea.split(",");

				if (lSpliteada.length != 7) {

					// Como la longitud tras splitear no es 7, la linea es erronea (Erroneas + 1)
					erroneas++;
					errores.add(linea);
				} else {

					try {

						// Insertamos el usuario en la BBDD. Si el ID devuelto no es -1, la linea es
						// correcta (Correctas + 1)

						/*
						 * String user = Character.toString(lSpliteada[0].charAt(0)) +
						 * Character.toString(lSpliteada[1].charAt(0)) +
						 * Character.toString(lSpliteada[2].charAt(0)) + leidas;
						 */

						String user = lSpliteada[0] + " " + lSpliteada[1] + " " + lSpliteada[2];

						Usuario u = new Usuario(user, lSpliteada[5]);
						usuarioDAO.migrar(u, con);

						if (u.getId() != -1) {
							correctas++;
						} else {
							erroneas++;
							errores.add(linea);
							LOG.warn("Error: " + linea);
						}
					} catch (Exception e) {
						erroneas++;
						errores.add(linea);
						LOG.warn("Error: " + linea);
					} // End Try-Catch
				} // End Else
			} // End While

			con.commit();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // End Try-Catch

		long minutos = ((System.currentTimeMillis() - inicio) / 1000) / 60;
		long segundos = ((System.currentTimeMillis() - inicio) / 1000) % 60;
		long milisegundos = System.currentTimeMillis() - inicio;

		// Enviados datos y pintamos
		request.setAttribute("leidas", leidas);
		request.setAttribute("correctas", correctas);
		request.setAttribute("erroneas", erroneas);
		request.setAttribute("minutos", minutos);
		request.setAttribute("segundos", segundos);
		request.setAttribute("milisegundos", milisegundos);
		request.setAttribute("errores", errores);

		LOG.info("Proceso migración terminado:");
		LOG.info("Leidas: " + leidas);
		LOG.info("Correctas: " + correctas);
		LOG.info("Erroneas: " + erroneas);
		LOG.info("Tiempo de ejecución: " + minutos + " minutos " + segundos + " segundos");
		LOG.info("Tiempo de ejecución (en milisegundos): " + milisegundos + " milisegundos");

		request.getRequestDispatcher("migracion/info.jsp").forward(request, response);
	}
}
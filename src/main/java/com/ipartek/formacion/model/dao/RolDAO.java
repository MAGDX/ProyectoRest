package com.ipartek.formacion.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.pojo.Rol;

public class RolDAO {

	private static RolDAO INSTANCE = null;

	private static String SQL_GETALL = "{CALL rol_getAll()}";
	private static String SQL_CREAR = "{CALL rol_crear(?,?)}";
	private static String SQL_GETBYID = "{CALL rol_getById(?)}";
	private static String SQL_GETBYNAME = "{CALL rol_getByName(?)}";
	private static String SQL_MODIFICAR = "{CALL rol_modificar(?,?)}";
	private static String SQL_DELETE = "{CALL rol_delete(?)}";

	private RolDAO() {
		super();
	}

	public static RolDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RolDAO();
		}
		return INSTANCE;
	}

	public ArrayList<Rol> getAll() {

		ArrayList<Rol> lista = new ArrayList<Rol>();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cst = con.prepareCall(SQL_GETALL);
				ResultSet rs = cst.executeQuery()) {

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	public boolean crear(Rol pojo) throws SQLException {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection(); CallableStatement cst = con.prepareCall(SQL_CREAR)) {

			cst.setString(1, pojo.getNombre());
			cst.registerOutParameter(2, Types.INTEGER);

			cst.executeUpdate();

			pojo.setId(cst.getInt("pId"));
		}

		return resultado;
	}

	public Rol getById(int id) {
		Rol rol = new Rol();

		try (Connection con = ConnectionManager.getConnection(); CallableStatement cst = con.prepareCall(SQL_GETBYID)) {

			// sustituyo la 1º ? por la variable id
			cst.setInt(1, id);

			try (ResultSet rs = cst.executeQuery()) {
				if (rs.next()) {
					rol = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rol;
	}

	public ArrayList<Rol> getByName(String nombre) {
		ArrayList<Rol> lista = new ArrayList<Rol>();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cst = con.prepareCall(SQL_GETBYNAME)) {

			cst.setString(1, nombre);

			try (ResultSet rs = cst.executeQuery()) {

				while (rs.next()) {
					lista.add(mapper(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public boolean modificar(Rol pojo) throws SQLException {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cst = con.prepareCall(SQL_MODIFICAR)) {

			cst.setString(1, pojo.getNombre());
			cst.setInt(2, pojo.getId());

			int affectedRows = cst.executeUpdate();
			if (affectedRows == 1) {
				resultado = true;
			}
		}
		return resultado;
	}

	public boolean delete(int id) {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection(); CallableStatement cst = con.prepareCall(SQL_DELETE)) {

			cst.setInt(1, id);

			int affetedRows = cst.executeUpdate();
			if (affetedRows == 1) {
				resultado = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	private Rol mapper(ResultSet rs) throws SQLException {
		Rol r = new Rol();
		r.setId(rs.getInt("id"));
		r.setNombre(rs.getString("nombre"));
		return r;
	}
}
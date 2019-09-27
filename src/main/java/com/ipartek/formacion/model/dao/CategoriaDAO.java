package com.ipartek.formacion.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.pojo.Categoria;
import com.ipartek.formacion.model.pojo.Rol;
import com.ipartek.formacion.model.pojo.Usuario;

public class CategoriaDAO {

	private static CategoriaDAO INSTANCE = null;

	private static final String SQL_GET_ALL = "{CALL categoria_getall()}";
	private static final String SQL_DELETE = "{CALL categoria_delete(?)}";
	private static final String SQL_INSERT = "{CALL categoria_insert(?,?)}";
	private static final String SQL_UPDATE = "{CALL categoria_update(?,?)}";
	private static final String SQL_GETBY_ID = "{CALL categoria_getbyId(?)}";
	private static String SQL_GETBYNAME = "{CALL categoria_getByName(?)}";

	private CategoriaDAO() {
		super();
	}

	public static synchronized CategoriaDAO getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new CategoriaDAO();
		}

		return INSTANCE;
	}

	public ArrayList<Categoria> getAll() {

		ArrayList<Categoria> lista = new ArrayList<Categoria>();

		try (Connection con = ConnectionManager.getConnection();
				//PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				//ResultSet rs = pst.executeQuery()) {
				CallableStatement cst = con.prepareCall(SQL_GET_ALL);
				ResultSet rs = cst.executeQuery()) {

			while (rs.next()) {
				lista.add(mapper(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	
	
	public boolean delete(int id) {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cst = con.prepareCall(SQL_DELETE);) {
			// 1 es la primera interrogacion y el id el pid
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
	
	
	
	public boolean modificar(Categoria pojo) throws Exception {
		boolean resultado = false;
		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cst = con.prepareCall(SQL_UPDATE)) {

			cst.setString(1, pojo.getNombre());
			cst.setInt(2, pojo.getId());

			int affectedRows = cst.executeUpdate();
			if (affectedRows == 1) {
				resultado = true;
			}

		}
		return resultado;
	}
	
	
	

	
	
	
	public Categoria getById(int id) {
		Categoria resul = new Categoria();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cst = con.prepareCall(SQL_GETBY_ID)) {

			cst.setInt(1, id);

			try (ResultSet rs = cst.executeQuery()) {
				if (rs.next()) {
					resul = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}
	
	public ArrayList<Categoria> getByName(String nombre) {
		ArrayList<Categoria> lista = new ArrayList<Categoria>();

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
	
	public Categoria crear(Categoria pojo) throws Exception {

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cst = con.prepareCall(SQL_INSERT)) {

			
			cst.setString(1, pojo.getNombre());
			cst.registerOutParameter(2, Types.INTEGER );

			cst.executeUpdate();
			
			int idGenerado = cst.getInt(2);	

			pojo.setId(idGenerado);
		}
		return pojo;
	}
	
	
//______________________________________________________________________________________________
	private Categoria mapper(ResultSet rs) throws SQLException {
		Categoria c = new Categoria();
		c.setId(rs.getInt("id"));
		c.setNombre(rs.getString("nombre"));
		return c;
	}
}
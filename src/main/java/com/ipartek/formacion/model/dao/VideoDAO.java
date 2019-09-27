package com.ipartek.formacion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.pojo.Categoria;
import com.ipartek.formacion.model.pojo.Usuario;
import com.ipartek.formacion.model.pojo.Video;
import com.mysql.jdbc.Statement;

public class VideoDAO {

	private static VideoDAO INSTANCE = null;

	private static final String SQL_GET_ALL = "SELECT v.id as 'video_id', v.nombre as 'video_nombre', codigo, u.id as 'usuario_id', u.nombre as 'usuario_nombre', c.id as 'categoria_id', c.nombre as 'categoria_nombre' FROM video as v, usuario as u , categoria as c WHERE v.usuario_id = u.id AND v.categoria_id = c.id ORDER BY v.id DESC LIMIT 500;";
	private static final String SQL_GET_ALL_VISIBLE = "SELECT v.id as 'video_id', v.nombre as 'video_nombre', v.codigo, u.id as 'usuario_id', u.nombre as 'usuario_nombre', c.id as 'categoria_id', c.nombre as 'categoria_nombre', l.likes as likes FROM video as v, usuario as u , categoria as c, v_likes as l WHERE v.usuario_id = u.id AND v.categoria_id = c.id AND v.id = l.video_id AND u.fecha_eliminacion IS NULL ORDER BY v.id ASC LIMIT 500;";
	private static final String SQL_GET_ALL_NO_VISIBLE = "SELECT v.id as 'video_id', v.nombre as 'video_nombre', v.codigo, u.id as 'usuario_id', u.nombre as 'usuario_nombre', c.id as 'categoria_id', c.nombre as 'categoria_nombre' FROM video as v, usuario as u , categoria as c WHERE v.usuario_id = u.id AND v.categoria_id = c.id AND u.fecha_eliminacion IS NOT NULL ORDER BY v.id ASC LIMIT 500;";
	private static final String SQL_GET_BY_ID = "SELECT v.id as 'video_id', v.nombre as 'video_nombre', codigo, u.id as 'usuario_id', u.nombre as 'usuario_nombre', c.id as 'categoria_id',c.nombre as 'categoria_nombre' FROM video as v, usuario as u , categoria as c WHERE v.usuario_id = u.id AND v.categoria_id = c.id AND v.id = ? ORDER BY v.id DESC LIMIT 500;";
	private static final String SQL_UPDATE = "UPDATE video SET `nombre`= ?, `codigo`= ? , `usuario_id`= ? , `categoria_id`= ? WHERE `id` = ?;";
	private static final String SQL_INSERT = "INSERT INTO video (nombre, codigo, usuario_id, categoria_id ) VALUES (?,?,?,?);";
	private static final String SQL_LIKES = "SELECT * FROM v_likes;";
	private static final String SQL_GETVIDEOS = "SELECT u.id usuario_id, u.nombre usuario_nombre, v.id video_id, v.nombre video_nombre, v.codigo, v.categoria_id as categoria_id, c.nombre categoria_nombre, l.likes as likes FROM usuario as u, video as v, categoria as c, v_likes as l WHERE u.id = v.usuario_id AND c.id = v.categoria_id AND v.id = l.video_id AND u.id = ?;";
	private static final String SQL_GET_BY_NAME_VIDEO = "SELECT v.id as 'video_id', v.nombre as 'video_nombre',v.codigo, u.id as 'usuario_id', u.nombre as 'usuario_nombre', c.id as 'categoria_id', c.nombre as 'categoria_nombre', l.likes as likes FROM 	video as v, usuario as u , categoria as c, v_likes as l WHERE 	v.usuario_id = u.id AND v.categoria_id = c.id AND v.id = l.video_id AND u.fecha_eliminacion IS NULL AND v.nombre like ? ORDER BY v.id ASC LIMIT 500;";

	private VideoDAO() {
		super();
	}

	public static synchronized VideoDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VideoDAO();
		}
		return INSTANCE;
	}

	public ArrayList<Video> getAll() {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Listado de videos a visibles o no visibles Visible: Son los videos que el
	 * usuario tenga fecha_eliminacion == NULL
	 * 
	 * No Visible: Son los videos que el usuario tenga fecha_eliminacion <> NULL
	 * 
	 * @param isVisible true visbles, false los no visibles
	 * @return
	 */
	public ArrayList<Video> getAllVisible(boolean isVisible) {

		ArrayList<Video> lista = new ArrayList<Video>();

		String sql = SQL_GET_ALL_VISIBLE;
		if (!isVisible) {
			sql = SQL_GET_ALL_NO_VISIBLE;
		}

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {
			Video v = null;
			while (rs.next()) {
				v = mapper(rs);
				if (isVisible) {
					v.setLikes(rs.getInt("likes"));
				}
				lista.add(v);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	public Video getById(int id) {
		Video video = new Video();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			// sustituyo la 1º ? por la variable id
			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					/*
					 * Video v = new Video(); v.setId( rs.getInt("id") ); v.setNombre(
					 * rs.getString("nombre")); v.setCodigo( rs.getString("codigo"));
					 */
					video = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return video;
	}

	public ArrayList<Video> getVideosByUsuario(int idUsuario) {
		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GETVIDEOS)) {

			pst.setInt(1, idUsuario);

			try (ResultSet rs = pst.executeQuery()) {
				Video v = null;
				while (rs.next()) {
					v = mapper(rs);
					v.setLikes(rs.getInt("likes"));
					lista.add(v);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public boolean modificar(Video pojo, int usuarioId, int categoriaId) throws Exception {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getCodigo());
			pst.setInt(3, usuarioId);
			pst.setInt(4, categoriaId);
			pst.setInt(5, pojo.getId());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resultado = true;
			}

		}
		return resultado;
	}

	public boolean crear(Video pojo, int usuarioId, int categoriaId) throws Exception {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getCodigo());
			pst.setInt(3, usuarioId);
			pst.setInt(4, categoriaId);

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				// conseguir id generado de forma automatica
				try (ResultSet rsKeys = pst.getGeneratedKeys()) {
					if (rsKeys.next()) {
						pojo.setId(rsKeys.getInt(1));
						resultado = true;
					}
				}
			}
		}
		return resultado;
	}

	public boolean delete(int id) {
		boolean resultado = false;
		String sql = "DELETE FROM video WHERE id = ?;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			pst.setInt(1, id);

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				resultado = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	public ArrayList<Video> getAllLikes() {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_LIKES);
				ResultSet rs = pst.executeQuery()) {
			Video video = null;
			while (rs.next()) {
				video = mapper(rs);
				video.setLikes(rs.getInt("likes"));
				lista.add(video);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<Video> getAllByNombre(String nombre) {
		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_NAME_VIDEO);) {

			pst.setString(1, "%" + nombre + "%");

			try (ResultSet rs = pst.executeQuery()) {
				Video video = null;
				while (rs.next()) {
					video = mapper(rs);
					video.setLikes(rs.getInt("likes"));
					lista.add(video);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Convierte un Resultado de la BD a un POJO
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Video mapper(ResultSet rs) throws SQLException {
		Video v = new Video();
		v.setId(rs.getInt("video_id"));
		v.setNombre(rs.getString("video_nombre"));
		v.setCodigo(rs.getString("codigo"));

		Usuario u = new Usuario();
		u.setId(rs.getInt("usuario_id"));
		u.setNombre(rs.getString("usuario_nombre"));

		v.setCategoria(new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nombre")));
		v.setUsuario(u);
		return v;
	}
}
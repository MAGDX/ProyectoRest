package com.ipartek.formacion.controller.api;

import java.util.Comparator;

import com.ipartek.formacion.model.pojo.Usuario;

public class Comparador implements Comparator<Usuario> {

	@Override
	public int compare(Usuario o1, Usuario o2) {

		return (o1.getNombre().compareTo(o2.getNombre()));
	}
}
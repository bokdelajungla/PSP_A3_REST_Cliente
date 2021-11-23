package com.example.demo.entidad;

import org.springframework.stereotype.Component;

@Component
public class Videojuego {
	private int id;
	private String nombre;
	private String company;
	private String nota;
	
	public Videojuego() {
		super();
	}	

	public Videojuego(int id, String nombre, String company, String nota) {
		this.id = id;
		this.nombre = nombre;
		this.company = company;
		this.nota = nota;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	@Override
	public String toString() {
		return "Videojuego [id=" + id + ", nombre=" + nombre + ", company=" + company + ", nota=" + nota + "]";
	}
}

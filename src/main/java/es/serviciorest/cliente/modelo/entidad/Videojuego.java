package es.serviciorest.cliente.modelo.entidad;

import org.springframework.stereotype.Component;

/**
 * Clase que simula un videojuego (datos del mismo)
 * 
 * @author Grupo 16
 * @version 1.0
 *
 */
@Component
public class Videojuego {
	
	// Atributos del videojuego
	private String nombre;
	private int id;
	private String companyia;
	private int nota;
	
	// Constructor por defecto del videojuego
	public Videojuego() {
		super();
	}
	
	// Constructor con parámetros del videojuego
	public Videojuego(int id, String nombre, String companyia, int nota) {
		this.id = id;
		this.nombre = nombre;
		this.companyia = companyia;
		this.nota = nota;
	}
	
	// Getters y Setters del videojuego
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyia() {
		return companyia;
	}
	public void setCompanyia(String companyia) {
		this.companyia = companyia;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	

	@Override
	public String toString() {
		return "Videojuego [nombre=" + nombre + ", id=" + id + ", companyia=" + companyia + ", nota=" + nota + "]";
	}
	
}
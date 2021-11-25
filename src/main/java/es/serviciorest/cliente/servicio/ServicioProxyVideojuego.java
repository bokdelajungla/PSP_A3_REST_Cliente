package es.serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.serviciorest.cliente.modelo.entidad.Videojuego;

@Service
public class ServicioProxyVideojuego {

	// URL del servicio REST
	public static final String URL = "http://localhost:8081/videojuegos/";
		
	@Autowired
	private RestTemplate restTemplate;
	
	// CRUD completo: Create, Read, Update & Delete
	

	// Create
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public Videojuego altaVideojuego(Videojuego v) {
		try {
			ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL, v, Videojuego.class);
			System.out.println("alta -> codigo respuesta:" + re.getStatusCode());
			return re.getBody();
		} catch(HttpClientErrorException e) {
			System.out.println("obtener -> no se ha encontrado el videojuefo con id:" + v.getId());
			System.out.println("obtener -> codigo respuesta:" + e.getStatusCode());
			return null;
		}
	}
	
	// Read
	
	/**
	 *
	 * @param id
	 * @return
	 */
	public Videojuego obtenerVideojuego(int id) {
		try {
			// URL: http://localhost:8081/videojuegos/{id}
			ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
			HttpStatus hs = re.getStatusCode();
			// 200 - OK
			if(hs == HttpStatus.OK) {
				// el getBody() me devuelve el Videojuego
				return re.getBody();
			} else {
				System.out.println("Respuesta no contemplada");
				return null;
			}
		// 404 - NOT FOUND
		} catch(HttpClientErrorException e) {
			System.out.println("obtener -> no se ha encontrado el videojuefo con id:" + id);
			System.out.println("obtener -> codigo respuesta:" + e.getStatusCode());
			return null;
		}
	}
	
	/**
	 *
	 * @return lista de videojuegos
	 */
	public List<Videojuego> listarVideojuegos() {
		try {
			// URL: http://localhost:8081/videojuegos
			// Lo casteamos a un array porque responseEntity trabaja con arrays
			ResponseEntity<Videojuego[]> re = restTemplate.getForEntity(URL, Videojuego[].class);
			Videojuego[] arrayVideojuegos = re.getBody();
			// devolvemos un Arraylist rehaciendo el casting
			return Arrays.asList(arrayVideojuegos);
		} catch(HttpClientErrorException e) {
			System.out.println("listar -> error al obtener la lista de videojuegos");
			System.out.println("listar -> codigo respuesta:" + e.getStatusCode());
			return null;
		}
	}
	
	// Update
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public boolean modificarVideojuego(Videojuego v) {
		try {
			restTemplate.put(URL + v.getId(), v, Videojuego.class);
			return true;
		} catch(HttpClientErrorException e) {
			System.out.println("modificar -> No se ha modificado el videojuefo con id:" + v.getId());
			System.out.println("modificar -> codigo respuesta:" + e.getStatusCode());
			return false;
		}
	}
	
	// Delete
	
	/**
	 * Borra un videojuego pasándole el ID del miso como parámetro
	 * @param id ID del videojuego a borrar
	 * @return true en caso de que se haya borrado, false en caso contrario
	 */
	public boolean borrarVideojuego(int id) {
		try {
			restTemplate.delete(URL + id);
			return true;
		} catch(HttpClientErrorException e) {
			System.out.println("borrar -> No se ha borrado el videojuefo con id:" + id);
			System.out.println("borrar -> codigo respuesta:" + e.getStatusCode());
			return false;
		}
	}
	
	
	
}

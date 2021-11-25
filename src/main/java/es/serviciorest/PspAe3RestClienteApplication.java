package es.serviciorest;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import es.serviciorest.cliente.modelo.entidad.Videojuego;
import es.serviciorest.cliente.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class PspAe3RestClienteApplication implements CommandLineRunner{

	@Autowired
	private ServicioProxyVideojuego spv;
	
	// necesario para parar la aplicacion
	@Autowired
	private ApplicationContext context;
	
	// no podemos dar de alta un objeto que no tengamos el código fuente con @Component o @Service, por eso suamos @Bean
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(PspAe3RestClienteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		boolean salir = false;
		int opc = 0;
		int id, nota;
		String nombre, companyia;
		System.out.println("************** Arrancando el cliente REST **************");
		do {
			System.out.println("*********************** MENU ************************");
			System.out.println("1 - dar de alta");
			System.out.println("2 - dar de baja");
			System.out.println("3 - modificar un videojuego");
			System.out.println("4 - obtener un videojuego");
			System.out.println("5 - listar los videojuegos");
			System.out.println("6 - salir\n");
			System.out.println("Introduzca una opcion:");
			try {
				opc = sc.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Opcion no valida, se vuelve al menu");
			}
			switch(opc) {
				case 1:
					System.out.println("************* Dar de alta un Videojuego **************");
					Videojuego videojuego = new Videojuego();
					sc.nextLine(); // Limpiamos Buffer
					System.out.println("Introduzca el nombre: ");
					nombre = sc.nextLine();
					System.out.println("Introduzca la compañia: ");
					companyia = sc.nextLine();
					System.out.println("Introduzca la nota: ");
					nota = sc.nextInt();
					videojuego.setNombre(nombre);
					videojuego.setCompanyia(companyia);
					videojuego.setNota(nota);
					Videojuego videojuegoAlta = spv.altaVideojuego(videojuego);		
					System.out.println("se ha dado de alta el videojuego:"+ videojuegoAlta.toString());
					break;
				case 2:
					System.out.println("************* Dar de baja un Videojuego **************");
					System.out.println("Introduzca el ID del videojuego a borrar: ");
					id = sc.nextInt();
					boolean borrado = spv.borrarVideojuego(id);
					if(borrado) {
						System.out.println("Se ha borrado el videojuego con id "+ id);
					} else {
						System.out.println("NO se ha podido borrar el videojuego con id "+ id);
					}
					break;
				case 3:
					System.out.println("************** Modificar un Videojuego ***************");
					System.out.println("Introduzca el ID del videojuego a modificar: ");
					id = sc.nextInt();
					sc.nextLine(); // Limpiamos Buffer
					System.out.println("Introduzca el nuevo nombre: ");
					nombre = sc.nextLine();
					System.out.println("Introduzca la nueva compañia: ");
					companyia = sc.nextLine();
					System.out.println("Introduzca la nueva nota: ");
					nota = sc.nextInt();
					boolean modificado = spv.modificarVideojuego(new Videojuego(id, nombre, companyia, nota));
					if(modificado) {
						System.out.println("Se ha modificado el videojuego con id "+ id);
					} else {
						System.out.println("NO se ha podido modificar el videojuego con id "+ id);
					}
					break;
				case 4:
					System.out.println("*************** Obtener un Videojuego ****************");
					System.out.println("Introduzca el ID del videojuego: ");
					id = sc.nextInt();
					System.out.println(spv.obtenerVideojuego(id).toString());
					break;
				case 5:
					System.out.println("*************** Listando Videojuegos ****************");
					List<Videojuego> listaVideojuegos = spv.listarVideojuegos();
					for (Videojuego v : listaVideojuegos)
						System.out.println(v.toString());
				case 6:
					System.out.println("Ha salido de la aplicacion cliente");
					salir = true;
					break;
				default: 
					System.out.println("Opcion no valida, se vuelve al menu");
					break;
			}
		} while(!salir);
		sc.close();
		pararAplicacion();
	}
	
	public void pararAplicacion() {
		// el código de salida (como en java cunado usamos exit(0) 
		// se puede poner simplificado con la funcion lambda () -> 0)
		SpringApplication.exit(context, () -> 0);
	}

}

package com.example.demo;

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

import com.example.demo.entidad.Videojuego;
import com.example.demo.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class Actividad3ClienteApplication implements CommandLineRunner{
	
	@Autowired
	private ServicioProxyVideojuego spv;
	
	@Autowired
	private ApplicationContext context;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Actividad3ClienteApplication.class, args);
	}
	
		@Override
		public void run(String... args) throws Exception {
			System.out.println("****** Arrancando el cliente REST ******");

			Scanner sc = new Scanner(System.in);
			boolean salir = false;
			int opcion = 0;
			String peticion = new String();
			
			System.out.println("Bienvenido a la Biblioteca:");
			while(!salir){
				
				System.out.println("MENU: Escriba el numero con la opción deseada");
				System.out.println("1 - Dar de alta un videojuego");
				System.out.println("2 - Dar de baja un videojuego por ID");
				System.out.println("3 - Modificar un videojuego por ID");
				System.out.println("4 - Obtener un videojuego por ID");
				System.out.println("5 - Listar todos los videojuegos");
				System.out.println("6 - Salir");
				
				try {
					opcion=sc.nextInt();
				}
				catch(InputMismatchException e) {
					opcion = 0; 
					sc.next();
				}
				finally {
					//Comprobamos la opcion seleccionada
					switch(opcion) {
						case 1: 
							System.out.println("*********** ALTA VIDEOJUEGO ***************");
							Videojuego videojuego = new Videojuego();
							System.out.println("Escriba el nombre:");
							String input = sc.next();
							videojuego.setNombre(input);
							System.out.println("Escriba la compañia:");
							input = sc.next();
							videojuego.setCompany(input);
							System.out.println("Escriba la nota:");
							input = sc.next();
							videojuego.setNota(input);
							Videojuego vAlta = spv.alta(videojuego);
							System.out.println("run -> Videojuego dado de alta " + vAlta);
							break;
							
						case 2:
							System.out.println("********** BORRAR VIDEOJUEGO **************");
							System.out.println("Introduzca el ID");
							opcion =sc.nextInt();
							boolean borrado = spv.borrar(opcion);
							System.out.println("run -> Videojuego borrado " + borrado);	
							break;
							
						case 3:
							System.out.println("********* MODIFICAR VIDEOJUEGO *************");	
							Videojuego vModificar = new Videojuego();
							opcion = sc.nextInt();
							vModificar.setId(opcion);
							System.out.println("Escriba el nombre:");
							input = sc.next();
							vModificar.setNombre(input);
							System.out.println("Escriba la compañia:");
							input = sc.next();
							vModificar.setCompany(input);
							System.out.println("Escriba la nota:");
							input = sc.next();
							vModificar.setNota(input);
							boolean modificado = spv.modificar(vModificar);
							System.out.println("run -> Videojuego modificado? " + modificado);
							break;
						
						case 4:
							System.out.println("Introduzca el ID");
							opcion=sc.nextInt();
							System.out.println("************ GET VIDEOJUEGO ***************");
							Videojuego videojuegoAObtener = spv.obtener(opcion);
							System.out.println("run -> Persona con id: " + videojuegoAObtener);
							break;
							
						case 5:
							System.out.println("********** LISTAR VIDEOJUEGO ***************");
							List<Videojuego> listaVideojuegos = spv.listar();
							listaVideojuegos.forEach((v) -> System.out.println(v));
							break;
							
						case 6:
							System.out.println("...Saliendo");
							salir=true;
							pararAplicacion();
							break;
							
						default:
							System.out.println("Opción no válida. Introduzca un número entre 1 y 6");
					}
				}
			}
			sc.close();
		}
		
		public void pararAplicacion() {
			//Esta aplicacion levanta un servidor web, por lo que tenemos que darle 
			//la orden de pararlo cuando acabemos. Para ello usamos el metodo exit, 
			//de la clase SpringApplication, que necesita el contexto de Spring y 
			//un objeto que implemente la interfaz ExitCodeGenerator. 
			//Podemos usar la funcion lambda "() -> 0" para simplificar 
			
			SpringApplication.exit(context, () -> 0);
			
			//Podriamos hacerlo tambien de una manera clasica, es decir, creando
			//la clase anonima a partir de la interfaz. 
			//El codigo de abajo sería equivalente al de arriba
			//(pero mucho más largo)
			/*
			SpringApplication.exit(context, new ExitCodeGenerator() {
				
				@Override
				public int getExitCode() {
					return 0;
				}
			});*/
		}
	}

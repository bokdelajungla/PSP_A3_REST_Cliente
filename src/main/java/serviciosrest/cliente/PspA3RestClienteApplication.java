package serviciosrest.cliente;

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

import serviciosrest.modelo.entidad.Videojuego;
import serviciosrest.cliente.servicio.ServicioProxyVideojuego;

//NOTA IMPORTANTE:
//Si se usa la version 2.6 de Spring hay que añadir la linea
//spring.main.allow-circular-references=true al fichero application.properties para que no
//nos de un error de dependencia circular (en nuestro caso no existe tal bucle de dependencia pero el
//programa insiste en que sí), así que forzamos a que no haga dicha comprobación.

@SpringBootApplication
public class PspA3RestClienteApplication implements CommandLineRunner{

	//Inyectaremos todos los objetos que necesitamos para
	//acceder a nuestro ServicioRest, que en nuestro caso es el ServicioProxyVideojuego
	@Autowired
	private ServicioProxyVideojuego spv;
	
	//Tambien necesitaremos acceder al contexto de Spring para parar
	//la aplicacion, ya que esta app al ser una aplicacion web se
	//lanzará en un tomcat. De esta manera le decimos a Spring que
	//nos inyecte su propio contexto.
	@Autowired
	private ApplicationContext context;
	
	//En este metodo daremos de alta un objeto de tipo RestTemplate que sera
	//el objeto mas importante de esta aplicacion. Sera usado por los 
	//objetos ServicioProxyVideojuego para hacer las peticiones HTTP a nuestro
	//servicio REST. Como no podemos anotar la clase RestTemplate porque
	//no la hemos creado nosotros, usaremos la anotacion @Bean para decirle
	//a Spring que ejecute este metodo y meta el objeto devuelto dentro
	//del contexto de Spring con ID "restTemplate" (el nombre del metodo)
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}	
	
	//Metodo main que lanza la aplicacion
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(PspA3RestClienteApplication.class, args);

		//Notese que como este metodo es estatico no podemos acceder
		//a los metodos dinamicos de la clase, como el "spv"
		//Para solucionar esto, haremos que nuestra clase implemente
		//"CommandLineRunner" e implementaremos el metodo "run"
		//Cuando se acabe de arrancar el contexto, se llamara automaticamente
		//al metodo run
	}
	
	//Este metodo es dinamico por la tanto ya podemos acceder a los atributos
	//dinamicos
	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		boolean salir = false;
		int opcion = 0;
		
		//Bucle principal de funcionamiento
		System.out.println("Bienvenido al Servicio de videojuegos:");
		while(!salir){
			System.out.println("Elija una opción:");
			System.out.println("1 - Dar de alta un videojuego");
			System.out.println("2 - Dar de baja un videojuego por ID");
			System.out.println("3 - Modificar un videojuego por ID");
			System.out.println("4 - Obtener un videojuego por ID");
			System.out.println("5 - Listar todos los videojuegos");
			System.out.println("6 - Salir");
			
			//Try-Catch para el Scanner
			try {
				opcion=sc.nextInt();
			}
			catch(InputMismatchException e) {
				//Tratamos el error
				opcion = 0; //Inicializamos la opción
			}
			finally {
				sc.nextLine();//Limpiamos el buffer
				//Variables para almacenar los datos introducidos
				int id =-1;
				String[] datos = new String[4];
				Videojuego v = new Videojuego();
				//Comprobamos la opcion seleccionada
				switch(opcion) {
					case 1: //Dar de ALTA un videojuego
						//Obtenemos los datos del usuario
						
						System.out.println("Introduzca los datos del Videojuego:");
						System.out.println("Nombre:");
						datos[0] = sc.nextLine();
						System.out.println("Compañia:");
						datos[1] = sc.nextLine();
						System.out.println("Año:");
						datos[2] = sc.nextLine();
						System.out.println("Nota:");
						datos[3] = sc.nextLine();
						try {
							//Añadimos los datos al Videojuego
							v.setNombre(datos[0]);
							v.setCompania(datos[1]);
							v.setAnno(Integer.parseInt(datos[2]));
							v.setNota(Integer.parseInt(datos[3]));
							//Le pasamos el objeto al spv
							Videojuego vAlta = spv.alta(v);
							if(vAlta != null) {
								System.out.println("El videojuego se ha dado de alta satisfactoriamente");	
							}
							else {
								System.out.println("Se ha producido un error al dar de alta el videojuego");
							}
						}catch(NumberFormatException e) {
							System.out.println("Ha introducido valores no válidos para el año o la nota.");
							break;
						}
						break;
						
					case 2: //Dar de baja un videojuego por ID
						System.out.println("Intoduzca el ID del Videojuego para dar de BAJA");
						try {
							id = sc.nextInt();
						}
						catch(InputMismatchException e) {
							System.out.println("Introduzca un ID válido");
						}
						sc.nextLine();//Limpiamos el buffer
						//Invocamos el método de spv para borrar y comprobamos el resultado
						if(spv.borrar(id)) {
							System.out.println("El Videojuego con ID "+id+" se ha borrado correctamente");
						}
						else {
							System.out.println("No se ha podido borrar el Videojuego con ID "+id);
						}
						break;
					
					case 3: //Modificar Videojuego por ID
						System.out.println("Intoduzca el ID del Videojuego para MODIFICAR");
						try {
							id = sc.nextInt();
						}
						catch(InputMismatchException e) {
							System.out.println("Introduzca un ID válido");
						}
						sc.nextLine();//Limpiamos el buffer
						//Solicitamos los Datos del Videojuego
						System.out.println("Introduzca los datos del Videojuego:");
						System.out.println("Nombre:");
						datos[0] = sc.nextLine();
						System.out.println("Compañia:");
						datos[1] = sc.nextLine();
						System.out.println("Año:");
						datos[2] = sc.nextLine();
						System.out.println("Puntuacion:");
						datos[3] = sc.nextLine();
						//Añadimos los datos al Videojuego, incluido el id
						try {
							v.setId(id);
							v.setNombre(datos[0]);
							v.setCompania(datos[1]);
							v.setAnno(Integer.parseInt(datos[2]));
							v.setNota(Integer.parseInt(datos[3]));
							//Invocamos el método de spv para modificar y comprobamos el resultado
							if(spv.modificar(v)) {
								System.out.println("El Videojuego con ID "+v.getId()+" se ha modificado correctamente");
							}
							else {
								System.out.println("No se ha podido modificar el Videojuego con ID "+v.getId());
							}
						}catch(NumberFormatException e) {
							System.out.println("Ha introducido valores no válidos para el año o la nota.");
							break;
						}
						break;
						
					case 4: //Obtener un videojuego por ID
						System.out.println("Intoduzca el ID del Videojuego:");
						try {
							id = sc.nextInt();
						}
						catch(InputMismatchException e) {
							System.out.println("Introduzca un ID válido");
						}
						sc.nextLine();//Limpiamos el buffer
						//Invocamos al spv para obtener el videojuego por el id
						v = spv.obtener(id);
						if(v != null) {
							System.out.println("Se ha obtenido el videojuego con id="+id+": "+v);
						}
						else {
							System.out.println("No se ha podido obtener el videojuego con id="+id);
						}
						break;
						
					case 5: //Listar todos los videojuegos
						System.out.println("Lista de todos los Videojuegos:");
						List<Videojuego> listaVideojuegos = spv.listar(null);
						if(listaVideojuegos !=null) {
							//Recorremos la lista y la imprimimos con funciones lambda
							//Tambien podriamos haber usado un for-each clasico de java
							listaVideojuegos.forEach((i) -> System.out.println(i));
						}
						else {
							System.out.println("No se han encontrado videojuegos");
						}
						break;
						
					case 6: //Salir
						System.out.println("Ha elegido salir.\nHasta la próxima!");
						salir=true;
						break;
						
					default: //Opción no válida
						System.out.println("Opción no válida. Introduzca un número entre 1 y 6");
				}
			}
		}
		sc.close(); //Cerramos el Scanner
		
		System.out.println("******************************************");		
		System.out.println("******** Parando el cliente REST *********");	
		//Mandamos parar nuestra aplicacion Spring Boot
		pararAplicacion();
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

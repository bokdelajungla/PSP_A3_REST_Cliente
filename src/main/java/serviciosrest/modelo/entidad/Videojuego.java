package serviciosrest.modelo.entidad;

/**
 * Clase Videojuego:
 * Los videojuegos tendrán un ID, un nombre, una compañía, una año y una nota.
 * 
 * @author Jorge
 *
 */

public class Videojuego {
	
	private int id;
	private String nombre;
	private String compania;
	private int anno;
	private int nota;
	
	public Videojuego() {
		super();
	}
	
	public Videojuego(int id, String nombre, String compania, int anno, int nota) {
		this.id = id;
		this.nombre = nombre;
		this.compania = compania;
		this.anno = anno;
		this.nota = nota;	
	}

	//toString()
	@Override
	public String toString() {
		return "Videojuego [id=" + id + ", nombre=" + nombre + ", compania=" + compania + ", año=" + anno + ", nota="
				+ nota + "]";
	}

	//Metodo hashCode()
	//Para poder implementar el requerimiento2, puesto que el ID va a ser único
	//ya que es controlado por el DAO y no lo añade el usuario, debemos hacer depender
	//el hash del nombre que es el que queremos que también sea único
	
	/**
	 * Dos Videojuegos con el mismo nombre tendrán el mismo hashCode
	 * El hashCode sirve para hacer comparaciones rápidas
	 * Si dos objetos tienen distinto hash se consideran diferentes.
	 * Si tienen el mismo hash, se ejecuta equals para comprobar si son iguales.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)//Si el objeto es el mismo -> true
			return true;
		if (obj == null)//Si el objeto es null -> false
			return false;
		if (getClass() != obj.getClass())//Si es de otra clase -> false
			return false;
		Videojuego other = (Videojuego) obj;
		if (id != other.id) {//Si tienen id distinto hay que comprobar el nombre
			if (nombre == null) {
				if (other.nombre != null) 
					return false;
			} else if (!nombre.equals(other.nombre))//Si el nombre es distinto son diferentes
				return false;
		}
		//Si tienen el mismo id o el mismo nombre -> true
		return true;
	}
	
	//GETTERS & SETTERS
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

	public String getCompania() {
		return compania;
	}

	public void setCompania(String compania) {
		this.compania = compania;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

}
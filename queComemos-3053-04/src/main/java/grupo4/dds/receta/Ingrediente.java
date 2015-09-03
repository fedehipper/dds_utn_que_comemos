package grupo4.dds.receta;

import grupo4.dds.misc.CoberturaIgnore;

public class Ingrediente {
	
	private String nombre;
	private float cantidad;
	
	public Ingrediente(String nombre, float cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	public Ingrediente(String nombre) {
		this.nombre = nombre;
	}
	
	public Ingrediente() {
	}
	
	public float getCantidad() {
		return this.cantidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public boolean esCarne() {
		return nombre.equals("carne") || nombre.equals("chivito") || nombre.equals("chori") || nombre.equals("pollo");
	}

	@CoberturaIgnore 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}
	
	@CoberturaIgnore 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ingrediente))
			return false;
		Ingrediente other = (Ingrediente) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}


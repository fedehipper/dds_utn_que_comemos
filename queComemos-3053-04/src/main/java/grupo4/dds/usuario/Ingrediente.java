package grupo4.dds.usuario;

public class Ingrediente {
	
	private String nombre;
	private  Float cantidad;
	
	
	public Ingrediente (String nombre, Float cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	public Float getCantidad() {
		return this.cantidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public boolean getEsCarne() {
		return (nombre == "carne" | nombre == "chivito" | nombre == "chori" | nombre == "pollo");
	}

	public boolean esFruta() {
		return nombre == "fruta";
	}

}


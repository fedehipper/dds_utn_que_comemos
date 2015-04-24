package grupo4.dds;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Usuario {
	private Double estatura;
	private Double peso;
	private String nombre;
	private Character sexo;
	private LocalDate fechaNac;
	private Collection<String> preferenciasAlimenticias = new ArrayList<>();
	private Collection <String> alimentosNoPreferidos = new ArrayList<>();
	private Collection<Condicion> condiciones = new ArrayList<>();
	private Rutina rutina;
	private Receta receta;

	
	public Usuario(Double estatura, Double peso){
		this.peso = peso;
		this.estatura = estatura;
	
	}
	
	public Usuario(String nombre, char sexo, LocalDate fechaNac, Double altura, Double peso, Rutina rutina){
		this.nombre = nombre;
		this.sexo = sexo;
		this.fechaNac = fechaNac;
		this.estatura = altura;
		this.peso = peso;
		this.rutina = rutina;
	}
	
	public double indiceDeMasaCorporal(){
		return peso/(estatura*estatura);
	}
	
	public boolean esValido(){
		return (tieneCamposObligatorios() && tieneMasDe(4) && cumpleCondiciones() && esFechaDeNacimiento());
		
	}

	public boolean tieneCamposObligatorios() {
		
		return (this.nombre != null) && (this.peso != null) && (this.estatura != null) && (this.fechaNac != null) && (this.rutina != null);
	}
	
	public boolean tieneMasDe(int cantCaracteres){
		return this.nombre.length() > cantCaracteres;
	}
	
	public boolean cumpleCondiciones(){
		return condiciones.isEmpty() ? true : condiciones.stream().allMatch(unaCondicion-> unaCondicion.esValido(this));
	}
	
	
	public boolean esFechaDeNacimiento(){
		
		LocalDate fechaActual = LocalDate.now();
		return this.fechaNac.isBefore(fechaActual) ;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setPreferenciasAlimenticias(
			Collection<String> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public Collection<String> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}
	
	


}

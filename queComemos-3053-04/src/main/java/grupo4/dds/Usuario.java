package grupo4.dds;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static grupo4.dds.Rutina.*;

public class Usuario {
	private Double estatura;
	private Double peso;
	private String nombre;
	private Character sexo;
	private LocalDate fechaNac;
	private Collection<String> preferenciasAlimenticias = new ArrayList<>();
	private Collection<String> alimentosNoPreferidos = new ArrayList<>();
	private Collection<Condicion> condiciones = new ArrayList<>();
	private Rutina rutina;
	private Collection<Receta> recetas = new ArrayList<>();

	
	//------CONSTRUCTORES-----
	
	
	public Usuario(Double estatura, Double peso) {
		this.peso = peso;
		this.estatura = estatura;

	}
	
	
	//Creado para testear por ahora
	
	
	public Usuario(String nombre, char sexo, LocalDate fechaNac, Double altura,
			Double peso, Rutina rutina) {
		this.nombre = nombre;
		this.sexo = sexo;
		this.fechaNac = fechaNac;
		this.estatura = altura;
		this.peso = peso;
		this.rutina = rutina;
	}
	
	
	//------CONSTRUCTORES-----
	
	
	public double indiceDeMasaCorporal() {
		return peso / (estatura * estatura);
	}

	public boolean esValido() {
		return (tieneCamposObligatorios() && tieneMasDe(4)
				&& cumpleCondiciones() && esFechaDeNacimiento());

	}

	public boolean tieneCamposObligatorios() {

		return (this.nombre != null) && (this.peso != null)
				&& (this.estatura != null) && (this.fechaNac != null)
				&& (this.rutina != null);
	}

	public boolean tieneMasDe(int cantCaracteres) {
		return this.nombre.length() > cantCaracteres;
	}

	public boolean cumpleCondiciones() {
		return condiciones.isEmpty() ? true : condiciones.stream().allMatch(
				unaCondicion -> unaCondicion.esValido(this));
	}

	public boolean esFechaDeNacimiento() {
		LocalDate fechaActual = LocalDate.now();
		return this.fechaNac.isBefore(fechaActual);
	}

	public boolean sigueRutinaSaludable() {
		if (this.condiciones.isEmpty())
			return this.imcEstaEntre(18, 30);
		else
			return this.cumpleNecesidades();
	}

	public boolean imcEstaEntre(int unValor, int otroValor) {
		return (this.indiceDeMasaCorporal() >= unValor)
				&& (this.indiceDeMasaCorporal() <= otroValor);
	}

	public boolean cumpleNecesidades() {
		return this.condiciones.stream().allMatch(
				condicion -> condicion.cumpleNecesidades(this));
	}

	public boolean leGusta(String alimento) {
		return this.preferenciasAlimenticias.contains(alimento);
	}

	public boolean tieneRutinaActivaConEjercicioAdicional() {
		return (this.rutina == ACTIVA_EJERCICIO_ADICIONAL);
	}

	public boolean tieneRutinaActivaSinEjercicioAdicional() {
		return this.rutina == ACTIVA_SIN_EJERCICIO_ADICIONAL;
	}

	public boolean pesaMasDe(Double valorPeso) {
		return this.peso > valorPeso;
	}

	public void agregarCondicion(Condicion condicion) {
		this.condiciones.add(condicion);
	}

	public boolean esRecetaAdecuada(Receta receta) {
		return this.condiciones.stream().allMatch(condicion -> condicion.esRecomendable(receta));
	}
	
	
	public void agregarReceta(Receta unaReceta){
		if (unaReceta.esValida()&& unaReceta.puedeSerVistaOModificadaPor(this))
			this.recetas.add(unaReceta);
	}
	
	public boolean puedeVerOModificar (RecetaDelSistema unaReceta){
		return unaReceta.puedeSerVistaOModificadaPor(this);
	}
	
	public void modificarReceta (RecetaDelSistema unaReceta, String nombre, HashMap<String, Double> ingredientes, 
			HashMap<String, Double> condimentos, String preparacion,int calorias, String dificultad,Temporada temporada, Collection<Receta> subReceta){
		unaReceta.serModificadaPor(this,nombre, ingredientes, condimentos, preparacion, calorias, dificultad, temporada, subReceta);
	}
	
	
	//-----Getters y setters-----
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
	public Collection<Receta> getRecetas() {
		return recetas;
	}
	

}

package grupo4.dds;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

	public Usuario(Double estatura, Double peso) {
		this.peso = peso;
		this.estatura = estatura;

	}

	public Usuario(String nombre, char sexo, LocalDate fechaNac, Double altura,
			Double peso, Rutina rutina) {
		this.nombre = nombre;
		this.sexo = sexo;
		this.fechaNac = fechaNac;
		this.estatura = altura;
		this.peso = peso;
		this.rutina = rutina;
	}

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

	public void agregarReceta(Receta unaReceta) {
		if (unaReceta.esValida())
			this.recetas.add(unaReceta);
	}

}

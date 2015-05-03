package grupo4.dds.usuario;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaDelSistema;
import grupo4.dds.receta.Temporada;
import grupo4.dds.usuario.condicion.Condicion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

public class Usuario {

	/* Datos básicos */
	private String nombre;
	private Sexo sexo;
	private LocalDate fechaNacimiento;

	/* Datos de la complexión */

	private float peso;
	private float altura;

	/* Otros datos */

	private Rutina rutina;
	private Collection<Alimento> preferenciasAlimenticias = new ArrayList<>();
	private Collection<Alimento> comidasQueLeDisgustan = new ArrayList<>();
	private Collection<Condicion> condiciones = new ArrayList<>();
	private Collection<Receta> recetas = new ArrayList<>();

	/* Constructores */

	public Usuario(float estatura, float peso) {
		this.peso = peso;
		this.altura = estatura;

	}
	
	public Usuario(String nombre, LocalDate fechaNacimiento, float altura,
			float peso, Rutina rutina) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.altura = altura;
		this.peso = peso;
		this.rutina = rutina;
	}
		
	public Usuario(String nombre, Sexo sexo, LocalDate fechaNacimiento, float altura,
			float peso, Rutina rutina) {
		this(nombre, fechaNacimiento, altura, peso, rutina);
		this.sexo = sexo;
	}

	/* Servicios */

	public double indiceDeMasaCorporal() {
		return peso / (altura * altura);
	}

	public boolean esValido() {
		return tieneCamposObligatorios() && nombre.length() > 4
				&& tieneCondicionesValidas()
				&& fechaNacimiento.isBefore(LocalDate.now());

	}

	public boolean sigueRutinaSaludable() {

		double imc = indiceDeMasaCorporal();
		return (18 < imc) && (imc < 30) && subsanaTodasLasCondiciones();

	}

	public boolean leGusta(Alimento alimento) {
		return this.preferenciasAlimenticias.contains(alimento);
	}

	public void agregarReceta(Receta unaReceta) {
		if (unaReceta.esValida() && unaReceta.puedeSerVistaOModificadaPor(this))
			this.recetas.add(unaReceta);
	}

	public boolean tieneRutina(Rutina rutina) {
		return rutina.equals(rutina);
	}

	public boolean puedeVerOModificar(RecetaDelSistema unaReceta) {
		return unaReceta.puedeSerVistaOModificadaPor(this);
	}

	/* Servicios internos */

	private boolean tieneCamposObligatorios() {

		return (this.nombre != null) && (this.peso != 0) && (this.altura != 0)
				&& (this.fechaNacimiento != null) && (this.rutina != null);
	}

	private boolean todasLasCondicionesCumplen(Predicate<Condicion> predicado) {
		return condiciones.isEmpty() ? true : condiciones.stream().allMatch(
				predicado);
	}

	private boolean tieneCondicionesValidas() {
		return todasLasCondicionesCumplen(unaCondicion -> unaCondicion
				.esValidaCon(this));
	}

	private boolean subsanaTodasLasCondiciones() {
		return todasLasCondicionesCumplen(unaCondicion -> unaCondicion
				.subsanaCondicion(this));
	}

	// TODO verificar que sea lo pedido en el punto 4
	public boolean esRecetaAdecuada(Receta receta) {
		return this.condiciones.stream().allMatch(
				condicion -> condicion.esRecomendable(receta));
	}

	// TODO corregir
	public void modificarReceta(RecetaDelSistema unaReceta, String nombre,
			HashMap<String, Double> ingredientes,
			HashMap<String, Double> condimentos, String preparacion,
			int calorias, String dificultad, Temporada temporada,
			Collection<Receta> subReceta) {
		unaReceta.serModificadaPor(this, nombre, ingredientes, condimentos,
				preparacion, calorias, dificultad, temporada, subReceta);
	}

	/* Accessors and Mutators */

	public String getNombre() {
		return nombre;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public float getAltura() {
		return altura;
	}

	public float getPeso() {
		return peso;
	}

	public Collection<Alimento> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}

	public Collection<Alimento> getComidasQueLeDisgustan() {
		return comidasQueLeDisgustan;
	}

	public Collection<Condicion> getCondiciones() {
		return condiciones;
	}

	public Rutina getRutina() {
		return rutina;
	}

	public Collection<Receta> getRecetas() {
		return recetas;
	}

	public void agregarCondicion(Condicion condicion) {
		condiciones.add(condicion);
	}

	public void agregarPreferenciaAlimenticia(Alimento alimento) {
		preferenciasAlimenticias.add(alimento);
	}

	public void agregarComidaQueLeDisgusta(Alimento alimento) {
		comidasQueLeDisgustan.add(alimento);
	}

	public void setPreferenciasAlimenticias(
			Collection<Alimento> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

}

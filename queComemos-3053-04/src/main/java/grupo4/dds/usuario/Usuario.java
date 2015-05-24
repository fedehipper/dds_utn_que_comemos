package grupo4.dds.usuario;

import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;

import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.condicion.Condicion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Usuario {

	/* Datos basicos */
	private String nombre;
	private Sexo sexo;
	private LocalDate fechaNacimiento;

	/* Datos de la complexion */
	private float peso;
	private float altura;

	/* Otros datos */
	private Rutina rutina;
	private List<Ingrediente> preferenciasAlimenticias = new ArrayList<>();
	private List<Ingrediente> comidasQueLeDisgustan = new ArrayList<>();
	private List<Condicion> condiciones = new ArrayList<>();
	private List<Receta> recetas = new ArrayList<>();
	private List<Grupo> grupos = new ArrayList<>();

	/* Constructores */
	public Usuario(){};
	
	public Usuario(String nombre, LocalDate fechaNacimiento, float altura, float peso, Rutina rutina) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.altura = altura;
		this.peso = peso;
		this.rutina = rutina;
	}

	public Usuario(String nombre, Sexo sexo, LocalDate fechaNacimiento, float altura, float peso, Rutina rutina) 
		{this(nombre, fechaNacimiento, altura, peso, rutina);
		this.sexo = sexo;
	}

	/* Servicios */
	public double indiceDeMasaCorporal() {
		return peso / (altura * altura);
	}

	public boolean esValido() {
		return tieneCamposObligatorios() && nombre.length() > 4 && tieneCondicionesValidas() && fechaNacimiento.isBefore(LocalDate.now());
	}

	public boolean sigueRutinaSaludable() {
		double imc = indiceDeMasaCorporal();
		return 18 < imc && imc < 30 && subsanaTodasLasCondiciones();
	}

	public boolean leGusta(String alimento) {
		return this.preferenciasAlimenticias.stream().anyMatch(a -> a.getNombre() == alimento);
	}

	public void agregarReceta(Receta receta) {
		if (esAdecuada(receta) && receta.puedeSerAgregadaPor(this))
			recetas.add(receta);
		else
			throw new NoSePuedeAgregarLaReceta();
	}

	public boolean tieneRutina(Rutina rutina) {
		return rutina.equals(rutina);
	}
	
	public boolean tieneReceta(Receta receta) {
		return recetas.contains(receta);
	}

	public boolean puedeVer(Receta receta) {
		return receta.puedeSerVistaPor(this);
	}

	public boolean puedeModificar(Receta receta) {
		return receta.puedeSerModificadaPor(this);
	}

	public boolean esAdecuada(Receta receta) {
		return receta.esValida() && todasLasCondicionesCumplen(condicion -> condicion.esRecomendable(receta));
	}

	public void modificarReceta(Receta receta, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes, 
			List<Ingrediente> condimentos, String preparacion, List<Receta> subRecetas) {
		try {
			receta.modificarReceta(this, encabezado, ingredientes, condimentos, preparacion, subRecetas);
		} catch (NoSePuedeModificarLaReceta e) {
			//TODO: hacer algo con esta excepción
			throw e;
		}
	}
	
	public Receta recetaMasReciente() {
		return recetas.get(recetas.size() - 1);
	}

	/* Servicios internos */
	private boolean tieneCamposObligatorios() {
		return this.nombre != null && this.peso != 0 && this.altura != 0 && this.fechaNacimiento != null && this.rutina != null;
	}

	private boolean todasLasCondicionesCumplen(Predicate<Condicion> predicado) {
		return condiciones.isEmpty() ? true : condiciones.stream().allMatch(predicado);
	}

	private boolean tieneCondicionesValidas() {
		return todasLasCondicionesCumplen(unaCondicion -> unaCondicion.esValidaCon(this));
	}

	private boolean subsanaTodasLasCondiciones() {
		return todasLasCondicionesCumplen(unaCondicion -> unaCondicion.subsanaCondicion(this));
	}
	
	public boolean sugerirReceta(Receta unaReceta) {
		return !(unaReceta.compartenPalabrasClave(comidasQueLeDisgustan)) && (this.esAdecuada(unaReceta));			
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

	public List<Ingrediente> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}

	public List<Ingrediente> getComidasQueLeDisgustan() {
		return comidasQueLeDisgustan;
	}

	public List<Condicion> getCondiciones() {
		return condiciones;
	}

	public Rutina getRutina() {
		return rutina;
	}

	public Collection<Receta> getRecetas() {
		return recetas;
	}

	public void agregarCondicion(Condicion condicion) {
		this.condiciones.add(condicion);
	}

	public void agregarPreferenciaAlimenticia(Ingrediente alimento) {
		this.preferenciasAlimenticias.add(alimento);
	}

	public void agregarComidaQueLeDisgusta(Ingrediente alimento) {
		this.comidasQueLeDisgustan.add(alimento);
	}

	public void setPreferenciasAlimenticias(List<Ingrediente> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public void agregarGrupo(Grupo grupo) {
		this.grupos.add(grupo);		
	}
	
	public List<Grupo> getGrupos() {
		return this.grupos;
	}

}

package grupo4.dds.usuario;

import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeGuardarLaRecetaEnElHistorial;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.condicion.Condicion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
	private Set<GrupoUsuarios> grupos = new HashSet<>();
	private List<Receta> historial = new ArrayList<>();

	/* Constructores */
	public Usuario(){
	};
	
	public Usuario(String nombre) {
		this.nombre = nombre;
	}
	
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

	public boolean sigueRutinaSaludable() {
		double imc = indiceDeMasaCorporal();
		return 18 < imc && imc < 30 && subsanaTodasLasCondiciones();
	}

	public void agregarReceta(Receta receta) {
		if (esAdecuada(receta) && receta.puedeSerAgregadaPor(this))
			recetas.add(receta);
		else
			throw new NoSePuedeAgregarLaReceta();
	}

	public boolean esAdecuada(Receta receta) {
		return receta.esValida() && todasLasCondicionesCumplen(condicion -> condicion.esRecomendable(receta));
	}
	
	public void modificarReceta(Receta receta, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes, 
			List<Ingrediente> condimentos, String preparacion, List<Receta> subRecetas) {
			receta.modificarReceta(this, encabezado, ingredientes, condimentos, preparacion, subRecetas);
			//TODO: hacer algo con excepción NoSePuedeModificarLaReceta
	}
	
	public boolean puedeSugerirse(Receta receta) {
		return leGusta(receta) && esAdecuada(receta);		
	}
	
	public List<Receta> recetasQuePuedeVer(RepositorioDeRecetas repositorio) {
		return repositorio.listarRecetasPara(this);
	}
	
	public List<Receta> recetasQuePuedeVer(RepositorioDeRecetas repositorio, List<Filtro> filtros, PostProcesamiento postProcesamiento ) {
		return repositorio.listarRecetasPara(this, filtros, postProcesamiento);
	}
	
	public boolean puedeVer(Receta receta) {
		return receta.puedeSerVistaPor(this) || algunGrupoPuedeVer(receta);
	}
	
	public void marcarFavorita(Receta receta) {
		if(!puedeVer(receta))
			throw new NoSePuedeGuardarLaRecetaEnElHistorial();
			
		historial.add(receta);
	}
		
	/* Servicios secundarios */

	public boolean esValido() {
		return tieneCamposObligatorios() && nombre.length() > 4 && tieneCondicionesValidas() && fechaNacimiento.isBefore(LocalDate.now());
	}
	
	public boolean tienePreferenciasAlimenticias() {
		return !preferenciasAlimenticias.isEmpty();
	}
	
	public boolean leGusta(String nombreComida) {
		return preferenciasAlimenticias.contains(Ingrediente.ingrediente(nombreComida));
	}
	
	public boolean leGusta(Ingrediente comida) {
		return leGusta(comida.getNombre());
	}
	
	public boolean leGusta(Receta receta) {
		return receta.noContieneNinguna(comidasQueLeDisgustan);
	}

	public boolean leGustaLaCarne() {
		return preferenciasAlimenticias.stream().anyMatch(a -> a.esCarne());
	}
	
	public boolean tieneRutina(Rutina rutina) {
		return rutina.equals(rutina);
	}
	
	public boolean tieneReceta(Receta receta) {
		return recetas.contains(receta);
	}

	public boolean puedeModificar(Receta receta) {
		return puedeVer(receta);
	}
	
	public Receta recetaMasReciente() {
		return recetas.get(recetas.size() - 1);
	}
	
	public boolean perteneceA(GrupoUsuarios grupo) {
		return grupos.contains(grupo);
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
		
	private boolean algunGrupoPuedeVer(Receta receta) {
		return grupos.stream().anyMatch(g -> g.puedeVer(receta));
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

	public Rutina getRutina() {
		return rutina;
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

	public void agregarGrupo(GrupoUsuarios grupo) {
		
		grupos.add(grupo);
		
		if (!grupo.esMiembro(this)) 
		   grupo.agregarUsuario(this);
	}
	
	public List<Receta> getHistorioal() {
		//TODO: probablemente no queramos retornar el historial, revisar cuando surja nuevo comportamiento
		return historial;
	}
	
	public List<Condicion> getCondiciones() {
		return this.condiciones;
	}

}

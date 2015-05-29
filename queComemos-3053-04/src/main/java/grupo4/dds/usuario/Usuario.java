package grupo4.dds.usuario;

import grupo4.dds.excepciones.NoSeEncontroLaReceta;
import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeGuardarLaRecetaEnElHistorial;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.condicion.Condicion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	private List<GrupoUsuarios> grupos = new ArrayList<>();
	private List<Receta> historial = new ArrayList<>();

	/* Constructores */
	public Usuario(){
	};
	
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

	public boolean tienePreferenciasAlimenticias() {
		return !preferenciasAlimenticias.isEmpty();
	}
	
	public boolean leGusta(String nombreAlimento) {
		return preferenciasAlimenticias.contains(Ingrediente.ingrediente(nombreAlimento));
	}

	public boolean leGustaLaCarne() {
		return preferenciasAlimenticias.stream().anyMatch(a -> a.esCarne());
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
	
	public boolean tieneRutina(Rutina rutina) {
		return rutina.equals(rutina);
	}
	
	public boolean tieneReceta(Receta receta) {
		return recetas.contains(receta);
	}

	public boolean puedeModificar(Receta receta) {
		return puedeVer(receta);
	}
	
	public void modificarReceta(Receta receta, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes, 
			List<Ingrediente> condimentos, String preparacion, List<Receta> subRecetas) {
			receta.modificarReceta(this, encabezado, ingredientes, condimentos, preparacion, subRecetas);
			//TODO: hacer algo con excepción NoSePuedeModificarLaReceta
	}
	
	public Receta recetaMasReciente() {
		return recetas.get(recetas.size() - 1);
	}
	
	public boolean puedeSugerirse(Receta receta) {
		return receta.noContieneNinguna(comidasQueLeDisgustan) && esAdecuada(receta);	
		//TODO: revisar si se pretendía que sea adecuado (válida y cumple condiciones) o que solo cumpla condiciones.
	}
	
	public List<Receta> recetasQuePuedeVer(RepositorioDeRecetas repositorio) {
		return repositorio.listarRecetasPara(this);
	}
	
	public boolean puedeVer(Receta receta) {
		return receta.puedeSerVistaPor(this) || algunGrupoPuedeVer(receta);
	}
	
	public boolean algunGrupoPuedeVer(Receta receta) {
		return grupos.stream().anyMatch(g -> g.puedeVer(receta));
	}
	
	public void marcarFavorita(Receta receta) {
		if(!puedeVer(receta))
			throw new NoSePuedeGuardarLaRecetaEnElHistorial();
			
		historial.add(receta);
	}
	
	
	public Receta buscarUnaReceta(String nombre, RepositorioDeRecetas repositorio) {
		List<Receta> encontrada = new ArrayList<>();
		encontrada = this.recetasQuePuedeVer(repositorio).stream().filter(r -> r.getNombreDelPlato().equals(nombre)).collect(Collectors.toList());
		if (!encontrada.isEmpty())
			return encontrada.get(0);
		else
			throw new NoSeEncontroLaReceta();
	}
	
	public void buscarYAgregarAlHistorial(String nombre, RepositorioDeRecetas repositorio) {
		this.historial.add(this.buscarUnaReceta(nombre, repositorio));
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
		//TODO: Validar que el usuario no exista, o usar un Collection Set
		this.grupos.add(grupo);
		grupo.agregarUsuario(this);
	}
	
	public List<Receta> getHistorioal() {
		return historial;
	}

	
	
	public List<GrupoUsuarios> getGrupos() {
		return this.grupos;
	}
	
	public List<Ingrediente> getComidasQueLeDisgustan() {
		return comidasQueLeDisgustan;
	}


}

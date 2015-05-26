package grupo4.dds.usuario;

import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
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
	private List<Grupo> grupos = new ArrayList<>();
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

	public boolean esElDuenio(Receta receta) {
		return receta.puedeSerVistaPor(this);
	}
	
	// entrega 2, punto 3
	public List<Receta> recetasQuePuedeVer(RepositorioDeRecetas repositorio) {
		return repositorio.listarRecetasParaUnUsuario(this);
	}
	
	// entrega 2, punto 3 TESTEAR SI QUIERE BUSCAR UNA RECETA QUE NO ESTE EN EL REPOSITORIO
	public Receta buscarUnaReceta(String nombre, RepositorioDeRecetas repositorio) {
		return this.recetasQuePuedeVer(repositorio).stream().filter(r -> r.getEncabezado().getNombreDelPlato().equals(nombre)).collect(Collectors.toList()).get(0);
	}
	
	// entrega 2, punto 3
	public void agregarRecetaAlHistorial(Receta receta) {
		this.historial.add(receta);
	}
	
	// entrega 2, punto 3
	public void buscarYAgregarAlHistorial(String nombre, RepositorioDeRecetas repositorio) {
		Receta recetaEncontrada = this.buscarUnaReceta(nombre, repositorio);
		if (!recetaEncontrada.equals(null))
			this.agregarRecetaAlHistorial(recetaEncontrada);
	}
	
	// entrega 2, punto 2
	public boolean gruposPuedenVer(Receta receta) {
		return this.grupos.stream().anyMatch(g -> g.puedenVerLaReceta(receta));
	}
	
	public boolean puedeVer(Receta receta) {
		return this.esElDuenio(receta) || this.gruposPuedenVer(receta);
	}
	
	public boolean puedeModificar(Receta receta) {
		return receta.puedeSerModificadaPor(this) || this.gruposPuedenVer(receta);
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
	
	public boolean leGustaLaCarne() {
		return preferenciasAlimenticias.stream().anyMatch(a -> a.esCarne());
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

	public void agregarGrupo(Grupo grupo) {
		this.grupos.add(grupo);		
	}
	
	public List<Grupo> getGrupos() {
		return this.grupos;
	}
	
	public List<Receta> getHistorioal() {
		return this.historial;
	}

}

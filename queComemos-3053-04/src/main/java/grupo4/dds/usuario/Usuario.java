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
import grupo4.dds.usuario.condicion.Vegano;
import grupo4.dds.usuario.gestionDePerfiles.Administrador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Usuario {

	/* Datos basicos */
	protected String nombre;
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
	private Set<Receta> historial = new HashSet<>();
	private boolean marcaFavorita;
	
	/* Constructores */
	
	public static Usuario crearPerfil(String nombre, Sexo sexo,
			LocalDate fechaNacimiento, float altura, float peso, Rutina rutina, boolean marcaFavorita) {
		
		Usuario self = new Usuario(nombre, sexo, fechaNacimiento, altura, peso, rutina, marcaFavorita);
		Administrador.get().solicitarIncorporación(self);
		return self;
	}
	
	public static Usuario crearPerfil(String nombre) {
		return crearPerfil(nombre, null, null, 0, 0, null, false);
	}

	private Usuario(String nombre, Sexo sexo, LocalDate fechaNacimiento, float altura, float peso,
			Rutina rutina, boolean marcaFavorita) {
		
		this.setMarcaFavorita(marcaFavorita);
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.altura = altura;
		this.peso = peso;
		this.rutina = rutina;
		this.sexo = sexo;
	}
	
	protected Usuario() {}

	/* Servicios */
	
	public float indiceDeMasaCorporal() {
		return peso / (altura * altura);
	}

	public boolean sigueRutinaSaludable() {
		float imc = indiceDeMasaCorporal();
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
		return repositorio.listarRecetasPara(this, null, null);
	}
	
	public List<Receta> recetasQuePuedeVer(RepositorioDeRecetas repositorio, List<Filtro> filtros, PostProcesamiento postProcesamiento ) {
		return repositorio.listarRecetasPara(this, filtros, postProcesamiento);
	}
	
	public boolean puedeVer(Receta receta) {
		return receta.puedeSerVistaPor(this) || algunGrupoPuedeVer(receta);
	}
			
	public boolean cumpleTodasLasCondicionesDe(Usuario usuario) {
		return usuario.noTieneCondiciones() ? true : this.getCondiciones().containsAll(usuario.getCondiciones());
	}
	
	/* Servicios secundarios */

	public boolean esValido() {
		return tieneCamposObligatorios() && nombre.length() > 4 && tieneCondicionesValidas() && fechaNacimiento.isBefore(LocalDate.now());
	}
	
	public boolean tienePreferenciasAlimenticias() {
		return !preferenciasAlimenticias.isEmpty();
	}
	
	public boolean noTieneCondiciones() {
		return condiciones.isEmpty();
	}
	
	public boolean leGusta(String nombreComida) {
		return preferenciasAlimenticias.contains(new Ingrediente(nombreComida));
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
		return this.rutina == null ? false : this.rutina.equals(rutina);
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
	
	public String toString() {
		return nombre;
	}
	
	public boolean esVegano() {
		return this.condiciones.contains(new Vegano());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(altura);
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + Float.floatToIntBits(peso);
		result = prime * result + ((rutina == null) ? 0 : rutina.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (Float.floatToIntBits(altura) != Float.floatToIntBits(other.altura))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Float.floatToIntBits(peso) != Float.floatToIntBits(other.peso))
			return false;
		if (rutina != other.rutina)
			return false;
		if (sexo != other.sexo)
			return false;
		return true;
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
	
	private List<Condicion> getCondiciones() {
		return condiciones;
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
	
	public Set<Receta> getHistorial() {
		return Collections.unmodifiableSet(historial);
	}

	public void solicitudAceptada() {
		// TODO hacer algo
	}

	public void solicitudRechazada(String motivo) {
		// TODO hacer algo
	}

	// punto 5 entrega 4
	public boolean esMarcaFavorita() {
		return marcaFavorita;
	}

	// punto 5 entrega 4
	public void setMarcaFavorita(boolean marcaFavorita) {
		this.marcaFavorita = marcaFavorita;
	}

	// punto 5 entrega 4
	public void marcarFavorita(Receta receta) {
		if(!puedeVer(receta))
			throw new NoSePuedeGuardarLaRecetaEnElHistorial();
		historial.add(receta);
	}
	
	// punto 5 entrega 4
	public void marcarRecetasComoFavoritas(List<Receta> consulta) {
		consulta.forEach(r -> marcarFavorita(r));
	}

}
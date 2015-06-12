package grupo4.dds.receta;

import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import queComemos.entrega3.dominio.Dificultad;

public class Receta {

	protected Usuario creador;

	/* Encabezado de la receta */
	protected EncabezadoDeReceta encabezado = new EncabezadoDeReceta();

	/* Detalle de la receta */
	protected List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	protected List<Ingrediente> condimentos = new ArrayList<Ingrediente>();
	protected List<Receta> subrecetas = new ArrayList<Receta>();
	protected String preparacion;

	/* Constructores */

	public static Receta crearNueva() {
		return crearNueva(null, null, null);
	}
	
	public static Receta crearNueva(Usuario creador,
			EncabezadoDeReceta encabezado, String preparacion) {

		return crearNueva(creador, encabezado, null, null, null, preparacion);
	}

	public static Receta crearNueva(Usuario creador,
			EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, List<Receta> subrecetas,
			String preparacion) {

		Receta self = new Receta(creador, encabezado, ingredientes,
				condimentos, subrecetas, preparacion);
		
		//TODO: hacer validas recetas en los test para agregar esta funcion
		//if(creador != null)
		//	creador.agregarReceta(self);
		
		RepositorioDeRecetas.get().agregarReceta(self);

		return self;
	}

	protected Receta(Usuario creador, EncabezadoDeReceta encabezado,
			List<Ingrediente> ingredientes, List<Ingrediente> condimentos,
			List<Receta> subrecetas, String preparacion) {

		this.creador = creador;
		this.encabezado = encabezado != null ? encabezado
				: new EncabezadoDeReceta();
		this.preparacion = preparacion;
		this.ingredientes = ingredientes != null ? ingredientes
				: new ArrayList<>();
		this.condimentos = condimentos != null ? condimentos
				: new ArrayList<>();
		this.subrecetas = subrecetas != null ? subrecetas
				: new ArrayList<Receta>();
	}

	/* Servicios */

	public boolean esValida() {
		int totalCalorias = getTotalCalorias();
		return !ingredientes.isEmpty() && 10 <= totalCalorias
				&& totalCalorias <= 5000;
	}

	public boolean tieneIngrediente(String nombreIngrediente) {
		return tiene(getIngredientes(), nombreIngrediente);
	}

	public boolean tieneCondimento(String nombreCondimento) {
		return tiene(getCondimentos(), nombreCondimento);
	}

	public float cantidadCondimento(String nombreCondimento) {
		int index = getCondimentos().indexOf(
				new Ingrediente(nombreCondimento));
		return getCondimentos().get(index).getCantidad();
	}

	public boolean puedeSerVistaPor(Usuario usuario) {
		if(creador == null)
			return false;
		
		return creador.equals(usuario);
	}

	public boolean puedeSerAgregadaPor(Usuario usuario) {
		return puedeSerVistaPor(usuario);
	}

	public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado,
			List<Ingrediente> ingredientes, List<Ingrediente> condimentos,
			String preparacion, List<Receta> subrecetas) {

		if (!usuario.puedeModificar(this))
			throw new NoSePuedeModificarLaReceta();

		this.encabezado = encabezado;
		this.ingredientes = ingredientes != null ? ingredientes
				: new ArrayList<>();
		this.condimentos = condimentos != null ? condimentos
				: new ArrayList<>();
		this.subrecetas = subrecetas != null ? subrecetas
				: new ArrayList<Receta>();
		this.preparacion = preparacion;

	}

	public String getPreparacion() {
		if (preparacion == null & subrecetas.isEmpty())
			return "";

		String preparacionDeSubrecetas = subrecetas == null ? null : subrecetas
				.stream().map(Receta::getPreparacion)
				.collect(Collectors.joining(""));

		if (preparacionDeSubrecetas == null)
			return preparacion;

		return String.join("", preparacion, preparacionDeSubrecetas);
	}

	public List<Ingrediente> getIngredientes() {
		return getConSubrecetas((Receta receta) -> {
			return receta.ingredientes;
		}, ingredientes);
	}

	public List<Ingrediente> getCondimentos() {
		return getConSubrecetas((Receta receta) -> {
			return receta.condimentos;
		}, condimentos);
	}

	public boolean contieneAlguna(List<Ingrediente> comidas) {
		return !noContieneNinguna(comidas);
	}

	public boolean noContieneNinguna(List<Ingrediente> comidas) {
		return Collections.disjoint(getIngredientes(), comidas);
	}

	public boolean tieneCarne() {
		return getIngredientes().stream().anyMatch(i -> i.esCarne());
	}

	@Override
	public String toString() {
		return getNombreDelPlato();
	}

	/* Servicios privados */

	private boolean tiene(List<Ingrediente> lista, String nombre) {
		return lista.contains(new Ingrediente(nombre));
	}

	// TODO mejorar para llegar a algo más cercano a fold/reduct
	private List<Ingrediente> getConSubrecetas(
			Function<Receta, List<Ingrediente>> f, List<Ingrediente> seed) {

		List<Ingrediente> acum = new ArrayList<Ingrediente>(seed);

		for (Receta elem : subrecetas) {
			acum.addAll(f.apply(elem));
		}

		return acum;

	}

	/* Accessors and Mutators */

	public int getTotalCalorias() {
		return encabezado.getTotalCalorias();
	}

	public void setTotalCalorias(int totalCalorias) {
		encabezado.setTotalCalorias(totalCalorias);
	}

	public void agregarIngrediente(Ingrediente unIngrediente) {
		ingredientes.add(unIngrediente);
	}

	public void agregarCondimento(Ingrediente unCondimento) {
		condimentos.add(unCondimento);
	}

	public void agregarSubreceta(Receta subreceta) {
		subrecetas.add(subreceta);
	}

	public String getNombreDelPlato() {
		return encabezado.getNombreDelPlato();
	}

	public Temporada getTemporada() {
		return encabezado.getTemporada();
	}

	public Dificultad getDificultad() {
		return encabezado.getDificultad();
	}

}

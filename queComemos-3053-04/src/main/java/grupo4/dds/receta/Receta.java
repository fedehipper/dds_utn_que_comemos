package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Receta {

	protected Usuario creador;

	/* Encabezado de la receta */
	protected EncabezadoDeReceta encabezado = new EncabezadoDeReceta();

	/* Detalle de la receta */
	protected HashMap<String, Float> ingredientes = new HashMap<String, Float>();
	protected HashMap<String, Float> condimentos = new HashMap<String, Float>();
	protected ArrayList<Receta> subrecetas;
	protected String preparacion;

	/* Constructores */
	
	public Receta(){}

	public Receta(Usuario creador) {
		this.creador = creador;
	}// Creado para testear por ahora
	//TODO: lanzar excepción cuando no se especifica el creador
	public Receta(Usuario creador, EncabezadoDeReceta encabezado, String preparacion) {
		this.creador = creador;
		this.encabezado = encabezado != null ? encabezado : new EncabezadoDeReceta();
		this.preparacion = preparacion;
	}
	
	public Receta(Usuario creador, EncabezadoDeReceta encabezado,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, ArrayList<Receta> subrecetas,
			String preparacion) {
		this(creador, encabezado, preparacion);
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.subrecetas = subrecetas;
	}

	/* Servicios */

	public boolean esValida() {
		int totalCalorias = getTotalCalorias();
		return !ingredientes.isEmpty() && 10 <= totalCalorias
				&& totalCalorias <= 5000;
	}

	public boolean tieneIngrediente(String unIngrediente) {
		return this.ingredientes.containsKey(unIngrediente);
	}

	public boolean tieneCondimento(String condimento) {
		return this.condimentos.containsKey(condimento);
	}

	public Float cantidadCondimento(String unCondimento) {
		return this.condimentos.get(unCondimento);
	}

	public boolean puedeSerVistaPor(Usuario usuario) {
		return creador.equals(usuario);
	}

	public boolean puedeSerModificadaPor(Usuario usuario) {
		return puedeSerVistaPor(usuario);
	}
	
	public boolean puedeSerAgregadaPor(Usuario usuario) {
		return puedeSerVistaPor(usuario);
	}

	public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			ArrayList<Receta> subRecetas) throws NoSePuedeModificarLaReceta {

		if (!puedeSerModificadaPor(usuario))
			throw new NoSePuedeModificarLaReceta();

		this.encabezado = encabezado;
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.subrecetas = subRecetas;
		this.preparacion = preparacion;
		
		if (!usuario.esAdecuada(this))
			throw new EsInadecuadaDespuesDeModificar();
	}

	public Collection<String> getNombreIngredientes() {
		return getConSubrecetas((Receta receta) -> {
			return receta.ingredientes.keySet();
		}, ingredientes.keySet());
	}

	public String getPreparacion() {

		if(preparacion == null && subrecetas == null)
			return null;
		
		String preparacionDeSubrecetas = subrecetas == null ? null : subrecetas.stream()
				.map(Receta::getPreparacion).collect(Collectors.joining("\n"));
		
		if(preparacionDeSubrecetas == null)
			return preparacion;
		
		return String.join("\n", preparacion, preparacionDeSubrecetas);
	}

	public Collection<String> getNombreCondimentos() {
		return getConSubrecetas((Receta receta) -> {
			return receta.condimentos.keySet();
		}, condimentos.keySet());
	}

	/* Servicios Internos */
	//TODO mejorar para llegar a algo más cercano a fold/reduct
	private Collection<String> getConSubrecetas(
			Function<Receta, Collection<String>> f, Collection<String> seed) {

		class CollectionMerger implements BinaryOperator<Collection<String>> {

			@Override
			public Collection<String> apply(Collection<String> t,
					Collection<String> u) {
				Collection<String> a = new HashSet<String>();

				a.addAll(t);
				a.addAll(u);

				return a;
			}

		}

		CollectionMerger merger = new CollectionMerger();

		if (subrecetas == null)
			return seed;

		Collection<String> coleccionesDeSubrecetas = subrecetas.stream().map(f)
				.collect(Collectors.reducing(merger)).get();

		return merger.apply(seed, coleccionesDeSubrecetas);

	}

	/* Accessors and Mutators */

	public int getTotalCalorias() {
		return encabezado.getTotalCalorias();
	}

	public void setTotalCalorias(int totalCalorias) {
		encabezado.setTotalCalorias(totalCalorias);
	}

	public void agregarIngrediente(String key, Float value) {
		ingredientes.put(key, value);
	}

	public void agregarCondimento(String key, Float value) {
		condimentos.put(key, value);
	}
	
	public void agregarSubreceta(Receta subreceta) {
		subrecetas.add(subreceta);
	}

	public void agregarIngredientes(HashMap<String, Float> ingredientes) {
		ingredientes.putAll(ingredientes);
	}

	public void agregarCondimentos(HashMap<String, Float> condimentos) {
		condimentos.putAll(condimentos);
	}
	
	public void agregarSubrecetas(Collection<Receta> subrecetas) {
		subrecetas.addAll(subrecetas);
	}
	
	public String getNombreDelPlato() {
		return encabezado.getNombreDelPlato();
	}

	public Temporada getTemporada() {
		return encabezado.getTemporada();
	}

	public String getDificultad() {
		return encabezado.getDificultad();
	}

}

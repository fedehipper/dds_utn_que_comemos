package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Collection;

public class Receta {

	protected Usuario creador;

	/* Encabezado de la receta */
	protected String nombreDelPlato;
	protected Temporada temporada;
	protected int totalCalorias;
	protected String dificultad;

	/* Detalle de la receta */
	protected HashMap<String, Float> ingredientes = new HashMap<String, Float>();
	protected HashMap<String, Float> condimentos = new HashMap<String, Float>();
	protected Collection<Receta> subRecetas;
	protected String preparacion;

	/* Constructores */

	public Receta(Usuario creador) {
		this.creador = creador;
	}// Creado para testear por ahora

	protected Receta(Usuario creador, String nombreDelPlato,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			int totalCalorias, String dificultad, Temporada temporada,
			Collection<Receta> subRecetas) {
		this.creador = creador;
		this.nombreDelPlato = nombreDelPlato;
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.preparacion = preparacion;
		this.totalCalorias = totalCalorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.subRecetas = subRecetas;
	}

	static public Receta crearNueva(Usuario creador, String nombreDelPlato,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			int totalCalorias, String dificultad, Temporada temporada,
			Collection<Receta> subRecetas) {
		
		Receta nuevaReceta = new Receta(creador, nombreDelPlato, ingredientes, condimentos, preparacion, totalCalorias, dificultad, temporada, subRecetas);
		creador.agregarReceta(nuevaReceta);
		
		return nuevaReceta;
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

	public Float cantidadCondimento(String unCondimento) {
		return this.condimentos.get(unCondimento);
	}

	public boolean puedeSerVistaPor(Usuario usuario) {
		return creador.equals(usuario);
	}

	public boolean puedeSerModificadaPor(Usuario usuario) {
		return puedeSerVistaPor(usuario);
	}

	public void modificarEncabezado(Usuario usuario, String nombreDelPlato,
			String dificultad, Temporada temporada)
			throws NoTienePermisoParaModificar {

		if (!puedeSerModificadaPor(usuario))
			throw new NoTienePermisoParaModificar();

		this.nombreDelPlato = nombreDelPlato;
		this.dificultad = dificultad;
		this.temporada = temporada;
	}

	public void modificarDetalle(Usuario usuario,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			Collection<Receta> subRecetas) throws NoTienePermisoParaModificar {

		if (!puedeSerModificadaPor(usuario))
			throw new NoTienePermisoParaModificar();

		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.preparacion = preparacion;
		this.subRecetas = subRecetas;
	}

	/* Accessors and Mutators */

	public Collection<String> getNombreIngredientes() {
		return ingredientes.keySet();
	}

	public String getPreparacion() {
		return preparacion;
	}

	public Collection<String> getNombreCondimentos() {
		return condimentos.keySet();
	}

	public int getTotalCalorias() {
		return totalCalorias;
	}

	public void setTotalCalorias(int totalCalorias) {
		this.totalCalorias = totalCalorias;
	}

	public HashMap<String, Float> getIngredientes() {
		return ingredientes;
	}

	public HashMap<String, Float> getCondimentos() {
		return condimentos;
	}

}

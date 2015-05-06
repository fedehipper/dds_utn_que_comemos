package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Collection;

public class Receta {

	protected Usuario creador;

	/* Encabezado de la receta */
	protected EncabezadoDeReceta encabezado = new EncabezadoDeReceta();

	/* Detalle de la receta */
	protected HashMap<String, Float> ingredientes = new HashMap<String, Float>();
	protected HashMap<String, Float> condimentos = new HashMap<String, Float>();
	protected Collection<Receta> subRecetas;
	protected String preparacion;

	/* Constructores */

	public Receta(Usuario creador) {
		this.creador = creador;
	}// Creado para testear por ahora

	public Receta(Usuario creador, EncabezadoDeReceta encabezado,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, Collection<Receta> subRecetas,
			String preparacion) {
		this.creador = creador;
		this.encabezado = encabezado;
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.subRecetas = subRecetas;
		this.preparacion = preparacion;
	}

	static public Receta crearNueva(Usuario creador,
			EncabezadoDeReceta encabezado, HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			Collection<Receta> subRecetas) {

		Receta nuevaReceta = new Receta(creador, encabezado, ingredientes,
				condimentos, subRecetas, preparacion);
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

	public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			Collection<Receta> subRecetas) throws NoTienePermisoParaModificar {

		if (!puedeSerModificadaPor(usuario))
			throw new NoTienePermisoParaModificar();

		this.encabezado = encabezado;
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.subRecetas = subRecetas;
		this.preparacion = preparacion;
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
		return encabezado.getTotalCalorias();
	}

	public void setTotalCalorias(int totalCalorias) {
		encabezado.setTotalCalorias(totalCalorias);
	}

	public HashMap<String, Float> getIngredientes() {
		return ingredientes;
	}

	public HashMap<String, Float> getCondimentos() {
		return condimentos;
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

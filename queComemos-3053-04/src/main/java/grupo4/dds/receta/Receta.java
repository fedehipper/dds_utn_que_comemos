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
	protected String dificultad;
	protected Temporada temporada;

	/* Constructores */

	public Receta(Usuario creador) {
		this.creador = creador;
	}// Creado para testear por ahora

	public Receta(Usuario creador, EncabezadoDeReceta encabezado,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			Collection<Receta> subRecetas) {
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
				condimentos,preparacion, subRecetas);
		creador.agregarReceta(nuevaReceta);
		return nuevaReceta;
	}
	
	/* Servicios */

	public boolean esValida() {
		return !ingredientes.isEmpty() && 10 <= totalCalorias()
				&& totalCalorias() <= 5000;
	}

	public boolean tenes(String unIngrediente) {
		return this.ingredientes.containsKey(unIngrediente)||this.condimentos.containsKey(unIngrediente);
	}

	public Float cantidadCondimento(String unCondimento) {
		return this.condimentos.get(unCondimento);
	}

	public boolean puedeSerVistaPor(Usuario usuario) {
		return esElCreador(usuario);
	}

	public boolean puedeSerModificadaPor(Usuario usuario) {
		return esElCreador(usuario);
	}

	//TODO completar. No especificaron como se resuelve este metodo, devuelve "50" para poder testear.
	public int totalCalorias(){return 50;}

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

	public boolean esElCreador(Usuario unUsuario){
		return creador.equals(unUsuario);
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

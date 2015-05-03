package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Collection;

public class RecetaDelSistema {

	protected String nombreDelPlato;
	protected HashMap<String, Double> ingredientes = new HashMap<String, Double>();
	protected HashMap<String, Double> condimentos = new HashMap<String, Double>();
	protected String preparacion;
	protected int totalCalorias;
	protected String dificultad;
	protected Temporada temporada;
	protected Collection<Receta> subReceta;

	/* Constructores */

	public RecetaDelSistema() {
	};// Creado para testear por ahora

	public RecetaDelSistema(String nombreDelPlato,
			HashMap<String, Double> ingredientes,
			HashMap<String, Double> condimentos, String preparacion,
			int calorias, String dificultad, Temporada temporada,
			Collection<Receta> subReceta) {
		this.nombreDelPlato = nombreDelPlato;
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.preparacion = preparacion;
		this.totalCalorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
	}

	/* Servicios */

	public boolean esValida() {
		int totalCalorias = getTotalCalorias();
		return !ingredientes.isEmpty() && 10 <= totalCalorias
				&& totalCalorias <= 5000;
	}

	public boolean tenesIngrediente(String unIngrediente) {
		return this.ingredientes.containsKey(unIngrediente);
	}

	public Double cantidadCondimento(String unCondimento) {
		return this.condimentos.get(unCondimento);
	}

	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario) {
		return true;
	}

	public void serModificadaPor(Usuario unUsuario, String nombre,
			HashMap<String, Double> ingredientes,
			HashMap<String, Double> condimentos, String preparacion,
			int calorias, String dificultad, Temporada temporada,
			Collection<Receta> subReceta) {
		Receta unaReceta = new Receta(nombre, ingredientes, condimentos,
				preparacion, calorias, dificultad, temporada, subReceta,
				unUsuario);
		unUsuario.agregarReceta(unaReceta);
	}

	/* Accessors and Mutators */

	public Collection<String> getNombreIngredientes() {
		return ingredientes.keySet();
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

	public HashMap<String, Double> getIngredientes() {
		return ingredientes;
	}

	public HashMap<String, Double> getCondimentos() {
		return condimentos;
	}

}

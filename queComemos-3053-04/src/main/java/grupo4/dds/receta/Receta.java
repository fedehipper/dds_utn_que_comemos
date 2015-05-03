package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Collection;

public class Receta {

	protected Usuario creador;
	protected String nombreDelPlato;
	protected HashMap<String, Float> ingredientes = new HashMap<String, Float>();
	protected HashMap<String, Float> condimentos = new HashMap<String, Float>();
	protected String preparacion;
	protected int totalCalorias;
	protected String dificultad;
	protected Temporada temporada;
	protected Collection<RecetaPublica> subReceta;

	/* Constructores */

	public Receta(Usuario creador) {
		this.creador = creador;
	}// Creado para testear por ahora

	public Receta(Usuario creador, String nombreDelPlato,
			HashMap<String, Float> ingredientes,
			HashMap<String, Float> condimentos, String preparacion,
			int totalCalorias, String dificultad, Temporada temporada,
			Collection<RecetaPublica> subReceta) {
		this.creador = creador;
		this.nombreDelPlato = nombreDelPlato;
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.preparacion = preparacion;
		this.totalCalorias = totalCalorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.subReceta = subReceta;
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

	public Float cantidadCondimento(String unCondimento) {
		return this.condimentos.get(unCondimento);
	}

	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario){
		return creador.equals(unUsuario);
	}

	// TODO arreglar este metodo
	public void serModificadaPor(Usuario unUsuario, String nombre, HashMap<String, Float> ingredientes, 
			HashMap<String, Float> condimentos, String preparacion,int calorias, String dificultad,Temporada temporada, Collection<RecetaPublica> subReceta){
		if(this.puedeSerVistaOModificadaPor(unUsuario)){
			this.nombreDelPlato = nombre;
			this.ingredientes = ingredientes;
			this.condimentos = condimentos;
			this.preparacion = preparacion;
			this.totalCalorias = calorias;
			this.dificultad = dificultad;
			this.temporada = temporada;			
			this.subReceta= subReceta;}
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

	public HashMap<String, Float> getIngredientes() {
		return ingredientes;
	}

	public HashMap<String, Float> getCondimentos() {
		return condimentos;
	}

}

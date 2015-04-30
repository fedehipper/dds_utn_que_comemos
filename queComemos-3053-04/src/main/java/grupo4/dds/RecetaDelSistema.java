package grupo4.dds;

import java.util.HashMap;

public class RecetaDelSistema {

	private String nombreDelPlato;
	private HashMap<String, Double> ingredientes = new HashMap<String, Double>();
	private HashMap<String, Double> condimentos = new HashMap<String, Double>();
	private String preparacion;
	private int calorias;
	private String dificultad;
	private Temporada temporada;
	private Receta subReceta;

	//------CONSTRUCTORES-----
	public RecetaDelSistema(){};//Creado para testear por ahora
	
	public RecetaDelSistema(String nombreDelPlato,
			HashMap<String, Double> ingredientes, HashMap<String, Double> condimentos,
			String preparacion, int calorias, String dificultad,
			Temporada temporada) {
		this.nombreDelPlato = nombreDelPlato;
		this.ingredientes = ingredientes;
		this.condimentos = condimentos;
		this.preparacion = preparacion;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
	}
	//------CONSTRUCTORES-----
	
	public boolean esValida() {
		return this.tieneIngredientes() && totalCaloriasEntre(10, 5000);
	}

	public boolean tieneIngredientes() {
		return !(this.ingredientes.isEmpty());
	}

	public boolean totalCaloriasEntre(int unValor, int otroValor) {
		return (this.calorias >= unValor) && (this.calorias <= otroValor);
	}

	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario) {
		return true;
	}

	
	//-----Getters y setters-----
	
	public HashMap<String, Double> getIngredientes() {
		return ingredientes;
	}
	
	public HashMap<String, Double> getCondimentos() {
		return condimentos;
	}
	
	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}
}

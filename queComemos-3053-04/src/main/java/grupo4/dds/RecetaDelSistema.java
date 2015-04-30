package grupo4.dds;

import java.util.HashMap;
import java.util.Collection;

public class RecetaDelSistema {

	

	protected String nombreDelPlato;
	protected HashMap<String, Double> ingredientes = new HashMap<String, Double>();
	protected HashMap<String, Double> condimentos = new HashMap<String, Double>();
	protected String preparacion;
	protected int calorias;
	protected String dificultad;
	protected Temporada temporada;
	protected Collection<Receta> subReceta;
	
	//------CONSTRUCTORES-----
	public RecetaDelSistema(){};//Creado para testear por ahora
	
	public RecetaDelSistema(String nombreDelPlato,
			HashMap<String, Double> ingredientes, HashMap<String, Double> condimentos,
			String preparacion, int calorias, String dificultad,
			Temporada temporada, Collection<Receta> subReceta) {
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

	public void serModificadaPor(Usuario unUsuario, String nombre, HashMap<String, Double> ingredientes, 
			HashMap<String, Double> condimentos, String preparacion,int calorias, String dificultad,Temporada temporada, Collection<Receta> subReceta){
		Receta unaReceta= new Receta(nombre,ingredientes,condimentos,preparacion,calorias,dificultad,temporada, subReceta, unUsuario);
		unUsuario.agregarReceta(unaReceta);
	}
	//Posiblemente parezca un metodo con muchos parametros si a alguno se le ocurre como mejorarlo, no lo duden.	
	
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

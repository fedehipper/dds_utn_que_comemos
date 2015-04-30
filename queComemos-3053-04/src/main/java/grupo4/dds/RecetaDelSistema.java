package grupo4.dds;

import java.util.ArrayList;
import java.util.Collection;

public class RecetaDelSistema {

	protected String nombreDelPlato;
	protected Collection<String> ingredientes = new ArrayList<>();
	protected Collection<String> condimentos = new ArrayList<>();
	protected String preparacion;
	protected int calorias;
	protected String dificultad;
	protected Temporada temporada;
	protected Collection<Receta> subReceta;

	//------CONSTRUCTORES-----
	public RecetaDelSistema(){};//Creado para testear por ahora
	
	public RecetaDelSistema(String nombreDelPlato,
			Collection<String> ingredientes, Collection<String> condimentos,
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

	public void serModificadaPor(Usuario unUsuario, String nombre, Collection<String> ingredientes, 
			Collection<String> condimentos, String preparacion,int calorias, String dificultad,Temporada temporada, Collection<Receta> subReceta){
		unUsuario.agregarReceta(nombre, ingredientes, condimentos, preparacion, calorias, dificultad, temporada, subReceta);
	}
	//Posiblemente parezca un metodo con muchos parametros si a alguno se le ocurre como mejorarlo, no lo duden.	
	
	//-----Getters y setters-----
	
	public Collection<String> getIngredientes() {
		return ingredientes;
	}
	
	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}
}

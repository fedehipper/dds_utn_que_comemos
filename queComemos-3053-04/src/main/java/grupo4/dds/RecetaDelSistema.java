package grupo4.dds;

import java.util.ArrayList;
import java.util.Collection;

public class RecetaDelSistema {
	
	private String nombreDelPlato;
	private Collection<String> ingredientes = new ArrayList<>();
	private Collection<String> condimentos = new ArrayList<>();
	private String preparacionDeReceta; 
	private int calorias;
	private String dificultad;
	private Collection<String> temporada = new ArrayList<>();
	private Receta subReceta;
	
	
	public boolean esValida() {
		return this.tieneIngredientes() && totalCaloriasEntre(10,5000);
	}
	
	public boolean tieneIngredientes(){
		return !(this.ingredientes.isEmpty());
	}
	
	public boolean totalCaloriasEntre(int unValor, int otroValor){
		return (this.calorias >= unValor) && (this.calorias <= otroValor) ;
	}

	public Collection<String> getIngredientes() {
		return ingredientes;
	}

	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}
	
	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario){
		return true;
	}


}

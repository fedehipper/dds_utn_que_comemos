package grupo4.dds;

import java.util.ArrayList;
import java.util.Collection;

public class RecetaDelSistema {

	private String nombreDelPlato;
	private Collection<String> ingredientes = new ArrayList<>();
	private Collection<String> condimentos = new ArrayList<>();
	private String preparacion;
	private int calorias;
	private String dificultad;
	private Temporada temporada;
	private Receta subReceta;

	//------CONSTRUCTORES-----
	public RecetaDelSistema(){};//Creado para testear por ahora
	
	public RecetaDelSistema(String nombreDelPlato,
			Collection<String> ingredientes, Collection<String> condimentos,
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
	
	public Collection<String> getIngredientes() {
		return ingredientes;
	}
	
	public void setCalorias(int calorias) {
		this.calorias = calorias;
	}
}

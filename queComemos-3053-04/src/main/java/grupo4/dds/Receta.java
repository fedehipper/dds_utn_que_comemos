package grupo4.dds;

import java.util.HashMap;

public class Receta extends RecetaDelSistema {

	private Usuario creador;
	
	//------CONSTRUCTORES-----
	public Receta(){};
	public Receta(Usuario elCreador){
		creador=elCreador;
	}
	//Creados para testear por ahora
	
	public Receta(String nombreDelPlato,
			HashMap<String, Double> ingredientes, HashMap<String, Double> condimentos, 
			String preparacion,int calorias, String dificultad, 
			Temporada temporada,Usuario elCreador) {
		super(nombreDelPlato, ingredientes, condimentos, preparacion, calorias, dificultad, temporada);		
		this.creador=elCreador;
	}
	//------CONSTRUCTORES-----
	
	
	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario){
		return (unUsuario == creador);
	}
	
}

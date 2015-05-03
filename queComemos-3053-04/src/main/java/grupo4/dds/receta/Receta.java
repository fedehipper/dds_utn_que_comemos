package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Collection;


public class Receta extends RecetaDelSistema {

	private Usuario creador;
	
	
	//------CONSTRUCTORES-----
	
	
	public Receta(){};
	public Receta(Usuario elCreador){
		creador=elCreador;
	}
	
	
	//Creados para testear por ahora
	
	
	public Receta(String nombreDelPlato,
			HashMap<String, Float> ingredientes, HashMap<String, Float> condimentos, 
			String preparacion,int calorias, String dificultad, 
			Temporada temporada,Collection<Receta> subReceta,Usuario elCreador) {
		super(nombreDelPlato, ingredientes, condimentos, preparacion, calorias, dificultad, temporada,subReceta);		
		this.creador=elCreador;
	}
	
	
	//------CONSTRUCTORES-----
		
	
	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario){
		return (unUsuario == creador);
	}
	
	
	public void serModificadaPor(Usuario unUsuario, String nombre, HashMap<String, Float> ingredientes, 
			HashMap<String, Float> condimentos, String preparacion,int calorias, String dificultad,Temporada temporada, Collection<Receta> subReceta){
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
	
	
	//Hay que agregar un Error exeption o algo asi
	
	
}

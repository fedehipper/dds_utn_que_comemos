package grupo4.dds.receta.builder;

import queComemos.entrega3.dominio.Dificultad;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Temporada;

public class BuilderEncabezado {
	
	private EncabezadoDeReceta encabezado;
	
	public BuilderEncabezado(){
		encabezado = new EncabezadoDeReceta();
	}

	public BuilderEncabezado setTotalCalorias(int totalCalorias){
		encabezado.setTotalCalorias(totalCalorias);
		return this;
	}
	
	public BuilderEncabezado setNombreDelPlato(String nombreDelPlato){
		encabezado.setNombreDelPlato(nombreDelPlato);
		return this;
	}
	
	public BuilderEncabezado setTemporada(Temporada temporada){
		encabezado.setTemporada(temporada);
		return this;
	}
	
	public BuilderEncabezado setDificultad(Dificultad dificultad){
		encabezado.setDificultad(dificultad);
		return this;
	}
	
	public EncabezadoDeReceta build(){
		return encabezado;
	}
	
}

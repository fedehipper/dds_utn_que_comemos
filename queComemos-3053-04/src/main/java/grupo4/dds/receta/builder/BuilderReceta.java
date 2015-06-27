package grupo4.dds.receta.builder;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.receta.builder.BuilderEncabezado;
import queComemos.entrega3.dominio.Dificultad;
import grupo4.dds.receta.Temporada;


public class BuilderReceta {

	private Receta receta;
	private BuilderEncabezado encabezado;
	
	public BuilderReceta(){
		this.receta = new Receta();
		this.encabezado=new BuilderEncabezado();
	}
	
	public BuilderReceta setCreador(Usuario creador){
		receta.setCreador(creador);
		return this;
	}
	
	public BuilderReceta setTotalCalorias(int totalCalorias){
		this.encabezado.setTotalCalorias(totalCalorias);
		return this;
	}
	
	public BuilderReceta setNombreDelPlato(String nombreDelPlato){
		this.encabezado.setNombreDelPlato(nombreDelPlato);
		return this;
	}
	
	public BuilderReceta setTemporada(Temporada temporada){
		this.encabezado.setTemporada(temporada);
		return this;
	}

	public BuilderReceta setDificultad(Dificultad dificultad){
		this.encabezado.setDificultad(dificultad);
		return this;
	}

	public BuilderReceta setIngrediente(Ingrediente unIngrediente) {
		this.receta.agregarIngrediente(unIngrediente);
		return this;
	}

	public BuilderReceta setCondimento(Ingrediente unCondimento) {
		this.receta.agregarCondimento(unCondimento);
		return this;
	}

	public BuilderReceta setSubreceta(Receta subreceta) {
		this.receta.agregarSubreceta(subreceta);
		return this;
	}

	public BuilderReceta setPreparacion(String preparacion) {
		this.receta.setPreparacion(preparacion);
		return this;
	}
	
	protected BuilderReceta setEncabezado(EncabezadoDeReceta encabezado) {
		this.receta.setEncabezado(encabezado);
		return this;
	}
	
	public Receta build(){	
		setEncabezado(this.encabezado.build());

		if (!receta.esValida()){
			throw new RuntimeException();
		}
		
		return receta;
	}
	
}

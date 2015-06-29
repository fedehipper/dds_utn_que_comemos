package grupo4.dds.receta.builder;

import grupo4.dds.receta.Receta;

/*import queComemos.entrega3.dominio.Dificultad;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;

import grupo4.dds.receta.Temporada;
import grupo4.dds.receta.builder.BuilderEncabezado;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.receta.builder.*;*/


public class BuilderReceta extends Builder {
	
	public BuilderReceta(){
		this.receta = new Receta();
	}
	/*
	public BuilderReceta setCreador(Usuario creador) {
		super.setCreador(creador, receta);
		return this;
	}

	public BuilderReceta setTotalCalorias(int totalCalorias) {
		super.setTotalCalorias(totalCalorias,encabezado);
		return this;
	}

	public BuilderReceta setNombreDelPlato(String nombreDelPlato) {
		super.setNombreDelPlato(nombreDelPlato,encabezado);
		return this;
	}

	public BuilderReceta setTemporada(Temporada temporada) {
		super.setTemporada(temporada,encabezado);
		return this;
	}

	public BuilderReceta setDificultad(Dificultad dificultad) {
		super.setDificultad(dificultad,encabezado);
		return this;
	}

	public BuilderReceta setIngrediente(Ingrediente unIngrediente) {
		super.setIngrediente(unIngrediente, receta);
		return this;
	}

	public BuilderReceta setCondimento(Ingrediente unCondimento) {
		super.setCondimento(unCondimento, receta);
		return this;
	}

	public BuilderReceta setSubreceta(Receta subreceta) {
		super.setSubreceta(subreceta,receta);
		return this;
	}

	public BuilderReceta setPreparacion(String preparacion) {
		super.setPreparacion(preparacion, receta);
		return this;
	}


	public Receta build() {	
		return super.build(encabezado, receta);

	}
*/
}

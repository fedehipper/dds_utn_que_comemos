package grupo4.dds.receta.builder;

import grupo4.dds.receta.RecetaPublica;

/*import queComemos.entrega3.dominio.Dificultad;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Temporada;
import grupo4.dds.usuario.Usuario;*/

public class BuilderRecetaPublica extends Builder{
	
	public BuilderRecetaPublica(){
		receta=new RecetaPublica();
	}
	/*
	public BuilderRecetaPublica setCreador(Usuario creador) {
		super.setCreador(creador, receta);
		return this;
	}

	public BuilderRecetaPublica setTotalCalorias(int totalCalorias) {
		super.setTotalCalorias(totalCalorias,encabezado);
		return this;
	}

	public BuilderRecetaPublica setNombreDelPlato(String nombreDelPlato) {
		super.setNombreDelPlato(nombreDelPlato,encabezado);
		return this;
	}

	public BuilderRecetaPublica setTemporada(Temporada temporada) {
		super.setTemporada(temporada,encabezado);
		return this;
	}

	public BuilderRecetaPublica setDificultad(Dificultad dificultad) {
		super.setDificultad(dificultad,encabezado);
		return this;
	}

	public BuilderRecetaPublica setIngrediente(Ingrediente unIngrediente) {
		super.setIngrediente(unIngrediente, receta);
		return this;
	}

	public BuilderRecetaPublica setCondimento(Ingrediente unCondimento) {
		super.setCondimento(unCondimento, receta);
		return this;
	}

	public BuilderRecetaPublica setSubreceta(Receta subreceta) {
		super.setSubreceta(subreceta,receta);
		return this;
	}

	public BuilderRecetaPublica setPreparacion(String preparacion) {
		super.setPreparacion(preparacion, receta);
		return this;
	}


	public RecetaPublica build() {	
		super.setEncabezado(encabezado.build(),receta);
		
		if (!receta.esValida()){
			throw new RuntimeException();
		}
		
		if (receta.getCreador()!=null){
			throw new RuntimeException();
		}
		
		return receta;
	}*/
}

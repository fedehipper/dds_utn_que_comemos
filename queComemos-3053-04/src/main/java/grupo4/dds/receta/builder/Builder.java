package grupo4.dds.receta.builder;

import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Temporada;
import grupo4.dds.usuario.Usuario;
import queComemos.entrega3.dominio.Dificultad;

public abstract class Builder {

	protected Receta receta;
	protected BuilderEncabezado encabezado=new BuilderEncabezado();
	
	public Builder setCreador(Usuario creador/*,Receta receta*/) {
		receta.setCreador(creador);
		return this;
	}

	public Builder setTotalCalorias(int totalCalorias/*,BuilderEncabezado encabezado*/) {
		encabezado.setTotalCalorias(totalCalorias);
		return this;
	}

	public Builder setNombreDelPlato(String nombreDelPlato/*,BuilderEncabezado encabezado*/) {
		encabezado.setNombreDelPlato(nombreDelPlato);
		return this;
	}

	public Builder setTemporada(Temporada temporada/*,BuilderEncabezado encabezado*/) {
		encabezado.setTemporada(temporada);
		return this;
	}

	public Builder setDificultad(Dificultad dificultad/*,BuilderEncabezado encabezado*/) {
		encabezado.setDificultad(dificultad);
		return this;
	}

	public Builder setIngrediente(Ingrediente unIngrediente/*,Receta receta*/) {
		receta.agregarIngrediente(unIngrediente);
		return this;
	}

	public Builder setCondimento(Ingrediente unCondimento/*,Receta receta*/) {
		receta.agregarCondimento(unCondimento);
		return this;
	}

	public Builder setSubreceta(Receta subreceta/*,Receta receta*/) {
		receta.agregarSubreceta(subreceta);
		return this;
	}

	public Builder setPreparacion(String preparacion/*,Receta receta*/) {
		receta.setPreparacion(preparacion);
		return this;
	}

	public Builder setEncabezado(EncabezadoDeReceta encabezado/*,Receta receta*/) {
		receta.setEncabezado(encabezado);
		return this;
	}

	public Receta build(/*BuilderEncabezado encabezado,Receta receta*/) {	
		setEncabezado(encabezado.build()/*,receta*/);
	
		if (!receta.esValida()){
			throw new RuntimeException();
		}
		
		return receta;
	}
}
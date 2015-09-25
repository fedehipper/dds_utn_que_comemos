package grupo4.dds.receta.builder;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import grupo4.dds.excepciones.RecetaInvalida;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Temporada;
import grupo4.dds.usuario.Usuario;
import queComemos.entrega3.dominio.Dificultad;

public abstract class Builder implements WithGlobalEntityManager {

	protected Receta receta;
	protected BuilderEncabezado encabezado=new BuilderEncabezado();
	
	public Builder setCreador(Usuario creador) {
		receta.setCreador(creador);
		return this;
	}

	public Builder setTotalCalorias(int totalCalorias) {
		encabezado.setTotalCalorias(totalCalorias);
		return this;
	}

	public Builder setNombreDelPlato(String nombreDelPlato) {
		encabezado.setNombreDelPlato(nombreDelPlato);
		return this;
	}

	public Builder setTemporada(Temporada temporada) {
		encabezado.setTemporada(temporada);
		return this;
	}

	public Builder setDificultad(Dificultad dificultad) {
		encabezado.setDificultad(dificultad);
		return this;
	}

	public Builder setIngrediente(Ingrediente unIngrediente) {
		receta.agregarIngrediente(unIngrediente);
		return this;
	}

	public Builder setCondimento(Ingrediente unCondimento) {
		receta.agregarCondimento(unCondimento);
		return this;
	}

	public Builder setSubreceta(Receta subreceta) {
		receta.agregarSubreceta(subreceta);
		return this;
	}

	public Builder setPreparacion(String preparacion) {
		receta.setPreparacion(preparacion);
		return this;
	}

	public Builder setEncabezado(EncabezadoDeReceta encabezado) {
		receta.setEncabezado(encabezado);
		return this;
	}

	public Receta build() {	
		setEncabezado(encabezado.build());
	
		if (!receta.esValida()){
			throw new RecetaInvalida();
		}
		
		entityManager().persist(receta);
		
		return receta;
	}
}
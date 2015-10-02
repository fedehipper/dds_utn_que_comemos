package grupo4.dds.receta.builder;

import java.util.List;

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
	protected EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
	
	public Builder() {
		receta = receta();
		receta.setEncabezado(encabezado);
	}
	
	protected abstract Receta receta();

	public Builder creador(Usuario creador) {
		receta.setCreador(creador);
		return this;
	}

	public Builder calorias(int calorias) {
		encabezado.setTotalCalorias(calorias);
		return this;
	}

	public Builder nombre(String nombreDelPlato) {
		encabezado.setNombreDelPlato(nombreDelPlato);
		return this;
	}

	public Builder temporada(Temporada temporada) {
		encabezado.setTemporada(temporada);
		return this;
	}

	public Builder facil() {
		encabezado.setDificultad(Dificultad.FACIL);
		return this;
	}
	
	public Builder mediana() {
		encabezado.setDificultad(Dificultad.MEDIANA);
		return this;
	}
	
	public Builder dificil() {
		encabezado.setDificultad(Dificultad.DIFICIL);
		return this;
	}

	public Builder ingrediente(Ingrediente unIngrediente) {
		receta.agregarIngrediente(unIngrediente);
		return this;
	}
	
	public Builder ingredientes(List<Ingrediente> ingredientes) {
		receta.agregarIngredientes(ingredientes);
		return this;
	}

	public Builder condimento(Ingrediente unCondimento) {
		receta.agregarCondimento(unCondimento);
		return this;
	}

	public Builder condimentos(List<Ingrediente> condimentos) {
		receta.agregarCondimentos(condimentos);
		return this;
	}

	public Builder subreceta(List<Receta> subrecetas) {
		receta.agregarSubrecetas(subrecetas);
		return this;
	}

	public Builder subreceta(Receta subreceta) {
		receta.agregarSubreceta(subreceta);
		return this;
	}

	public Builder preparacion(String preparacion) {
		receta.setPreparacion(preparacion);
		return this;
	}

	public Builder encabezado(EncabezadoDeReceta encabezado) {
		receta.setEncabezado(encabezado);
		return this;
	}

	public Receta build() {	
	
		if (!receta.esValida()){
			throw new RecetaInvalida();
		}
		
		entityManager().persist(receta);
		
		return receta;
	}
}
package grupo4.dds.receta;

import java.util.List;

import queComemos.entrega3.dominio.Dificultad;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.usuario.Usuario;


public class BuilderReceta {
//Receta
	public Receta buildRecetaPublicaTipo1(EncabezadoDeReceta encabezado, String preparacion) {
		return build(null, encabezado, null, null, null, preparacion,new RecetaPublica());
	}
	
	public Receta buildRecetaPublicaTipo2(Usuario creador, EncabezadoDeReceta encabezado,String preparacion,List<Ingrediente> ingredientes) {
		return build(creador, encabezado,ingredientes,null,null,preparacion, new RecetaPublica());
	}
	
	public Receta buildRecetaPublicaTipo3(Usuario creador, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, List<Receta> subrecetas, String preparacion) {
		
		return build(creador, encabezado, ingredientes, condimentos, subrecetas, preparacion, new RecetaPublica());
	}
	
	public Receta buildRecetaTipo1(Usuario creador, EncabezadoDeReceta encabezado, String preparacion) {
		return buildRecetaTipo3(creador, encabezado, null, null, null, preparacion);
	}
	
	public Receta buildRecetaTipo2(Usuario creador, EncabezadoDeReceta encabezado,String preparacion,List<Ingrediente> ingredientes){
		return buildRecetaTipo3(creador, encabezado,ingredientes,null,null, preparacion);
	}
	
	public Receta buildRecetaTipo3(Usuario creador, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, List<Receta> subrecetas, String preparacion) {
		
		return build(creador, encabezado, ingredientes, condimentos, subrecetas, preparacion, new Receta());
	}
	
	private Receta build(Usuario creador, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, List<Receta> subrecetas, String preparacion, Receta receta)
	{	
		if (creador!=null){receta.agregarCreador(creador);};
		if (encabezado != null){receta.agregarEncabezado(encabezado);};
		if (!(ingredientes.isEmpty())){receta.agregarIngredientes(ingredientes);};
		if (!(condimentos.isEmpty())){receta.agregarCondimentos(condimentos);};
		if (!(subrecetas.isEmpty())){receta.agregarSubrecetas(subrecetas);};
		if (preparacion!=null){receta.agregarPreparacion(preparacion);};
		
		return receta;
	}
//Encabezado
		
	public EncabezadoDeReceta buildEncabezadoTipo1(String nombreDelPlato, Temporada temporada, Dificultad dificultad) {
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setNombreDelPlato(nombreDelPlato);
		encabezado.setTemporada(temporada);
		encabezado.setDificultad(dificultad);
		
		return encabezado;
	}
	
	public EncabezadoDeReceta buildEncabezadoTipo2(String nombreDelPlato, Temporada temporada, Dificultad dificultad, int calorias) {
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setNombreDelPlato(nombreDelPlato);
		encabezado.setTemporada(temporada);
		encabezado.setDificultad(dificultad);
		encabezado.setTotalCalorias(calorias);
		
		return encabezado;
	}
}

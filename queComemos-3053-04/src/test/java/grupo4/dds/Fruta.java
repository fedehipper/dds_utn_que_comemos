package grupo4.dds;

import queComemos.entrega3.dominio.Dificultad;
import grupo4.dds.persistor.MongoPersistor;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Temporada;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;
import grupo4.dds.usuario.Usuario;

public class Fruta {
	
	public static void main(String[] args){
		
		Usuario fecheSena;
	    Usuario arielFolino;
	    Receta receta;
	    Receta receta2;
	    Receta receta3;
		
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false, null);
		arielFolino = Usuario.crearPerfil("Ariel Folino", null, null, 1.69f, 96.0f, null, false, null);
		
		receta = new BuilderReceta().setCreador(fecheSena).
				 setPreparacion("Preparación antes de modificar").
				 setTotalCalorias(4500).setEncabezado(new EncabezadoDeReceta("Sopa", Temporada.INVIERNO , Dificultad.DIFICIL)).
				 setIngrediente(new Ingrediente("frutas", 0f)).
				 build();
		
		receta2 = new BuilderReceta().setCreador(arielFolino).setPreparacion("Preparación propia").
				 setTotalCalorias(400).setNombreDelPlato("Pebete").
				 setIngrediente(new Ingrediente("test",0f)).
				 build();
		
		receta3 = new BuilderRecetaPublica().setNombreDelPlato("la papa").setCreador(arielFolino).setIngrediente(new Ingrediente("caca", 0f)).setTotalCalorias(500).build();
		
		MongoPersistor.get().dataStore().save(fecheSena);
		MongoPersistor.get().dataStore().save(arielFolino);
		
		MongoPersistor.get().dataStore().save(receta);
		MongoPersistor.get().dataStore().save(receta2);
		MongoPersistor.get().dataStore().save(receta3);
		
		fecheSena.marcarFavorita(receta);
		
		arielFolino.marcarFavorita(receta2);
		
		fecheSena.marcarFavorita(receta3);
		
		fecheSena.desmarcarFavorita(receta);
		
		
		
		
		
		
	}

}

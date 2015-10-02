package grupo4.dds.receta.builder;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;

public class BuilderRecetaPublica extends Builder{
	
	@Override
	protected Receta receta(){
		return new RecetaPublica();
	}
	
}

package grupo4.dds.receta.builder;

import grupo4.dds.receta.Receta;

public class BuilderReceta extends Builder {
	
	@Override
	protected Receta receta(){
		return new Receta();
	}
	
}

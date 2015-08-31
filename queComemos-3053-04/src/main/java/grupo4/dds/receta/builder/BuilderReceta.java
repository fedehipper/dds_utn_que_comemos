package grupo4.dds.receta.builder;

import grupo4.dds.persistor.MongoPersistor;
import grupo4.dds.receta.Receta;

public class BuilderReceta extends Builder {
	
	public BuilderReceta(){
		this.receta = new Receta();
	}
	
	public Receta build() {
		if(receta.getCreador() != null)
			MongoPersistor.get().dataStore().save(receta.getCreador());
		
		return super.build();
	}
	
}

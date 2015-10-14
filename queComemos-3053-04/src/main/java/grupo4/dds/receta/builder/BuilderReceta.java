package grupo4.dds.receta.builder;

import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.receta.Receta;

public class BuilderReceta extends Builder<Receta> {

	@Override
	@CoberturaIgnore
	protected Receta receta() {
		return new Receta();
	}
	
}

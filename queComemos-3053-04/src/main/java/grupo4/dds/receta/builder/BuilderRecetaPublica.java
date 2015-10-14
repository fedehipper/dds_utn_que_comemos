package grupo4.dds.receta.builder;

import grupo4.dds.excepciones.NoSePuedeSetearCreadorARecetaPublica;
import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.Usuario;

public class BuilderRecetaPublica extends Builder<RecetaPublica>{
	
	@Override
	@CoberturaIgnore
	protected RecetaPublica receta() {
		return new RecetaPublica();
	}
	
	@Override
	public Builder<RecetaPublica> creador(Usuario creador) {
		throw new NoSePuedeSetearCreadorARecetaPublica();
	}
	
}

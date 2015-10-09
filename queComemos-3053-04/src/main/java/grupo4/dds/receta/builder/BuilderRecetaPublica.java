package grupo4.dds.receta.builder;

import grupo4.dds.excepciones.NoSePuedeSetearCreadorARecetaPublica;
import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.Usuario;

public class BuilderRecetaPublica extends Builder{
	
	static public Receta buildRecetaValida() {	
		Builder builder = new BuilderRecetaPublica();
		return builder.calorias(10).ingrediente(Ingrediente.nuevoIngrediente("", 0)).build();
	}
	
	@Override
	@CoberturaIgnore
	protected Receta receta(){
		return new RecetaPublica();
	}
	
	@Override
	public Builder creador(Usuario creador) {
		throw new NoSePuedeSetearCreadorARecetaPublica();
	}
	
}

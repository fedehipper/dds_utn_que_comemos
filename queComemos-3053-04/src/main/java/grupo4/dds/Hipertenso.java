package grupo4.dds;


public class Hipertenso implements Condicion {

	public boolean esValido(Usuario usuario) {

		return usuario.getPreferenciasAlimenticias().size() > 0;
	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return usuario.tieneRutinaActivaConEjercicioAdicional();
	}
	
	//----------------------------------
	
	public boolean noEsRecomendable(Receta receta) {
		return (receta.getIngredientes().contains("sal") || (receta.getIngredientes().contains("caldo")));
	}

 
}

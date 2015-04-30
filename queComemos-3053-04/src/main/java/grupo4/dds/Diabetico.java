package grupo4.dds;

public class Diabetico implements Condicion {

	public boolean esValido(Usuario usuario) {

		return (usuario.getSexo() != null)
				&& (usuario.getPreferenciasAlimenticias().size() > 0);
	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return (usuario.tieneRutinaActivaConEjercicioAdicional()
				|| (usuario.tieneRutinaActivaSinEjercicioAdicional()))
				&& (!(usuario.pesaMasDe(70.0)));

	}

	public boolean esRecomendable(Receta receta) {
		return (receta.getCondimentos().get("azucar") <= 100);
	}
	
	//return !(receta.getIngredientes().containsKey("sal") || (receta.getIngredientes().containsKey("caldo")));
}

package grupo4.dds;

public class Celiaco implements Condicion {

	public boolean esValido(Usuario usuario) {
		return true;
	}

	public boolean cumpleNecesidades(Usuario usuario) {
		return true;
	}
	
	public boolean noEsRecomendable(Receta receta) {
		return true;
	}

}

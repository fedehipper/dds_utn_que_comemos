package grupo4.dds;

public interface Condicion {

	public boolean esValido(Usuario usuario);

	public boolean cumpleNecesidades(Usuario usuario);

	public boolean noEsRecomendable(Receta receta);
}

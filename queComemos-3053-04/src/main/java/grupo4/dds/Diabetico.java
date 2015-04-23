package grupo4.dds;

public class Diabetico implements Condicion {

	
	public boolean esValido(Usuario usuario) {
		
		return (usuario.getSexo() != null) && (usuario.getPreferenciasAlimenticias().size() > 0) ;
	}

}

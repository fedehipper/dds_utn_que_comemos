package grupo4.dds;

public class Hipertenso implements Condicion {

	
	public boolean esValido(Usuario usuario) {
		
		return usuario.getPreferenciasAlimenticias().size() > 0;
	}

}

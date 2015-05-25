package grupo4.dds.receta;

import grupo4.dds.excepciones.EsInadecuadaDespuesDeModificar;
import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;

import java.util.List;

public class RecetaPublica extends Receta {

	/* Constructores */

	public RecetaPublica() {}

	public RecetaPublica(EncabezadoDeReceta encabezado, String preparacion) {
		super(null, encabezado, preparacion);
	}

	/* Servicios */
	
	public boolean puedeSerVistaPor(Usuario unUsuario) {
		return true;
	}

	public boolean puedeSerModificadaPor(Usuario unUsuario) {
		return true;
	}
	
	public boolean puedeSerAgregadaPor(Usuario usuario) {
		return false;
	}

	public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado,
			List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, String preparacion,
			List<Receta> subRecetas) {

		if (!puedeSerModificadaPor(usuario))
			throw new NoSePuedeModificarLaReceta();
		
		Receta receta = convertirEnPrivada(usuario);
		receta.modificarReceta(usuario, encabezado, ingredientes, condimentos, preparacion, subRecetas);
		
		try {
		usuario.agregarReceta(receta);
		}
		catch(NoSePuedeAgregarLaReceta e){
			throw new EsInadecuadaDespuesDeModificar();
		}
	}

	private Receta convertirEnPrivada(Usuario usuario) {
		return new Receta(usuario, encabezado, ingredientes, condimentos, subrecetas, preparacion);
	}

}

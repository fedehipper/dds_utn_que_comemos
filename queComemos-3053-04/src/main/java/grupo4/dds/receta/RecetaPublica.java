package grupo4.dds.receta;

import grupo4.dds.excepciones.EsInadecuadaDespuesDeModificar;
import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("publica")
public class RecetaPublica extends Receta {

	/* Constructores */

	public RecetaPublica(){}
	
	public static RecetaPublica crearNueva(EncabezadoDeReceta encabezado, String preparacion) {
		RecetaPublica self = new RecetaPublica(encabezado, preparacion);
		
		RepositorioDeRecetas.get().agregarReceta(self);
		
		self.entityManager().persist(self);
		
		return self;
	}

	private RecetaPublica(EncabezadoDeReceta encabezado, String preparacion) {
		super(null, encabezado, null, null, null, preparacion);
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

	public void modificarReceta(Usuario usuario, EncabezadoDeReceta encabezado, List<Ingrediente> ingredientes,
			List<Ingrediente> condimentos, String preparacion, List<Receta> subRecetas) {

		//TODO: descomentar cuando sea posible que se de esta situación
//		if (!puedeSerModificadaPor(usuario))
//			throw new NoSePuedeModificarLaReceta();
		
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
		return Receta.crearNueva(usuario, encabezado, 
				new ArrayList<Ingrediente>(ingredientes), 
				new ArrayList<Ingrediente>(condimentos), 
				new ArrayList<Receta>(subrecetas), preparacion);
	}

}

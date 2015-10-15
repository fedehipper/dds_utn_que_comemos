package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.MappedSuperclass;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

@MappedSuperclass
public abstract class AbstractRecetasMasConsultadas extends Monitor implements WithGlobalEntityManager {

	protected String condicionOrden;
	
	public void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		resultadoConsulta.forEach(r -> usuario.consulto(r));
	}
	
	public List<Receta> recetasMasConsultadas(int cantidad) {
		String query = "from Receta order by " + condicionOrden + " desc";
		return entityManager().createQuery(query, Receta.class).setMaxResults(cantidad).getResultList();
	}	

}
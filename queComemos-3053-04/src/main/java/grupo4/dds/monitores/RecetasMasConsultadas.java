package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("mas_consultadas")
public class RecetasMasConsultadas extends AbstractRecetasMasConsultadas {

	@Transient
	protected Map<Receta, Integer> recetasConsultadas = new HashMap<>();
	
	@Override
	public void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {		
		super.seRealizoUnaConsulta(recetasConsultadas, resultadoConsulta);
	}
	
	public Map<Receta, Integer> recetasMasConsultadas(int cantidad) {
		return super.recetasMasConsultadas(recetasConsultadas, cantidad);
	}

}
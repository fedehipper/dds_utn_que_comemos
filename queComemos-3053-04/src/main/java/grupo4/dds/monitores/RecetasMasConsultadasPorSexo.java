package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

public class RecetasMasConsultadasPorSexo extends AbstractRecetasMasConsultadas {
	
	@Transient
	private Map<Receta, Integer> recetasConsultadasHombres = new HashMap<>();
	@Transient
	private Map<Receta, Integer> recetasConsultadasMujeres = new HashMap<>();
	
	@Override
	public void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {	
		super.seRealizoUnaConsulta(usuario.esHombre() ? recetasConsultadasHombres : recetasConsultadasMujeres, resultadoConsulta);
	}
	
	public Map<Receta, Integer> recetasMasConsultadasPor(Sexo sexo, int cantidad) {
		return super.recetasMasConsultadas(sexo.equals(Sexo.MASCULINO) ? recetasConsultadasHombres : recetasConsultadasMujeres, cantidad);
	}	
	
}
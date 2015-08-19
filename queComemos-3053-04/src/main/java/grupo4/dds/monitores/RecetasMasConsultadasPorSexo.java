package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecetasMasConsultadasPorSexo extends AbstractRecetasMasConsultadas {
	
	private Map<Receta, Integer> recetasConsultadasHombres = new HashMap<>();
	private Map<Receta, Integer> recetasConsultadasMujeres = new HashMap<>();
	
	@Override
	public void notificarConsulta(List<Receta> resultadoConsulta, Usuario usuario) {	
		super.seRealizoUnaConsulta(usuario.esHombre() ? recetasConsultadasHombres : recetasConsultadasMujeres, resultadoConsulta);
	}
	
	public Map<Receta, Integer> recetasMasConsultadasPor(Sexo sexo, int cantidad) {
		return super.recetasMasConsultadas(sexo.equals(Sexo.MASCULINO) ? recetasConsultadasHombres : recetasConsultadasMujeres, cantidad);
	}	
	
}
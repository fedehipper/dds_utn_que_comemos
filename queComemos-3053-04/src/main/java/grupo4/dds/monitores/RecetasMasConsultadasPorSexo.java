package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Sexo;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

public class RecetasMasConsultadasPorSexo extends AbstractRecetasMasConsultadas {
	
	public Map<Receta, Integer> recetasMasConsultadasPor(Sexo sexo, int cantidad) {
		
		String query;
		
		if(sexo.equals(Sexo.MASCULINO))
			query = "from Receta order by consultasHombres";
		else
			query = "from Receta order by consultasMujeres";
		
		TypedQuery<Receta> typedQuery = entityManager().createQuery(query, Receta.class);
		
		return typedQuery.getResultList().stream().collect(Collectors.toMap(r -> r, Receta::totalConsultas));
	}	
	
}
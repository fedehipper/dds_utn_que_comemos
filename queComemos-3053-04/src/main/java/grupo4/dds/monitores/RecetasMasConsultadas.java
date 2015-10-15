package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.TypedQuery;

@Entity
@DiscriminatorValue("mas_consultadas")
public class RecetasMasConsultadas extends AbstractRecetasMasConsultadas {

	public Map<Receta, Integer> recetasMasConsultadas(int cantidad) {
		
		TypedQuery<Receta> query = entityManager().createQuery("from Receta order by (consultasHombres + consultasMujeres)", Receta.class);
		
		return query.getResultList().stream().collect(Collectors.toMap(r -> r, Receta::totalConsultas));
	}

}
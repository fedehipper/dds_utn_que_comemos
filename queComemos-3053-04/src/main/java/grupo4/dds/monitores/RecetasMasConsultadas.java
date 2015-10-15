package grupo4.dds.monitores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("mas_consultadas")
public class RecetasMasConsultadas extends AbstractRecetasMasConsultadas {
	
	public RecetasMasConsultadas() {
		condicionOrden = "(consultasHombres + consultasMujeres)";
	}

}
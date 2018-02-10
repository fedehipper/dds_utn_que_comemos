package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Sexo;
import static grupo4.dds.usuario.Sexo.MASCULINO;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("mas_consultadas_por_sexo")
public class RecetasMasConsultadasPorSexo extends AbstractRecetasMasConsultadas {

    public List<Receta> recetasMasConsultadasPor(Sexo sexo, int cantidad) {
        condicionOrden = sexo.equals(MASCULINO) ? "consultasHombres" : "consultasMujeres";
        return recetasMasConsultadas(cantidad);
    }

}

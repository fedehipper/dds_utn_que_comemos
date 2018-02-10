package grupo4.dds.receta;

import javax.persistence.Embeddable;
import lombok.Data;
import queComemos.entrega3.dominio.Dificultad;

@Embeddable
@Data
public class EncabezadoDeReceta {

    protected String nombreDelPlato;
    protected Temporada temporada;
    protected int totalCalorias;
    protected Dificultad dificultad;

    public EncabezadoDeReceta() {
    }

    public EncabezadoDeReceta(String nombreDelPlato, Temporada temporada, Dificultad dificultad) {
        this.nombreDelPlato = nombreDelPlato;
        this.temporada = temporada;
        this.dificultad = dificultad;
    }

    public EncabezadoDeReceta(String nombreDelPlato, Temporada temporada, Dificultad dificultad, int calorias) {
        this.nombreDelPlato = nombreDelPlato;
        this.temporada = temporada;
        this.dificultad = dificultad;
        this.totalCalorias = calorias;
    }

}

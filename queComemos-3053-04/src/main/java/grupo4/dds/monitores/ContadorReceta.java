package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Usuarios")
@Data
public class ContadorReceta {

    @Id
    @GeneratedValue
    @Column(name = "id_usuario")
    private long id;

    private Receta receta;
    private int cantConsultas;

}

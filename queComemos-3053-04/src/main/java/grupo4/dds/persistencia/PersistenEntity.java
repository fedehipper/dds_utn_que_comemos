package grupo4.dds.persistencia;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class PersistenEntity {

    @Id
    @GeneratedValue
    private long id;
}

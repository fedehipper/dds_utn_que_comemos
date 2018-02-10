package grupo4.dds.receta;

import static java.util.Arrays.asList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Ingredientes")
@Data
public class Ingrediente {

    @Id
    @GeneratedValue
    @Column(name = "id_ingrediente")
    private long id;

    public String nombre;
    private float cantidad;

    private Ingrediente() {
    }

    public static Ingrediente nuevaComida(String nombre) {
        return nuevoIngrediente(nombre, 0);
    }

    public static Ingrediente nuevoIngrediente(String nombre, float cantidad) {
        return new Ingrediente(nombre, cantidad);
    }

    public static Ingrediente nuevoCondimento(String nombre, float cantidad) {
        return nuevoIngrediente(nombre, cantidad);
    }

    private Ingrediente(String nombre, float cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public boolean esCarne() {
        return asList("carne", "chivito", "chori", "pollo").contains(nombre);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) obj;
        if (nombre == null) {
            if (other.nombre != null) {
                return false;
            }
        } else if (!nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

}

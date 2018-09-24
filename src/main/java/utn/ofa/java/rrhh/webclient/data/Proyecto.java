package utn.ofa.java.rrhh.webclient.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Proyecto implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double presupuestoMaximo;
    
    @ManyToOne @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;
    
    @ManyToMany
    @JoinTable(name = "Proyecto_Empleado", joinColumns = @JoinColumn(name = "ID_PROYECTO"), inverseJoinColumns = @JoinColumn(name = "ID_EMPLEADO"))
    private List<Empleado> empleados;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPresupuestoMaximo(Double presupuestoMaximo) {
        this.presupuestoMaximo = presupuestoMaximo;
    }

    public Double getPresupuestoMaximo() {
        return presupuestoMaximo;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Proyecto)) {
            return false;
        }
        final Proyecto other = (Proyecto) object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        if (!(nombre == null ? other.nombre == null : nombre.equals(other.nombre))) {
            return false;
        }
        if (!(descripcion == null ? other.descripcion == null : descripcion.equals(other.descripcion))) {
            return false;
        }
        if (!(presupuestoMaximo == null ? other.presupuestoMaximo == null :
              presupuestoMaximo.equals(other.presupuestoMaximo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result = PRIME * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = PRIME * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = PRIME * result + ((presupuestoMaximo == null) ? 0 : presupuestoMaximo.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return this.getId() + " - " + this.getNombre() + ": " + this.getDescripcion() + " (Presupuesto M�ximo: " + this.getPresupuestoMaximo() + ")";
//        return "ClassName{" + "nombre=" + nombre + "\r\n" + "descripcion=" + descripcion + '}';
    }


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setEmpleados(List empleados) {
        this.empleados = empleados;
    }

    public List getEmpleados() {
        return empleados;
    }

}

package utn.ofa.java.rrhh.webclient.data;



import java.util.Date;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table (name="Empleados")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "TIPO_EMPLEADO" )
public abstract class Empleado {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id;
    String nombre;
    String correoElectronico;
    String cuil;
    @Column (name="fecha_ingreso")
    Date fechaIngreso;
    @Column (name="hs_trabajadas")
    Integer horasTrabajadas;
    
//    @ManyToMany(mappedBy = "empleados")
//    protected List<Proyecto> proyectosAsignados;
    
    public Empleado () {}
    
    public Empleado(Integer pId, String pNombre, String pCorreoElectronico, String pCuil, Date pFechaIngreso, Integer pHorasTrabajadas) {
        this.id = pId;
        this.nombre = pNombre;
        this.correoElectronico = pCorreoElectronico;
        this.cuil = pCuil;
        this.fechaIngreso = pFechaIngreso;
        this.horasTrabajadas = pHorasTrabajadas;
    }

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

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getCuil() {
        return cuil;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public abstract Double salario();

    public abstract boolean esEfectivo();

    public abstract boolean esContratado();
/*
    public void setProyectosAsignados(List proyectosAsignados) {
        this.proyectosAsignados = proyectosAsignados;
    }

    public List getProyectosAsignados() {
        return proyectosAsignados;
    }
*/
}

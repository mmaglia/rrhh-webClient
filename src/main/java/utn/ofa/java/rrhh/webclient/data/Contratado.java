package utn.ofa.java.rrhh.webclient.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "2")
public class Contratado extends Empleado {
    
    @Column (name="costo_hora")
    Integer costoHora;


    public void setCostoHora(Integer costoHora) {
        this.costoHora = costoHora;
    }

    public Integer getCostoHora() {
        return this.costoHora;
    }

    @Override
    public boolean esEfectivo() {
        return false;
    }

    @Override
    public boolean esContratado() {
        return true;
    }

    @Override
    public Double salario() {
        return (this.getHorasTrabajadas().doubleValue() * this.getCostoHora().doubleValue());
    }
}

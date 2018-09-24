package utn.ofa.java.rrhh.webclient.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "1")
public class Efectivo extends Empleado {
    @Column (name="sueldo_basico")
    Double sueldoBasico;
    Double comisiones;
    @Column (name="hs_minimas")
    Integer horasMinimasObligatorias;
    
    
    public Efectivo() {
        super();
    }

    public Efectivo(Integer pId, String pNombre, String pCorreoElectronico, String pCuil, Date pFechaIngreso, Integer pHorasTrabajadas, Double pSueldoBasico, Double pComisiones, Integer pHorasMinimasObligatorias) {
        super(pId, pNombre, pCorreoElectronico, pCuil, pFechaIngreso, pHorasTrabajadas);
        
        this.sueldoBasico = pSueldoBasico;
        this.comisiones = pComisiones;
        this.horasMinimasObligatorias = pHorasMinimasObligatorias;
    }

    public void setSueldoBasico(Double sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    public Double getSueldoBasico() {
        return sueldoBasico;
    }

    public void setComisiones(Double comisiones) {
        this.comisiones = comisiones;
    }

    public Double getComisiones() {
        return comisiones;
    }

    public void setHorasMinimasObligatorias(Integer horasMinimasObligatorias) {
        this.horasMinimasObligatorias = horasMinimasObligatorias;
    }

    public Integer getHorasMinimasObligatorias() {
        return horasMinimasObligatorias;
    }

    @Override
    public boolean esEfectivo() {
        return true;
    }

    @Override
    public boolean esContratado() {
        return false;
    }

    @Override
    public Double salario() {
        double salario = this.sueldoBasico + this.comisiones;


        if (this.horasTrabajadas > this.horasMinimasObligatorias) {
            salario += (this.horasTrabajadas - this.horasMinimasObligatorias) * (this.sueldoBasico / 20);
        }
             
        return salario;
    }
}

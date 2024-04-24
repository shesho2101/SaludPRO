/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.tratMedico;

import principal.dominio.medico.Medico;
import principal.dominio.tratamiento.Tratamiento;

/**
 *
 * @author PC
 */
public class TratamientoMedico {
    
    private Tratamiento trat;
    private Medico med;

    public TratamientoMedico() {
    }

    public Tratamiento getTrat() {
        return trat;
    }

    public void setTrat(Tratamiento trat) {
        this.trat = trat;
    }

    public Medico getMed() {
        return med;
    }

    public void setMed(Medico med) {
        this.med = med;
    }

    @Override
    public String toString() {
        return "TratamientoMedico{" + trat + ", " + med + '}';
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.tratMedicamento;

import principal.dominio.medicamento.Medicamento;
import principal.dominio.tratamiento.Tratamiento;

/**
 *
 * @author PC
 */
public class TratamientoMedicamento {
    
    private Tratamiento trat;
    private Medicamento medi;

    public TratamientoMedicamento() {
    }

    public Tratamiento getTrat() {
        return trat;
    }

    public void setTrat(Tratamiento trat) {
        this.trat = trat;
    }

    public Medicamento getMedi() {
        return medi;
    }

    public void setMedi(Medicamento medi) {
        this.medi = medi;
    }

    @Override
    public String toString() {
        return "TratamientoMedicamento{" + trat + ", " + medi + '}';
    }
    
    
    
}

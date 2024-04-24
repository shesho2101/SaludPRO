/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.historialClinico;

import principal.dominio.paciente.Paciente;

/**
 *
 * @author PC
 */
public class HistorialClinico {
    
    private StringBuilder historial;
    private Paciente pac;

    public HistorialClinico(StringBuilder historial) {
        this.historial = historial;
    }

    public HistorialClinico() {
    }

    public StringBuilder getHistorial() {
        return historial;
    }

    public void setHistorial(StringBuilder historial) {
        this.historial = historial;
    }

    public Paciente getPac() {
        return pac;
    }

    public void setPac(Paciente pac) {
        this.pac = pac;
    }

    @Override
    public String toString() {
        return "HistorialClinico\n" + historial;
    }
    
    
    
}

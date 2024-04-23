/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import principal.dominio.cita.CitaServices;
import principal.dominio.historialClinico.HistorialClinicoServices;
import principal.dominio.paciente.GrupoSanguineo;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.user.UsuarioServices;



/**
 *
 * @author PC
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        
        UsuarioServices us = new UsuarioServices();
        PacienteServices ps = new PacienteServices();
        
        CitaServices cs = new CitaServices();
        
        cs.createCita(1, "20", "2024-12-02", "10");
        
        
    
    }
    
}

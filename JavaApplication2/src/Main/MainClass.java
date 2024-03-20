/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import principal.cbd.UsrCBD;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.userPass.Usr;

/**
 *
 * @author PC
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        PacienteServices pacS = new PacienteServices();
        MedicoServices medS = new MedicoServices();
        UsrCBD ctr = new UsrCBD();
        System.out.println(pacS.searchPerId("1097182129").getInstance());
        try {
            //System.out.println(ctr.listUsr());
        } catch (Exception e) {
        }
    }
    
}

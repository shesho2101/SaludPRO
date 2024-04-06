/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import principal.cbd.UsrCBD;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.user.Usuario;

/**
 *
 * @author PC
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        
        PacienteServices pacS = new PacienteServices();
        MedicoServices medS = new MedicoServices();
        UsrCBD ctr = new UsrCBD();
        ConsultorioServices cons = new ConsultorioServices();
        //cons.saveConsultorio("Consultorio A" , 1);
        //cons.imprimirConsultorios();
        //System.out.println(cons.searchPerCod(1));
        medS.createMed("10", "q", 1);
        //ctr.saveUsr(new Usuario("10", "juan", "tabarespelaezs"));
        try {
            //System.out.println(ctr.listUsr());
        } catch (Exception e) {
        }
    }
    
}

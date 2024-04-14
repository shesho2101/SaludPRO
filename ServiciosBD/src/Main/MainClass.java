/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import java.util.Date;
import principal.DAO.Entities.UsuarioDAO;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.user.Usuario;
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
        
        PacienteServices pacS = new PacienteServices();
        MedicoServices medS = new MedicoServices();
        UsuarioServices usrS = new UsuarioServices();
        ConsultorioServices cons = new ConsultorioServices();
        //usrS.createUsr("112", "Maria", "Abello");
        //pacS.createPac("112", "2005-10-9", "Female");
        
        
        try {
            //System.out.println(ctr.listUsr());
        } catch (Exception e) {
        }
    }
    
}

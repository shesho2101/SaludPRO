/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.cbd;

import java.util.ArrayList;
import java.util.Collection;
import principal.dominio.medico.Medico;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.Paciente;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.userPass.Usr;

/**
 *
 * @author PC
 */
public class UsrCBD extends CBD{
    PacienteServices pacS = new PacienteServices();
    MedicoServices medS = new MedicoServices();
    
    public void saveUsr(Usr usr) throws Exception{
        try {
            String sql = "";
            if(usr == null){
                throw new Exception("Debe indicar un paciente");
            }
            
            sql = "INSERT INTO usuario(contraseña, usuario)" 
                + "VALUES ( '" + usr.getObj().getId()  + "' , '" + usr.getUsuario() + "');"; 

            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modUsr(Usr usr) throws Exception{
        try {
            String sql = "";
            if(usr == null){
                throw new Exception("Debe indicar un paciente a modificar");
            }
            
            sql = "UPDATE usuario SET usuario = '" + usr.getUsuario()+ "' WHERE contraseñaPaciente = '" + usr.getObj().getId()+ "'"; 

            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
     public void delUsr(String idUsr) throws Exception{
        try {
            String sql = "";
            
            sql = "DELETE FROM usuario WHERE contraseña = '" + idUsr + "'";

            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
     
    public Usr searchUsrPerId(String id) throws Exception{
        try {
            String sql = "";

            sql = "SELECT * FROM usuario "
                + "WHERE contraseña = '" + id + "';";

            
            consultarBase(sql);

            Usr usr = null;
            while(result.next()){
                usr = new Usr();
                if(pacS.searchPerId(id) != null){
                    Paciente pac = pacS.searchPerId(result.getString(1));
                    usr.setObj(pac);
                }
                if(medS.searchPerId(id) != null){
                    Medico med = medS.searchPerId(result.getString(1));
                    usr.setObj(med);
                }
                usr.setUsuario(result.getString(2));
            }
            
            desconectarBase();
            return usr;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Collection<Usr> listUsr() throws Exception{
        try {
            
            String sql = "SELECT contraseña, usuario FROM usuario";
            
            consultarBase(sql);
            
            Collection<Usr> usuarios = new ArrayList();
            while(result.next()){
                if(result.getString(1) == null){
                    Medico med = medS.searchPerId(result.getString(2));
                    usuarios.add(new Usr(med, result.getString(2)));
                }
                if(result.getString(2) == null){
                    Paciente pac = pacS.searchPerId(result.getString(1));
                    usuarios.add(new Usr(pac, result.getString(2)));
                }
            }
            desconectarBase();
            return usuarios;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.cbd;

import java.util.ArrayList;
import java.util.Collection;
import principal.dominio.paciente.Paciente;

/**
 *
 * @author PC
 */
public final class PacienteCBD extends CBD{
    
    public void savePaciente(Paciente pac) throws Exception{
        try {
            if(pac == null){
                throw new Exception("Debe indicar un paciente");
            }
            
            String sql = "INSERT INTO Paciente(idPac, nombre, apellido, edad)" 
                    + "VALUES ( '" + pac.getId()  + "' , ' " + pac.getNombre() + "' , '" + pac.getApellido() + "' , '" + pac.getEdad() + "');"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modPaciente(Paciente pac) throws Exception{
        try {
            if(pac == null){
                throw new Exception("Debe indicar un paciente a modificar");
            }
            String sql = "UPDATE paciente SET nombre = '" + pac.getNombre()+ "' , "
                    + "apellido = '" + pac.getApellido() + "' , edad = '" + pac.getEdad() + "' WHERE idPac = '" + pac.getId() + "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPac(String idPac) throws Exception{
        try {
            String sql = "DELETE FROM paciente WHERE idPac = '" + idPac + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Paciente searchPacientePerId(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM paciente "
                    + "WHERE idPac = '" + id + "';";
            
            consultarBase(sql);
            
            Paciente pac = null;
            
            while(result.next()){
                pac = new Paciente();
                pac.setIdPac(result.getString(1));
                pac.setNombre(result.getString(2));
                pac.setApellido(result.getString(3));
                pac.setEdad(result.getString(4));
            }
            desconectarBase();
            return pac;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Collection<Paciente> listPac() throws Exception{
        try {
            String sql = "SELECT idPac, nombre, apellido, edad FROM paciente";
            
            consultarBase(sql);
            
            Collection<Paciente> pacientes = new ArrayList();
            while(result.next()){
                pacientes.add(new Paciente(result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
            }
            desconectarBase();
            return pacientes;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
}

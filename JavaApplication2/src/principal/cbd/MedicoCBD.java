/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.cbd;

import java.util.ArrayList;
import java.util.Collection;
import principal.dominio.medico.Medico;
/**
 *
 * @author PC
 */
public class MedicoCBD extends CBD{
    
    public void saveDoctor(Medico med) throws Exception{
        try {
            if(med == null){
                throw new Exception("Debe indicar un paciente");
            }
            
            String sql = "INSERT INTO Medico(idDoc, nombre, apellido, especializacion)" 
                    + "VALUES ( '" + med.getId()  + "' , ' " + med.getNombre() + "' , '" + med.getApellido() + "' , '" + med.getEspecializacion()+ "');"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modMedico(Medico pac) throws Exception{
        try {
            if(pac == null){
                throw new Exception("Debe indicar un paciente a modificar");
            }
            String sql = "UPDATE medico SET nombre = '" + pac.getNombre()+ "' , "
                    + "apellido = '" + pac.getApellido() + "' , especializacion = '" + pac.getEspecializacion()+ "' WHERE idDoc = '" + pac.getId() + "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delDoc(String idDoc) throws Exception{
        try {
            String sql = "DELETE FROM medico WHERE idDoc = '" + idDoc + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Medico searchDocPerId(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM medico "
                    + "WHERE idDoc = '" + id + "';";
            
            consultarBase(sql);
            
            Medico med = null;
            
            while(result.next()){
                med = new Medico();
                med.setIdDoc(result.getString(1));
                med.setNombre(result.getString(2));
                med.setApellido(result.getString(3));
                med.setEspecializacion(result.getString(4));
            }
            desconectarBase();
            return med;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Collection<Medico> listPac() throws Exception{
        try {
            String sql = "SELECT idDoc, nombre, apellido, especializacion FROM medico";
            
            consultarBase(sql);
            
            Collection<Medico> medicos = new ArrayList();
            while(result.next()){
                medicos.add(new Medico(result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
            }
            desconectarBase();
            return medicos;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
}

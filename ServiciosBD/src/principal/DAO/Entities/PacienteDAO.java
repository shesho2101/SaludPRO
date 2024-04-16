/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import principal.DAO.Abstract.DAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.dominio.paciente.GrupoSanguineo;
import principal.dominio.paciente.Paciente;
import principal.dominio.user.UsuarioServices;

/**
 *
 * @author PC
 */
@SuppressWarnings("unchecked")
public final class PacienteDAO extends DAO{
    
    private final UsuarioServices us;
    
    public PacienteDAO(){
        this.us = new UsuarioServices();
    }
        
    public void savePaciente(Paciente pac) throws Exception{
        try {
            if(pac == null){
                throw new Exception("Debe indicar un paciente");
            }
            
            String sql = "INSERT INTO Paciente(ID_Paciente, fecha_nacimiento, sexo, grupoSanguineo, telefono, correoElectronico)" 
                    + "VALUES ( '" + pac.getUsr().getId()  + "' , ' " + pac.getFechaNacimiento() + "' , '" + pac.getSexo()+ "', '"
                    + pac.getSangre() + "' , '" + pac.getTelefono() + "' , '" + pac.getCorreo() + "')"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /*
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
    */
    
    public void delPac(String idPac) throws Exception{
        try {
            String sql = "DELETE FROM paciente WHERE ID_Paciente = '" + idPac + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Paciente searchPacientePerId(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM paciente "
                    + "WHERE ID_Paciente = '" + id + "';";
            
            consultarBase(sql);
            
            Paciente pac = null;
            
            while(result.next()){
                pac = new Paciente();
                pac.setUsr(us.searchPerID(result.getString(1)));
                pac.setFechaNacimiento(result.getDate(2).toString());
                pac.setEdad(result.getInt(3));
                pac.setSexo(result.getString(4));
                GrupoSanguineo grupo = GrupoSanguineo.valueOf(result.getString(5));
                pac.setSangre(grupo);
                pac.setTelefono(result.getString(6));
                pac.setCorreo(result.getString(7));
            }
            desconectarBase();
            return pac;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<Paciente> listPac() throws Exception{
        try {
            String sql = "SELECT * FROM paciente";
            
            consultarBase(sql);
            
            List<Paciente> pacientes = new ArrayList();
            Paciente pac = null;
            while(result.next()){
                pac = new Paciente();
                pac.setUsr(us.searchPerID(result.getString(1)));
                pac.setFechaNacimiento(result.getDate(2).toString());
                pac.setEdad(result.getInt(3));
                pac.setSexo(result.getString(4));
                GrupoSanguineo grupo = GrupoSanguineo.valueOf(result.getString(5));
                pac.setSangre(grupo);
                pac.setTelefono(result.getString(6));
                pac.setCorreo(result.getString(7));
                pacientes.add(pac);
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

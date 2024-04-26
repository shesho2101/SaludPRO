/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import principal.DAO.Abstract.DAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.user.Usuario;

/**
 *
 * @author PC
 */
@SuppressWarnings("unchecked")
public class UsuarioDAO extends DAO{

    
    public void saveUsr(Usuario usr) throws Exception{
        try {
            if(usr == null){
                throw new Exception("Debe indicar un usuario");
            }
            
            String sql = "INSERT INTO usuario(ID, nombre, apellidos)" 
                + "VALUES ( '" + usr.getId()  + "' , '" + usr.getNombre() + "', '" + usr.getApellidos()+ "')"; 

            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /*
    public void modUsr(Usr usr) throws Exception{
        try {
            String sql = "";
            if(usr == null){
                throw new Exception("Debe indicar un usuario a modificar");
            }
            
            sql = "UPDATE usuario SET usuario = '" + usr.getUsuario()+ "' WHERE contraseñaPaciente = '" + usr.getObj().getId()+ "'"; 

            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    */
     public void delUsr(String idUsr) throws Exception{
        try {

            String sql = "DELETE FROM usuario WHERE ID = '" + idUsr + "'";

            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
     
    public Usuario searchUsrPerId(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM usuario "
                + "WHERE ID = '" + id + "'";

            
            consultarBase(sql);

            Usuario usr = null;
            while(result.next()){
                usr = new Usuario();
                usr.setId(result.getString(1));
                usr.setNombre(result.getString(2));
                usr.setApellidos(result.getString(3));
            }
            
            desconectarBase();
            return usr;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<Usuario> listUsr() throws Exception{
        try {
            
            String sql = "SELECT * FROM usuario";
            
            consultarBase(sql);
            
            List<Usuario> usuarios = new ArrayList();
            Usuario usr = null;
            while(result.next()){
                usr = new Usuario();
                usr.setId(result.getString(1));
                usr.setNombre(result.getString(2));
                usr.setApellidos(result.getString(3));
                usuarios.add(usr);
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

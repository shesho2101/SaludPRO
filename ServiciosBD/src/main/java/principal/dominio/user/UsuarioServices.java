/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.user;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.UsuarioDAO;

/**
 *
 * @author PC
 */
public class UsuarioServices {
    
    UsuarioDAO cbd;
    
    public UsuarioServices(){
        cbd = new UsuarioDAO();
    }
    
    public void createUsr(String ID, String nombre, String apellidos) throws Exception{
        try {
            if(ID == null || ID.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(nombre == null || nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            if(searchPerID(ID) != null){
                throw new Exception("El usuario ya existe");
            }
            
            Usuario usr = new Usuario();
            usr.setId(ID);
            usr.setNombre(nombre);
            usr.setApellidos(apellidos);
            cbd.saveUsr(usr);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void deleteUsr(String ID){
        try {
            if(ID == null || ID.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            cbd.delUsr(ID);
            
        } catch (Exception e) {
        }
    }
    
    public Usuario searchPerID(String ID) throws Exception{
        try {
            if(ID == null || ID.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            Usuario usr = cbd.searchUsrPerId(ID);
            
            return usr;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Usuario> listUsr() throws Exception{
        try {
            return cbd.listUsr();
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            List<Usuario> usuarios = listUsr();
            
            if(usuarios == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(usuarios);
            
            
        } catch (Exception e) {
            throw e;
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.administrador;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.AdministradorDAO;
import principal.dominio.user.UsuarioServices;

/**
 *
 * @author PC
 */
public class AdministradorServices {
    
    private AdministradorDAO cbd;
    private UsuarioServices us;
    
    public AdministradorServices(){
        this.us = new UsuarioServices();
        this.cbd = new AdministradorDAO();
    }
    
    public void saveAdmin(String id) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            Administrador admin = new Administrador();
            admin.setUsr(us.searchPerID(id));
            
            cbd.saveAdmin(admin);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void deleteAdmin(String id) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            //Call bd
            cbd.delAdmin(id);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Administrador searchPerId(String id) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            Administrador admin = cbd.searchAdminPerID(id);
            
            return admin;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Administrador> listAdmin() throws Exception{
        try {     
            return cbd.listAdmin();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            List<Administrador> admins = listAdmin();
            //Validacion
            if(admins == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(admins);
        } catch (Exception e) {
            throw e;
        }
    }
    
}

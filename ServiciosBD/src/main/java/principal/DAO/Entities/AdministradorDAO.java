/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import principal.DAO.Abstract.DAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.dominio.administrador.Administrador;
import principal.dominio.user.UsuarioServices;

/**
 *
 * @author PC
 */
@SuppressWarnings("unchecked")
public class AdministradorDAO extends DAO{
    
    private UsuarioServices us;

    public AdministradorDAO() {
        this.us = new UsuarioServices();
    }
    
    public void saveAdmin(Administrador admin) throws Exception{
        try {
            if(admin == null){
                throw new Exception("Debe indicar un admin");
            }
            
            String sql = "INSERT INTO administrador(ID_Admin)" 
                    + "VALUES ( '" + admin.getUsr().getId() + "')";
            
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delAdmin(String codAdmin) throws Exception{
        try {
            String sql = "DELETE FROM administrador WHERE ID_Admin = '" + codAdmin + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Administrador searchAdminPerID(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM administrador "
                    + "WHERE ID_Admin = '" + id + "'";
            
            consultarBase(sql);
            
            Administrador admin = null;
            
            while(result.next()){
                admin = new Administrador();
                admin.setUsr(us.searchPerID(result.getString(1)));
            }
            desconectarBase();
            return admin;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<Administrador> listAdmin() throws Exception{
        try {
            String sql = "SELECT * FROM administrador";
            
            consultarBase(sql);
            
            List<Administrador> admins = new ArrayList();
            Administrador admin = null;
            
            while(result.next()){
                admin = new Administrador();
                admin.setUsr(us.searchPerID(result.getString(1)));
                admins.add(admin);
            }
            
            desconectarBase();
            return admins;
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    
}

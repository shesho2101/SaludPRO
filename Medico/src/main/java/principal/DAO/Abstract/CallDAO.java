/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Abstract;

import java.sql.ResultSet;

/**
 *
 * @author PC
 */
public class CallDAO extends DAO{
    
    public ResultSet consultDataBase(String sql) throws Exception{
        try {
            if(sql == null || sql.trim().isEmpty()){
                throw new Exception("No especifico una linea de comando");
            }
            
            consultarBase(sql);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void desconnect() throws Exception{
        desconectarBase();
    }

    
}

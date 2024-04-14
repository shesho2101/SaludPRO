/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import java.util.ArrayList;
import java.util.Collection;
import principal.DAO.Abstract.DAO;
import principal.dominio.medicamento.Medicamento;

/**
 *
 * @author PC
 */
public class MedicamentoDAO extends DAO{
    
    public void saveMedicamento(Medicamento medi) throws Exception{
        try {
            if(medi == null){
                throw new Exception("Debe indicar un medicamento");
            }
            
            String sql = "INSERT INTO medicamento(nombre, descripcion) "
                    + "VALUES ('" + medi.getNombre() + "' , '" + medi.getDescripcion() + "')";
            
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delMedicamento(String nombre) throws Exception{
        try {
            String sql = "DELETE FROM medicamento WHERE nombre = '" + nombre + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delMedicamento(int cod) throws Exception{
        try {
            String sql = "DELETE FROM medicamento WHERE codigo = '" + cod + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Medicamento searchPerName(String name) throws Exception{
        try {
            String sql = "SELECT * FROM medicamento "
                    + "WHERE nombre = '" + name + "'";
            
            consultarBase(sql);
            
            Medicamento medi = null;
            while(result.next()){
                medi = new Medicamento();
                medi.setNombre(result.getString(1));
                medi.setDescripcion(result.getString(2));
            }
            desconectarBase();
            return medi;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Collection<Medicamento> listMedi() throws Exception{
        try {
            String sql = "SELECT * FROM medi";
            
            consultarBase(sql);
            
            Collection<Medicamento> medis = new ArrayList();
            Medicamento medi = null;
            
            while(result.next()){
                medi = new Medicamento();
                medi.setNombre(result.getString(1));
                medi.setDescripcion(result.getString(2));
                medis.add(medi);
            }
            desconectarBase();
            return medis;
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
    
}

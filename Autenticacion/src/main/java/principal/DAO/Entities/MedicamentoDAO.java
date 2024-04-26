/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
                medi.setCodigo(result.getInt(1));
                medi.setNombre(result.getString(2));
                medi.setDescripcion(result.getString(3));
            }
            desconectarBase();
            return medi;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Medicamento searchPerCod(int cod) throws Exception{
        try {
            String sql = "SELECT * FROM medicamento "
                    + "WHERE codigo = '" + cod + "'";
            
            consultarBase(sql);
            
            Medicamento medi = null;
            while(result.next()){
                medi = new Medicamento();
                medi.setCodigo(result.getInt(1));
                medi.setNombre(result.getString(2));
                medi.setDescripcion(result.getString(3));
            }
            desconectarBase();
            return medi;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<Medicamento> listMedi() throws Exception{
        try {
            String sql = "SELECT * FROM medicamento";
            
            consultarBase(sql);
            
            List<Medicamento> medis = new ArrayList();
            Medicamento medi = null;
            
            while(result.next()){
                medi = new Medicamento();
                medi.setCodigo(result.getInt(1));
                medi.setNombre(result.getString(2));
                medi.setDescripcion(result.getString(3));
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

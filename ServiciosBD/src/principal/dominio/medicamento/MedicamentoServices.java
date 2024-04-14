/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.medicamento;

import java.util.Collection;
import principal.DAO.Entities.MedicamentoDAO;

/**
 *
 * @author PC
 */
public class MedicamentoServices {
    
    MedicamentoDAO cbd;
    
    public MedicamentoServices(){
        this.cbd = new MedicamentoDAO();
    }
    
    public void createMedicamento(String nombre, String descripcion) throws Exception{
        try {
            //Validaciones
            if(nombre == null || nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            if(descripcion == null || descripcion.trim().isEmpty()){
                throw new Exception("La descripcion no puede ser nula");
            }
            if(searchPerName(nombre) != null){
                throw new Exception("Ya existe ese medicamento");
            }
            
            Medicamento medi = new Medicamento();
            medi.setNombre(nombre);
            medi.setDescripcion(descripcion);
            //Call
            cbd.saveMedicamento(medi);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delMed(String name) throws Exception{
        try {
            if(name == null || name.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            
            cbd.delMedicamento(name);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delMed(int cod) throws Exception{
        try {
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            
            cbd.delMedicamento(cod);
        } catch (Exception e) {
            throw e;
        }
    }
    public Medicamento searchPerName(String nombre) throws Exception{
        try {
            if(nombre == null || nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            
            return cbd.searchPerName(nombre);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Collection<Medicamento> listMedi() throws Exception{
        try {
            return cbd.listMedi();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            Collection<Medicamento> medis = listMedi();
            if(medis == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(medis.toString());
        } catch (Exception e) {
            throw e;
        }
    }
    
}

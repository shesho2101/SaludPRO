/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.historialClinico;

import principal.DAO.Abstract.CallDAO;
import principal.DAO.Entities.HistorialClinicoDAO;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import principal.dominio.paciente.PacienteServices;

/**
 *
 * @author PC
 */
public class HistorialClinicoServices {
    
    private CallDAO cd;
    private HistorialClinicoDAO cbd;
    private PacienteServices ps;
    
    public HistorialClinicoServices(){
        this.cd = new CallDAO();
        this.cbd = new HistorialClinicoDAO();
        this.ps = new PacienteServices();
    }

    public StringBuilder getCitas(String id) throws Exception{
        try {
            
            String sql = "SELECT NumHab, fecha, asistencia FROM cita "
                    + "INNER JOIN paciente USING(ID_Paciente)"
                    + "WHERE ID_Paciente = '" + id + "'";
            
            ResultSet rs = cd.consultDataBase(sql);
            StringBuilder buildFinal = new StringBuilder();
            StringBuilder builder = null;
            while(rs.next()){
                builder = new StringBuilder();
                builder.append("Numero del consultorio: ");
                builder.append(String.valueOf(rs.getInt(1)));
                builder.append(" Fecha de la cita: ");
                builder.append(rs.getDate(2).toString());
                builder.append(" Asistencia: ");
                builder.append(rs.getBoolean(3));
                buildFinal.append(builder);
                buildFinal.append("\n");
            }

            return buildFinal;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void createHistorialClinico(String id) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(ps.searchPerId(id) == null){
                throw new Exception("El paciente no existe");
            }
            
            HistorialClinico hc = new HistorialClinico();
            StringBuilder build = constructHis(id);
            hc.setHistorial(build);
            hc.setPac(ps.searchPerId(id));
            cbd.saveHistorialClinico(hc);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public StringBuilder constructHis(String id) throws Exception{
        try {
            StringBuilder build = new StringBuilder();
            build.append(ps.searchPerId(id).toString());
            build.append("\nCitas \n");
            build.append(getCitas(id));
            return build;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modificarHistorial(String id) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            HistorialClinico hc = new HistorialClinico();
            hc.setHistorial(constructHis(id));
            hc.setPac(ps.searchPerId(id));
            cbd.modHistorial(hc);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public HistorialClinico searchHC(String id) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            return cbd.searchHistorialPerPaciente(id);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<HistorialClinico> listHC() throws Exception{
        try {
            return cbd.listHistoriales();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            List<HistorialClinico> hcs = listHC();
            if(hcs == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(hcs.toString());
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    
    
    
    
    
}

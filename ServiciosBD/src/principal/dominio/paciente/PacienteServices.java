
package principal.dominio.paciente;

import java.util.Collection;
import java.util.Date;
import principal.DAO.Entities.PacienteDAO;
import principal.dominio.user.UsuarioServices;

/**
 *
 * @author PC
 */
public class PacienteServices {
    
    private PacienteDAO cbd;
    private UsuarioServices us;
    
    
    public PacienteServices(){
        this.cbd = new PacienteDAO();
        this.us = new UsuarioServices();
    }
    
    public void createPac(String id, String fechaNacimiento, String sexo) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(fechaNacimiento == null || fechaNacimiento.trim().isEmpty()){
                throw new Exception("La fecha de nacimiento no puede ser nula");
            }
            if(searchPerId(id) != null){
                throw new Exception("Ya existe el paciente");
            }
            
            Paciente pac = new Paciente();
            pac.setUsr(us.searchPerID(id));
            pac.setFechaNacimiento(fechaNacimiento);
            pac.setSexo(sexo);
            cbd.savePaciente(pac);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    /*
    public void modPaciente(String id, String edadAct, String edadNew) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(edadAct == null || edadAct.trim().isEmpty()){
                throw new Exception("La edad puede ser nula");
            }
            if(edadNew == null || edadNew.trim().isEmpty()){
                throw new Exception("La nueva edad no puede ser nula");
            }
            
            Paciente pac = searchPerId(id);
            pac.setEdad(edadNew);
            cbd.modPaciente(pac);
            
        } catch (Exception e) {
            throw e;
        }
    }
    */
    public void delPac(String id) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            cbd.delPac(id);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Paciente searchPerId(String id) throws Exception{
        try {
            if(id == null || id.isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            Paciente pac = cbd.searchPacientePerId(id);
            
            return pac;
        } catch (Exception e) {
            throw e;
        }
    }
    
    private Collection<Paciente> listPac() throws Exception{
        try {
            Collection<Paciente> pacientes = cbd.listPac();
            return pacientes;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimirPacientes() throws Exception{
        try {
            Collection<Paciente> pacientes = listPac();
            
            if(pacientes.isEmpty()){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(pacientes);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
}

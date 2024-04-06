
package principal.dominio.paciente;

import java.util.Collection;
import principal.cbd.PacienteCBD;

/**
 *
 * @author PC
 */
public class PacienteServices {
    
    private PacienteCBD cbd;
    
    public PacienteServices(){
        this.cbd = new PacienteCBD();
    }
    
    public void createPac(String id, String nombre, String apellido, String edad) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(nombre == null || nombre.trim().isEmpty()){
                throw new Exception("Inserte un nombre valido");
            }
            if(apellido == null || apellido.trim().isEmpty()){
                throw new Exception("Inserte un apellido valido");
            }
            if(edad == null || edad.trim().isEmpty()){
                throw new Exception("Inserte una edad valida");
            }
            if(searchPerId(id) != null){
                throw new Exception("Ya esta registrado");
            }
            
            Paciente pac = new Paciente();
            pac.setIdPac(id);
            pac.setNombre(nombre);
            pac.setApellido(apellido);
            pac.setEdad(edad);
            cbd.savePaciente(pac);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
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
            
            System.out.println(pacientes.toString());
            
        } catch (Exception e) {
            throw e;
        }
    }
    
}

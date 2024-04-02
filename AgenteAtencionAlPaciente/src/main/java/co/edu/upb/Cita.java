package co.edu.upb;

public class Cita {

    private String sede;
    private String fecha;
    private String hora;
    private Paciente paciente;
    private Medico medico;
    private String especialidad;

    public Cita(String sede, String fecha, String hora, Medico medico, Paciente paciente, String especialidad) {
        this.sede = sede;
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.paciente = paciente;
        this.especialidad = especialidad;
    }

    // Getters y setters para fecha, hora, medico y consultorio
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}

package co.edu.upb;

public class Medico extends Trabajador {
    private String especialidad;
    private String sede;
    private String consultorio;

    public Medico(String nombre, String documento, String especialidad, String sede, String consultorio, String cargo) {
        super(nombre, documento, cargo);
        this.especialidad = especialidad;
        this.sede = sede;
        this.consultorio = consultorio;
    }

    // Getters y setters para nombre y consultorio
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }



}

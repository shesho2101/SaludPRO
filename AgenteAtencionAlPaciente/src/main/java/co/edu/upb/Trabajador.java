package co.edu.upb;

public class Trabajador {
    protected String nombre;
    protected String documento;
    protected String cargo;

    public Trabajador(String nombre, String documento, String cargo) {
        this.nombre = nombre;
        this.documento = documento;
        this.cargo = cargo;
    }

    public String[] getDatos() {
        return new String[]{nombre, documento, cargo};
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }



}

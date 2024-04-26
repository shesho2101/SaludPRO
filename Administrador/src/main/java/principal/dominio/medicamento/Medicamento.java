package principal.dominio.medicamento;

public class Medicamento {

    private int codigo;
    private String nombre;
    private int cantidad; // Reemplazamos 'descripcion' por 'cantidad'

    public Medicamento() {
    }

    public Medicamento(String nombre, int cantidad) { // Cambiamos el parámetro de 'descripcion' a 'cantidad'
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() { // Cambiamos el getter
        return cantidad;
    }

    public void setCantidad(int cantidad) { // Cambiamos el setter
        this.cantidad = cantidad;
    }

    @Override
    public String toString() { // Actualizamos el método `toString`
        return "Medicamento{" + "codigo=" + codigo + ", nombre=" + nombre + ", cantidad=" + cantidad + '}';
    }
}

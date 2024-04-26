package principal.dominio.medicamento;

import java.util.List;
import principal.DAO.Entities.MedicamentoDAO;

public class MedicamentoServices {

    MedicamentoDAO cbd;

    public MedicamentoServices() {
        this.cbd = new MedicamentoDAO();
    }

    // Cambiamos 'descripcion' por 'cantidad'
    public void createMedicamento(String nombre, int cantidad) throws Exception {
        try {
            // Validaciones
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre no puede ser nulo");
            }
            if (cantidad <= 0) { // Validamos que la cantidad sea positiva
                throw new Exception("La cantidad debe ser mayor que cero");
            }
            if (searchPerName(nombre) != null) {
                throw new Exception("Ya existe ese medicamento");
            }

            // Creamos el medicamento con el nuevo atributo 'cantidad'
            Medicamento medi = new Medicamento(nombre, cantidad);

            // Llamada al DAO para guardar el medicamento
            cbd.saveMedicamento(medi);
        } catch (Exception e) {
            throw e;
        }
    }

    // Otros métodos sin cambios
    public void delMed(String name) throws Exception {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new Exception("El nombre no puede ser nulo");
            }

            cbd.delMedicamento(name);
        } catch (Exception e) {
            throw e;
        }
    }

    public void delMed(int cod) throws Exception {
        try {
            if (cod == 0) {
                throw new Exception("El código no puede ser cero");
            }

            cbd.delMedicamento(cod);
        } catch (Exception e) {
            throw e;
        }
    }

    // Método para modificar un medicamento existente
    public void updateMedicamentoCantidad(int codigo, int nuevaCantidad) throws Exception {
        try {
            if (nuevaCantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor que cero");
            }

            Medicamento medicamento = cbd.searchPerCod(codigo);
            if (medicamento == null) {
                throw new Exception("El medicamento con el código " + codigo + " no existe.");
            }

            // Actualiza la cantidad
            medicamento.setCantidad(nuevaCantidad);

            // Llama al DAO para actualizar el campo 'cantidad'
            cbd.updateCantidad(medicamento.getCodigo(), medicamento.getCantidad());
        } catch (Exception e) {
            throw new Exception("Error al actualizar la cantidad del medicamento: " + e.getMessage(), e);
        }
    }

    public Medicamento searchPerName(String nombre) throws Exception {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre no puede ser nulo");
            }

            return cbd.searchPerName(nombre);
        } catch (Exception e) {
            throw e;
        }
    }

    public Medicamento searchPerCod(int cod) throws Exception {
        try {
            if (cod == 0) {
                throw new Exception("El código no puede ser cero");
            }

            return cbd.searchPerCod(cod);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Medicamento> listMedi() throws Exception {
        try {
            return cbd.listMedi();
        } catch (Exception e) {
            throw e;
        }
    }

    public void imprimir() throws Exception {
        try {
            List<Medicamento> medis = listMedi();
            if (medis == null || medis.isEmpty()) {
                throw new Exception("La lista está vacía");
            }

            System.out.println(medis.toString());
        } catch (Exception e) {
            throw e;
        }
    }
}

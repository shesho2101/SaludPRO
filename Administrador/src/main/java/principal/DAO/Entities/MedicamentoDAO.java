package principal.DAO.Entities;

import java.util.ArrayList;
import java.util.List;
import principal.DAO.Abstract.DAO;
import principal.dominio.medicamento.Medicamento;

public class MedicamentoDAO extends DAO {

    public void saveMedicamento(Medicamento medi) throws Exception {
        try {
            if (medi == null) {
                throw new Exception("Debe indicar un medicamento");
            }

            String sql = "INSERT INTO medicamento(nombre, cantidad) "
                    + "VALUES ('" + medi.getNombre() + "', " + medi.getCantidad() + ")"; // Cambiamos a 'cantidad'

            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void delMedicamento(String nombre) throws Exception {
        try {
            String sql = "DELETE FROM medicamento WHERE nombre = '" + nombre + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void delMedicamento(int cod) throws Exception {
        try {
            String sql = "DELETE FROM medicamento WHERE codigo = '" + cod + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateCantidad(int codigo, int nuevaCantidad) throws Exception {
        try {
            String sql = "UPDATE medicamento "
                    + "SET cantidad = " + nuevaCantidad + " "
                    + "WHERE codigo = " + codigo + ";";

            insertModDel(sql); // Método para ejecutar SQL de actualización
        } catch (Exception e) {
            throw new Exception("Error al actualizar la cantidad del medicamento", e);
        }
    }

    public Medicamento searchPerName(String name) throws Exception {
        try {
            String sql = "SELECT * FROM medicamento WHERE nombre = '" + name + "'";
            consultarBase(sql);

            Medicamento medi = null;
            while (result.next()) {
                medi = new Medicamento();
                medi.setCodigo(result.getInt(1));
                medi.setNombre(result.getString(2));
                medi.setCantidad(result.getInt(3)); // Cambiamos a 'cantidad'
            }
            desconectarBase();
            return medi;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }

    public Medicamento searchPerCod(int cod) throws Exception {
        try {
            String sql = "SELECT * FROM medicamento WHERE codigo = '" + cod + "'";
            consultarBase(sql);

            Medicamento medi = null;
            while (result.next()) {
                medi = new Medicamento();
                medi.setCodigo(result.getInt(1));
                medi.setNombre(result.getString(2));
                medi.setCantidad(result.getInt(3)); // Cambiamos a 'cantidad'
            }
            desconectarBase();
            return medi;
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }

    public List<Medicamento> listMedi() throws Exception {
        try {
            String sql = "SELECT * FROM medicamento";
            consultarBase(sql);

            List<Medicamento> medis = new ArrayList<>();
            Medicamento medi;

            while (result.next()) {
                medi = new Medicamento();
                medi.setCodigo(result.getInt(1));
                medi.setNombre(result.getString(2));
                medi.setCantidad(result.getInt(3)); // Cambiamos a 'cantidad'
                medis.add(medi);
            }

            desconectarBase();
            return medis;
        } catch (Exception e) {
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
}

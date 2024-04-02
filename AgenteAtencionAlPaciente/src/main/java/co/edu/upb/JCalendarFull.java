package co.edu.upb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JCalendarFull extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JLabel lblFechaSeleccionada;
    private final JLabel lblHoraSeleccionada;
    private final JButton btnAceptar;
    private final JSpinner spinnerFecha;
    private final JSpinner spinnerHora;
    private final SpinnerDateModel spinnerFechaModel;
    private final SpinnerDateModel spinnerHoraModel;

    private Calendar selectedDate;

    public JCalendarFull(JFrame parent) {
        super(parent, "Seleccionar Fecha y Hora", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // Spinner para la fecha
        Calendar fechaMinima = Calendar.getInstance();
        fechaMinima.set(Calendar.HOUR_OF_DAY, 0);
        fechaMinima.set(Calendar.MINUTE, 0);
        fechaMinima.set(Calendar.SECOND, 0);
        fechaMinima.set(Calendar.MILLISECOND, 0);
        spinnerFechaModel = new SpinnerDateModel(new Date(), fechaMinima.getTime(), null, Calendar.DAY_OF_MONTH);
        spinnerFecha = new JSpinner(spinnerFechaModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(dateEditor);

        panel.add(new JLabel("Fecha: "));
        panel.add(spinnerFecha);

        // Spinner para la hora
        spinnerHoraModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        spinnerHora = new JSpinner(spinnerHoraModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(timeEditor);

        panel.add(new JLabel("Hora: "));
        panel.add(spinnerHora);

        lblFechaSeleccionada = new JLabel("");

        lblHoraSeleccionada = new JLabel("");

        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedDate = Calendar.getInstance();
                selectedDate.setTime((java.util.Date) spinnerFecha.getValue());
                selectedDate.set(Calendar.HOUR_OF_DAY, spinnerHoraModel.getDate().getHours());
                selectedDate.set(Calendar.MINUTE, spinnerHoraModel.getDate().getMinutes());

                // Verificar si la fecha seleccionada es anterior a la fecha actual
                if (selectedDate.before(Calendar.getInstance())) {
                    JOptionPane.showMessageDialog(JCalendarFull.this, "La fecha seleccionada no puede ser anterior a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                setVisible(false);
            }
        });
        panel.add(btnAceptar);

        add(panel);
    }

    public Calendar getSelectedDate() {
        return selectedDate;
    }

    public String getHoraCompleta() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(selectedDate.getTime());
    }
}

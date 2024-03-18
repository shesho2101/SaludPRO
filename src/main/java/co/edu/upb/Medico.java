package co.edu.upb;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Medico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;

    public Medico() {
        setTitle("IPS Salud Pro - Medico");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Poner en pantalla completa
        setResizable(false); // Desactivar la capacidad de redimensionamiento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(150, 198, 198));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[3];

        buttons[0] = createButton("Buscar historia clínica", 640, 320);
        buttons[1] = createButton("Ver agenda", 640, 400);
        buttons[2] = createButton("Reportar paciente", 640, 480);
        
        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazAzul.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
    }

    private JButton createButton(final String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(x, y, 260, 50);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!movimiento) {
                    moverBotones();
                }

                switch (text) {
                    case "Buscar historia clínica":
                        crearNuevoPanel(1);
                        break;

                    case "Ver agenda":
                        crearNuevoPanel(2);
                        break;

                    case "Reportar paciente":
                        crearNuevoPanel(3);
                        break;
                }
            }
        });
        contentPane.add(button);
        return button;
    }

    private void moverBotones() {
        if (!movimiento) {
            Timer timer = new Timer(10, new ActionListener() {
                int deltaX = 33;
                int duration = 250;
                long startTime = System.currentTimeMillis();

                public void actionPerformed(ActionEvent e) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;

                    if (elapsedTime > duration) {
                        ((Timer) e.getSource()).stop();
                    } else {
                        moveButtonsToLeft(deltaX);
                    }
                }
            });

            timer.start();
            movimiento = true;
        }
    }

    private void moveButtonsToLeft(int deltaX) {
        for (JButton button : buttons) {
            button.setLocation(button.getX() - deltaX, button.getY());
        }
    }

    private void crearNuevoPanel(int opcion) {
        nuevoPanel = new JPanel();
        nuevoPanel.setBackground(new Color(250, 250, 250));
        nuevoPanel.setBounds(panelPositionX, 0, 800, 900);
    
        contentPane.add(nuevoPanel);
        contentPane.repaint();
        movimiento = true;
    
        switch (opcion) {
            case 1:
                cargarPanelHistoriaClinica();
                break;
    
            case 2:
                cargarPanelVerAgenda();
                break;
    
            case 3:
                cargarPanelReportar();
                break;
    
            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }
    
    private void cargarPanelHistoriaClinica() {
        if (movimiento) {
            JTextField textFieldDocumento;
            
            JPanel panelHistoriaClinica = new JPanel();
            panelHistoriaClinica.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelHistoriaClinica.setLayout(null);
            panelHistoriaClinica.setBounds(760, 0, 800, 900);  // Cambiado el límite inferior a 0

            JButton btnAgendarCita = new JButton("Buscar cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(305, 437, 180, 40);
            panelHistoriaClinica.add(btnAgendarCita);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(338, 321, 114, 45);
            panelHistoriaClinica.add(lblDocumento);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(305, 376, 180, 34);
            textFieldDocumento.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelHistoriaClinica.add(textFieldDocumento);

            contentPane.add(panelHistoriaClinica);
            contentPane.setComponentZOrder(panelHistoriaClinica, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }

        }
    }

    private void cargarPanelVerAgenda() {
        if (movimiento) {
            //movimiento = false;

            JTable table;

            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(760, 0, 800, 900);  // Cambiado el límite inferior a 0

            JButton btnAgendarCita = new JButton("Ver agenda");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(306, 286, 180, 40);
            panelCancelarCita.add(btnAgendarCita);

            JLabel lblDocumento = new JLabel("Fecha");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(306, 170, 114, 45);
            panelCancelarCita.add(lblDocumento);

            String[] opciones = {"Día", "Semana", "Mes"};
            JComboBox<String> menuDesplegable = new JComboBox<>(opciones);
            menuDesplegable.setModel(new DefaultComboBoxModel<>(opciones));
            menuDesplegable.setBounds(306, 225, 180, 30);
            panelCancelarCita.add(menuDesplegable);

            // Crear la tabla y su modelo de datos
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que todas las celdas sean no editables
                }
            };
            model.addColumn("Nombre");
            model.addColumn("Fecha");
            model.addColumn("Hora");

            // Llenar la tabla con datos de ejemplo
            /*model.addRow(new Object[]{"Juan", "2024-03-17", "10:00"});
            model.addRow(new Object[]{"María", "2024-03-18", "11:30"});
            model.addRow(new Object[]{"Pedro", "2024-03-18", "14:00"});*/

            table = new JTable(model);
            table.setFont(new Font("Tahoma", Font.PLAIN, 15));
            table.setBounds(40, 340, 700, 400);
            table.setFocusable(false); // Deshabilitar la posibilidad de seleccionar celdas
            panelCancelarCita.add(table);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void cargarPanelReportar() {
        if (movimiento) {
            //movimiento = false;
            JTextField textFieldDocumento;

            JPanel panelReportar = new JPanel();
            panelReportar.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelReportar.setLayout(null);
            panelReportar.setBounds(760, 0, 800, 900);  // Cambiado el límite inferior a 0

            JButton btnAgendarCita = new JButton("Buscar cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(305, 437, 180, 40);
            panelReportar.add(btnAgendarCita);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(305, 376, 180, 34);
            textFieldDocumento.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelReportar.add(textFieldDocumento);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(338, 321, 114, 45);
            panelReportar.add(lblDocumento);

            contentPane.add(panelReportar);
            contentPane.setComponentZOrder(panelReportar, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void limpiarPanel(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            panel.remove(component);
        }
        panel.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Medico().setVisible(true);
            }
        });
    }
}

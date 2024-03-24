package co.edu.upb;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Administrador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;

    public Administrador() {
        setTitle("IPS Salud Pro - Administrador");
        setSize(1600, 900);
        setResizable(false); // Desactivar la capacidad de redimensionamiento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(150, 198, 198));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[3];

        buttons[0] = createButton("Inventario medicamentos", 610, 320);
        buttons[1] = createButton("Administrar consultorios", 610, 400);
        buttons[2] = createButton("Administrar usuarios", 610, 480);

        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazBlanca.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
    }

    private JButton createButton(final String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(x, y, 300, 50);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!movimiento) {
                    moverBotones();
                }

                switch (text) {
                    case "Inventario medicamentos":
                        crearNuevoPanel(1);
                        break;

                    case "Administrar consultorios":
                        crearNuevoPanel(2);
                        break;

                    case "Administrar usuarios":
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
                int deltaX = 25;
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
                cargarInventario();
                break;

            case 2:
                cargarConsultorios();
                break;

            case 3:
                cargarUsuarios();
                break;

            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }

    private void cargarInventario() {
        if (movimiento) {

            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 63)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            JPanel panelMedicamentos = new JPanel();
            panelMedicamentos.setFont(new Font("Tahoma", Font.PLAIN, 15));
            panelMedicamentos.setBounds(40, 168, 700, 400);
            panelMedicamentos.setFocusable(false); // Deshabilitar la posibilidad de seleccionar celdas
            panelCancelarCita.add(panelMedicamentos);
            panelMedicamentos.setLayout(null);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);

            JButton btnAgendarCita = new JButton("Agregar");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(65, 618, 180, 40);
            panelCancelarCita.add(btnAgendarCita);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelCancelarCita.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelCancelarCita.add(btnBorrar);

            JLabel lblNombreCategoria = new JLabel("Inventario medicamentos");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(257, 96, 267, 40);
            panelCancelarCita.add(lblNombreCategoria);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }

        }
    }

    private void cargarConsultorios() {
        if (movimiento) {
            //movimiento = false;

            JTable table;

            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            // Crear la tabla y su modelo de datos
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que todas las celdas sean no editables
                }
            };
            model.addColumn("Nombre");

            // Llenar la tabla con datos de ejemplo
            /*for(int i = 0; i <= 24; i++) {
                model.addRow(new Object[]{""});
            }*/

            table = new JTable(model);
            table.setFont(new Font("Tahoma", Font.PLAIN, 15));
            table.setBounds(40, 168, 700, 400);
            table.setFocusable(false); // Deshabilitar la posibilidad de seleccionar celdas
            panelCancelarCita.add(table);

            JButton btnAgendarCita = new JButton("Agregar");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(65, 618, 180, 40);
            panelCancelarCita.add(btnAgendarCita);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelCancelarCita.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelCancelarCita.add(btnBorrar);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);

            JLabel lblNombreCategoria = new JLabel("Control consultorios");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(289, 96, 202, 40);
            panelCancelarCita.add(lblNombreCategoria);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void cargarUsuarios() {
        if (movimiento) {
            //movimiento = false;

            JTable table;

            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            // Crear la tabla y su modelo de datos
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que todas las celdas sean no editables
                }
            };
            model.addColumn("Nombre");

            // Llenar la tabla con datos de ejemplo
            /*for(int i = 0; i <= 24; i++) {
                model.addRow(new Object[]{""});
            }*/

            table = new JTable(model);
            table.setFont(new Font("Tahoma", Font.PLAIN, 15));
            table.setBounds(40, 168, 700, 400);
            table.setFocusable(false); // Deshabilitar la posibilidad de seleccionar celdas
            panelCancelarCita.add(table);

            JButton btnAgendarCita = new JButton("Agregar");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(65, 618, 180, 40);
            panelCancelarCita.add(btnAgendarCita);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelCancelarCita.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelCancelarCita.add(btnBorrar);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);

            JLabel lblNombreCategoria = new JLabel("Administrar usuarios");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(285, 96, 210, 40);
            panelCancelarCita.add(lblNombreCategoria);

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
                new Administrador().setVisible(true);
            }
        });
    }
}

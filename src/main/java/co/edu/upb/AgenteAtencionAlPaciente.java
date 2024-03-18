package co.edu.upb;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Objects;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class AgenteAtencionAlPaciente extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private final int panelPositionX = 770;

    public AgenteAtencionAlPaciente() {
        setTitle("IPS Salud Pro - Agente atención al paciente");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Poner en pantalla completa
        setResizable(false); // Desactivar la capacidad de redimensionamiento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(150, 198, 198));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[4];

        buttons[0] = createButton("Agendar cita", 660, 290);
        buttons[1] = createButton("Reprogramar cita", 660, 370);
        buttons[2] = createButton("Cancelar cita", 660, 450);
        buttons[3] = createButton("Activar cita", 660, 530);

        JLabel lblFondo = new JLabel("");
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/interfazBlanca.jpg")));
        lblFondo.setIcon(icon);
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
    }

    private JButton createButton(final String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(x, y, 220, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!movimiento) {
                    moverBotones();
                }

                switch (text) {
                    case "Agendar cita":
                        crearNuevoPanel(1);
                        break;

                    case "Reprogramar cita":
                        crearNuevoPanel(2);
                        break;

                    case "Cancelar cita":
                        crearNuevoPanel(3);
                        break;

                    case "Activar cita":
                        crearNuevoPanel(4);
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
                final int deltaX = 33;
                final int duration = 250;
                final long startTime = System.currentTimeMillis();

                public void actionPerformed(ActionEvent e) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;

                    if (elapsedTime > duration) {
                        ((Timer) e.getSource()).stop();
                    } else {
                        moveButtonsToLeft();
                    }
                }
            });

            timer.start();
            movimiento = true;
        }
    }

    private void moveButtonsToLeft() {
        for (JButton button : buttons) {
            button.setLocation(button.getX() - 40, button.getY());
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
                cargarPanelAgendarCita();
                break;
    
            case 2:
                cargarPanelAgendarCita();
                break;
    
            case 3:
                cargarPanelCancelarCita();
                break;
    
            case 4:
                cargarPanelActivarCita();
                break;
    
            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }
    
    private void cargarPanelAgendarCita() {
        if (movimiento) {
            JTextField textFieldApellido;
            JTextField textFieldNombre;
            JTextField textFieldDocumento;

            JPanel panelAgendarCita = new JPanel();
            panelAgendarCita.setBackground(new Color(7, 29, 68));
            panelAgendarCita.setLayout(null);
            panelAgendarCita.setBounds(760, 0, 800, 900);

            JButton btnAgendarCita = new JButton("Agendar cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(310, 700, 180, 40);
            panelAgendarCita.add(btnAgendarCita);

            String[] sedes = {"Bucaramanga", "Floridablanca", "Piedecuesta", "Girón", "Lebrija", "Pamplona", "Rionegro"};
            String[] opciones = {"Medicina familiar", "Fisioterapia", "Medina interna", "Psicología"};

            JComboBox<String> menuDesplegableSedes = new JComboBox<>(sedes);
            menuDesplegableSedes.setModel(new DefaultComboBoxModel<>(opciones));
            menuDesplegableSedes.setBounds(310, 600, 180, 30);
            panelAgendarCita.add(menuDesplegableSedes);

            JComboBox<String> menuDesplegableOpciones = new JComboBox<>(opciones);
            menuDesplegableOpciones.setModel(new DefaultComboBoxModel<>(sedes));
            menuDesplegableOpciones.setBounds(310, 505, 180, 30);
            panelAgendarCita.add(menuDesplegableOpciones);

            textFieldNombre = new JTextField();
            textFieldNombre.setBounds(310, 208, 180, 34);
            panelAgendarCita.add(textFieldNombre);

            textFieldApellido = new JTextField();
            textFieldApellido.setBounds(310, 307, 180, 34);
            panelAgendarCita.add(textFieldApellido);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(310, 406, 180, 34);
            panelAgendarCita.add(textFieldDocumento);

            JLabel lblNombre = new JLabel("Nombre");
            lblNombre.setForeground(new Color(255, 255, 255));
            lblNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombre.setBounds(310, 153, 180, 45);
            panelAgendarCita.add(lblNombre);

            JLabel lblApellido = new JLabel("Apellido");
            lblApellido.setForeground(new Color(255, 255, 255));
            lblApellido.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblApellido.setBounds(310, 252, 180, 45);
            panelAgendarCita.add(lblApellido);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(310, 351, 180, 45);
            panelAgendarCita.add(lblDocumento);

            contentPane.add(panelAgendarCita);
            contentPane.setComponentZOrder(panelAgendarCita, 0);

            JLabel lblEspecialidad = new JLabel("Especialidad");
            lblEspecialidad.setForeground(new Color(255, 255, 255));
            lblEspecialidad.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblEspecialidad.setBounds(310, 545, 180, 45);
            panelAgendarCita.add(lblEspecialidad);

            JLabel lblCiudad = new JLabel("Ciudad");
            lblCiudad.setForeground(new Color(255, 255, 255));
            lblCiudad.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblCiudad.setBounds(310, 450, 180, 45);
            panelAgendarCita.add(lblCiudad);

        }
    }
    
    private void cargarPanelCancelarCita() {
        if (movimiento) {
            //movimiento = false;
            JTextField textFieldDocumento;
            
            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(760, 0, 800, 900);  // Cambiado el límite inferior a 0

            JButton btnAgendarCita = new JButton("Buscar cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(305, 437, 180, 40);
            panelCancelarCita.add(btnAgendarCita);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(305, 376, 180, 34);
            panelCancelarCita.add(textFieldDocumento);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(338, 321, 114, 45);
            panelCancelarCita.add(lblDocumento);

            contentPane.add(panelCancelarCita);
            contentPane.setComponentZOrder(panelCancelarCita, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }    

    private void cargarPanelActivarCita() {
        if (movimiento) {
            //movimiento = false;
            JTextField textFieldDocumento;
            
            JPanel panelActivarCita = new JPanel();
            panelActivarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActivarCita.setLayout(null);
            panelActivarCita.setBounds(760, 0, 800, 900);  // Cambiado el límite inferior a 0

            JButton btnAgendarCita = new JButton("Buscar cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(305, 437, 180, 40);
            panelActivarCita.add(btnAgendarCita);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(305, 376, 180, 34);
            panelActivarCita.add(textFieldDocumento);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(338, 321, 114, 45);
            panelActivarCita.add(lblDocumento);

            contentPane.add(panelActivarCita);
            contentPane.setComponentZOrder(panelActivarCita, 0);

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
                new AgenteAtencionAlPaciente().setVisible(true);
            }
        });
    }
}

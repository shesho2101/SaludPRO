package co.edu.upb;

import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import principal.DAO.Abstract.CallDAO;
import principal.dominio.cita.CitaServices;
import principal.dominio.historialClinico.HistorialClinicoServices;

public class Medico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelActual; // Panel actual que se va a mostrar
    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;
    //Base de datos
    private HistorialClinicoServices hcs;
    private CitaServices cs;

    //Historia Clinica
    private JButton btnHistorial;
    private JTextField textFieldDocumentoHis;

    //Calendario
    private JCalendar calendario;
    private JButton btnCalendar;

    //Reporte
    private JButton btnReportarCita;
    private JTextField textFieldDocumentoRep;
    private JTextField dateRep;


    public Medico() {
        //Inicializacion base de datos
        this.hcs = new HistorialClinicoServices();
        this.cs = new CitaServices();

        //Historial
        this.btnHistorial = new JButton();
        this.textFieldDocumentoHis = new JTextField();

        //Calendario
        this.calendario = new JCalendar();
        this.btnCalendar = new JButton();

        //Reporte
        this.btnReportarCita = new JButton();
        this.dateRep = new JTextField();
        this.textFieldDocumentoRep = new JTextField();

        FrameController.registerFrame("MedicoFrame", this);


        setTitle("IPS Salud Pro - Medico");
        setSize(1600, 900);
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


        // Añadir el botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnCerrarSesion.setBounds(10, 790, 200, 40); // Posición en la parte inferior izquierda
        btnCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarSesion(); // Llama a la función para cerrar sesión
            }
        });
        contentPane.add(btnCerrarSesion);

        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazAzul.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
        addActionHistorial();
        addActionCalendar();
        addActionRep();
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
                // Posición final donde queremos que se detengan los botones
                final int posicionFinal = 250; // Ajusta este valor a tu posición final deseada
                int deltaX = 33;
                long startTime = System.currentTimeMillis();
                int positionLimit = -33; // Limite de posición para detener el desplazamiento

                public void actionPerformed(ActionEvent e) {
                    for (JButton button : buttons) {
                        // Nueva posición
                        int newPosX = button.getX() - deltaX;

                        // Si la nueva posición es menor o igual a la posición final, detén el desplazamiento
                        if (newPosX <= posicionFinal) {
                            newPosX = posicionFinal; // Asegurar que no se pase de la posición final
                            ((Timer) e.getSource()).stop(); // Detener el Timer
                            movimiento = false; // Indicar que el movimiento ha terminado
                        }

                        // Asignar la nueva posición al botón
                        button.setLocation(newPosX, button.getY());
                    }
                }
            });

            timer.start(); // Iniciar el Timer
            movimiento = true; // Indicar que se está moviendo
        }
    }

    private void crearNuevoPanel(int opcion) {
        if (panelActual != null) {
            limpiarPanel(panelActual);
        }
        // Asegúrate de inicializar nuevoPanel
        if (nuevoPanel == null) {
            nuevoPanel = new JPanel();
        }

        panelActual = new JPanel(); // Crear nuevo panel
        panelActual.setBackground(new Color(250, 250, 250));
        panelActual.setBounds(800, 0, 800, 900);

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
        if (movimiento && panelActual != null) {
            limpiarPanel(panelActual);

            calendario.setVisible(false);
            btnCalendar.setVisible(false);
            
            panelActual.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            btnHistorial.setText("Buscar historial");
            btnHistorial.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnHistorial.setBounds(305, 437, 200, 40);
            panelActual.add(btnHistorial);

            JLabel lblDocumento = new JLabel("<html>Documento<p> Paciente<html>");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(340, 321, 120, 45);
            panelActual.add(lblDocumento);

            textFieldDocumentoHis.setBounds(305, 376, 200, 34);
            textFieldDocumentoHis.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelActual.add(textFieldDocumentoHis);

            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }

        }
    }
    
    private void addActionHistorial(){
        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFieldDocumentoHis.getText() == null || textFieldDocumentoHis.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "No hay documento ingresado");
                } else{
                    JFrame frame = new JFrame();
                    frame.setSize(1000, 600);
                    frame.setVisible(true);

                    JTextArea text = new JTextArea();
                    text.setFont(new Font("Tahoma", Font.BOLD, 17));
                    try {
                        text.setText(hcs.constructHis(textFieldDocumentoHis.getText()).toString());

                        JScrollPane scroll = new JScrollPane(text);
                        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        frame.getContentPane().add(scroll, BorderLayout.CENTER);
                    } catch (Exception ex) {
                        Logger.getLogger(Medico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
    }

    private void cargarPanelVerAgenda() {
        if (movimiento && panelActual != null) {
            //movimiento = false;
            limpiarPanel(panelActual);

            calendario.setVisible(true);
            btnCalendar.setVisible(true);

            panelActual.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0
            
            
            calendario.setBounds(150,150,500,400);
            panelActual.add(calendario);
            
            btnCalendar.setText("Mostrar citas");
            btnCalendar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnCalendar.setBounds(305, 600, 200, 40);
            panelActual.add(btnCalendar);
            
            getContentPane().add(panelActual);
            getContentPane().setComponentZOrder(panelActual, 0);
            panelActual.revalidate();
            panelActual.repaint();
            
            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }
    
    private void addActionCalendar(){
        btnCalendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                JFrame frame = new JFrame();
                frame.setSize(1000, 600);
                frame.setVisible(true);
                
                Date fechaSelec = calendario.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaSelec);
                
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaFormateada = sdf.format(fechaSelec);
                
                System.out.println(fechaFormateada);
                
                JTextArea text = new JTextArea();
                text.setFont(new Font("Tahoma", Font.BOLD, 17));
                try {
                    text.setText(cs.getCitasLikeFecha(fechaFormateada).toString());
                } catch (Exception ex) {
                }
                
                
                JScrollPane scroll = new JScrollPane(text);
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                frame.getContentPane().add(scroll, BorderLayout.CENTER);
                    
                
            }
        });
    }
    
    private void cargarPanelReportar() {
        if (movimiento && panelActual != null) {
            limpiarPanel(panelActual);
            calendario.setVisible(false);
            btnCalendar.setVisible(false);

            panelActual.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            btnReportarCita.setText("Reportar cita");
            btnReportarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnReportarCita.setBounds(305, 437, 180, 40);
            panelActual.add(btnReportarCita);

            textFieldDocumentoRep.setBounds(180, 376, 180, 34);
            textFieldDocumentoRep.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelActual.add(textFieldDocumentoRep);
            
            dateRep.setBounds(430, 376, 180, 34);
            dateRep.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelActual.add(dateRep);

            JLabel lblDocumento = new JLabel("<html>Documento<p> Paciente<html>");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(210, 321, 120, 45);
            panelActual.add(lblDocumento);
            
            JLabel lblDocumento2 = new JLabel("Fecha Cita");
            lblDocumento2.setForeground(new Color(255, 255, 255));
            lblDocumento2.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento2.setBounds(465, 321, 120, 45);
            panelActual.add(lblDocumento2);
            
            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }
    
    private void addActionRep(){
        btnReportarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cs.modificarAsistencia(textFieldDocumentoRep.getText(), dateRep.getText(), true);
                } catch (Exception ex) {
                    
                }
            }
        });
    }

    private void cerrarSesion() {
        FrameController.openFrame("LoginFrame");
        FrameController.cerrarSesion(); // Llama al controlador para cerrar sesión

    }

    private void limpiarPanel(JPanel panel) {
        if (panel != null) {
            panel.removeAll(); // Eliminar todos los componentes del panel
            panel.repaint();   // Repintar para asegurar que todo esté limpio
        }
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


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
    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;
    //Base de datos
    private HistorialClinicoServices hcs;
    private CitaServices cs;
    
    //Historia Clinica
    private JButton btnHistorial;
    private JTextField textFieldDocumento;
    
    //Calendario
    private JCalendar calendario;
    private JButton btnCalendar;
    
    
    public Medico() {
        //Inicializacion base de datos
        this.hcs = new HistorialClinicoServices();
        this.cs = new CitaServices();
        
        //Historial
        this.btnHistorial = new JButton();
        this.textFieldDocumento = new JTextField();
        
        //Calendario
        this.calendario = new JCalendar();
        this.btnCalendar = new JButton();
        
        
        
        
        
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
        
        /*
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
        */
        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazAzul.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
        addActionHistorial();
        addActionCalendar();
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
                int deltaX = 30;
                int duration = 240;
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
            
            
            JPanel panelHistoriaClinica = new JPanel();
            panelHistoriaClinica.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelHistoriaClinica.setLayout(null);
            panelHistoriaClinica.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            btnHistorial.setText("Buscar historial");
            btnHistorial.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnHistorial.setBounds(305, 437, 200, 40);
            panelHistoriaClinica.add(btnHistorial);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(340, 321, 120, 45);
            panelHistoriaClinica.add(lblDocumento);

            textFieldDocumento.setBounds(305, 376, 200, 34);
            textFieldDocumento.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelHistoriaClinica.add(textFieldDocumento);

            contentPane.add(panelHistoriaClinica);
            contentPane.setComponentZOrder(panelHistoriaClinica, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }

        }
    }
    
    public void addActionHistorial(){
        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFieldDocumento.getText() == null || textFieldDocumento.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "No hay documento ingresado");
                } else{
                    JFrame frame = new JFrame();
                    frame.setSize(1000, 600);
                    frame.setVisible(true);

                    JTextArea text = new JTextArea();
                    text.setFont(new Font("Tahoma", Font.BOLD, 17));
                    try {
                        text.setText(hcs.constructHis(textFieldDocumento.getText()).toString());

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
        if (movimiento) {
            //movimiento = false;

            

            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0
            
            
            calendario.setBounds(150,150,500,400);
            panelCancelarCita.add(calendario);
            
            btnCalendar.setText("Mostrar citas");
            btnCalendar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnCalendar.setBounds(305, 600, 200, 40);
            panelCancelarCita.add(btnCalendar);
            
            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);
            panelCancelarCita.revalidate();
            panelCancelarCita.repaint();
            
            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }
    
    public void addActionCalendar(){
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
        if (movimiento) {
            //movimiento = false;
            JTextField textFieldDocumento;

            JPanel panelReportar = new JPanel();
            panelReportar.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelReportar.setLayout(null);
            panelReportar.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

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
            lblDocumento.setBounds(338, 321, 120, 45);
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


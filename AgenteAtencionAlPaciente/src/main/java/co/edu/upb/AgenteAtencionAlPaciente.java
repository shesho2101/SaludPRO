package co.edu.upb;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import principal.DAO.Abstract.CallDAO;
import principal.dominio.cita.Cita;
import principal.dominio.cita.CitaServices;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medico.MedicoServices;
import principal.dominio.sede.SedeServices;

public class AgenteAtencionAlPaciente extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private final int panelPositionX = 770;
    private CitaServices cs;
    private ConsultorioServices conSer;
    private SedeServices ss;
    private MedicoServices ms;
    private CallDAO cd;
    
    //Agendar cita variables
    private JTextField textFieldFecha;
    private JTextField textFieldConsultorio;
    private JTextField textFieldDocumentoPac;
    private JTextField textFieldDocumentoDoc;
    private JButton btnAgendarCita;
    private JComboBox<String> menuDesplegableOpciones;
    private JComboBox<String> menuDesplegableSedes;
    
    //Cancelar cita variables
    private JTextField textFieldDocumentoPacCan;
    private JButton btnCancelarCita;
    private JTable tablaCitas;
    private JScrollPane scroll;
    private DefaultTableModel dt;
    private JButton btnBRACita;
    
    //Reprogramar cita
    private JTextField textFieldDateNew;
    
    public AgenteAtencionAlPaciente() throws Exception {

        FrameController.registerFrame("AgenteFrame", this);


        this.cs = new CitaServices();
        this.ss = new SedeServices();
        this.ms = new MedicoServices();
        this.cd = new CallDAO();
        this.conSer = new ConsultorioServices();
        
        //Agendar cita
        this.btnAgendarCita = new JButton();
        
       
        
        //Cancelar cita
        this.btnCancelarCita = new JButton();
        this.tablaCitas = new JTable();
        this.scroll = new JScrollPane(tablaCitas);
        this.dt = new DefaultTableModel();
        this.btnBRACita = new JButton();
        

        setTitle("IPS Salud Pro - Agente atención al paciente");
        setSize(1600, 900);
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
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/interfazBlanca.jpg")));
        lblFondo.setIcon(icon);
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
        addActionAgregar();
        createTable();
        addActionSearch();
        addActionBRA();
    }

    private JButton createButton(final String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(x, y, 220, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
                } catch (Exception ex) {
                    try {
                        throw ex;
                    } catch (Exception ex1) {
                        Logger.getLogger(AgenteAtencionAlPaciente.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                
            }
        });
        contentPane.add(button);
        return button;
    }

    private void cerrarSesion() {
        FrameController.openFrame("LoginFrame");
        FrameController.cerrarSesion(); // Llama al controlador para cerrar sesión

    }

    private void moverBotones() {
        if (!movimiento) {
            Timer timer = new Timer(10, new ActionListener() {
                final int deltaX = 30;
                final int duration = 215;
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

    private void crearNuevoPanel(int opcion) throws Exception {
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
                cargarPanelReprogramarCita();
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
    
    private void cargarPanelAgendarCita() throws Exception {
        if (movimiento) {
            

            JPanel panelAgendarCita = new JPanel();
            panelAgendarCita.setBackground(new Color(7, 29, 68));
            panelAgendarCita.setLayout(null);
            panelAgendarCita.setBounds(800, 0, 800, 900);

            btnAgendarCita.setText("Agendar cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(310, 700, 180, 40);
            panelAgendarCita.add(btnAgendarCita);

            String[] sedes = {"Bucaramanga", "Floridablanca", "Piedecuesta", "Girón", "Lebrija", "Pamplona", "Rionegro"};
            String[] opciones = {"Medicina familiar", "Fisioterapia", "Medina interna", "Psicología"};

            menuDesplegableSedes = new JComboBox<>(sedes);
            menuDesplegableSedes.setModel(new DefaultComboBoxModel<>(sedes));
            menuDesplegableSedes.setBounds(310, 600, 180, 30);
            panelAgendarCita.add(menuDesplegableSedes);

            menuDesplegableOpciones = new JComboBox<>(opciones);
            menuDesplegableOpciones.setModel(new DefaultComboBoxModel<>(opciones));
            menuDesplegableOpciones.setBounds(310, 505, 180, 30);
            panelAgendarCita.add(menuDesplegableOpciones);

            textFieldDocumentoPac = new JTextField();
            textFieldDocumentoPac.setBounds(310, 208, 180, 34);
            panelAgendarCita.add(textFieldDocumentoPac);

            textFieldDocumentoDoc = new JTextField();
            textFieldDocumentoDoc.setBounds(310, 307, 180, 34);
            panelAgendarCita.add(textFieldDocumentoDoc);

            textFieldConsultorio = new JTextField();
            textFieldConsultorio.setBounds(440, 406, 180, 34);
            panelAgendarCita.add(textFieldConsultorio);
            
            textFieldFecha = new JTextField();
            textFieldFecha.setBounds(175, 406, 180, 34);
            panelAgendarCita.add(textFieldFecha);

            JLabel lblNombre = new JLabel("<html>Documento<p> Paciente<html>");
            lblNombre.setForeground(new Color(255, 255, 255));
            lblNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombre.setBounds(310, 153, 180, 45);
            panelAgendarCita.add(lblNombre);

            JLabel lblApellido = new JLabel("<html>Documento<p> Doctor<html>");
            lblApellido.setForeground(new Color(255, 255, 255));
            lblApellido.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblApellido.setBounds(310, 252, 180, 45);
            panelAgendarCita.add(lblApellido);

            JLabel lblDocumentoPac = new JLabel("Consultorio");
            lblDocumentoPac.setForeground(new Color(255, 255, 255));
            lblDocumentoPac.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumentoPac.setBounds(450, 351, 180, 45);
            panelAgendarCita.add(lblDocumentoPac);
            
            JLabel lblDocumentoDoc = new JLabel("Fecha");
            lblDocumentoDoc.setForeground(new Color(255, 255, 255));
            lblDocumentoDoc.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumentoDoc.setBounds(200, 351, 180, 45);
            panelAgendarCita.add(lblDocumentoDoc);

            contentPane.add(panelAgendarCita);
            contentPane.setComponentZOrder(panelAgendarCita, 0);

            JLabel lblEspecialidad = new JLabel("Ciudad");
            lblEspecialidad.setForeground(new Color(255, 255, 255));
            lblEspecialidad.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblEspecialidad.setBounds(310, 545, 180, 45);
            panelAgendarCita.add(lblEspecialidad);

            JLabel lblCiudad = new JLabel("Especialidad");
            lblCiudad.setForeground(new Color(255, 255, 255));
            lblCiudad.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblCiudad.setBounds(310, 450, 180, 45);
            panelAgendarCita.add(lblCiudad);
            
                
            
            //agregarCita(, , t, );
            
        }
    }
    
    private void addActionAgregar(){
        btnAgendarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    agregarCita((String) menuDesplegableSedes.getSelectedItem(),textFieldConsultorio.getText(),textFieldDocumentoDoc.getText() , textFieldDocumentoPac.getText(), textFieldFecha.getText(), (String) menuDesplegableOpciones.getSelectedItem());
                } catch (Exception ex) {
                    Logger.getLogger(AgenteAtencionAlPaciente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void agregarCita(String sede, String consultorio, String docMed, String docPac, String fecha, String especializacion) throws Exception{
        try {
            int codSede = ss.searchPerNombre(sede).getCod();
            if(ms.searchPerEspecializacion(especializacion, docMed) != null){
                cs.createCita(conSer.searchPerSede(codSede,consultorio).getNumHab(), docMed, fecha, docPac);
            }            
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void cargarPanelReprogramarCita() {
        if (movimiento) {
            //movimiento = false;
            
            
            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            btnCancelarCita.setText("Buscar citas");
            btnCancelarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnCancelarCita.setBounds(200, 580, 180, 40);
            panelCancelarCita.add(btnCancelarCita);
            
            btnBRACita.setText("Reprogramar");
            btnBRACita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBRACita.setBounds(415, 580, 180, 40);
            panelCancelarCita.add(btnBRACita);
            
            panelCancelarCita.add(scroll);
            
            textFieldDocumentoPacCan = new JTextField();
            textFieldDocumentoPacCan.setBounds(200, 200, 180, 34);
            panelCancelarCita.add(textFieldDocumentoPacCan);
            
            textFieldDateNew = new JTextField();
            textFieldDateNew.setBounds(415, 200, 180, 34);
            panelCancelarCita.add(textFieldDateNew);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(230, 160, 120, 45);
            panelCancelarCita.add(lblDocumento);
            
            JLabel lblDate = new JLabel("<html>Fecha<p> nueva<html>");
            lblDate.setForeground(new Color(255, 255, 255));
            lblDate.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDate.setBounds(465, 150, 120, 45);
            panelCancelarCita.add(lblDate);

            contentPane.add(panelCancelarCita);
            contentPane.setComponentZOrder(panelCancelarCita, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }  
    
    private void cargarPanelCancelarCita() {
        if (movimiento) {
            //movimiento = false;
            
            
            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            btnCancelarCita.setText("Buscar citas");
            btnCancelarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnCancelarCita.setBounds(200, 580, 180, 40);
            panelCancelarCita.add(btnCancelarCita);
            
            btnBRACita.setText("Borrar");
            btnBRACita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBRACita.setBounds(415, 580, 180, 40);
            panelCancelarCita.add(btnBRACita);
            
            panelCancelarCita.add(scroll);
            
            textFieldDocumentoPacCan = new JTextField();
            textFieldDocumentoPacCan.setBounds(305, 200, 180, 34);
            panelCancelarCita.add(textFieldDocumentoPacCan);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(335, 160, 120, 45);
            panelCancelarCita.add(lblDocumento);

            contentPane.add(panelCancelarCita);
            contentPane.setComponentZOrder(panelCancelarCita, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }   
    
    private void createTable(){
        String[] nombreC = {"Numero Cita","Consultorio", "Fecha", "Asistencia"};
        dt.setColumnIdentifiers(nombreC);
        tablaCitas.setDefaultEditor(Object.class, null);
        tablaCitas.setModel(dt);
        tablaCitas.getTableHeader().setReorderingAllowed(false);
        scroll.setBounds(225, 280, 350, 250);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setBackground(Color.blue);
    }
    
    private void getCitas(String id) throws Exception{
        try {
            
            String sql = "SELECT NumCita, NumHab, fecha, asistencia FROM cita "
                    + "INNER JOIN paciente USING(ID_Paciente)"
                    + "WHERE ID_Paciente = '" + id + "'"
                    + "ORDER BY fecha ASC";
            
            ResultSet rs = cd.consultDataBase(sql);
            while(rs.next()){
                dt.addRow(new Object[] {rs.getInt(1), conSer.searchPerCod(rs.getInt(2)).getNombre(), rs.getDate(3).toString(), rs.getBoolean(4)});
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void addActionSearch(){
        btnCancelarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(tablaCitas.getRowCount() != 0){
                        dt.setNumRows(0);
                    } else{
                        getCitas(textFieldDocumentoPacCan.getText());
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(AgenteAtencionAlPaciente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void addActionBRA(){
        btnBRACita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(btnBRACita.getText().equals("Borrar")){
                        int row = tablaCitas.getSelectedRow();
                        if(row == -1){
                            throw new Exception("No escogio fila");
                        }
                        int numCita = (int) dt.getValueAt(row, 0);
                        dt.removeRow(row);
                        cs.deleteCita(numCita);
                    }
                    if(btnBRACita.getText().equals("Reprogramar")){
                        int row = tablaCitas.getSelectedRow();
                        int numCita = (int) dt.getValueAt(row, 0);
                        dt.setValueAt(textFieldDateNew.getText(), row, 2);
                        cs.modificarCita(numCita, textFieldDateNew.getText());
                    }
                    if(btnBRACita.getText().equals("Activar")){
                        int row = tablaCitas.getSelectedRow();
                        int numCita = (int) dt.getValueAt(row, 0);
                        dt.setValueAt(true, row, 3);
                        cs.modificarAsistencia(numCita, true);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(AgenteAtencionAlPaciente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    

    private void cargarPanelActivarCita() {
        if (movimiento) {
            //movimiento = false;
            
            
            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0
            
            btnCancelarCita.setText("Buscar citas");
            btnCancelarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnCancelarCita.setBounds(200, 580, 180, 40);
            panelCancelarCita.add(btnCancelarCita);
            
            btnBRACita.setText("Activar");
            btnBRACita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBRACita.setBounds(415, 580, 180, 40);
            panelCancelarCita.add(btnBRACita);
            
            panelCancelarCita.add(scroll);
            
            textFieldDocumentoPacCan = new JTextField();
            textFieldDocumentoPacCan.setBounds(305, 200, 180, 34);
            panelCancelarCita.add(textFieldDocumentoPacCan);

            JLabel lblDocumento = new JLabel("<html>Documento<p> Paciente<html>");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(335, 150, 120, 45);
            panelCancelarCita.add(lblDocumento);

            contentPane.add(panelCancelarCita);
            contentPane.setComponentZOrder(panelCancelarCita, 0);

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
                try {
                    new AgenteAtencionAlPaciente().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(AgenteAtencionAlPaciente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}

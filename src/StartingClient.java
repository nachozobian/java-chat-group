import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingClient{
     private JPanel panelPrincipal;
     public JFrame framePrincipal;
     private JTextField puerto;
     private JTextField nombre;
     private JTextField ipAdress;

    public String clientName;
    public static int clientPort;
    public static String clientiP;
    Client c;
    JPanel panel, panel_1;
    JLabel logo,chatLabel,lblName,portNum, lblIP, flag1, flag2, flag3;
    JButton NewButton;
    public StartingClient(){

        framePrincipal = new JFrame();
        framePrincipal.setType(Window.Type.POPUP);
        framePrincipal.setResizable(false);
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePrincipal.setBounds(150,100, 400, 500);

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new LineBorder(new Color(0,0,0), 3, false));
        panelPrincipal.setBackground(new Color(140, 232, 140));
        framePrincipal.setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0,0,0), 3, true));
        panel.setBackground(new Color(0, 222, 98));
        panelPrincipal.add(panel);
        panelPrincipal.setLayout(null);

        logo = new JLabel("");
        logo.setBounds(5,5, 130, 60);
        ImageIcon logImage = new ImageIcon("src/marca doble-1v2.png");
        Image rimage = logImage.getImage();
        Image rScale = rimage.getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon rScaledIcon = new ImageIcon(rScale);
        logo.setIcon(rScaledIcon);
        panelPrincipal.add(logo);

        chatLabel = new JLabel("CONEXION DE UN CLIENTE");
        chatLabel.setFont(new Font("Garamond", Font.BOLD, 18));
        chatLabel.setForeground(new Color(44, 46, 43));
        chatLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        chatLabel.setBounds(50, 200, 300, 300);
        panelPrincipal.add(chatLabel);

        panel_1 = new JPanel();
        panel_1.setBackground(new Color(94, 179, 60));
        panel_1.setBorder(new LineBorder(new Color(0,0,0),3, true));
        panel_1.setBounds(45,70, 300, 350);
        panelPrincipal.add(panel_1);
        panel_1.setLayout(null);


        lblName = new JLabel("NOMBRE:");
        lblName.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        lblName.setFont(new Font("Arial", Font.BOLD, 12));
        lblName.setBounds(10,42,100, 20);
        panel_1.add(lblName);

        nombre = new JTextField();
        nombre.setColumns(20);
        nombre.setBounds(100, 42, 100,20);
        panel_1.add(nombre);

        portNum = new JLabel("PUERTO:");
        portNum.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        portNum.setFont(new Font("Arial", Font.BOLD, 12));
        portNum.setBounds(10,92,100, 20);
        panel_1.add(portNum);

        puerto = new JTextField();
        puerto.setColumns(20);
        puerto.setBounds(100, 92, 100,20);
        panel_1.add(puerto);

        lblIP = new JLabel("IP:");
        lblIP.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        lblIP.setFont(new Font("Arial", Font.BOLD, 12));
        lblIP.setBounds(10,142,100, 20);
        panel_1.add(lblIP);

        ipAdress = new JTextField();
        ipAdress.setColumns(20);
        ipAdress.setBounds(100, 142, 100,20);
        panel_1.add(ipAdress);

        flag1 = new JLabel("---------");
        flag1.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        flag1.setForeground(Color.yellow);
        flag1.setBounds(80, 60, 160, 20);
        panel_1.add(flag1);

        flag2 = new JLabel("---------");
        flag2.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        flag2.setForeground(Color.yellow);
        flag2.setBounds(80, 110, 160, 20);
        panel_1.add(flag2);

        flag3 = new JLabel("---------");
        flag3.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        flag3.setForeground(Color.yellow);
        flag3.setBounds(80, 160, 160, 20);
        panel_1.add(flag3);


        NewButton = new JButton("START");
        NewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nombre.getText();
                String pNum = puerto.getText();
                String ipS = ipAdress.getText();
                framePrincipal.dispose();
                if (name.isEmpty()) {
                    flag1.setText("Parametro obigatorio!!");
                } else if (pNum.isEmpty()) {
                    flag2.setText("Parmetro obligatorio!!");
                } else if (ipS.isEmpty()) {
                    flag3.setText("Parametro obligatorio!!");
                } else {
                    int po = Integer.parseInt(pNum);
                    clientName = name;
                    clientPort = po;
                    clientiP = ipS;
                    Client c = new Client(clientName, clientPort, clientiP);
                    c.connect();
                    c.startReading();
                }
            }
        });
        NewButton.setBackground(new Color(50, 140, 90));
        NewButton.setBounds(50, 200, 180, 20);
        panel_1.add(NewButton);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //Asi aseguramos que la ventana no sea ejecutada en otro thread
            @Override
            public void run() {
                try{
                    StartingClient frame = new StartingClient();
                    frame.framePrincipal.setVisible(true);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}

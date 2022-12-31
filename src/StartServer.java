
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StartServer implements Runnable{

    private JPanel panelPrincipal;
    public JFrame framePrincipal;
    private JTextField puerto;
    static int numPuerto;
    static Socket socket;
    public static ArrayList clientes = new ArrayList();

    public StartServer(Socket socket)  {
        try{
            StartServer.socket = socket;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public StartServer(){

        framePrincipal = new JFrame();
        framePrincipal.setType(Window.Type.POPUP);
        framePrincipal.setResizable(false);
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePrincipal.setBounds(150,100, 400, 500);

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new LineBorder(new Color(0,0,0), 3, false));
        panelPrincipal.setBackground(new Color(204, 255, 204));
        framePrincipal.setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0,0,0), 3, true));
        panel.setBackground(new Color(0, 222, 98));
        panelPrincipal.add(panel);
        panelPrincipal.setLayout(null);

        JLabel logo = new JLabel("");
        logo.setBounds(5,5, 130, 60);
        ImageIcon logImage = new ImageIcon("src/marca doble-1v2.png");
        Image rimage = logImage.getImage();
        Image rScale = rimage.getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon rScaledIcon = new ImageIcon(rScale);
        logo.setIcon(rScaledIcon);
        panelPrincipal.add(logo);

        JLabel chatLabel = new JLabel("UMA CHAT GROUP");
        chatLabel.setFont(new Font("Garamond", Font.BOLD, 18));
        chatLabel.setForeground(new Color(44, 46, 43));
        chatLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        chatLabel.setBounds(50, 200, 300, 300);
        panelPrincipal.add(chatLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(94, 179, 60));
        panel_1.setBorder(new LineBorder(new Color(0,0,0),3, true));
        panel_1.setBounds(45,70, 300, 350);
        panelPrincipal.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblChat = new JLabel("Inserte el número del puerto para entrar al grupo");
        lblChat.setFont(new Font("Arial", Font.BOLD, 12));
        lblChat.setBounds(10,50, 300, 23);
        panel_1.add(lblChat);

        JLabel portNum = new JLabel("PUERTO:");
        portNum.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        portNum.setFont(new Font("Arial", Font.BOLD, 12));
        portNum.setBounds(10,92,100, 20);
        panel_1.add(portNum);

        puerto = new JTextField();
        puerto.setColumns(20);
        puerto.setBounds(100, 90, 190,20);
        panel_1.add(puerto);

        JLabel flag = new JLabel("---------");
        flag.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        flag.setForeground(Color.yellow);
        flag.setBounds(110, 120, 160, 20);
        panel_1.add(flag);

        JButton NewButton = new JButton("START");
        NewButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String prt = puerto.getText();
                if(prt.isEmpty()){
                    flag.setText("Hace falta un puerto");
                }
                else{
                    int po = Integer.parseInt(prt);
                    numPuerto = po;
                    framePrincipal.dispose();
                    ServerSocket s;
                    try{
                        s = new ServerSocket(po);
                        JOptionPane.showMessageDialog(null, "START");
                        int poolsize = 10*Runtime.getRuntime().availableProcessors();
                        ExecutorService tasks = Executors.newFixedThreadPool(poolsize);
                        while(true){
                            Socket socket = s.accept();
                            System.out.println("ACEPTAD");
                            tasks.execute(new StartServer(socket));
                        }
                    }
                    catch(Exception exc){
                        exc.printStackTrace();
                    }

                }
            }
        });
        NewButton.setBackground(new Color(50, 140, 90));
        NewButton.setBounds(20, 150, 260, 30);
        panel_1.add(NewButton);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() { //Asi aseguramos que la ventana no sea ejecutada en otro thread
            @Override
            public void run() {
              try{
                  StartServer frame = new StartServer();
                  frame.framePrincipal.setVisible(true);
              }
              catch(Exception e){
                  e.printStackTrace();
              }
            }
        });

    }
    @Override
    public void run() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientes.add(writer);
            while(true){
                String type = reader.readLine().trim();
                switch(type){
                    case "2":
                        String data = reader.readLine().trim();
                        if(!data.isEmpty() ){
                            for (int i = 0; i < clientes.size(); i++) {
                                try {
                                    PrintWriter clientHandle = (PrintWriter) clientes.get(i);
                                    clientHandle.write(data);
                                    clientHandle.write("\r\n");
                                    clientHandle.flush();
                                } catch (Exception exc) {
                                    exc.printStackTrace();
                                }
                            }
                            data = "";
                        }
                        break;
                    case "1":
                        //To continue.
                        break;
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client extends JFrame{
    private final String clientName;
    private JTextField message;
    static Socket client;
    static PrintWriter out;
    static BufferedReader buffer_read;
    private JPanel panelPrincipal;
    private JTextArea textArea, textAreaUser;
    public static int clientPort1;
    public static String clientiP1;
    public Client(String clientName, int clientPort, String clientiP){
        this.clientiP1 = clientiP;
        this.clientPort1 = clientPort;
        this.clientName = clientName;
        paintClient();
    }

    public void paintClient(){
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150,100, 400, 500);

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0,0,0), 1, true));
        panel.setBackground(new Color(20, 119, 63));
        panel.setBounds(0,0, 400,50);
        panelPrincipal.add(panel);
        panel.setLayout(null);


        JLabel lblOut = new JLabel("");
        ImageIcon logImage = new ImageIcon("src/Back.png");
        Image rimage = logImage.getImage();
        lblOut.setBounds(3,3,33,45);
        Image rScale = rimage.getScaledInstance(lblOut.getWidth(), lblOut.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon rScaledIcon = new ImageIcon(rScale);
        lblOut.setIcon(rScaledIcon);
        lblOut.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    int question = JOptionPane.showConfirmDialog(null, "Terminar conexion con el servidor?");
                    if(question == 0){
                        client.close();
                        JOptionPane.showMessageDialog(null, "Conexion Terminada!");
                    }
                }catch(Exception exp){
                    exp.printStackTrace();
                }
            }
        });
        panel.add(lblOut);


        JLabel profile = new JLabel("");
        profile.setBounds(43,3, 48, 45);
        ImageIcon logImage1 = new ImageIcon("src/Java.png");
        Image rimage1 = logImage1.getImage();
        Image rScale1 = rimage1.getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon rScaledIcon1 = new ImageIcon(rScale1);
        profile.setIcon(rScaledIcon1);
        profile.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        panel.add(profile);

        JLabel phone = new JLabel("");
        phone.setBounds(310,3, 40, 45);
        ImageIcon logImage4 = new ImageIcon("src/Phone.png");
        Image rimage4 = logImage4.getImage();
        Image rScale4 = rimage4.getScaledInstance(phone.getWidth(), phone.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon rScaledIcon4 = new ImageIcon(rScale4);
        phone.setIcon(rScaledIcon4);
        phone.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        panel.add(phone);

        JLabel lblGroupName1 = new JLabel("Concurrentes 22/23");
        lblGroupName1.setForeground(Color.white);
        lblGroupName1.setFont(new Font("Arial Black", Font.BOLD, 16));
        lblGroupName1.setBounds(105,10,200, 30);
        panel.add(lblGroupName1);


        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 420, 400, 60);
        panel.setBorder(new LineBorder(new Color(0,0,0), 1, true));
        panel_1.setBackground(new Color(199, 218, 205));
        panelPrincipal.add(panel_1);
        panel_1.setLayout(null);

        message = new JTextField();
        message.setBounds(45,5,260,30);
        panel_1.add(message);
        message.setColumns(10);

        JLabel send = new JLabel("");
        send.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e){
                startWriting();
            }
        });

        send.setBounds(320, 5, 45, 30);
        ImageIcon sendLogo = new ImageIcon("src/Send.png");
        Image rimage2 = sendLogo.getImage();
        Image rScale2 = rimage2.getScaledInstance(send.getWidth(), send.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon rScaledIcon2 = new ImageIcon(rScale2);
        send.setIcon(rScaledIcon2);
        panel_1.add(send);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 50, 400, 370);
        panelPrincipal.add(scrollPane);

        textArea = new JTextArea();
        textArea.setEditable(true);
        textArea.setFont(new Font("Garamond", Font.BOLD, 14));
        textArea.setBounds(10, 50, 260, 30);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane.setViewportView(textArea);

        setVisible(true);
        revalidate();
        repaint();
    }


    public void startReading(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    String msg;
                    try{
                        msg = buffer_read.readLine();
                        if (!msg.isEmpty()) {

                            if (textArea.getText().isEmpty()) {
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                textArea.setText(msg + "\n" + sdf.format(cal.getTime()) +"\n");
                                msg = "";

                            } else {
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                textArea.setText(textArea.getText() + msg + "\n" + sdf.format(cal.getTime()) +"\n");
                                msg = "";
                            }

                        }
                    } catch (IOException e) {
                    }
                }
            }
        }).start();

    }

    public void startWriting(){
            try{
                out.write("2\n");
                out.flush();
                out.write(clientName + ": " + message.getText() +"\r\n");
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void connect() {
        try {
            client = new Socket(clientiP1, clientPort1);
            buffer_read = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

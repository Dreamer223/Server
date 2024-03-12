package JDK.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class ClientGUI extends JFrame implements ClientView {

    public static String getName;
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static Dimension screenSize = toolkit.getScreenSize();
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 500;
    private static final int POS_X = (int) (screenSize.getWidth() / 2 - (WINDOW_WIDTH / 2)) + WINDOW_HEIGHT;
    private static final int POS_Y = (int) (screenSize.getHeight() / 2 - (WINDOW_HEIGHT / 2));
    private JTextArea log;
    private JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    private JPasswordField password;
    private JButton btnLogin, btnSend, btnDisconnect;
    private JPanel headerPanel;


    private ClientController clientController;


    public ClientGUI() {
        setting();
        createPanel();

        setVisible(true);
    }


    public void setClient(ClientController clientController) {
        this.clientController = clientController;
    }


    private void setting() {
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }


    @Override
    public void showMessage(String msg) {
        log.append(msg + "\n");
    }



    public void disconnectedFromServer(){
        hideHeaderPanel(true);
    }


    public void disconnectFromServer(){
        clientController.disconnectedFromServer();
        disconnectedFromServer();
    }


    public void hideHeaderPanel(boolean visible){
        headerPanel.setVisible(visible);
    }


    public void login(){
        if (clientController.connectToServer(tfLogin.getText())){
            headerPanel.setVisible(false);
        }
    }


    private void message() throws IOException {
        clientController.message(tfMessage.getText());
        tfMessage.setText("");
    }


    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }


    private Component createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField("Ivan Ivanovich");
        password = new JPasswordField("123456");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }


    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }


    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        btnDisconnect = new JButton("disconnect");
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    try {
                        message();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    message();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        panel.add(btnDisconnect, BorderLayout.SOUTH);
        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnectFromServer();
            }
        });
        return panel;
    }


    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            this.disconnectedFromServer();
        }
    }
}

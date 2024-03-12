package JDK.server;

import JDK.server.ServerController;
import JDK.Repository.FileStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ServerView {
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static Dimension screenSize = toolkit.getScreenSize();
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 500;
    private static final int POS_X = (int) (screenSize.getWidth() / 2 - (WINDOW_WIDTH / 2)) + WINDOW_HEIGHT;
    private static final int POS_Y = (int) (screenSize.getHeight() / 2 - (WINDOW_HEIGHT / 2));

    JButton btnStart, btnStop;
    JTextArea log;

    private ServerController server;

    public ServerGUI(){
        setting();
        createPanel();

        setVisible(true);
    }

    private void setting() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Server");
    }

    public ServerController getConnection(){
        return server;
    }

    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.start();
                btnStart.setEnabled(false);

            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stop();
                btnStart.setEnabled(true);
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }

    @Override
    public void showMessage(String msg) {
        log.append(msg);
    }

    @Override
    public void setServerController(ServerController serverController) {
        this.server = serverController;
    }
}
package JDK.client;

import JDK.server.ServerController;

import java.io.IOException;


/**
 * Класс, с помощью которого клиент подключается к серверу
 */
public class ClientController {
    private boolean connected;
    private String name;
    private ClientView clientView;
    private ServerController server;

    public ClientController(ClientGUI clientView, ServerController server) {
        this.clientView = clientView;
        this.server = server;
        clientView.setClient(this);
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)){
            connected = true;
            showOnWindow("Вы успешно подключились!\n");
            String log = server.getHistory();
            if (log != null){
                showOnWindow(log);
            }
            return true;
        } else {
            showOnWindow("Подключение не удалось");
            return false;
        }
    }



    public void disconnectFromServer() {
        if (connected) {
            connected = false;
            clientView.disconnectFromServer();
            showOnWindow("Вы были отключены от сервера!");
            server.disconnectUser(this);
        }
    }

    public void message(String text) throws IOException {
        if (connected) {
            if (!text.isEmpty()) {
                server.message(name + ": " + text);
            }
        } else {
            showOnWindow("Нет подключения к серверу");
        }
    }

    public String getName() {
        return name;
    }

    protected void showOnWindow(String text) {
        clientView.showMessage(text + "\n");
    }

    public void answerFromServer(String text) {
        showOnWindow(text);
    }

}
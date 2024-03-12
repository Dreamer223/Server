package JDK.server;

import JDK.Repository.Repository;
import JDK.client.ClientController;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean work;
    private final ServerView serverView;
    private List<ClientController> clientList;
    private final Repository repository;

    public ServerController(ServerView serverView, Repository repository) {
        this.serverView = serverView;
        this.repository = repository;
        clientList = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void start(){
        if (work){
            showOnWindow("Сервер уже был запущен");
        } else {
            showOnWindow("Сервер запущен!");
            work = true;
        }
    }

    public void stop(){
        if (!work){
            showOnWindow("Сервер уже был остановлен");
        } else {
            work = false;
            for (ClientController client: clientList){
                disconnectUser(client);
            }
            showOnWindow("Сервер остановлен!");
        }
    }

    public void disconnectUser(ClientController client){
        clientList.remove(client);
        if (client != null){
            client.disconnectFromServer();
            showOnWindow(client.getName() + " отключился от беседы");
        }
    }

    public boolean connectUser(ClientController client){
        if (!work){
            return false;
        }
        clientList.add(client);
        showOnWindow(client.getName() + " подключился к беседе");
        return true;
    }

    public void message(String text) throws IOException {
        if (!work){
            return;
        }
        text += "";
        showOnWindow(text);
        answerAll(text);
        saveInHistory(text);
    }

    public String getHistory() {
        return repository.loadLog();
    }

    private void answerAll(String text){
        for (ClientController client: clientList){
            client.answerFromServer(text);
        }
    }

    private void showOnWindow(String text){
        serverView.showMessage(text + "\n");
    }

    private void saveInHistory(String text) throws IOException {
        repository.saveInLog(text);
    }
}

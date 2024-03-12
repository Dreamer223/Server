package JDK;

import JDK.Repository.FileStorage;
import JDK.Repository.Repository;
import JDK.client.ClientController;
import JDK.client.ClientGUI;
import JDK.server.ServerController;
import JDK.server.ServerGUI;

public class Main {
    public static void main(String[] args) {
        Repository repo = new FileStorage();
        ServerGUI server = new ServerGUI();
        ServerController serverController = new ServerController(server, repo);

        ClientGUI client = new ClientGUI();
        ClientController clientView = new ClientController(client, serverController);
        ClientGUI client2 = new ClientGUI();
        ClientController clientView2 = new ClientController(client2, serverController);
        server.setServerController(serverController);
        client2.setClient(clientView2);
        client.setClient(clientView);

    }
}
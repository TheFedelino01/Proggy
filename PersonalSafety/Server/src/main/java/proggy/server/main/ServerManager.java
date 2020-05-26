package proggy.server.main;

import proggy.server.manager;
import proggy.server.utility.ESPManager;
import proggy.server.utility.Telegram;

public class ServerManager {

    private static ServerManager INSTANCE;

    public static ServerManager getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new ServerManager();
        return INSTANCE;
    }

    private final ESPManager espManager;
    //private final Telegram telegram;
    private final manager manager;
    //thSocket
    private boolean started;

    private ServerManager() {
        manager = new manager();
        espManager = new ESPManager(1234, manager);
        //telegram = new Telegram();
        started = false;
    }


    public void start() {
        if (started)
            throw new IllegalStateException("Server già avviato");

        espManager.start();
        //telegram.start();
    }

    public ESPManager getEspManager() {
        return espManager;
    }


    public boolean isStarted() {
        return started;
    }

}

package proggyGradle.Database;

/**
 * Per switchare velocemente tra db locale e remoto
 */
public class DbManager {
    public static final boolean REMOTE_DB = true;
    private static DbManagerInterface instance;

    public static DbManagerInterface getInstance() {
        if (instance == null) {
            if (REMOTE_DB)
                instance = DbManagerRemote.getInstance();
            else
                instance = DbManagerLocal.getInstance();
        }
        return instance;
    }

    private DbManager() {
    }

}

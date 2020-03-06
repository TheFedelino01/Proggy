package proggyGradle.Database;

public interface DbManagerInterface {
    String getFromDb(String query);

    void writeOnDb(String query);

    String getChatId(String identificatore);
}

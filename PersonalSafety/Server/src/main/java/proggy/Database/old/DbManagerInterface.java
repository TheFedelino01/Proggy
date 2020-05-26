package proggy.Database.old;

public interface DbManagerInterface {
    String getFromDb(String query);

    String writeOnDb(String query);

    String getChatId(String identificatore);
}

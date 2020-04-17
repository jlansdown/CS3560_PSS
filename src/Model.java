public interface Model {
    Task createTask();
    void deleteTask();
    void editTask();
    boolean checkForOverlap();

    //sendError();

}

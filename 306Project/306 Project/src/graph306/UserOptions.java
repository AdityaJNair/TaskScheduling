package graph306;

/**
 * Singleton class to hold the user's input command line parameters.
 */


public class UserOptions {

    //Singleton field
    private static UserOptions instance = new UserOptions(); //May need to verify that this has been instantiated in methods.

    //Data fields
    private String filenameIn;
    private String filenameOut;
    private boolean isVisible;
    private boolean isParallel;
    private int parallelThreads;
    private int processors;

    //Singleton methods
    private UserOptions(){
        //Do nothing, this class shouldn't be instantiated
    }
    /**
     * Gets an instance of the UserOptions object
     * @return The current UserOptions.
     */
    public static UserOptions getInstance(){
        return instance;
    }

    //Getters and setters for all fields.
    public String getFilenameIn() {
        return filenameIn;
    }
    public void setFilenameIn(String filenameIn) {
        this.filenameIn = filenameIn;
    }
    public String getFilenameOut() {
        return filenameOut;
    }
    public void setFilenameOut(String filenameOut) {
        this.filenameOut = filenameOut;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public boolean isParallel() {
        return isParallel;
    }
    public void setParallel(boolean parallel) {
        isParallel = parallel;
    }
    public int getParallelThreads() {
        return parallelThreads;
    }
    public void setParallelThreads(int parallelThreads) {
        this.parallelThreads = parallelThreads;
    }
    public int getProcessors() {
        return processors;
    }
    public void setProcessors(int processors) {
        this.processors = processors;
    }

}

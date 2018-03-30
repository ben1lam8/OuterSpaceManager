package lamit.outerspacemanager.com.outerspacemanager.event;

public class RepositoryMessageEvent {

    private final String message;
    private final int length;

    public RepositoryMessageEvent(String message, int length){
        this.message = message;
        this.length = length;
    }

    public String getMessage() {
        return message;
    }

    public int getLength() {
        return length;
    }
}

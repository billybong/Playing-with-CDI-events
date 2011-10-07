package playingwithevents.events;

public class MyErrorEvent extends BaseEvent{

    private final String name;
    private final Throwable exception;

    public MyErrorEvent(String name, Throwable exception) {
        this.name = name;
        this.exception = exception;
    }

    public String getName() {
        return name;
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "MyErrorEvent{" +
                "name='" + name + '\'' +
                ", exception=" + exception +
                '}';
    }
}

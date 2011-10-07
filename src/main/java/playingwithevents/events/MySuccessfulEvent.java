package playingwithevents.events;

public class MySuccessfulEvent extends BaseEvent{
    @Override
    public String toString() {
        return "MySuccessfulEvent{" +
                "name='" + name + '\'' +
                '}';
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

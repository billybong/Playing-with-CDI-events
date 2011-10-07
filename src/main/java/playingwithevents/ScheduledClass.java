package playingwithevents;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import playingwithevents.events.*;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Singleton
public class ScheduledClass {

    @Inject
    @InForeground
    private Event<BaseEvent> event;

    @Inject
    @Category("ScheduledClass")
    Logger log;

    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void fireOfSuccesfulEvent(){
        log.info("About to fire of event");

        MySuccessfulEvent mySuccessfulEvent = new MySuccessfulEvent();
        mySuccessfulEvent.setName("I am alive!!");

        this.event.select(ConfigTypeAnnotation.getConfigTypeAnnotation(MySuccessfulEvent.class)).fire(mySuccessfulEvent);

        log.info("Fired event: " + event);

    }

    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    public void fireOfErrorEvent(){
        log.info("About to fire of event");

        MyErrorEvent errorEvent = new MyErrorEvent("Some strange error", new RuntimeException("Nasty bug"));

        this.event.select(ConfigTypeAnnotation.getConfigTypeAnnotation(MyErrorEvent.class)).fire(errorEvent);

        log.info("Fired event: " + event);

    }

    @PostConstruct
    public void init(){

    }

}

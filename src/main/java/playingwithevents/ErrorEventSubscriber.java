package playingwithevents;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import playingwithevents.events.*;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class ErrorEventSubscriber {

    @Inject
    @Category("ErrorSubscriber")
    Logger log;


    public void listenToErrors(@Observes @InBackground @EventType(MyErrorEvent.class) BaseEvent event){

      log.info("Error Event was received! : " + event);

    }
}

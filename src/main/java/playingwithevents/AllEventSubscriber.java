package playingwithevents;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import playingwithevents.events.*;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class AllEventSubscriber {

    @Inject
    @Category("AllEventSubscriber")
    Logger log;

    public void listenToSuccess(@Observes @InBackground @EventType(MySuccessfulEvent.class) BaseEvent event){

      log.info("Succesful Event was received! : " + event);

    }

    
        public void listenToErrors(@Observes @InBackground @EventType(MyErrorEvent.class) BaseEvent event){

      log.info("Error Event was received! : " + event);

    }
}

package playingwithevents.jmsgateway;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import playingwithevents.events.BaseEvent;
import playingwithevents.events.ConfigTypeAnnotation;
import playingwithevents.events.InBackground;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

@MessageDriven(name = "MDBExample", activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/BackgroundEventQueue")
})
public class BackgroundEventDispatcher implements MessageListener {

    public BackgroundEventDispatcher() {
    }

    @Inject
    @InBackground
    Event<BaseEvent> backGroundEvent;

    @Inject
    @Category("BackGroundEventDispatcher")
    Logger log;

    public void onMessage(Message message) {

        log.info("Received JMS message");

        if (!(message instanceof ObjectMessage))
            throw new RuntimeException("Invalid message type received");

        ObjectMessage msg = (ObjectMessage) message;



        try {
            Serializable eventObject = msg.getObject();
            if (!(eventObject instanceof BaseEvent))
                throw new RuntimeException("Unknown event type received");

            fireEvent((BaseEvent)eventObject);

        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void fireEvent(BaseEvent eventObject) {
         backGroundEvent.select(ConfigTypeAnnotation.getConfigTypeAnnotation(eventObject.getClass())).fire(eventObject);

        log.info("Fired " + backGroundEvent);
    }
}

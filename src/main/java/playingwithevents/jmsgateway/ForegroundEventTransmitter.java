package playingwithevents.jmsgateway;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;
import playingwithevents.events.BaseEvent;
import playingwithevents.events.InForeground;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
public class ForegroundEventTransmitter {
    @Inject
    @Category("EventDispatcher")
    Logger logger;


    @Resource(mappedName="java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:/queue/BackgroundEventQueue", type = javax.jms.Queue.class)
    private Queue backgroundEventQueue;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    @PostConstruct
    public void init() {
        try {
            connection = connectionFactory.createConnection();
            session=connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            producer=session.createProducer(backgroundEventQueue);

        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }

    @PreDestroy
    public void destroy() {
        try {

        if(connection!=null)
            connection.close();

        if(session != null)
            session.close();
        }
        catch(Exception e) {
        }

    }


    public void event(@Observes @InForeground BaseEvent event) {
        try {
            logger.info("About to send JMS message");
            ObjectMessage msg = session.createObjectMessage();
            msg.setObject(event);
            producer.send(msg);
            session.commit();
            logger.info("Successfully sent message");
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }

    }

}


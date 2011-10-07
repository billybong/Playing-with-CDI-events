package playingwithevents.events;

import javax.enterprise.util.AnnotationLiteral;

public abstract class ConfigTypeAnnotation extends AnnotationLiteral<EventType> implements EventType{

    public static ConfigTypeAnnotation getConfigTypeAnnotation(final Class<?> type) {
        return new ConfigTypeAnnotation() {

            public Class<?> value() {
                return type;
            }
        };
    }
}

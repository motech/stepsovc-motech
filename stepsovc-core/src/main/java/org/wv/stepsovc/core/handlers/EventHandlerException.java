package org.wv.stepsovc.core.handlers;

import org.motechproject.model.MotechEvent;

public class EventHandlerException extends RuntimeException {
    public EventHandlerException(MotechEvent motechEvent, Exception exception) {
        super(motechEvent.toString(), exception);
    }
}

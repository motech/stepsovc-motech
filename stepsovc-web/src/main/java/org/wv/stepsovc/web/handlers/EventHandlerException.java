package org.wv.stepsovc.web.handlers;

import org.motechproject.scheduler.domain.MotechEvent;

public class EventHandlerException extends RuntimeException {
    public EventHandlerException(MotechEvent motechEvent, Exception exception) {
        super(motechEvent.toString(), exception);
    }
}

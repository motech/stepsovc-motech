package unit.org.wv.stepsovc.core.handlers;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.aggregator.aggregation.AggregateMotechEvent;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.sms.api.service.SmsService;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.aggregator.SMSMessage;
import org.wv.stepsovc.web.handlers.AggregateSMSEventHandler;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AggregateSMSEventHandlerTest {

    @Mock
    CMSLiteService mockCMSLiteService;
    @Mock
    SmsService mockSmsService;
    AggregateSMSEventHandler aggregateSMSEventHandler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        aggregateSMSEventHandler = new AggregateSMSEventHandler();
        ReflectionTestUtils.setField(aggregateSMSEventHandler, "smsService", mockSmsService);
        ReflectionTestUtils.setField(aggregateSMSEventHandler, "cmsLiteService", mockCMSLiteService);
    }

    @Test
    public void shouldHandleAggregatedSMSByIdentifierForSingleMessage() throws Exception {

        String phoneNumber = "9999";
        String content = "aaa (aaa) services (001,002)";
        Map<String, Object> parameters = new HashMap<String, Object>();
        String groupKey = "groupKey";

        GenericMessage<SMSMessage> genericMessage = new GenericMessage<SMSMessage>(new SMSMessage(DateTime.now(), phoneNumber, content, groupKey, "someDate"));
        List<GenericMessage<SMSMessage>> messageList = Arrays.asList(genericMessage);
        parameters.put(AggregateMotechEvent.VALUES_KEY, messageList);
        MotechEvent motechEvent = new MotechEvent("subject", parameters);
        String language = "en";
        when(mockCMSLiteService.getStringContent(language, groupKey)).thenReturn(new StringContent(language, groupKey, "Upcoming: %s will be visiting your facility on %s. Please make necessary arrangements."));
        aggregateSMSEventHandler.handleAggregatedSMSByIdentifier(motechEvent);
        verify(mockSmsService).sendSMS(phoneNumber, "Upcoming: aaa (aaa) services (001,002) will be visiting your facility on someDate. Please make necessary arrangements.");

    }

    @Test
    public void shouldNotThrowExceptionWhenThereIsNoMessageToAggregate() throws Exception {

        String phoneNumber = "9999";
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<SMSMessage> messageList = new ArrayList<SMSMessage>();
        parameters.put(AggregateMotechEvent.VALUES_KEY, messageList);
        MotechEvent motechEvent = new MotechEvent("subject", parameters);
        String language = "en";
        aggregateSMSEventHandler.handleAggregatedSMSByIdentifier(motechEvent);
        verify(mockSmsService, never()).sendSMS(phoneNumber, "Upcoming: aaa (aaa) services (001,002) will be visiting your facility on someDate. Please make necessary arrangements.");
    }

    @Test
    public void shouldHandleAggregatedSMSByIdentifierForMultipleMessages() throws Exception {

        String phoneNumber = "9999";
        String content1 = "aaa (aaa) services (001,002)";
        String content2 = "aaa (aaa) services (001,002)";
        Map<String, Object> parameters = new HashMap<String, Object>();
        String groupKey = "groupKey";

        GenericMessage<SMSMessage> genericMessage1 = new GenericMessage<SMSMessage>(new SMSMessage(DateTime.now(), phoneNumber, content1, groupKey, "someDate"));
        GenericMessage<SMSMessage> genericMessage2 = new GenericMessage<SMSMessage>(new SMSMessage(DateTime.now(), phoneNumber, content2, groupKey, "someDate"));
        List<GenericMessage<SMSMessage>> messageList = Arrays.asList(genericMessage1, genericMessage2);
        parameters.put(AggregateMotechEvent.VALUES_KEY, messageList);
        MotechEvent motechEvent = new MotechEvent("subject", parameters);
        String language = "en";
        when(mockCMSLiteService.getStringContent(language, groupKey)).thenReturn(new StringContent(language, groupKey, "Upcoming: %s will be visiting your facility on %s. Please make necessary arrangements."));
        aggregateSMSEventHandler.handleAggregatedSMSByIdentifier(motechEvent);
        verify(mockSmsService).sendSMS(phoneNumber, "Upcoming: aaa (aaa) services (001,002), aaa (aaa) services (001,002) will be visiting your facility on someDate. Please make necessary arrangements.");

    }
}

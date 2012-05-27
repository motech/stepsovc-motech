package org.wv.stepsovc.core.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.sms.api.service.SmsService;

import static org.mockito.MockitoAnnotations.initMocks;

public class AggregateSMSEventHandlerTest {

    @Mock
    CMSLiteService mockCMSLiteService;

    @Mock
    SmsService mockSmsService;



    @Before
    public void setUp() throws Exception {
        initMocks(this);

    }

    @Test
    public void shouldHandleAggregatedSMSByIdentifier() throws Exception {

    }
}

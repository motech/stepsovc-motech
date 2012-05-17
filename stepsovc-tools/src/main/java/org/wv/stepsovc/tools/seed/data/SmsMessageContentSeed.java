package org.wv.stepsovc.tools.seed.data;

import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.tools.seed.Seed;

import java.util.Locale;
import java.util.Properties;

@Component
public class SmsMessageContentSeed extends Seed {

    @Autowired
    private CMSLiteService cmsLiteService;
    @Autowired
    @Qualifier(value = "textMessage")
    private Properties textMessages;

    @Override
    protected void preLoad() {
        super.preLoad();
    }

    @Override
    protected void load() {
        try {
            for (String property : textMessages.stringPropertyNames()) {
                cmsLiteService.addContent(new StringContent(Locale.ENGLISH.getLanguage(), property, textMessages.getProperty(property)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

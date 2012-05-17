package org.wv.stepsovc.tools.dmis;

import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DMISDataProcessorTest {

    @Test
    public void shouldEncryptData() throws IllegalBlockSizeException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
        DMISDataProcessor dmisDataProcessor = new DMISDataProcessor();
        assertThat(dmisDataProcessor.encrypt("Alice"), is("YqP/JPi4teO9VCf0O9m+iQ=="));
    }

    @Test
    public void shouldDecryptData() throws Exception {
        DMISDataProcessor dmisDataProcessor = new DMISDataProcessor();
        assertThat(dmisDataProcessor.decrypt("YqP/JPi4teO9VCf0O9m+iQ=="), is("Alice"));
    }

}

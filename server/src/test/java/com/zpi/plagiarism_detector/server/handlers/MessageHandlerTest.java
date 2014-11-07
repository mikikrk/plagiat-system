package com.zpi.plagiarism_detector.server.handlers;

import com.zpi.plagiarism_detector.commons.protocol.Message;
import com.zpi.plagiarism_detector.commons.protocol.ProtocolCode;
import com.zpi.plagiarism_detector.commons.utils.TestUtils;
import com.zpi.plagiarism_detector.server.factories.handlers.AbstractMessageHandlerFactory;
import com.zpi.plagiarism_detector.server.factories.handlers.MessageHandlerFactory;
import junit.framework.Assert;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;

@PrepareForTest(ObjectInputStream.class)
public class MessageHandlerTest {
    private MessageHandler messageHandler;
    private AbstractMessageHandlerFactory messageHandlerFactory = new MessageHandlerFactory();
    private ObjectInputStream in;
    private Message message;

    @ObjectFactory
    public IObjectFactory setObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    public final void init() throws IOException, ClassNotFoundException {
        in = PowerMockito.mock(ObjectInputStream.class);
        messageHandler = messageHandlerFactory.createForStreams(null, in);
    }

    @Test(enabled = false) /* Problem z niedziałającym mock'iem */
    public final void tryReadMessageSuccessTest() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        message = new Message(ProtocolCode.TEST, null);
        PowerMockito.doReturn(message).when(in).readObject();

        // when
        boolean result = messageHandler.tryReadMessage();
//        boolean result = (boolean)TestUtils.callDeclaredMethod(messageHandler, "tryReadMessage");

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void tryReadMessageIOExceptionTest() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // given
        PowerMockito.doThrow(new IOException()).when(in).readObject();

        // when
        boolean result = (boolean)TestUtils.callDeclaredMethod(messageHandler, "tryReadMessage");

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void tryReadMessageClassNotFoundTest() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // given
        PowerMockito.doThrow(new ClassNotFoundException()).when(in).readObject();

        // when
        boolean result = (boolean)TestUtils.callDeclaredMethod(messageHandler, "tryReadMessage");

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void dispatchMessageSuccessTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // given
        message = new Message(ProtocolCode.TEST, "TEST");

        // when
        TestUtils.callDeclaredMethod(messageHandler, "dispatchMessage", message);

        // then
        Assert.assertTrue(true);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void dispatchMessageNullMessageTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // given
        message = null;

        // when
        TestUtils.callDeclaredMethod(messageHandler, "dispatchMessage", message);
    }

}

package com.zpi.plagiarism_detector.server.factories.handlers;

import com.zpi.plagiarism_detector.server.core.Server;
import com.zpi.plagiarism_detector.server.handlers.MessageHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class MessageHandlerFactoryTest {
    private MessageHandlerFactory messageHandlerFactory;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    @BeforeTest
    public void init() {
        messageHandlerFactory = new MessageHandlerFactory();
    }

    @BeforeMethod
    public void prepareMocks() {
        socket = mock(Socket.class);
        out = mock(ObjectOutputStream.class);
        in = mock(ObjectInputStream.class);
    }


    @Test
    public void createForSocketWithCorrectParamsTest() throws IOException {
        // given
        doReturn(out).when(socket).getOutputStream();
        doReturn(in).when(socket).getInputStream();

        // when
        MessageHandler messageHandler = messageHandlerFactory.createForSocket(socket);

        // then
        Assert.assertNotNull(messageHandler);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createForSocketWithNullSocketTest() throws IOException {
        // given
        socket = null;

        // when
        MessageHandler messageHandler = messageHandlerFactory.createForSocket(socket);
    }

    @Test(expectedExceptions = IOException.class)
    public void createForSocketWithIOException() throws IOException {
        // given
        doThrow(new IOException()).when(socket).getOutputStream();
        doThrow(new IOException()).when(socket).getInputStream();

        // when
        MessageHandler messageHandler = messageHandlerFactory.createForSocket(socket);
    }

    @Test
         public void createForStreamsWithNullParams() throws IOException {
        // given
        in = null;
        out = null;

        // when
        MessageHandler messageHandler = messageHandlerFactory.createForStreams(out, in);

        // then
        Assert.assertNotNull(messageHandler);
    }

    @Test
    public void createForStreamsWithNotNullParams() throws IOException {
        // when
        MessageHandler messageHandler = messageHandlerFactory.createForStreams(out, in);

        // then
        Assert.assertNotNull(messageHandler);
    }
}

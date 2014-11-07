package com.zpi.plagiarism_detector.server.handlers;

import com.zpi.plagiarism_detector.commons.utils.TestUtils;
import com.zpi.plagiarism_detector.server.factories.handlers.AbstractMessageHandlerFactory;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ConnectionHandlerTest {
    private ConnectionHandler connectionHandler;
    private Socket clientSocket;
    private AbstractMessageHandlerFactory messageHandlerFactory;
    private MessageHandler messageHandler;

    @BeforeMethod
    public void createMocks() {
        clientSocket = Mockito.mock(Socket.class);
        messageHandlerFactory = Mockito.mock(AbstractMessageHandlerFactory.class);
        messageHandler = Mockito.mock(MessageHandler.class);
        connectionHandler = new ConnectionHandler(clientSocket, messageHandlerFactory);
    }

    @Test
    public void runSuccessTest() throws InterruptedException, IOException, ClassNotFoundException {
        // given
        doReturn(messageHandler).when(messageHandlerFactory).createForSocket(any(Socket.class));

        // when
        Thread thread = new Thread(connectionHandler);
        thread.start();

        thread.join();

        // then
        verify(messageHandler).freeResources();
        verify(clientSocket).close();
    }

    @Test
    public void runIOExceptionTest() throws IOException, InterruptedException {
        // given
        doThrow(new IOException()).when(messageHandlerFactory).createForSocket(any(Socket.class));

        // when
        Thread thread = new Thread(connectionHandler);
        thread.start();
        thread.join();

        // then
        verify(messageHandler, never()).freeResources();
        verify(clientSocket).close();
    }

    @Test
    public void runClassNotFoundExceptionTest() throws IOException, InterruptedException {
        // given
        doReturn(messageHandler).when(messageHandlerFactory).createForSocket(any(Socket.class));

        // when
        Thread thread = new Thread(connectionHandler);
        thread.start();
        thread.join();

        // then
        verify(messageHandler).freeResources();
        verify(clientSocket).close();
    }

    @Test
    public void freeHandlerResourcesNullMessageHandlerTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        doReturn(null).when(messageHandlerFactory).createForSocket(any(Socket.class));

        // when
        Method method = connectionHandler.getClass().getDeclaredMethod("freeHandlerResources");
        method.setAccessible(true);
        method.invoke(connectionHandler);

        // then
        verify(clientSocket).close();
    }

    @Test
    public void freeHandlerResourcesClientSocketIOExceptionTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InterruptedException, NoSuchFieldException {
        // given
        Class<? extends ConnectionHandler> chClass = connectionHandler.getClass();
        Field messageHandlerField = chClass.getDeclaredField("messageHandler");
        messageHandlerField.setAccessible(true);
        messageHandlerField.set(connectionHandler, messageHandler);

        // when
        TestUtils.callDeclaredMethod(connectionHandler, "freeHandlerResources");

        // then
        verify(clientSocket).close();
        verify(messageHandler).freeResources();
    }
}
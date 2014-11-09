package com.zpi.plagiarism_detector.client.core;

import com.zpi.plagiarism_detector.client.io.ClientReader;
import com.zpi.plagiarism_detector.client.io.ClientWriter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Observer;

import static org.mockito.Mockito.*;

public class ClientTest {
    private Client client;
    private Socket socket;
    private ClientReader reader;
    private ClientWriter writer;

    @BeforeMethod
    public void init() {
        socket = mock(Socket.class);
        reader = mock(ClientReader.class);
        writer = mock(ClientWriter.class);
        client = new Client(socket, reader, writer);
    }

    @Test(expectedExceptions = InterruptedException.class)
    public void closeConnectionInterruptedExceptionTest() throws InterruptedException, IOException {
        // given
        doThrow(new InterruptedException()).when(writer).joinWriter();

        // when
        client.closeConnection();
    }

    @Test
    public void update_UpdateRegisteredObserverAndChangeStatusToUnchangedTest() {
        // given
        java.util.Observable observable = mock(java.util.Observable.class);
        Observer observer = mock(Observer.class);
        Object arg = new Object();
        client.addObserver(observer);

        // when
        client.update(observable, arg);

        // then
        verify(observer, times(1)).update(client, arg);
    }
}

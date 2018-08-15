package com.gmail.vip.alexd.rpc.e2e.test;

import com.gmail.vip.alexd.rpc.client.Client;
import com.gmail.vip.alexd.rpc.protocol.Response;
import com.gmail.vip.alexd.rpc.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RemoteCallTest {
    private static final String MAIL = "vip.alexd@gmail.com";
    private static final String GET_MAIL_METHOD = "getMail";
    private static final String GET_HOSTNAME_METHOD = "getHostName";
    private static final String SERVICE_NAME = "Service";
    private static final String WRONG_SERVICE_NAME = SERVICE_NAME.concat("Wrong");
    private static final String WRONG_METHOD_NAME = GET_MAIL_METHOD.concat("Wrong");
    private static final String SERVICE_NOT_FOUND = "Service not found";
    private static final String METHOD_NOT_FOUND = "Method not found";

    private Server server;
    private Client client;

    @Before
    public void setUp() throws Exception {
        server = new Server();
        server.run();
        client = new Client();
        client.run();
    }

    @Test
    public void shouldReturnMailWhenInvokeGetMailMethod() {
        assertEquals(MAIL,
                ((Response) client.remoteCall(1111, SERVICE_NAME,GET_MAIL_METHOD, new Object[]{}))
                        .getAnswer());
    }

    @Test
    public void shouldReturnHostNameWhenInvokeHostNameMethod() throws UnknownHostException {
        assertEquals(InetAddress.getLocalHost().getHostName(),
                ((Response) client.remoteCall(1111, SERVICE_NAME,GET_HOSTNAME_METHOD, new Object[]{}))
                        .getAnswer());
    }

    @Test
    public void shouldReturnServiceNotFoundWhenInvokeWithWrongService() {
        assertEquals(SERVICE_NOT_FOUND,
                ((Response) client.remoteCall(1111, WRONG_SERVICE_NAME,GET_HOSTNAME_METHOD, new Object[]{}))
                        .getAnswer());
    }

    @Test
    public void shouldReturnMethodNotFoundWhenInvokeWithWrongMethod() {
        assertEquals(METHOD_NOT_FOUND,
                ((Response) client.remoteCall(1111, SERVICE_NAME,WRONG_METHOD_NAME, new Object[]{}))
                        .getAnswer());
    }

    @After
    public void tearDown() throws IOException, InterruptedException {
        client.disconnect();
        server.disconnect();
    }
}

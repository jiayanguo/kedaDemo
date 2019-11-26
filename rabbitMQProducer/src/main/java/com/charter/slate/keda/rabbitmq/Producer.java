package com.charter.slate.keda.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * Created by jguo on 11/26/19.
 */
public class Producer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        String url = System.getenv("rabbitMQ");
        int count  = Integer.valueOf(System.getenv("repeat"));
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(url);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            int i = 0;
            while (i < count) {
                String message = "Hello World!" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
                i++;
            }
        }
    }
}

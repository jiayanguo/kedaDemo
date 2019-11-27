package com.charter.slate.keda.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.util.Random;

/**
 * Created by jguo on 11/26/19.
 */
public class Receiver {

    private final static String QUEUE_NAME = "hello";
    private static int time = 0;

    public static void main(String[] argv) throws Exception {
        String url = System.getenv("rabbitMQ");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(url);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        GetResponse response = channel.basicGet(QUEUE_NAME, true);
        System.out.println(response.getBody());

        Random random = new Random();
        int time = random.nextInt(10);
        Thread.sleep(time * 1000);
    }
}

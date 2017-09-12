/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tolet.messageserver;

import static com.tolet.messageserver.LogMessageProducer.topicName;
import static com.tolet.messageserver.LogMessageProducer.clientId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
 
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Ataur Rahman
 */
public class LogMessageConsumer extends Thread {
    
    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:2020");
            connectionFactory.setTrustAllPackages(true);
            connectionFactory.setDispatchAsync(true);
            
            Connection connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            try {
                
                TopicSubscriber consumer = session.createDurableSubscriber(topic,"Test_Durable_Subscriber");
                connection.start();
                
                while(true){
                    System.out.println("Waiting for message ==== ");
                    MapMessage msg = (MapMessage) consumer.receive();
                    System.out.println(msg);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LogMessageConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("$$$$$$$$$$ id = "+msg.getString("id"));
                    System.out.println("$$$$$$$$$$ message = "+msg.getString("message"));
                }
                //session.close();
                
            } catch (JMSException ex) {
                Logger.getLogger(LogMessageConsumer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(LogMessageConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws JMSException {
        LogMessageConsumer l = new LogMessageConsumer();
        l.start();
    }

}

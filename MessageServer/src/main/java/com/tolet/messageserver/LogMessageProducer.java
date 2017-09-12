/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tolet.messageserver;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;

import javax.jms.Session;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

/**
 *
 * @author Ataur Rahman
 */
public class LogMessageProducer  {
    public static String topicName = "topic";
    public static String clientId = "clientId";
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:2020");
        connectionFactory.setTrustAllPackages(true);
        Destination destination = new ActiveMQQueue("someQueue");
        Connection connection = connectionFactory.createConnection();
        //connection.setClientID(clientId);
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        Destination d = session.createTopic(topicName);
        try {
            String payload = "this is sent text..............................";
//            LogMessage m = new LogMessage();
//            m.setId(1);
//            m.setMessage(payload);
//            ObjectMessage msg = session.createObjectMessage();
//            msg.setObject(m);
            Topic topic = session.createTopic(topicName);
            
            MapMessage m = session.createMapMessage();
            m.setString("id", "5");
            m.setString("message", payload);
            
            MessageProducer producer = session.createProducer(d);
            producer.send(m);
            session.close();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
 
    }
    
    
}

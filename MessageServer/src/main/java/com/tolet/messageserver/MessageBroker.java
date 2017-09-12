/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tolet.messageserver;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

/**
 *
 * @author Ataur Rahman
 */
public class MessageBroker {
    public static void main(String[] args) throws URISyntaxException, Exception {
        BrokerService broker = BrokerFactory.createBroker(new URI(
                "broker:(tcp://localhost:2020)"));
        broker.start();
    }
}

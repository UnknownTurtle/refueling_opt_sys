package ru.spbu.mas;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessingRequests extends TickerBehaviour {
    private final Bunker agent;
    private final double a = 0.1;
    private int currentStep;
    private final int MAX_STEPS = 100;
    private double value = 0;
    private boolean firstDelivery = true;
    private ACLMessage mes = new ACLMessage(ACLMessage.INFORM);
    private ACLMessage query = new ACLMessage(ACLMessage.REQUEST);

    ProcessingRequests(Bunker agent, long period) {
        super(agent, period);
        this.setFixedPeriod(true);
        this.agent = agent;

        for (AID n: agent.LinkedAgents()) {
            mes.addReceiver(n);
            query.addReceiver(n);
        }
    }
    @Override
    protected void onTick() {
        ACLMessage msg = agent.receive();
        //System.out.println(msg);
        mes.setContent("Search bunker");
        agent.send(mes);


        if (msg==null && firstDelivery) {
            mes.setContent("hi from tick bunker");
            agent.send(mes);
            firstDelivery = false;
        }
        if (msg!=null){

            System.out.println("Я бункер. Дай мне!!! "+msg.getContent());
        }

        if (currentStep < MAX_STEPS) {
            this.currentStep++;
        }
        else {
            this.stop();
        }
    }

    }

package ru.spbu.mas;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class FindAverage extends TickerBehaviour {
    private final DefaultAgent agent;
    private int c = 0;
    private int currentStep;
    private final int MAX_STEPS = 100;
    private double value = 0;
    private boolean firstDelivery = true;
    private ACLMessage mes = new ACLMessage(ACLMessage.INFORM);
    private ACLMessage query = new ACLMessage(ACLMessage.REQUEST);
    private ACLMessage yes = new ACLMessage(ACLMessage.AGREE);
    private ArrayList<String> agentsList = new ArrayList<>();

    FindAverage(DefaultAgent agent, long period) {
        super(agent, period);
        this.setFixedPeriod(true);
        this.agent = agent;

        for (AID n: agent.LinkedAgents()) {
            mes.addReceiver(n);
            query.addReceiver(n);
        }
        this.agentsList.add("19");
        this.agentsList.add("20");
        this.agentsList.add("21");
        this.agentsList.add("22");
        this.agentsList.add("23");
        this.agentsList.add("24");
    }

    @Override
    protected void onTick() {
        ACLMessage msg = agent.receive();

        if(msg!=null&agent.LinkedAgents().size()>2&c<4)
        {
            mes.setContent(msg.getContent());
            System.out.println("Я получил письмо (агент "+agent.getLocalName()+" )"+msg.getContent());
            agent.send(mes);
            c++;
        }
        else if (msg!=null&agentsList.contains(agent.getLocalName())){
            System.out.println("OH YEAH I TAKE IT");


        }
        else if (msg!=null&agent.LinkedAgents().size()<3){
            query.setContent(msg.getContent());
            System.out.println("Я получил письмо (агент "+agent.getLocalName()+" ) "+msg.getContent());
            agent.send(query);
        }

        /*else if(msg!=null&Integer.getInteger(agent.getLocalName())>18){
            System.out.println("Я бункер. Дай мне!!! "+msg.getContent());

        }*/

        if (msg==null) {
            //mes.setContent("Было получено пустое сообщение");
            //agent.send(mes);
            firstDelivery = false;
        }

        if (currentStep < MAX_STEPS) {
            this.currentStep++;
        }
        else {

            this.stop();
        }
    }

}
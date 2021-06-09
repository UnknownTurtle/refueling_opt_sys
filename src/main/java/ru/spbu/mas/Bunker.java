package ru.spbu.mas;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Bunker extends Agent{
    private String type;
    private static final double maxSize = 30000.0;
    public double realSize = 0.0;

    public ArrayList<Request> mySchedule = new ArrayList<>();

    public static void setRequest()
    {

    }



    private ArrayList<AID> linkedAgents = new ArrayList<AID>();
    private double number;
    public  ArrayList<AID>  LinkedAgents() {
        return linkedAgents;
    }

    private ACLMessage mess = new ACLMessage(ACLMessage.INFORM);

    @Override
    protected void setup() {
        Object[] args = getArguments();

        if (args != null && args.length > 0) {
            String[] neighbors = args[0].toString().split(", ");
            for (String neighbor : neighbors) {
                AID uid = new AID(neighbor, AID.ISLOCALNAME);
                linkedAgents.add(uid);
                mess.addReceiver(uid);
            }
            //send(mess);
            int ID = Integer.parseInt(getAID().getLocalName());
            //System.out.println("I`m a bunker #" + ID);
            addBehaviour(new ProcessingRequests(this, TimeUnit.SECONDS.toMillis(1)));
        }
    }
}


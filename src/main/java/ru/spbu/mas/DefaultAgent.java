package ru.spbu.mas;

import jade.core.AID;
import java.util.ArrayList;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.concurrent.TimeUnit;
public class DefaultAgent extends Agent {
    private ArrayList<AID> linkedAgents = new ArrayList<AID>();
    private double number;
    public  ArrayList<AID>  LinkedAgents() {
        return linkedAgents;
    }
    public double GetValue() { return number; }
    public void SetValue(double val)
    {
        this.number = val;
    }
   // public void SetValue(String number) {this.number = number;}
    private ACLMessage mess = new ACLMessage(ACLMessage.INFORM);

    @Override
    protected void setup() {
        Object[] args = getArguments();
        //number = String.valueOf(Math.random()*(99-1)+1);
        number = (int)(Math.random()*(99-1)+1);
        mess.setContent(Integer.toString(0));
        if (args != null && args.length > 0) {
            String[] neighbors = args[0].toString().split(", ");
            for (String neighbor : neighbors) {
                AID uid = new AID(neighbor, AID.ISLOCALNAME);
                linkedAgents.add(uid);
                mess.addReceiver(uid);
            }
            //send(mess);
            int ID = Integer.parseInt(getAID().getLocalName());
            System.out.println("Agent #" + ID + " my number is " + number);
            addBehaviour(new FindAverage(this,TimeUnit.SECONDS.toMillis(1)));
        }
    }
}
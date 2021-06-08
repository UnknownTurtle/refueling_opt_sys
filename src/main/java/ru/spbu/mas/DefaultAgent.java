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
                if (getAID().getLocalName()=="1") {
                    while(mess.getAllReceiver().hasNext()) {
                        System.out.println(mess.getAllReceiver().next());
                    }
                }

            }

            //send(mess);
            int ID = Integer.parseInt(getAID().getLocalName());
            if (ID == 5) {
                System.out.println("BEFORE DELETED I`m default Agent #" + ID + " " + mess);
                System.out.println(linkedAgents);
            }

            AID uid1 = new AID(String.valueOf(19), AID.ISLOCALNAME);
            mess.removeReceiver(uid1);
            linkedAgents.remove(uid1);
            if (ID == 5) {
                System.out.println("AFTER DELETED I`m default Agent #" + ID + " " + mess);
                System.out.println(linkedAgents);
            }
            AID uid = new AID(String.valueOf(ID), AID.ISLOCALNAME);

          //  uid.removeResolvers(uid1);
            //System.out.println("I`m default Agent #" + ID + " my number is " + number);
            /*while(mess.get().hasNext()){

                System.out.println(mess.getAllIntendedReceiver().next());
            }*/
           // System.out.println("STOP____________");
            //addBehaviour(new FindAverage(this,TimeUnit.SECONDS.toMillis(1)));
        }
    }
}
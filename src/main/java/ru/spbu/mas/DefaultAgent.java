package ru.spbu.mas;

import jade.core.AID;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.Date;
import java.util.concurrent.TimeUnit;
public class DefaultAgent extends Agent {
    private ArrayList<AID> linkedAgents = new ArrayList<AID>();
    private double number;
    public String type = "basic";
    private boolean setup = true;
    private ArrayList<Request> requestList = new ArrayList<>();

    public  ArrayList<AID>  LinkedAgents() {
        return linkedAgents;
    }

    private ACLMessage mess = new ACLMessage(ACLMessage.INFORM);

    private ArrayList<String> getRequest(){
        String name = "src/main/java/ru/spbu/mas/requests"+getLocalName()+".txt";
        ArrayList<String> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(name))){
            String line;
            String lineRequest=null;
            while ((line=reader.readLine())!=null){
                lineRequest = line;
                list.add(line);
            }

            String arr[] = list.toArray(new String[0]);
            for(String str: arr){
                String temp[];
                temp = str.split(";");
                Request request = new Request(temp[0],Integer.parseInt(temp[1]),
                        Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
                requestList.add(request);

            }

        }catch (IOException e){
            list=null;
            e.printStackTrace();
        }
        return list;
    }
    @Override
    protected void setup() {
        //requestList.add()
        Object[] args = getArguments();

          if (args != null && args.length > 0) {
            String[] neighbors = args[0].toString().split(", ");
            for (String neighbor : neighbors) {
                AID uid = new AID(neighbor, AID.ISLOCALNAME);
                linkedAgents.add(uid);
                mess.addReceiver(uid);
                //send(mess);
            }

            ArrayList<String> newList = new ArrayList<>();
            newList = getRequest();
            if (newList!=null) {
                String arr[] = newList.toArray(new String[0]);
                for (String str : arr) {
                    mess.setContent(str);
                    //System.out.println("Я отправляю сообщение: "+mess + " для ");
                    send(mess);
                }
            }
            //send(mess);
           /* int ID = Integer.parseInt(getAID().getLocalName());
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
            AID uid = new AID(String.valueOf(ID), AID.ISLOCALNAME);*/

          //  uid.removeResolvers(uid1);
            //System.out.println("I`m default Agent #" + ID + " my number is " + number);
            /*while(mess.get().hasNext()){

                System.out.println(mess.getAllIntendedReceiver().next());
            }*/
           // System.out.println("STOP____________");
            addBehaviour(new FindAverage(this,TimeUnit.SECONDS.toMillis(1)));
        }
    }
}
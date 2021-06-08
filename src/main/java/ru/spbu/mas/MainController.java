package ru.spbu.mas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

class MainController {
    private static final int numberOfAgents = 24;
    void initRequests(){
        String name = "src/main/java/ru/spbu/mas/requests.txt";
        ArrayList<String> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(name))){
            String line;
            while ((line=reader.readLine())!=null){
                list.add(line);
            }
            String arr[] = list.toArray(new String[0]);
            for(String str: arr){
                String temp[];
                temp = str.split(";");
                Request request = new Request(temp[0],Integer.parseInt(temp[1]),
                        Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    void initAgents() {
        // Retrieve the singleton instance of the JADE Runtime
        HashMap<Integer, String> neighbors = new HashMap<Integer, String>();
        neighbors.put(1,"2, 3, 4, 5, 6, 7, 13");
        neighbors.put(2, "1");
        neighbors.put(3, "1");
        neighbors.put(4, "1");
        neighbors.put(5, "1, 19");
        neighbors.put(6, "1, 20");
        neighbors.put(7, "8, 9, 10, 11, 12, 1, 13");
        neighbors.put(8, "7");
        neighbors.put(9, "7, 21");
        neighbors.put(10, "7");
        neighbors.put(11, "7, 24");
        neighbors.put(12, "7");
        neighbors.put(13, "14, 15, 16, 17, 18, 7, 1");
        neighbors.put(14, "13");
        neighbors.put(15, "13, 22");
        neighbors.put(16, "13");
        neighbors.put(17, "13");
        neighbors.put(18, "13, 23");
        neighbors.put(19, "5"); //bunker 1
        neighbors.put(20, "6"); //bunker 2
        neighbors.put(21, "9"); //bunker 3
        neighbors.put(22, "15"); //bunker 4
        neighbors.put(23, "18"); //bunker 5
        neighbors.put(24, "11"); //bunker 6

        Runtime rt = Runtime.instance();
        //Create a container to host the Default Agent
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "10098");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createMainContainer(p);
        int k = 1;
        try {
            for(int i = 1; i <= MainController.numberOfAgents-2; i++) {
                k++;
                AgentController agent = cc.createNewAgent(Integer.toString(i),"ru.spbu.mas.DefaultAgent", new
                        Object[]{neighbors.get(i)});
                agent.start();
            }
            for(int i = k; i <= MainController.numberOfAgents; i++){
                AgentController agent = cc.createNewAgent(Integer.toString(i),"ru.spbu.mas.Bunker", new
                        Object[]{neighbors.get(i)});
                agent.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
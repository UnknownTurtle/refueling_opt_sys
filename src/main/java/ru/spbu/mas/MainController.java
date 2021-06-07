package ru.spbu.mas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

class MainController {
    private static final int numberOfAgents = 15;
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
                Request request = new Request(temp[0],Integer.parseInt(temp[1]),Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));

                //Request request1 = new Request("2000-01-01 10:00", 5,5,1.00);
            }

            //System.out.println(list);

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    void initAgents() {
// Retrieve the singleton instance of the JADE Runtime
        HashMap<Integer, String> neighbors = new HashMap<Integer, String>();
        neighbors.put(1,"4, 2, 15, 13");
        neighbors.put(2, "1, 3, 8, 11");
        neighbors.put(3, "2, 4, 9, 12");
        neighbors.put(4, "1, 3, 5, 7");
        neighbors.put(5, "4, 6, 11, 14");
        neighbors.put(6, "5, 7, 12, 15");
        neighbors.put(7, "4, 6, 8, 10");
        neighbors.put(8, "2, 7, 9, 14");
        neighbors.put(9, "3, 8, 10, 15");
        neighbors.put(10, "7, 9, 11, 13");
        neighbors.put(11, "2, 5, 10, 12");
        neighbors.put(12, "3, 6, 11, 13");
        neighbors.put(13, "1, 10, 12, 14");
        neighbors.put(14, "5, 8, 13, 15");
        neighbors.put(15, "1, 6, 9, 14");

        Runtime rt = Runtime.instance();
//Create a container to host the Default Agent
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "10098");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createMainContainer(p);

        try {
            for(int i=1; i <= MainController.numberOfAgents; i++) {
                AgentController agent = cc.createNewAgent(Integer.toString(i),"ru.spbu.mas.DefaultAgent", new
                        Object[]{neighbors.get(i)});
                agent.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //cc.createNewAgent(Integer.toString(i), "ru.spbu.mas.DefaultAgent", new
          //      Object[]{neighbors.get(i)});
    }
}
package ru.spbu.mas;

import java.util.HashMap;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

class MainController {
    private static final int numberOfAgents = 15;

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
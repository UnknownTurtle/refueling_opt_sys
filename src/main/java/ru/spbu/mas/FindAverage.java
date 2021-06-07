package ru.spbu.mas;

import com.sun.prism.null3d.NULL3DPipeline;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class FindAverage extends TickerBehaviour {
    private final DefaultAgent agent;
    private final double a = 0.1;
    private int currentStep;
    private final int MAX_STEPS = 100;
    private double value = 0;
    private boolean firstDelivery = true;
    private ACLMessage mes = new ACLMessage(ACLMessage.INFORM);

    FindAverage(DefaultAgent agent, long period) {
        super(agent, period);
        this.setFixedPeriod(true);
        this.agent = agent;

        for (AID n: agent.LinkedAgents()) {
            mes.addReceiver(n);
        }
    }
    @Override
    protected void onTick() {
        ACLMessage msg = agent.receive();
        if (msg==null && firstDelivery) {
            value = agent.GetValue() + (float) (Math.random() * 2 - 1); // добавление искажения [-1.0;1.0]
            System.out.println("Шаг: "+currentStep+". Отправлено число " + value + " от агента " + agent.getLocalName());
            mes.setContent(String.valueOf(value));
            agent.send(mes);
            firstDelivery = false;
        }
        if (msg != null) {
            value = MakeMean(agent.GetValue(), Double.valueOf(msg.getContent()));
            agent.SetValue(value);
            if (Math.random() > 0.2) {
                mes.setContent(String.valueOf((agent.GetValue()) + (float) (Math.random() * 2 - 1)));
                System.out.println("Шаг: " + currentStep + ". Отправлено число " + (agent.GetValue() * (float) (Math.random() * 2 - 1)) + " от агента " + agent.getLocalName());
                agent.send(mes);
            } else {
                System.out.println("Шаг: " + currentStep + ". Связь с агентом #" + agent.getLocalName() + " потеряна");
            }
        }
        if (currentStep < MAX_STEPS) {
            this.currentStep++;
        }
        else {
            System.out.println("Среднее арифметическое агента #" + agent.getLocalName() + " = " + agent.GetValue());
            this.stop();
        }
    }
    private double MakeMean(double my, double notmy) {
        return (my + a * (notmy - my));
    }
}
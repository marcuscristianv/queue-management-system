import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Simulation implements Runnable {

    private View view;
    private boolean sleep = true;
    private Integer time;

    private Integer noOfClients;
    private Integer noOfQueues;

    private Integer simulationTime;

    private Integer minArrivalTime;
    private Integer maxArrivalTime;

    private Integer minServiceTime;
    private Integer maxServiceTime;

    private Integer peakTime;
    private Integer maxClients;

    private Double averageWaitingTime = 0.0;
    private Double averageServiceTime = 0.0;

    private Scheduler scheduler;
    private List<Client> clientQueue;

    public Simulation() throws InterruptedException {
        view = new View();
        view.addStartListener(new StartListener());

        while(sleep) {
            Thread.sleep(1000);
        }

        clientQueue = Collections.synchronizedList(new ArrayList<>());
        generateRandomClients(clientQueue, noOfClients, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime);
        Collections.sort(clientQueue);
        scheduler = new Scheduler(noOfQueues, noOfClients);
    }

    public static void generateRandomClients(List<Client> clientQueue, Integer noOfClients, Integer minArrivalTime,
                                             Integer maxArrivalTime, Integer minServiceTime, Integer maxServiceTime) {
        int arrivalTime;
        int serviceTime;
        for(int i = 0; i < noOfClients; i++) {
            arrivalTime = (int)(Math.random() * (maxArrivalTime - minArrivalTime) + minArrivalTime);
            serviceTime = (int)(Math.random() * (maxServiceTime - minServiceTime) + minServiceTime);

            Client newClient = new Client(i, arrivalTime, serviceTime);
            clientQueue.add(newClient);
        }
    }

    public void fetchInput() {
        noOfClients = Integer.parseInt(view.getNoOfClientsText());
        noOfQueues = Integer.parseInt(view.getNoOfQueuesText());
        simulationTime = Integer.parseInt(view.getSimulationTimeText()) + 1;
        minArrivalTime = Integer.parseInt(view.getMinArrivalTimeText());
        maxArrivalTime = Integer.parseInt(view.getMaxArrivalTimeText());
        minServiceTime = Integer.parseInt(view.getMinServiceTimeText());
        maxServiceTime = Integer.parseInt(view.getMaxServiceTimeText());
        peakTime = maxClients = 0;
    }

    class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(view.getNoOfClientsText().isEmpty() || view.getNoOfQueuesText().isEmpty() || view.getSimulationTimeText().isEmpty() ||
                view.getMinArrivalTimeText().isEmpty() || view.getMaxArrivalTimeText().isEmpty() || view.getMinServiceTimeText().isEmpty()
                    || view.getMaxServiceTimeText().isEmpty()) {
                view.showError("Incorrect values!");
            } else {
                sleep = false;
                fetchInput();
            }
        }
    }

    public void setPeakTime() {
        int clientTotal = 0;
        for(Queue q: scheduler.getQueueList()) {
            clientTotal += q.getClientQueue().size();
        }

        if(clientTotal == 0 && clientQueue.size() == 0) {
            sleep = true;
        }

        if(clientTotal >= maxClients) {
            peakTime = time;
            maxClients = clientTotal;
        }

        time++;
    }

    public void setAverageTime() {
        double waitingTimeTotal = 0;
        double serviceTimeTotal = 0;

        for(Queue q: scheduler.getQueueList()) {
            waitingTimeTotal += q.getTotalWaitingTime();
            serviceTimeTotal += q.getTotalServiceTime();
        }

        averageWaitingTime = (averageWaitingTime + waitingTimeTotal / (double)noOfClients) / 2.0;
        averageWaitingTime = Math.round(averageWaitingTime * 100.0) / 100.0;

        averageServiceTime = (averageServiceTime + serviceTimeTotal / (double)noOfClients) / 2.0;
        averageServiceTime = Math.round(averageServiceTime * 100.0) / 100.0;
    }

    @Override
    public void run() {
        time = 0;
        FileWriter file = null;

        try {
            file = new FileWriter("eventLog.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(time < simulationTime && !sleep) {
            Iterator<Client> iterator = clientQueue.iterator();
            while(iterator.hasNext()) {
                Client c = iterator.next();
                if(c.getArrivalTime() == time) {
                    try {
                        scheduler.dispatchClient(c);
                        iterator.remove();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            String output = "Time: " + time + "\nWaiting clients: ";
            for(Client c: clientQueue) {
                output += c.toString() + "; ";
            }
            output += "\n";

            for(Queue q: scheduler.getQueueList()) {
                output += q.toString();
                output += "\n";
            }
            output += "\n";
            view.setEventLogText(output);

            try {
                output += "\n";
                file.append(output);
            } catch (IOException e) {
                e.printStackTrace();
                view.showError("Can't write in file!");
            }

            setPeakTime();

            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            if(!sleep) {
                setAverageTime();
            }
        }

        view.appendEventLogText("\nAverage Waiting Time: " + averageWaitingTime + "\nAverage Service Time: "
                                + averageServiceTime + "\nPeak Time: " + peakTime);
        try {
            file.append("\nAverage Waiting Time: " + averageWaitingTime + "\nAverage Service Time: "
                        + averageServiceTime + "\nPeak Time: " + peakTime);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Queue.stop = true;
    }
}


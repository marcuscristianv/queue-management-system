import java.util.*;

public class Scheduler {

    private List<Queue> queueList;
    private int maxServers;
    private int maxTasksPerServer;

    public Scheduler(int maxServers, int maxTasksPerServer) {
        this.queueList = Collections.synchronizedList(new ArrayList<>());
        this.maxServers = maxServers;
        this.maxTasksPerServer = maxTasksPerServer;

        for(int crt = 0; maxServers > 0; crt++, maxServers--) {
            Queue newQueue = new Queue(crt, maxTasksPerServer);
            this.queueList.add(newQueue);

            Thread newThread = new Thread(this.queueList.get(crt));
            newThread.start();
        }
    }

    public void dispatchClient(Client c) throws InterruptedException {
        int minWaitTime = Integer.MAX_VALUE;
        int crtClientAt = 0;

        for(int i = 0; i < this.queueList.size(); i++) {
            if(this.queueList.get(i).getWaitingTime().get() < minWaitTime) {
                minWaitTime = this.queueList.get(i).getWaitingTime().get();
                crtClientAt = i;
            }
        }

        this.queueList.get(crtClientAt).addClient(c);
    }

    public List<Queue> getQueueList() {
        return this.queueList;
    }

}

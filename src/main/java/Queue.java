import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {

    private int ID;
    private BlockingQueue<Client> clientQueue;
    private AtomicInteger waitingTime;

    private Double totalWaitingTime;
    private Double totalServiceTime;

    public static boolean stop = false;

    public Queue(int ID, int no) {
        this.ID = ID;
        clientQueue = new LinkedBlockingQueue<>();
        waitingTime = new AtomicInteger(0);
        totalWaitingTime = totalServiceTime = 0.0;
    }

    public BlockingQueue<Client> getClientQueue() {
        return clientQueue;
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = new AtomicInteger(waitingTime);
    }

    public Double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public Double getTotalServiceTime() {
        return totalServiceTime;
    }

    public void addClient(Client c) throws InterruptedException {
        synchronized (waitingTime) {
            c.setQueueID(ID);
            c.setWaitingTime(waitingTime.get());
            clientQueue.put(c);
            waitingTime.getAndAdd(c.getServiceTime());
        }
    }

    @Override
    public void run() {
        Client c;
        while(!stop) {
            try {
                if(!clientQueue.isEmpty()) {
                    c = this.clientQueue.peek();
                    c.setFinishTime();

                    totalWaitingTime += c.getWaitingTime();
                    totalServiceTime += c.getServiceTime();
                    setWaitingTime(this.waitingTime.intValue() - c.getServiceTime());

                    while(c.getServiceTime() > 1) {
                        c.setServiceTime(c.getServiceTime() - 1);
                        Thread.sleep(1000);
                    }

                    clientQueue.take();
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        if(!clientQueue.isEmpty()) {
            String result = "Queue " + ID + ": ";
            for (Client c : clientQueue) {
                result += c.toString() + "; ";
            }

            return result;
        }
        return "Queue " + ID + ": closed";
    }
}

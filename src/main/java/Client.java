public class Client implements Comparable<Client> {

    private int queueID;

    private int ID;
    private int arrivalTime;
    private int waitingTime;
    private int serviceTime;
    private int finishTime;

    public Client(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setFinishTime() {
        this.finishTime = this.arrivalTime + this.serviceTime + this.waitingTime;
    }

    @Override
    public String toString() {
        return "(" + this.ID + ", " + this.arrivalTime + ", " + this.serviceTime + ")";
    }

    @Override
    public int compareTo(Client o) {
        return this.arrivalTime - o.arrivalTime;
    }
}

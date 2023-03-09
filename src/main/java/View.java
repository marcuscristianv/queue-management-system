import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JPanel mainPanel = new JPanel();

    private JTextField noOfClients = new JTextField(4);
    private JTextField noOfQueues = new JTextField(4);

    private JTextField simulationTime = new JTextField(4);

    private JTextField minArrivalTime = new JTextField(4);
    private JTextField maxArrivalTime = new JTextField(4);

    private JTextField minServiceTime = new JTextField(4);
    private JTextField maxServiceTime = new JTextField(4);

    private JButton start = new JButton("                              Start Simulation                              ");
    private JTextArea eventLog = new JTextArea(20, 50);

    public View() {

        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panelTitlu = new JPanel();
        panelTitlu.setLayout(new FlowLayout());
        panelTitlu.add(new JLabel("Queues Management System - Marcus Cristian-Viorel 302210"));

        JPanel panelInput1 = new JPanel();
        panelInput1.setLayout(new FlowLayout());
        panelInput1.add(new JLabel("Number of Clients:"));
        panelInput1.add(noOfClients);
        panelInput1.add(new JLabel("Number of Queues:"));
        panelInput1.add(noOfQueues);

        JPanel panelInput2 = new JPanel();
        panelInput2.setLayout(new FlowLayout());
        panelInput2.add(new JLabel("Simulation Time:"));
        panelInput2.add(simulationTime);

        JPanel panelInput3 = new JPanel();
        panelInput3.setLayout(new FlowLayout());
        panelInput3.add(new JLabel("Min Arrival Time:"));
        panelInput3.add(minArrivalTime);
        panelInput3.add(new JLabel("Max Arrival Time:"));
        panelInput3.add(maxArrivalTime);

        JPanel panelInput4 = new JPanel();
        panelInput4.setLayout(new FlowLayout());
        panelInput4.add(new JLabel("Min Service Time:"));
        panelInput4.add(minServiceTime);
        panelInput4.add(new JLabel("Max Service Time:"));
        panelInput4.add(maxServiceTime);

        JPanel panelInput5 = new JPanel();
        panelInput5.setLayout(new FlowLayout());
        panelInput5.add(start);

        JPanel panelEventLog = new JPanel();
        panelEventLog.setLayout(new FlowLayout());
        eventLog.setEditable(false);
        JScrollPane scroll = new JScrollPane(eventLog);
        panelEventLog.add(scroll);

        mainPanel.add(panelTitlu);
        mainPanel.add(panelInput1);
        mainPanel.add(panelInput2);
        mainPanel.add(panelInput3);
        mainPanel.add(panelInput4);
        mainPanel.add(panelInput5);
        mainPanel.add(panelEventLog);

        this.add(mainPanel);
        this.setTitle("Queues Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 565);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public String getNoOfClientsText() {
        return noOfClients.getText();
    }

    public String getNoOfQueuesText() {
        return noOfQueues.getText();
    }

    public String getSimulationTimeText() {
        return simulationTime.getText();
    }

    public String getMinArrivalTimeText() {
        return minArrivalTime.getText();
    }

    public String getMaxArrivalTimeText() {
        return maxArrivalTime.getText();
    }

    public String getMinServiceTimeText() {
        return minServiceTime.getText();
    }

    public String getMaxServiceTimeText() {
        return maxServiceTime.getText();
    }

    public void setEventLogText(String text) {
        eventLog.append(text);
    }

    public void appendEventLogText(String text) {
        eventLog.append(text);
    }

    void addStartListener(ActionListener l) {
        start.addActionListener(l);
    }

    void showError(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}

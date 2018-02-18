public class Main {

    public static void main(String[] args) {
        QueueManager qm = new QueueManager();
        qm.addPcb(new ProcessControlBlock("AB", "Augmented Browser", 34, 72));
        qm.addPcb(new ProcessControlBlock("AMP", "Augmented Media Player", 56, 45));
        qm.addPcb(new ProcessControlBlock("ADT", "Augmented Doc Tool", 23, 33));
        qm.printQueue();

        //qm.runAlgorithm(SchedulerAlgorithm.FIRST_COME_FIRST_SERVED);
        //qm.runAlgorithm(SchedulerAlgorithm.SHORTEST_JOB_NEXT);
        //qm.runAlgorithm(SchedulerAlgorithm.PRIORITY);
        //qm.runAlgorithm(SchedulerAlgorithm.ROUND_ROBIN);
    }
}

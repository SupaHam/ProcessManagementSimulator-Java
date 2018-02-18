import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueManager {

    public static final int ROUND_ROBIN_CPU_CYCLES = 23;

    private List<ProcessControlBlock> queue = new ArrayList<>();
    private int processSwitchCount = 0;

    public void addPcb(ProcessControlBlock pcb) {
        this.queue.add(pcb);
    }

    public void printQueue() {
        PrettyPrint pp = new PrettyPrint("ID", "NAME", "PRIORITY", "STATE", "JOB LENGTH");
        for (ProcessControlBlock pcb : queue) {
            pp.addRow(pcb.getId(), pcb.getName(), pcb.getPriority(), pcb.getState(), pcb.getJobLength());
        }
        pp.print();
    }

    public void runAlgorithm(SchedulerAlgorithm algorithm) {
        long start = System.currentTimeMillis();
        if (algorithm == SchedulerAlgorithm.FIRST_COME_FIRST_SERVED) {
            firstComeFirstServed();
        } else if (algorithm == SchedulerAlgorithm.ROUND_ROBIN) {
            roundRobin();
        } else if (algorithm == SchedulerAlgorithm.SHORTEST_JOB_NEXT) {
            shortestJobNext();
        } else if (algorithm == SchedulerAlgorithm.PRIORITY) {
            priority();
        }
        System.out.println("Process switch count: " + processSwitchCount);
        System.out.println("Finished in " + (System.currentTimeMillis() - start) / 1000.0 + "s");
    }

    private void firstComeFirstServed() {
        for (ProcessControlBlock pcb : queue) {
            simulateProcessSwitch();
            pcb.setState(PcbState.PROCESSING);
            for (int i = pcb.getJobLength(); i > 0; i--) {
                pcb.setJobLength(pcb.getJobLength() - 1);
                printQueue();
                simulateFetchExecute();
            }
            if (pcb.getJobLength() <= 0) {
                pcb.setState(PcbState.TERMINATED);
                System.out.println("PCB " + pcb.getId() + " complete!");
                printQueue();
            }
        }
    }

    private void roundRobin() {
        boolean finished = true;

        ArrayList<ProcessControlBlock> queue = new ArrayList<>(this.queue);
        // TODO Not sure why the queue needs to be reversed.
        Collections.reverse(queue);

        for (ProcessControlBlock pcb : queue) {
            simulateProcessSwitch();
            if (pcb.getState() != PcbState.TERMINATED && pcb.getJobLength() > 0) {
                finished = false;
                pcb.setState(PcbState.PROCESSING);
                for (int i = 0; i < ROUND_ROBIN_CPU_CYCLES; i++) {
                    pcb.setJobLength(pcb.getJobLength() - 1);
                    printQueue();
                    simulateFetchExecute();
                    if (pcb.getJobLength() <= 0) {
                        pcb.setState(PcbState.TERMINATED);
                        System.out.println("PCB " + pcb.getId() + " complete!");
                        break;
                    }
                }
            }
            if (pcb.getState() != PcbState.TERMINATED) {
                pcb.setState(PcbState.WAITING);
                System.out.println("PCB " + pcb.getId() + " timed out!");
            }
        }

        if (!finished) {
            roundRobin();
        }
    }

    private void shortestJobNext() {
        ArrayList<ProcessControlBlock> queue = new ArrayList<>(this.queue);
        // Sort queue by job length from lowest to highest.
        queue.sort((o1, o2) -> Integer.compare(o1.getJobLength(), o2.getJobLength()));

        for (ProcessControlBlock pcb : queue) {
            simulateProcessSwitch();
            pcb.setState(PcbState.PROCESSING);
            for (int i = pcb.getJobLength(); i > 0; i--) {
                pcb.setJobLength(pcb.getJobLength() - 1);
                printQueue();
                simulateFetchExecute();
            }
            if (pcb.getJobLength() <= 0) {
                pcb.setState(PcbState.TERMINATED);
                System.out.println("PCB " + pcb.getId() + " complete!");
                printQueue();
            }
        }
    }

    private void priority() {
        ArrayList<ProcessControlBlock> queue = new ArrayList<>(this.queue);
        // Sort queue by priority from highest to lowest.
        queue.sort((o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority()));

        for (ProcessControlBlock pcb : queue) {
            simulateProcessSwitch();
            pcb.setState(PcbState.PROCESSING);
            for (int i = pcb.getJobLength(); i > 0; i--) {
                pcb.setJobLength(pcb.getJobLength() - 1);
                printQueue();
                simulateFetchExecute();
            }
            if (pcb.getJobLength() <= 0) {
                pcb.setState(PcbState.TERMINATED);
                System.out.println("PCB " + pcb.getId() + " complete!");
                printQueue();
            }
        }
    }

    private void simulateProcessSwitch() {
        try {
            Thread.sleep(1000);
            processSwitchCount++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void simulateFetchExecute() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

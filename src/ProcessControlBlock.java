public class ProcessControlBlock {

    private final String id;
    private final String name;
    private int priority;
    private PcbState state;
    private int jobLength;

    public ProcessControlBlock(String id, String name, int priority, int jobLength) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.state = PcbState.WAITING;
        this.jobLength = jobLength;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public PcbState getState() {
        return state;
    }

    public void setState(PcbState state) {
        this.state = state;
    }

    public int getJobLength() {
        return jobLength;
    }

    public void setJobLength(int jobLength) {
        this.jobLength = jobLength;
    }
}

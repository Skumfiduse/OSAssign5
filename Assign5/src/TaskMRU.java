public class TaskMRU implements Runnable{
    int[] sequence;
    int maxMemoryFrames;
    int MaxPageReference;
    int[] pageFaults;

    public TaskMRU(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults) {
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames;
        this.MaxPageReference = maxPageReference;
    }

    @Override
    public void run() {
        System.out.println("the thread ran well");
    }
    
    public String identify() {
        return "This is the TaskMRU Talking";
    }
}
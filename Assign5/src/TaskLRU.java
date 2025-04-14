public class TaskLRU {
    int[] sequence;
    int maxMemoryFrames;
    int MaxPageReference;
    int[] pageFaults;

    public TaskLRU(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults) {
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames;
        this.MaxPageReference = maxPageReference;
    }
    public String identify() {
        return "This is the TaskLRU Talking";
    }
}
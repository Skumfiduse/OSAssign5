import java.util.LinkedList;

class TaskLRU implements Runnable {
    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;
    LinkedList<Integer> lru;

    public TaskLRU(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults) {
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames;
        this.maxPageReference = maxPageReference;
        this.pageFaults = pageFaults;
        this.lru = new LinkedList<>();
    }

    @Override
    public void run() {
        // Create page fault and set to 0
        int pageFault = 0;

        // for each through the sequence
        for (int page : sequence) {
            // if page is not in fifo
            if (!lru.contains(page)) {
                // increment pageFault var.
                pageFault++;
                
                // if fifo is full
                if (lru.size() >= maxMemoryFrames) {
                    // Remove least recently used page
                    lru.removeFirst();
                }
            } else {
                // If it's there remove it
                lru.remove((Integer) page);
            }

            // Add it back in at the end of list.
            lru.addLast(page);
        }

        setReport(pageFault);
    }

    public void setReport(int pageFault) {
        // place the put into the pageFaults array at the index of maxMemoryFrames
        pageFaults[this.maxMemoryFrames] = pageFault;
    }
}
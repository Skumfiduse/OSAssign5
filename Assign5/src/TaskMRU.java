import java.util.LinkedList;

class TaskMRU implements Runnable {
    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;
    LinkedList<Integer> mru;

    public TaskMRU(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults) {
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames;
        this.maxPageReference = maxPageReference;
        this.pageFaults = pageFaults;
        this.mru = new LinkedList<>();
    }

    @Override
    public void run() {
        // create and set pageFault = 0
        int pageFault = 0;

        // for each through the sequence
        for (int page : sequence) {
            // if page is not in fifo
            if (!mru.contains(page)) {
                // increment pageFault var.
                pageFault++;

                // if fifo is full
                if (mru.size() >= maxMemoryFrames) {
                    // Remove most recently used page
                    mru.removeLast();
                }
            } else {
                // If it's there remove it
                mru.remove((Integer) page);
            }
            // Add it back in to the end
            mru.addLast(page);
        }

        setReport(pageFault);
    }

    public void setReport(int pageFault) {
        // place the put into the pageFaults array at the index of maxMemoryFrames
        pageFaults[this.maxMemoryFrames] = pageFault;         
    }
}
import java.util.Queue;

class TaskFIFO implements Runnable {
    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;
    Queue<Integer> fifo;
    public TaskFIFO(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults) {
    
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames;
        this.maxPageReference = maxPageReference;
        this.pageFaults = pageFaults;
        this.fifo = new java.util.LinkedList<>();

    }

    @Override
    public void run() {
        // create and set pageFault = 0
        int pageFault = 0;

        // for each through the sequence
        for (int page : this.sequence) {
            // if page is not in fifo
            if (!this.fifo.contains(page)) {
                // increment pageFault var.
                pageFault++;

                // if fifo is full
                if (this.fifo.size() >= this.maxMemoryFrames) {
                    // pop the oldest page
                    this.fifo.poll();
                }

                // add the needed page
                this.fifo.add(page);
            }
        }
    setReport(pageFault);
    }

    public void setReport(int pageFault) {
        // place the put into the pageFaults array at the index of maxMemoryFrames
        pageFaults[this.maxMemoryFrames] = pageFault;
    }
}

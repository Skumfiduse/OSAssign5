import java.util.Queue;

class TaskFIFO implements Runnable {
    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;
    Queue<Integer> fifo;
    int prev = 0;
    int maxDifference;
    String report = "";
    int timesDetected = 0;

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
        // place the put into the pageFaults array at the index of maxMemoryFrames
        this.pageFaults[this.maxMemoryFrames] = pageFault;

        // add the total of pageFault to pageFaults[0]
        int toAdd = this.pageFaults[0];
        this.pageFaults[0] = toAdd + pageFault;

        // if current = null return
        if (this.prev == 0) {
            this.prev = pageFault;
            return;
        }

        // if prev < current
        if (this.prev <= pageFault) {
            this.report += "detected - Previous " + prev + " : Current " + pageFault + " " + (prev - pageFault) + "\n";
            this.timesDetected++;
            if (this.prev - pageFault > this.maxDifference) {
                this.maxDifference = this.prev - pageFault;
            }
        }
        // set current to prev.
        this.prev = pageFault;
    }

    public void setmaxMemoryFrames(int newMax) {
        this.maxMemoryFrames = newMax;
    }

    public void setMaxPageReference(int maxPageReference) {
        this.maxPageReference = maxPageReference;
    }
}

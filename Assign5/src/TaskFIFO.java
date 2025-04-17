import java.util.Queue;

class TaskFIFO implements Runnable {
    int[] sequence;
    int maxMemoryFrames;
    int MaxPageReference;
    int[] pageFaults;
    Queue<Integer> fifo;
    int prev = 0;
    int maxDifference;
    String report = "";
    int timesDetected = 0;

    public TaskFIFO(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults) {
    
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames;
        this.MaxPageReference = maxPageReference;
        this.pageFaults = pageFaults;
        this.fifo = new java.util.LinkedList<>();

    }

    @Override
    public void run() {
        // create and set pageFault = 0
        int pageFault = 0;

        // for each through the sequence
        for (int page : sequence) {
            // if page is not in fifo
            if (!fifo.contains(page)) {
                // increment pageFault var.
                pageFault++;

                // if fifo is full
                if (fifo.size() >= maxMemoryFrames) {
                    // pop the oldest page
                    fifo.poll();
                }

                // add the needed page
                fifo.add(page);
            }
        }    
        
        // place the put into the pageFaults array at the index of maxMemoryFrames
        pageFaults[maxMemoryFrames] = pageFault;

        // add the total of pageFault to pageFaults[0]
        pageFaults[0] = pageFaults[0] + pageFault;

        // if current = null return
        if (prev == 0) {
            prev = pageFault;
            return;
        }

        // if prev < current
        if (prev < pageFault) {
            this.report += "detected - Previous " + prev + " : Current " + pageFault + " " + (prev - pageFault) + "\n";
            this.timesDetected++;
            if (prev - pageFault > maxDifference) {
                maxDifference = prev - pageFault;
            }
        }
        // set current to prev.
        prev = pageFault;
    }

    public void setmaxMemoryFrames(int maxMemoryFrames) {
        this.maxMemoryFrames = maxMemoryFrames;
    }

    public void setMaxPageReference(int maxPageReference) {
        this.MaxPageReference = maxPageReference;
    }
}

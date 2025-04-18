import java.util.Queue;

class TaskFIFO implements Runnable {
    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;
    Queue<Integer> fifo;
    int maxDifference;
    String report = "";
    int timesDetected = 0;
    String name;

    public TaskFIFO(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults) {
    
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames;
        this.maxPageReference = maxPageReference;
        this.pageFaults = pageFaults;
        this.fifo = new java.util.LinkedList<>();
        this.name = "fifo" + this.maxMemoryFrames;

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

    public synchronized void setReport(int pageFault) {
        
        synchronized (this.pageFaults){
        // place the put into the pageFaults array at the index of maxMemoryFrames
        pageFaults[this.maxMemoryFrames] = pageFault;

        // add the total of pageFault to pageFaults[0]
        int toAdd = this.pageFaults[0];
        this.pageFaults[0] = toAdd + pageFault;
        
        
        // if prev < current
        // System.out.println(pageFaults[maxMemoryFrames - 1] + "    " + this.maxMemoryFrames);
        if (pageFaults[maxMemoryFrames - 1] < pageFault && pageFaults[maxMemoryFrames - 1] != 0) { 
            String add = "detected - Previous " + pageFaults[maxMemoryFrames - 1] + " : Current " + pageFault + " " + (pageFault - pageFaults[maxMemoryFrames - 1]  + "\n");
            this.report = this.report + add;
            this.timesDetected++;
            System.out.print(this.timesDetected + "000000000000000000000000000000000000000000000000");
            if (this.pageFaults[maxMemoryFrames - 1] - pageFault > this.maxDifference) {
                this.maxDifference = this.pageFaults[maxMemoryFrames - 1] - pageFault;
            }
        }
        }

    }

    public void setmaxMemoryFrames(int newMax) {
        this.maxMemoryFrames = newMax;
    }

    public void setMaxPageReference(int maxPageReference) {
        this.maxPageReference = maxPageReference;
    }
}

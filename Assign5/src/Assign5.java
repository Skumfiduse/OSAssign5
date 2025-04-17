import java.util.Random;
import java.util.concurrent.ExecutorService;
public class Assign5 {
    public static void main(String[] args) {
        // create executor for thread management equal to the number of operating cores in the system.

        // create the pool for threads in the simulation.

        // Create the vars that track how many times each algorithm has the least ammount of faults include ties. each tie goes to both counts.
        int timesFifo = 0;
        int timesLru = 0;
        int timesMru = 0;

        // create the reportFifo, reportLru, and reportMru strings.
        // report strings
        String reportFifo = "";
        String reportLru = "";
        String reportMru = "";
        // times belady's was detected
        int detectedFifo = 0;
        int detectedLru = 0;
        int detectedMru = 0;
        // max diff for each algorithm
        int fifoMaxDiff = 0;
        int lruMaxDiff = 0;
        int mruMaxDiff = 0;

        // save the start time for the simulation in the var end.
        long end = System.currentTimeMillis();

        // create the main loop for the simulation. It loops 1000 times.
        for (int i = 1; i <= 1000; i++) {
           // create the sequecnce for the algorithms to use. no number over 250, length of 1000.
           int[] sequence = new int[1000];
           for (int j = 0; j < 1000; j++) {
            Random rand = new Random();
            int frame = rand.nextInt(251);
            sequence[j] = frame;
           }

           // find the max number in the sequence, name it maxPage. This number must be greater than 0 and less than 250.
           int maxPageReference = 0;
           for (int number : sequence) {
            if (number > maxPageReference) {
                maxPageReference = number;
            }
           }
           int[] pageFaults = new int[101];

           // create the objects for the algorithms. pass in the sequence, size the page fault array to 101, max to null, set maxPageRef to MaxPage.
           TaskFIFO fifo = new TaskFIFO(sequence, 101, maxPageReference, pageFaults);

           // create the memory frames loop. loop from 1 to 100.
              for (int j = 1; j <= 100; j++) {

              // set the maxMemoryFrames to the loop index for all the algorithms
              fifo.setmaxMemoryFrames(j);

              // call run on each of the algorithms and add them to the thread pool.

              // if fifo <= mru and lru add taskMRU.pageFaults[0] to the taskFIFO.timeswon times won var. Then do the same for LRU and MRU.

              // add TaskFIFO.timesDetected to detectedFifo, do the same for LRU and MRU.
              detectedFifo += fifo.timesDetected;

              // concatinate TaskFIFO.report with reportFifo. do the same with LRU and MRU
              reportFifo += fifo.report;

              if (fifo.maxDifference > fifoMaxDiff) {
                fifoMaxDiff = fifo.maxDifference;
              }
            }





        }   
        // Wait for the threads to finish.
            
        // close the tread pool

        // subtract the start time form the current time and set that = end.
        end = System.currentTimeMillis() - end;

        // print out the time of the simulation
        System.out.printf("Simulation took %d milliseconds\n", end);

        // print out the number of times each algorithm had the least amount of faults.
        System.out.printf("FIFO min PF : %d\n", timesFifo);

        // report the results of the simulation.
        System.out.printf("FIFO min PF  :  %d\n", timesFifo);
        System.out.printf("LRU min PF  :  %d\n", timesLru);
        System.out.printf("MRU min PF  :  %d\n", timesMru);

        System.out.println("Belady's Anomaly Report for FIFO");
        System.out.println(reportFifo);
        System.out.printf("Anomaly detected %d ties with a max of %d\n\n", detectedFifo, fifoMaxDiff);  
    }
}

class Report {
    String reportFifo;
    int timesFifo;
    String reportLru;
    int timesLru;
    String reportMru;
    int timesMru;
    int fifoMax;
    int lruMax;
    int mruMax;
    int timesFifoWon;
    int timesLruWon;
    int timesMruWon;

    void addFifo(String s, int max) {
        timesFifo++;
        reportFifo += s + "\n";
        if (this.fifoMax < max) {
            this.fifoMax = max;
        }
    }

    void addLru(String s, int max) {
        timesLru++;
        reportLru += s + "\n";
        if (this.lruMax < max) {
            this.lruMax = max;
        }
    }

    void addMru(String s, int max) {
        timesMru++;
        reportMru += s + "\n";
        if (this.mruMax < max) {
            this.mruMax = max;
        }
    }

    public void report() {
        System.out.println("Belady's Anomaly Report for FIFO");
        System.out.println(this.reportFifo);
        System.out.printf("Anomaly detected %d ties with a max of %d\n\n", this.timesFifo, this.fifoMax);
        System.out.println("Belady's Anomaly Report for LRU");
        System.out.println(this.reportLru);
        System.out.printf("Anomaly detected %d ties with a max of %d\n\n", this.timesLru, this.lruMax);
        System.out.println("Belady's Anomaly Report for MRU");
        System.out.println(this.reportFifo);
        System.out.printf("Anomaly detected %d ties with a max of %d", this.timesMru, this.mruMax);
    }
}

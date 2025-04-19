import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class Assign5 {
    public static void main(String[] args) {
        // create executor for thread management equal to the number of operating cores in the system.
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        // create the pool for threads in the simulation.
        
        // Create the vars that track how many times each algorithm has the least amount of faults include ties. each tie goes to both counts.
        int timesFifo = 0;
        int timesLru = 0;
        int timesMru = 0;

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

        // Double indexted arrays to hold results of the algorithms
        int[][] fifoResultsArray = new int[1001][101];
        int[][] lruResultsArray = new int[1001][101];
        int[][] mruResultsArray = new int[1001][101];

        // create the main loop for the simulation. It loops 1000 times.
        for (int sim = 1; sim <= 1000; sim++) {
           // create the sequecnce for the algorithms to use. no number over 250, length of 1000.

           Random rand = new Random();
           int[] sequence = new int[1000];
           for (int k = 0; k < 1000; k++) {
            sequence[k] = rand.nextInt(250) + 1;
           }

           // find the max number in the sequence, name it maxPage. This number must be greater than 0 and less than 250.
           int maxPageReference = 250;
           
           
           // create the memory frames loop. loop from 1 to 100.
           for (int maxMem = 1; maxMem <= 100; maxMem++) {
               
              // create the objects for the algorithms. pass in the sequence, size the page fault array to 101, max to null, set maxPageRef to MaxPage.
              TaskFIFO fifo = new TaskFIFO(sequence, maxMem, maxPageReference, fifoResultsArray[sim]);
              TaskMRU mru = new TaskMRU(sequence, maxMem, maxPageReference, mruResultsArray[sim]);
              TaskLRU lru = new TaskLRU(sequence, maxMem, maxPageReference, lruResultsArray[sim]);

              // call run on each of the algorithms and add them to the thread pool.
              executor.execute(fifo);
              executor.execute(mru);
              executor.execute(lru);

            }

        }   
        // Wait for the threads to finish.
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.out.print("User preemptive termination of program execution.");
        }

        // subtract the start time form the current time and set that = end.
        end = System.currentTimeMillis() - end;

        // print out the time of the simulation
        System.out.printf("Simulation took %d milliseconds.\n", end);

        for (int sim = 1; sim <= 1000; sim++) {
            for (int maxMem = 1; maxMem <= 100; maxMem++) {
                if (fifoResultsArray[sim][maxMem] <= lruResultsArray[sim][maxMem] && fifoResultsArray[sim][maxMem] <= mruResultsArray[sim][maxMem]) {
                    timesFifo++;
                }
                if (lruResultsArray[sim][maxMem] <= fifoResultsArray[sim][maxMem] && lruResultsArray[sim][maxMem] <= mruResultsArray[sim][maxMem]) {
                    timesLru++;
                }
                if (mruResultsArray[sim][maxMem] <= fifoResultsArray[sim][maxMem] && mruResultsArray[sim][maxMem] <= lruResultsArray[sim][maxMem]) {
                    timesMru++;
                }
            }
        }

        // report the results of the simulation.
        System.out.printf("FIFO min PF  :  %d\n", timesFifo);
        System.out.printf("LRU min PF  :  %d\n", timesLru);
        System.out.printf("MRU min PF  :  %d\n", timesMru);

        System.out.println("Belady's Anomaly Report for FIFO");
        for (int sim = 1; sim <= 1000; sim++) {
            for (int maxMem = 1; maxMem <= 100; maxMem++) {
                if (fifoResultsArray[sim][maxMem - 1] != 0 && fifoResultsArray[sim][maxMem] > fifoResultsArray[sim][maxMem - 1]) {
                    detectedFifo++;
                    int diff = fifoResultsArray[sim][maxMem] - fifoResultsArray[sim][maxMem - 1];
                    if (diff > fifoMaxDiff) {
                        fifoMaxDiff = diff;
                    }
                    System.out.printf("     Detected - Previous %d : Current %d (%d)\n", fifoResultsArray[sim][maxMem - 1], fifoResultsArray[sim][maxMem], diff);
                }

            }
        }
        System.out.printf("      Anomaly detected %d times with a max of %d\n\n", detectedFifo, fifoMaxDiff);  

        // for lru
        System.out.println("Belady's Anomaly Report for lru");
        for (int sim = 1; sim <= 1000; sim++) {
            for (int maxMem = 1; maxMem <= 100; maxMem++) {
                if (lruResultsArray[sim][maxMem - 1] != 0 && lruResultsArray[sim][maxMem] > lruResultsArray[sim][maxMem - 1]) {
                    detectedLru++;
                    int diff = lruResultsArray[sim][maxMem] - lruResultsArray[sim][maxMem - 1];
                    if (diff > lruMaxDiff) {
                        lruMaxDiff = diff;
                    }
                    System.out.printf("     Detected - Previous %d : Current %d (%d)\n", lruResultsArray[sim][maxMem - 1], lruResultsArray[sim][maxMem], diff);
                }

            }
        }
        System.out.printf("      Anomaly detected %d times with a max of %d\n\n", detectedLru, lruMaxDiff);

        // for mru
        System.out.println("Belady's Anomaly Report for mru");
        for (int sim = 1; sim <= 1000; sim++) {
            for (int maxMem = 1; maxMem <= 100; maxMem++) {
                if (mruResultsArray[sim][maxMem - 1] != 0 && mruResultsArray[sim][maxMem] > mruResultsArray[sim][maxMem - 1]) {
                    detectedMru++;
                    int diff = mruResultsArray[sim][maxMem] - mruResultsArray[sim][maxMem - 1];
                    if (diff > mruMaxDiff) {
                        mruMaxDiff = diff;
                    }
                    System.out.printf("     Detected - Previous %d : Current %d (%d)\n", mruResultsArray[sim][maxMem - 1], mruResultsArray[sim][maxMem], diff);
                }

            }
        }
        System.out.printf("      Anomaly detected %d times with a max of %d\n\n", detectedMru, mruMaxDiff);
    }
}

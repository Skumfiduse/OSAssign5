public class Assign5 {
    public static void main(String[] args) {
        TaskFIFO fifo = new TaskFIFO();
        TaskLRU lru = new TaskLRU();
        TaskMRU mru = new TaskMRU();
        System.out.println(fifo.identify());
        System.out.println(lru.identify());
        System.out.println(mru.identify());
    }
}

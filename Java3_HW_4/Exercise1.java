public class Exercise1 {
    
    static final Object sync = new Object();
    static volatile char phase = 'A';
    static final int number = 5;

    public static void main(String[] args) {
        
        new Thread(() -> {
            try{
                for (int i = 0; i < number; i++) {
                    synchronized (sync){
                        while(phase != 'A'){
                            sync.wait();
                        }
                        System.out.print(phase);// current A
                        phase = 'B'; // next B
                        sync.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try{
                for (int i = 0; i < number; i++) {
                    synchronized (sync){
                        while(phase != 'B'){
                            sync.wait();
                        }
                        System.out.print(phase);// current B
                        phase = 'C'; // next C
                        sync.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try{
                for (int i = 0; i < number; i++) {
                    synchronized (sync){
                        while(phase != 'C'){
                            sync.wait();
                        }
                        System.out.println(phase); // current C
                        phase = 'A'; // next A
                        sync.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

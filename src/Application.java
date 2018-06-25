import java.util.concurrent.atomic.AtomicInteger;

class SafeCounterWithoutLock {
    private final AtomicInteger counter = new AtomicInteger(0);

    public int getValue() {
        System.out.println("Initialize the counter");
        return counter.get();
    }

    public void increment(String msg) {
        while (true) {
            int existingValue = getValue();
            int newValue = existingValue + 1;
            if (counter.compareAndSet(existingValue, newValue)) {
                for (int i=0; i<5; i++){
                System.out.println(msg + newValue);
                }
                return;
            }
            System.out.println("fail to set new value");
        }
    }
}

class Application {
    public static void main(String args[]) {
        SafeCounterWithoutLock obj =new SafeCounterWithoutLock();
        Thread t1 = new Thread(){
            @Override
            public void run() {
                obj.increment("first thread");
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run() {
                obj.increment("second thread");
            }
        };
        t1.start();
        t2.start();
    }
}
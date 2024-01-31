package diningphilosopheres;

import java.util.concurrent.Semaphore;

/**
 * @Author: huangpenglong
 * @Date: 2024/1/31 16:24
 */
public class OddEvenCrossoverDining {

    private final int num;
    private final Semaphore[] forks;

    public OddEvenCrossoverDining(int num){
        this.num = num;
        this.forks = new Semaphore[num];
        for(int i = 0; i < num; i++){
            this.forks[i] = new Semaphore(1);
        }
    }

    private void startDining() {
        for(int i = 0; i < num; i++){
            final int philosopherNum = i;
            new Thread(() -> {
                while(true) {
                    try {
                        thinking(philosopherNum);
                        pickUpFork(philosopherNum);
                        eating(philosopherNum);
                        putDownFork(philosopherNum);
                    }
                    catch (InterruptedException e){
                    }
                }
            }, "Philosopher " + i).start();
        }
    }

    private void thinking(int philosopherNum) throws InterruptedException {
        System.out.println("Philosopher " + philosopherNum + "正在思考...");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void pickUpFork(int philosopherNum) throws InterruptedException {
        if(philosopherNum % 2 == 0){
            forks[philosopherNum].acquire();
            forks[(philosopherNum+1) % num].acquire();
        }
        else{
            forks[(philosopherNum+1) % num].acquire();
            forks[philosopherNum].acquire();
        }
    }

    private void eating(int philosopherNum) throws InterruptedException {
        System.out.println("Philosopher " + philosopherNum + "正在吃饭...");
        Thread.sleep((long) (Math.random() * 2000));
    }

    private void putDownFork(int philosopherNum) {
        forks[philosopherNum].release();
        forks[(philosopherNum+1) % num].release();
    }

    public static void main(String[] args) {
        new OddEvenCrossoverDining(10).startDining();
    }


}

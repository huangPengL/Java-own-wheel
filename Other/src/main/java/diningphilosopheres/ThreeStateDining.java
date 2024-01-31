package diningphilosopheres;

import java.util.concurrent.Semaphore;

/**
 * @Author: huangpenglong
 * @Date: 2024/1/31 16:43
 */
public class ThreeStateDining {

    private final int num;

    /**
     * 0: 没吃饭
     * 1: 正在吃饭
     */
    private final int[] state;
    private final Semaphore lock;

    public ThreeStateDining(int num){
        this.num = num;
        this.state = new int[num];
        this.lock = new Semaphore(1);
    }

    public void startDining(){
        for(int i = 0; i < num; i++){
            final int philosopherNum = i;
            new Thread(() -> {
                while(true) {
                    try {
                        thinking(philosopherNum);
                        pickUpForkAndEating(philosopherNum);
                        eating(philosopherNum);
                        putDownFork(philosopherNum);
                    }
                    catch (InterruptedException e){
                    }
                }
            }, "Philosopher " + i).start();
        }
    }

    private void thinking(int philosopherNum) throws InterruptedException  {
        System.out.println("Philosopher " + philosopherNum + "正在思考...");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void pickUpForkAndEating(int philosopherNum) throws InterruptedException  {
        lock.acquire();

        if(state[(philosopherNum-1+num)%num] == 0
                && state[(philosopherNum+1)%num] == 0){
            System.out.println("Philosopher " + philosopherNum + "的左边和右边没人，准备吃饭");
            state[philosopherNum] = 1;
        }

        lock.release();
    }

    private void lookOthers() {
        System.out.println("\n=====================================");
        for(int i = 0; i < num ; i++){
            if(state[i] == 1){
                System.out.println("Philosopher " + i + "正在吃饭！！");
            }
        }
        System.out.println("=====================================\n");
    }

    private void eating(int philosopherNum) throws InterruptedException {
        if(state[philosopherNum] == 1) {
            System.out.println("Philosopher " + philosopherNum + "正在吃饭...");
            Thread.sleep((long) (Math.random() * 2000));
            lookOthers();
        }
    }

    private void putDownFork(int philosopherNum) throws InterruptedException  {
        if(state[philosopherNum] == 1) {
            lock.acquire();
            state[philosopherNum] = 0;
            System.out.println("Philosopher " + philosopherNum + "吃完了...");
            lock.release();
        }
    }

    public static void main(String[] args) {
        new ThreeStateDining(5).startDining();
    }
}

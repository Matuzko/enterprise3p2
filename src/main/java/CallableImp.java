import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;


public class CallableImp implements Callable<Integer> {
    private Phaser phaser = null;
    private Integer massive[] = null;

    public CallableImp(Integer[] massive, Phaser phaser) {
        this.massive = massive;
        this.phaser = phaser;
    }

    public Integer call() throws Exception {
        Phaser phaser = this.phaser;
        int result = 0;


        for (int i = 0; i < massive.length; i++) {

            result += massive[i] * massive[i];

        }
        phaser.arriveAndAwaitAdvance();
        return result;
    }
}

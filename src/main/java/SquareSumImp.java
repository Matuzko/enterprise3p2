import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;


public class SquareSumImp implements SquareSum {


    public long getSquareSum(int[] values, int numberOfThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Phaser phaser = new Phaser(numberOfThreads);
        ArrayList<Callable> callable;
        Integer mass[] = new Integer[values.length];

        for (int i = 0; i < values.length; i++) {
            mass[i] = values[i];
        }

        callable = getCallableList(mass, numberOfThreads, phaser);

        ArrayList<Future<Integer>> futures = calcSquareSum(executorService, callable);

        long result = 0;

        for (Future<Integer> futureElements: futures
             ) {
            try{
                result += futureElements.get();
            }catch (InterruptedException ex){
                System.out.println(ex);
            }catch (ExecutionException ex){
                System.out.println(ex);
            }
        }
        System.out.println(result);
        return result;
    }

    public ArrayList<Future<Integer>> calcSquareSum(ExecutorService executorService, ArrayList<Callable> callableArrayList) {
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < callableArrayList.size(); i++) {
            futures.add(executorService.submit(callableArrayList.get(i)));
        }
        return futures;
    }

    public ArrayList<Callable> getCallableList(Integer massive[], int numberOfThreads, Phaser phaser) {
        ArrayList<Callable> arrayList = new ArrayList<>(numberOfThreads);
        if (massive.length % numberOfThreads == 0) {
            int numberOfCallables = massive.length / numberOfThreads;
            int start = 0;
            int end = numberOfCallables;
            for (int i = 0; i < numberOfThreads; i++) {
                Integer mass[] = Arrays.copyOfRange(massive, start, end);

                arrayList.add(new CallableImp(mass, phaser));
                start += numberOfCallables;
                end += numberOfCallables;
            }
        } else {
            int numberOfCallables = (massive.length / numberOfThreads) + 1;
            int start = 0;
            int end = numberOfCallables;
            for (int i = 0; i < numberOfThreads - 1; i++) {
                Integer mass[] = Arrays.copyOfRange(massive, start, end);

                arrayList.add(new CallableImp(mass, phaser));
                start += numberOfCallables;
                end += numberOfCallables;
            }
            Integer mass[] = Arrays.copyOfRange(massive, start, massive.length);

            arrayList.add(new CallableImp(mass, phaser));
        }
        return arrayList;
    }
}


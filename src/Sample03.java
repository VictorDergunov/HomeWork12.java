public class Sample03 {

    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;
    private static final int HALF1 = SIZE / 4;
    private static float[] arr = new float[SIZE];

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }

        SingleThread(arr);
        DoubleThread(arr);//разбиение на два потока
        FourthThread(arr);// разбиение на четыре потока
    }

    private static void SingleThread(float[] arr) {
        long start = System.currentTimeMillis();

        for(int i = 0; i < SIZE; i++){
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        long end = System.currentTimeMillis() - start;
        System.out.printf("Время выполнения однопоточного расчета %d%n", end);

    }

    private static void DoubleThread(float[] arr) {
        long start = System.currentTimeMillis();

        float[] a = new float[HALF];
        float[] b = new float[HALF];

        ThreadV2 t1 = new ThreadV2("a", a);
        ThreadV2 t2 = new ThreadV2("b", b);

        System.arraycopy(arr, 0, a, 0, HALF);
        System.arraycopy(arr, HALF, b, 0, HALF);


        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a, 0, arr, 0, HALF);
        System.arraycopy(b, 0, arr, HALF, HALF);

        long end = System.currentTimeMillis() - start;
        System.out.printf("Время выполнения многопоточного расчета %d%n", end);
    }

    private static void FourthThread(float[] arr) {
        long start = System.currentTimeMillis();

        float[] a = new float[HALF1];
        float[] b = new float[HALF1];
        float[] c = new float[HALF1];
        float[] d = new float[HALF1];

        ThreadV2 t1 = new ThreadV2("a", a);
        ThreadV2 t2 = new ThreadV2("b", b);
        ThreadV2 t3 = new ThreadV2("с", c);
        ThreadV2 t4 = new ThreadV2("в", d);

        System.arraycopy(arr, 0, a, 0, HALF1);
        System.arraycopy(arr, HALF1, b, 0, HALF1);
        System.arraycopy(arr, 2 * HALF1, c, 0, HALF1);
        System.arraycopy(arr, 3 * HALF1, d, 0, HALF1);


        t1.start();
        t2.start();
        t3.start();
        t4.start();


        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a, 0, arr, 0, HALF1);
        System.arraycopy(b, 0, arr, HALF1, HALF1);
        System.arraycopy(c, 0, arr, 2 * HALF1, HALF1);
        System.arraycopy(d, 0, arr, 3 * HALF1, HALF1);

        long end = System.currentTimeMillis() - start;
        System.out.printf("Время выполнения расчета при разбиении на четыре потока %d%n", end);
    }

}

class ThreadV2 extends Thread {

    float[] arr;

    ThreadV2(String name, float arr[]) {
        super(name);
        this.arr = arr;
    }

    @Override
    public void run() {
        Calculate();
    }

    public void Calculate() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

}

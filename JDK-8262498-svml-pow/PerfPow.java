import jdk.incubator.vector.*;

public class PerfPow {
    static final VectorSpecies<Double> SPECIES64  = DoubleVector.SPECIES_64;
    static final VectorSpecies<Double> SPECIES128 = DoubleVector.SPECIES_128;
    static final VectorSpecies<Double> SPECIES256 = DoubleVector.SPECIES_256;

    static final int N = 1024;

    static double[] a = new double[N];
    static double[] b = new double[N];
    static double[] r = new double[N];

    static {
        for (int i = 0; i < a.length; i++) {
            a[i] = i / 64.0;
            b[i] = i;
        }
    }

    static void test() {
        for (int i = 0; i < a.length; i += SPECIES256.length()) {
            DoubleVector av = DoubleVector.fromArray(SPECIES256, a, i);
            DoubleVector bv = DoubleVector.fromArray(SPECIES256, b, i);
            av.lanewise(VectorOperators.POW, bv).intoArray(r, i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            test();
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
             test();
        }
        long end = System.currentTimeMillis();

        System.out.println("Time for test: " + (end - start));
        System.out.println("r[0] = " + r[0]);
    }
}

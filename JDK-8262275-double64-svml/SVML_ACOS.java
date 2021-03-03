import jdk.incubator.vector.*;

public class SVML_ACOS {
  static final VectorSpecies<Double> SPECIES64 = DoubleVector.SPECIES_64;
  static double[] a = new double[64];
  static double[] rv = new double[64];
  static double[] r  = new double[64];

  static {
    for (int i = 0; i < a.length; i++) {
      a[i] = i / 64.0;
    }
  }

  static double svml_acos(double i) {
    double[] t = new double[1];
    t[0] = i;
    DoubleVector av = DoubleVector.fromArray(SPECIES64, t, 0);
    av.lanewise(VectorOperators.ACOS).intoArray(t, 0);
    return t[0];
  }

  static void vtest() {
    for (int i = 0; i < a.length; i++) {
      rv[i] = svml_acos(a[i]);
    }
  }

  static void test() {
    for (int i = 0; i < a.length; i++) {
      r[i] = Math.acos(a[i]);
    }
  }

  public static void main(String[] args) {
    for (int i = 0; i < 500000; i++) {
      test();
      vtest();
    }

    long start = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
       test();
    }
    long end = System.currentTimeMillis();
    System.out.println("Time for Math.acos: " + (end - start));

    start = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
       vtest();
    }
    end = System.currentTimeMillis();
    System.out.println("Time for svml_acos: " + (end - start));

    for (int i = 0; i < r.length; i++) {
      System.out.println(rv[i] - r[i]);
    }
  }
}

/*

Time for Math.acos: 3916
Time for svml_acos: 7399

Time for Math.acos: 3960
Time for svml_acos: 7385

Time for Math.acos: 3942
Time for svml_acos: 7503

--------------------------

Time for Math.acos: 3936
Time for svml_acos: 1355

Time for Math.acos: 3940
Time for svml_acos: 1354

Time for Math.acos: 3935
Time for svml_acos: 1353

--------------------------

WARNING: Using incubator modules: jdk.incubator.vector
Time for Math.acos: 3949
Time for svml_acos: 1353
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
0.0
-2.220446049250313E-16
0.0
0.0
0.0
0.0
0.0
0.0
0.0
-1.1102230246251565E-16
0.0
0.0
0.0
0.0
0.0
0.0
0.0
-1.1102230246251565E-16
0.0
0.0
0.0
0.0
-1.1102230246251565E-16
0.0
0.0
0.0
0.0
0.0
0.0
0.0
-5.551115123125783E-17
0.0
0.0

*/

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

public class Bar {
    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;
    static int[] a = new int[8000];
    static int[] b = new int[8000];
    static int[] c = new int[8000];

    public static void testSimple() {
        IntVector av = IntVector.fromArray(SPECIES, a, 0);
        av.intoArray(b, 0);
    }

    public static void testLoop() {
        int i = 0;
        for (; i + SPECIES.length() <= a.length; i += SPECIES.length()) {
            IntVector av = IntVector.fromArray(SPECIES, a, i);
            IntVector bv = IntVector.fromArray(SPECIES, b, i);
            av.add(bv).intoArray(c, i);
        }

        for (; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }

        for (int i = 0; i < 1000000; i++) {
            testSimple();
        }

        for (int i = 0; i < 1000000; i++) {
            testLoop();
        }

        System.out.println("b: " + b[0]);
        System.out.println("c: " + c[0]);
    }
}

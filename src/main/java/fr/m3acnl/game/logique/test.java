
public class test {

    public static double entier(double val) {
        return Math.round((val - Math.floor(val)) * 10);
    }

    public static double virgule(double val) {
        return Math.floor(val);
    }

    public static void main(String[] args) {
        Double val;
        val = 0.0;
        System.out.println(val + " -> " + entier(val) + "  " + virgule(val));
        val = 0.1;
        System.out.println(val + " -> " + entier(val) + "  " + virgule(val));
        val = 0.2;
        System.out.println(val + " -> " + entier(val) + "  " + virgule(val));
        val = 1.0;
        System.out.println(val + " -> " + entier(val) + "  " + virgule(val));
        val = 2.0;
        System.out.println(val + " -> " + entier(val) + "  " + virgule(val));
    }
}

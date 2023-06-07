import java.io.File;

public class Driver {
public static void main(String [] args) {
    Polynomial p = new Polynomial(new File("polynomial.txt"));
    p.saveToFile("polynomial1.txt");
    Polynomial q = new Polynomial(new double[] {1, 1}, new int[] {0, 1});
    Polynomial s = new Polynomial(new double[] {1, 1}, new int[] {0, 1});
    p.printPolynomial();
    p.add(q);
    q = q.multiply(s);
    p.printPolynomial();
    q.printPolynomial();
    }
}
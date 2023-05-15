public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[] {0};
    }

    public Polynomial(double[] coeff) {
        this.coefficients = coeff;
    }

    public Polynomial add(Polynomial poly) {
        int max = Math.max(this.coefficients.length, poly.coefficients.length);
        Polynomial new_poly = new Polynomial(new double[max]);
        for (int i = 0; i < max; i++) {
            double value1 = (i < this.coefficients.length) ? this.coefficients[i] : 0.0;
            double value2 = (i < poly.coefficients.length) ? poly.coefficients[i] : 0.0;
            new_poly.coefficients[i] += (value1 + value2);
        }
        return new_poly;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return (this.evaluate(x) == 0);
    }
}

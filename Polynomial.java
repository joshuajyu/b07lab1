import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[] {};
        this.exponents = new int[] {};
    }

    public Polynomial(double[] coeff, int[] exp) {
        this.coefficients = coeff;
        this.exponents = exp;
    }

    public Polynomial(File file) {
        try {
            Scanner scanner = new Scanner(file);
            String data = scanner.nextLine();
            scanner.close();
            data = data.replace("-", "+-");
            String[] poly = data.split("\\+");
            this.coefficients = new double[poly.length];
            this.exponents = new int[poly.length];
            for (int i = 0; i < poly.length; i++) {
                String[] term = poly[i].split("x");
                if (term.length == 1) {
                    if (poly[i].charAt(poly[i].length() - 1) == 'x') {
                        this.coefficients[i] = Double.parseDouble(term[0]);
                        this.exponents[i] = 1;
                    }
                    else {
                        this.coefficients[i] = Double.parseDouble(term[0]);
                        this.exponents[i] = 0;
                    }
                }
                else {
                    this.coefficients[i] = Double.parseDouble(term[0]);
                    this.exponents[i] = Integer.parseInt(term[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printPolynomial() {
        String poly = "";
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.exponents[i] == 0) {
                poly += this.coefficients[i];
            }
            else if (this.exponents[i] == 1) {
                poly += (this.coefficients[i] + "x");
            }
            else {
                poly += (this.coefficients[i] + "x" + this.exponents[i]);
            }
            poly += "+";
        }
        poly = poly.replace("+-", "-");
        poly = poly.replace(".0", "");
        poly = poly.substring(0, poly.length() - 1);
        System.out.println(poly);
    }

    public void saveToFile(String filename) {
        String poly = "";
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.exponents[i] == 0) {
                poly += this.coefficients[i];
            }
            else if (this.exponents[i] == 1) {
                poly += (this.coefficients[i] + "x");
            }
            else {
                poly += (this.coefficients[i] + "x" + this.exponents[i]);
            }
            poly += "+";
        }
        poly = poly.replace("+-", "-");
        poly = poly.replace(".0", "");
        poly = poly.substring(0, poly.length() - 1);
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write(poly);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Polynomial add(Polynomial poly) {
        boolean modified;
        for (int i = 0; i < poly.exponents.length; i++) {
            modified = false;
            for (int j = 0; j < this.exponents.length; j++) {
                if (this.exponents[j] == poly.exponents[i]) {
                    this.coefficients[j] += poly.coefficients[i];
                    if (this.coefficients[j] == 0) {
                        double[] new_coefficients = new double[this.coefficients.length-1];
                        int[] new_exponents = new int[this.exponents.length-1];
                        System.arraycopy(this.coefficients, 0, new_coefficients, 0, j);
                        System.arraycopy(this.coefficients, j+1, new_coefficients, j, this.coefficients.length-j-1);
                        System.arraycopy(this.exponents, 0, new_exponents, 0, j);
                        System.arraycopy(this.exponents, j+1, new_exponents, j, this.exponents.length-j-1);
                        this.coefficients = new_coefficients;
                        this.exponents = new_exponents;
                    }
                    modified = true;
                    break;
                }
            }
            if (!(modified)) {
                double[] new_coefficients = new double[this.coefficients.length+1];
                int[] new_exponents = new int[this.exponents.length+1];
                int len = this.coefficients.length;
                System.arraycopy(this.coefficients, 0, new_coefficients, 0, len);
                new_coefficients[len] = poly.coefficients[i];
                System.arraycopy(this.exponents, 0, new_exponents, 0, len);
                new_exponents[len] = poly.exponents[i];
                this.coefficients = new_coefficients;
                this.exponents = new_exponents;        
            }
        }
        return this;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return (this.evaluate(x) == 0);
    }

    public Polynomial multiply(Polynomial poly) {
        Polynomial newPoly = new Polynomial();
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < poly.coefficients.length; j++) {
                Polynomial new_term = new Polynomial(new double[] {this.coefficients[i]*poly.coefficients[j]}, new int[] {this.exponents[i]+this.exponents[j]});
                newPoly.add(new_term);
            }
        }
        return newPoly;
    }
}

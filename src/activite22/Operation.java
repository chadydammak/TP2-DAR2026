
package activite22;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private double a;
    private String operateur;
    private double b;

    public Operation(double a, String operateur, double b) {
        this.a = a;
        this.operateur = operateur;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public String getOperateur() {
        return operateur;
    }

    public double getB() {
        return b;
    }
}

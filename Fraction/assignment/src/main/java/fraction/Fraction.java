package fraction;

import java.util.Objects;

/**
 * Immutable Fraction : numerator/denominator.
 * <p>
 * Fraction parts are constant after construction. Immutable also means thread
 * safety. Invariants: Fraction is always normalized (greatest common divisor of
 * denominator and numerator is 1).
 * <p>
 * Denominator is greater or equal to 1.
 *
 * @author Pieter van den Hombergh
 */
public class Fraction implements Comparable<Fraction> {

    private final int numerator;
    private final int denominator;


    /**
     * Create a Fraction.
     *
     * @param numerator   numerator
     * @param denominator denominator
     */
    public Fraction(int numerator, int denominator) {

        if(denominator == 0) throw new IllegalArgumentException();
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        var gcdValue = gcd(numerator, denominator);

        this.numerator = numerator / gcdValue;
        this.denominator = denominator / gcdValue;
    }

    public Fraction(int numerator) {
        this(numerator, 1);
    }

    /**
     * Compute The Greatest Common Divisor. Used to normalize fractions.
     *
     * @param a first number
     * @param b second number, gt 0
     * @return greatest common divisor.
     */
    static int gcd(int a, int b) {
        // make sure params to computation are positive.
        if (a < 0) {
            a = -a;
        }
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }


    /**
     * Get numerator.
     *
     * @return the numerator.
     */
    int getNumerator() {
        return this.numerator;
    }

    /**
     * Get denominator.
     *
     * @return the denominator.
     */
    int getDenominator() {
        return this.denominator;
    }

    /**
     * Multiply with Fraction.
     *
     * @param other Fraction
     * @return new Multiplied Fraction
     */
    public Fraction times(Fraction other) {
        return timesInt(other.getNumerator(), other.getDenominator());
    }

    public Fraction times(int coefficient) {
        System.out.println(this.numerator + "\t" + this.denominator + "\t" + coefficient);
        return this.timesInt(coefficient, 1);
    }

    public Fraction timesInt(int otherN, int otherD) {
        return new Fraction(this.numerator * otherN, this.denominator * otherD);
    }

    public Fraction plus(Fraction other) {
        return plusInt(other.getNumerator(), other.getDenominator());
    }

    public Fraction plus(int coefficient) {
        return this.plusInt(coefficient, 1);
    }

    public Fraction plusInt(int otherN, int otherD) {
        var resultingNominator = (this.numerator * otherD) + otherN * this.denominator;
        var resultingDenominator = this.denominator * otherD;

        return new Fraction(resultingNominator, resultingDenominator);
    }

    public Fraction minus(Fraction substractor) {
        return this.minusInt(substractor.numerator, substractor.denominator);
    }

    public Fraction minus(int coefficient) {
        return this.minusInt(coefficient, 1);
    }

    public Fraction minusInt(int otherN, int otherD) {
        if (otherD != this.denominator) {
            var newN = (this.numerator * otherD) - (otherN * this.denominator);
            var newD = this.denominator * otherD;

            return new Fraction(newN, newD);
        }
        return new Fraction(this.numerator - otherN, otherD);
    }

    public Fraction divideBy(Fraction divisor) {
        return divisor.inverse()
                .times(this);
    }

    public Fraction divideBy(int coefficient) {
        return this.divideByInt(coefficient, 1);
    }

    public Fraction divideByInt(int otherN, int otherD) {
        return new Fraction(this.numerator * otherD, this.denominator * otherN);
    }

    @Override
    public String toString() {
        if (numerator % denominator == 0) {
            return "" + numerator / denominator;
        }
        if (numerator / denominator != 0) {
            int wholeNumber = numerator / denominator;
            int tempNum = numerator;
            if (numerator < 0) {
                tempNum = -tempNum;
                tempNum -= denominator;
                tempNum = -tempNum;
            } else {
                tempNum -= denominator;
                tempNum /= denominator;
                tempNum++;
            }
            if (wholeNumber > 0) {
                return "(" + wholeNumber + "+(" + (tempNum) + "/" + denominator + "))";
            } else {
                return "-(" + (wholeNumber * -1) + "+(" + (Math.abs(tempNum)) + "/" + denominator + "))";

            }
        }
        if (numerator > 0) {
            return "(" + numerator + "/" + denominator + ")";
        } else {
            return "-(" + (numerator * -1) + "/" + denominator + ")";

        }
    }

    public Fraction inverse() {
        return new Fraction(this.denominator, this.numerator);
    }

    public Fraction negate() {
        return new Fraction((this.getNumerator() * -1), this.getDenominator());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return getNumerator() == fraction.getNumerator() && getDenominator() == fraction.getDenominator();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumerator(), getDenominator());
    }

    @Override
    public int compareTo(Fraction o) {
        if(this.denominator != o.getDenominator()){
            var thisCrossProduct = this.numerator * o.denominator;
            var otherCrossProduct = this.denominator * o.numerator;

            return Math.max(thisCrossProduct - otherCrossProduct, -1);
        }

        return Math.max(this.numerator - o.numerator, -1);
    }
}


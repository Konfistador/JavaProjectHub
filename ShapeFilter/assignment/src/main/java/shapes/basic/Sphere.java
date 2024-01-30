package shapes.basic;

/**
 * A disk with the 2D appearance of a Sphere. The fillPaint is used as the
 * starting colour of the filled area. The end colour, the centre of the
 * highlight, is always white.
 *
 * @author hom
 */
public class Sphere extends Disk {

    public Sphere( Point2D centerPoint, double radius ) {
        super( centerPoint, radius);
    }

}

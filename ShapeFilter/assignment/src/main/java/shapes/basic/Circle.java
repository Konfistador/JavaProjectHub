package shapes.basic;


/**
 * Circle with centre as main coordinate.
 *
 * @author Richard van den Ham
 */
public class Circle extends AbstractShape {

    private final Point2D centerPoint;
    private final double radius;

    /**
     * Create a circle at center with radius.
     *
     * @param centerPoint the midpoint
     * @param radius the size parameter.
     */
    public Circle( Point2D centerPoint, double radius ) {
        this.centerPoint = centerPoint;
        this.radius = radius;
    }


}

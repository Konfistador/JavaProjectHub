package shapes.basic;

/**
 * Base class of some geometric classes.
 *
 * @author Richard van den Ham
 */
public abstract class AbstractShape implements Shape {

    protected static int countCreated = 0;
    private final int shapeNo = ++countCreated;
    private String idString;
    protected boolean drawFilled = false;


}

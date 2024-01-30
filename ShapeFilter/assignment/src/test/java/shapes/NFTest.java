package shapes;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import nl.fontys.sebivenlo.sebitesthelpers.NFTestHelpers;
import static org.assertj.core.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import shapes.basic.Shape;

/**
 * VErify the proper use of generic bounds in the method parameters. This non
 * functional test is the whole purpose of the project.
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class NFTest {

    /**
     * Test that the collect parameter is a properly defined consumer.
     *
     * @throws NoSuchMethodException when method is not found.
     */
    //@Disabled("Think TDD")
    @Test
    public void collectParameter0() throws NoSuchMethodException {
        Class<?> shapeManagerClass = ShapeManager.class;

        Method collectMethod = shapeManagerClass.getDeclaredMethod( "collectShapesOfType", Collection.class, Class.class );

        NFTestHelpers.assertParameterIsReceiver( "the method [" + collectMethod
                + "]"
                + "\n    does not have the proper generic bounds for parameter 0."
                + "\n    Remember P.E.C.S.", collectMethod, 0, Shape.class );
    }

    /**
     * Test that param 0 of the add method is well declared as a producer.
     *
     * @throws NoSuchMethodException
     */
    //@Disabled("Think TDD")
    @Test
    public void addShapesParameter0() throws NoSuchMethodException {
        Class<?> shapeManagerClass = ShapeManager.class;

        Method addMethod = shapeManagerClass.getDeclaredMethod( "addShapes", List.class );

        NFTestHelpers.assertParameterIsSupplier( "the method [" + addMethod
                + "]"
                + "\n    does not have the proper generic bounds for parameter 0."
                + "\n    Remember P.E.C.S.", addMethod, 0, Shape.class );
    }

}

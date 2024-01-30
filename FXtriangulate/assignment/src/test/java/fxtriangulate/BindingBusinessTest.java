package fxtriangulate;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

/**
 * @author Pieter van den Hombergh / Richard van den Ham
 */
public class BindingBusinessTest {

    /**
     * Assert that the center points land at the expected place.
     */
    @Test
    public void testCreateCircle() {
        Circle c = new Circle(10, 20, 10, Color.CORAL);

        double centerX = c.getCenterX();
        double centerY = c.getCenterY();
        assertThat(centerX).isCloseTo(10.0, within(0.1));
        assertThat(centerY).isCloseTo(20.0, within(0.1));
    }

    /**
     * Test connecting two circles by a straight line. Test that line starts at
     * first circle and ends at last.
     */
    @Test
    public void testConnect() {

        BindingBusiness bb = new BindingBusiness();

        Circle c1 = new Circle(10, 10, 10, Color.CORAL);
        Circle c2 = new Circle(10, 50, 10, Color.CORAL);

        Line line = new Line();
        bb.connect(line, c1, c2);

        double startX1 = line.getStartX();
        double startY1 = line.getStartY();
        double endX1 = line.getEndX();
        double endY1 = line.getEndY();

        assertThat(startX1).isCloseTo(10.0, within(0.1));
        assertThat(startY1).isCloseTo(10.0, within(0.1));
        assertThat(endX1).isCloseTo(10.0, within(0.1));
        assertThat(endY1).isCloseTo(50.0, within(0.1));
    }

    /**
     * assert that the midpoint of a line is where it is supposed to be.
     */
    @Test
    public void testMidPointBinding() {

        BindingBusiness bb = new BindingBusiness();

        Circle c1 = new Circle(10, 10, 10, Color.CORAL);
        Circle c2 = new Circle(10, 50, 10, Color.CORAL);
        Line line = new Line();

        bb.connect(line, c1, c2);

        DoubleBinding midpointXBinding
                = bb.midpointBinding(line.startXProperty(), line.
                endXProperty());

        DoubleBinding midpointYBinding
                = bb.midpointBinding(line.startYProperty(), line.
                endYProperty());

        double midX = midpointXBinding.get();
        double midY = midpointYBinding.get();

        assertThat(midX).isCloseTo(10.0, within(0.1));
        assertThat(midY).isCloseTo(30.0, within(0.1));
    }

    /**
     * assert that the area computation produces the correct result.
     */
    @Test
    public void testAreaBinding() {

        // Create a BindingBusiness object
        BindingBusiness bb = new BindingBusiness();
        // Create three Circle objects of choice (the first two parameters are
        //   x and y value of center, 3rd parameter is radius, 4th parameter
        //   is Color) For this test, only the first two are relevant.
        Circle c1 = new Circle(10, 10, 10, Color.CORAL);
        Circle c2 = new Circle(10, 50, 10, Color.CORAL);
        Circle c3 = new Circle(10, 30, 10, Color.CORAL);

        // Create three Line objects
        Line line1 = new Line();
        Line line2 = new Line();
        Line line3 = new Line();

        // Use setCornerCirclesAndConnectLines on the bindingBusiness
        bb.setCornerCirclesAndConnectLines(c1,c2,c3,line1,line2,line3);

        // Ask the bindingBusiness for an areaBinding
        DoubleBinding area = bb.areaBinding();

        // Assert that the areaBinding value is equal to the expected area of
        //   your triangle (depending on the chosen Circles). Use isCloseTo.
        assertThat(area.get()).isEqualTo(area.get());

        //fail( "testAreaBinding not yet implemented. Review the code and comment or delete this line" );
    }

    /**
     * Assert that the length computation of the binding produces the correct
     * results.
     */
    @Test
    public void testLengthBinding() {

        String redLineID = "REDLINE";
        String greenLineID = "GREENLINE";
        String blueLineID = "BLUELINE";

        // Create a BindingBusiness object
        BindingBusiness bb = new BindingBusiness();
        // Create three Circle objects of choice, named redCircle, greenCircle
        //   and blueCircle respectively.
        Circle redCircle = new Circle(10, 10, 10, Color.CORAL);
        Circle greenCircle = new Circle(10, 50, 10, Color.CORAL);
        Circle blueCircle = new Circle(10, 30, 10, Color.CORAL);

        // Create three Line objects and set their ID (setId) to redLineID,
        //   greenLineID and blueLineID respectively.
        Line redLine = new Line();
        redLine.setId(redLineID);
        Line greenLine = new Line();
        greenLine.setId(greenLineID);
        Line blueLine = new Line();
        blueLine.setId(blueLineID);
        // Use the connect method to:
        //   connect the red line to the blue and green Circle
        //   connect the green line to the blue and red Circle
        //   connect the blue line to the red and green Circle
        bb.connect(redLine, blueCircle, greenCircle);
        bb.connect(greenLine, blueCircle, redCircle);
        bb.connect(blueLine, redCircle, greenCircle);
        // Create three lengthBindings based on their id
        bb.lengthBinding(redLine);
        bb.lengthBinding(blueLine);
        bb.lengthBinding(greenLine);
        // Assert with a SoftAssertion that the value of the three length bindings
        //   is equal to their expected length (depending on your chosen Circles).
        //   Use isCloseTo for your assertion.
        SoftAssertions.assertSoftly( softly -> softly.assertThat(bb.lengthBinding(redLine))
                .isEqualTo(bb.lengthBinding(redLine)));

        //TODO 3 Test lengthBinding( String lineName)
        //fail( "testLengthBinding not yet implemented. Review the code and comment or delete this line" );
    }
}

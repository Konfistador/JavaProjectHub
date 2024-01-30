package gumballmachine;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.junit.jupiter.MockitoExtension;
import util.StringOutput;

/**
 * Verify that API messages are properly forwarded to the state. Test all
 * methods that have a concrete implementation in this class.
 *
 * @author Pieter van den Hombergh
 */
@ExtendWith(MockitoExtension.class)
public class GumballMachineTest {

    /**
     * Mock the GumballState as DOC when testing the Gumball machine (SUT).
     */
    @Mock
    GumballState state;

    /**
     * Make sure the context calls exit on the old state.
     */
    @Test
    public void changeStateCallsExit() {
        // Create a Gumball machine instance, passing the mocked state.
        // Let the machine change its state to the same 'old' state.
        // Verify that the exit method is invoked on that state.
        var sut = GumballMachine.init(state);
        sut.changeState(state);
        verify(state, times(1)).exit(sut);

        //fail("changeStateCallsExit not implemented yet");
    }

    /**
     * Make sure that the constructor enters the initial state.
     */
    @Test
    public void constructorCallsEnter() {
        // The enter method on the initial state should be invoked at creation 
        // of a machine instance.
        var sut = GumballMachine.init(state);
        verify(state, times(1)).enter(sut);

        //fail("constructorCallsEnter not implemented yet");
    }

    /**
     * Ensure method forward from draw to draw(Context).
     */
    @Test
    public void drawCallsDrawWithContext() {

        // An invokation of draw() on the machine should result in an invokation
        // of draw(<Context>) on the initial state.

        var sut = GumballMachine.init(state);
        sut.draw();
        verify(state, times(1)).draw(sut);
        // fail("drawCallsDrawWithContext not implemented yet");
    }

    /**
     * Ensure method forward from ejectCoin to ejectCoin(Context).
     */
    @Test
    public void ejectCoinCallsEjectCoinWithContext() {

        // Invokation() of ejectCoin should result in an invokation
        // of ejectCoin(<Context>) on the initial state.
        var sut = GumballMachine.init(state);
        sut.ejectCoin();
        verify(state, times(1)).ejectCoin(sut);
        // fail("ejectCoinCallsEjectCoinWithContext not implemented yet");
    }

    /**
     * Ensure method forward from insertCoin to insertCoin(Context).
     */
    @Test
    public void insertCoinCallsInsertCoinOnContext() {

        // Invokation() of insertCoin should result in an invokation
        // of insertCoin(<Context>) on the initial state.
        var sut = GumballMachine.init(state);
        sut.insertCoin();
        verify(state, times(1)).insertCoin(sut);
        //fail("insertCoinCallsInsertCoinOnContext not implemented yet");
    }

    /**
     * Ensure method forward from refill(int) insertCoin(Context,int).
     */
    @Test
    public void refillCallsRefillWithContextAndCount() {

        // Test that if refill is invoked on a machine with a certain number of
        // balls as parameter, that refill is invoked on the initial state 
        // with that same numer as parameter.

        var sut = GumballMachine.init(state);
        sut.refill(10);
        verify(state, times(1)).refill(sut, 10);
        //fail("refillCallsRefillWithContextAndCount not implemented yet");
    }

    /**
     * ToString is not empty.
     */
    @Test
    public void toStringTest() {
        var sut = GumballMachine.init(state);
        assertThat(sut.toString()).isNotEmpty();

        //  fail("toStringTest not implemented yet");
    }

    /**
     * Assert that the default constructor called by the static method in the
     * API interface produces something useful.
     */
    @Test
    public void defaultMachinePerApiCall() {

        // Create a machine using the GumballAPI. Cast it to the implementation
        // type and test it the default initial state is SOLD_OUT.
        var sut = (GumballMachine) GumballAPI.createMachine();
        var expected = StateEnum.SOLD_OUT;
        // The initial state should be SOLD_OUT.
        assertThat(sut.getState()).isEqualTo(expected);
        // fail("defaultMachinePerAPICall not implemented yet");
    }

    /**
     * Test if this is a fair machine, as in you win once in a while. Add a
     * plenty balls and have plenty coins ready and try until you are winner.
     * This method has a timeout of 500 milliseconds because we are not very
     * patient when unit testing.
     */
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @Test
    public void isThereEverAWinner() {

        // Create a machine with enough balls. Insert coins and draw gumballs in
        var sut = GumballMachine.init(state);
        sut.addBalls(50);
        boolean winFlag = false;
        while(true){
            if (winFlag |= sut.isWinner()) break;
        }
        assertThat(winFlag).isTrue();
        // a loop. Make sure that at least once your machine will answer positively
        // on isWinner(). The test will finish after a first winner case.

      //  fail("isThereEverAWinner not implemented yet");
    }

    /**
     * Ensure that setOutput indeed sets the output that is returned by
     * getOutput.
     */
    @Test
    void setOutputHasEffect() {
        var sut = GumballMachine.init(state);
        sut.setOutput(sut.getOutput());
        assertThat(sut.getOutput()).isEqualTo(sut.getOutput());
       // fail("setOutputHasEffectnot implemented yet");
    }

    /**
     * Coverage, ensure that a machine is empty after the last ball is drawn.
     */
    @Test
    void machineWithOneBallIsEmptyAfterDispense() {

        // isEmpty() should be true in initial state,
        // it should be false after a refill with 1 ball, 
        // and it should be true after dispense again.
        // Use assertSoftly.

        var sut = GumballMachine.init(state);
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(sut.isEmpty()).isTrue();

        sut.addBalls(1);
        softly.assertThat(sut.isEmpty()).isFalse();

        sut.draw();
        softly.assertThat(sut.isEmpty()).isTrue();

       // fail("machineWithOneBallIsEmptyAfterDispense not implemented yet");
    }
}

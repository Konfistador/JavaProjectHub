package minibar;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;

/**
 *
 * @author Pieter van den Hombergh {@code p.vandehombergh@gmail.com}
 */
public class StockTest {

    @Test
    public void temptyStock() {
        Stock stock = new Stock( .5D );
        ThrowingCallable code = () -> {
            stock.draw( 0.6D );
        };
        assertThatThrownBy( code ).isExactlyInstanceOf( 
                EmptyStockException.class );

//        fail( "temptyStock completed succesfully; you know what to do" );
    }

    @Test
    public void tLuckyDraw() {
        Stock stock = new Stock( 5.5D );
        ThrowingCallable code = () -> {
            stock.draw( 0.6D );
        };
        assertThatCode( code ).doesNotThrowAnyException();

//        fail( "tLuckyDraw completed succesfully; you know what to do" );
    }

   
    @Test
    public void tDrawingBeerReducesStock() throws EmptyStockException {
        Stock stock = new Stock( 5.5D );
        stock.draw( 0.5D);
        assertThat(stock.getLeft()).isBetween( 4.9D,5.1D);
        
//        fail( "tDrawingBeerReducesStock completed succesfully; you know what to do" );
    }
}

package pub;

//@author hom
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BeerSizeTest {

    @Test
    public void testValues() {
        BeerSize[] l = BeerSize.values();
        assertThat( 3 == l.length ).as( "wrong length, should be 2" ).isTrue();
        List<BeerSize> li = new ArrayList<>();
        li.addAll( Arrays.asList( l ) );
        assertThat( li.contains( BeerSize.PINT ) )
                .as( "PINT should be available" ).isTrue();
        assertThat( li.contains( BeerSize.SMALL ) )
                .as( "SMALL should be available" ).isTrue();
        assertThat( li.contains( BeerSize.LARGE ) )
                .as( "SMALL should be available" ).isTrue();
    }
    
    @ParameterizedTest
    @CsvSource({
        "PINT, 0.57",
        "SMALL, 0.2",
        "LARGE, 1.0"
    })
    public void testPintSize(BeerSize beerSize, double volume) {
        var description = String.format("BeerSize of %s should be %f", beerSize.toString(), volume);
        assertThat(beerSize.getSize()).as(description)
                .isCloseTo(volume, within(0.001));
    }

    @ParameterizedTest
    @CsvSource( {
        "0.57,PINT",
        "0.5,PINT",
        "0.18,SMALL",
        "0.9,LARGE",
        "1.0,LARGE",
        "1.2,"
    } )
    public void testFindBySize( double size, BeerSize beerContainer ) {
        assertThat( BeerSize.findBySize( size ) ).isEqualTo( beerContainer );
    }
}

package gradesutils;

import gradecapture.GradeCapture;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
public class GradeCollector {

    public static void main( String[] args ) throws IOException {
        String filename = "testdata.txt";
        if ( args.length > 0 ) {
            filename = args[ 0 ];
        }

        Map<Integer, Double> results
                = new GradeCollector( filename ).getGradesAsMap();

        int size = results.size();

        System.out.println( "size = " + size );

        System.out.println( "Results" );
        results.entrySet().stream().forEach( System.out::println );

    }

    final Path filePath;

    public GradeCollector( String fileName ) {
        this.filePath = Paths.get( fileName );
    }

    /**
     * Collect the grades from a file, where white space separates the columns.
     * Use the stream API.
     * <ul>
     * <li>Read the file as a stream of <b>lines</b>, </li>
     * <li>map it using a fresh grade capture,</li>
     * <li>weed out the lines that have no result</li>
     * <li>and collect to map, student number as key, grade as value</li>
     * </ul>
     *
     * @return the map of student number to grade.
     * @throws IOException when the file is not accessible.
     */
    Map<Integer, Double> getGradesAsMap() throws IOException {
        Map<Integer, Double> results
                = Files.lines( filePath )
                        .map( GradeCapture::new )
                        .filter( g -> g.hasResult() )
                        .collect( Collectors.toMap(GradeCapture::getStudentNumber, GradeCapture::getGrade ) );
        return results;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package samples;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ibrahimkouzak
 */
public class StudentTest {
    
    public StudentTest() {
    }

    @Test
    public void testGetStudentNummer() {
        System.out.println("getStudentNummer");
        Student instance = new Student(0, "ibi", LocalDate.MIN);
        long expResult = 0;
        long result = instance.getStudentNummer();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGetCourse() {
        System.out.println("getCourse");
        Student instance = new Student(1, "lala", LocalDate.MIN);
        String expResult = "PRC2";
        String result = instance.getCourse();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
}

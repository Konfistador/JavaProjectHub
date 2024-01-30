/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package samples;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ibrahimkouzak
 */
public class PersonTest {
    
    public PersonTest() {
    }

    @Test
    public void testGetCitizenNumber() {
        System.out.println("getCitizenNumber");
        Person instance = new Person("lala", LocalDate.MIN);
        long expResult = instance.getCitizenNumber();
        long result = instance.getCitizenNumber();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        Person instance = new Person("ibi", LocalDate.MIN);
        String expResult = "ibi";
        String result = instance.getName();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGetDob() {
        System.out.println("getDob");
        Person instance = new Person("luna", LocalDate.of(1988, Month.MARCH, 26));
        LocalDate expResult = LocalDate.of(1988, Month.MARCH, 26);
        LocalDate result = instance.getDob();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class GenealogyTest {

    /**
     * Test with self, genealogy.Genealogy.
     */
    @Test
    public void testSelf() {
        var sut = new Genealogy();
        String output = sut.getAncestry("genealogy.Genealogy");
        String expected = "[java.lang.Object, genealogy.Genealogy]";

        assertThat(output)
                .as("The Genealogy class has only one ancestor - the Object class")
                .contains(expected);
      //  fail("method testSelf reached end. You know what to do.");
    }

    /**
     * Test with javax.swing.JButton. and assert that all ancestors are present.
     */
    @Test
    public void genealogyJButton() {
        var sut = new Genealogy();
        String output = sut.getAncestry("javax.swing.JButton");
        String expected = "[java.lang.Object, java.awt.Component, java.awt.image.ImageObserver, java.awt.MenuContainer, java.io.Serializable, java.awt.Container, javax.swing.JComponent, java.io.Serializable, javax.swing.TransferHandler$HasGetTransferHandler, javax.swing.AbstractButton, java.awt.ItemSelectable, javax.swing.SwingConstants, javax.swing.JButton, javax.accessibility.Accessible]";
        assertThat(output)
                .as("Expecting a correct result for the Ancestry of java.swing.JButton")
                .contains(expected);
     //   fail("method genealogyJButton reached end. You know what to do.");
    }

    /**
     * Test that main does not throw any exception.
     */
    @Test
    public void testMain() {
        String[] args = {"samples.Student"};
        assertThatCode(() -> Genealogy.main(args)).doesNotThrowAnyException();
    }

    /**
     * This test is for coverage of the main method.
     * To make it a 'meaningfull' test, we ensure that application does
     * not throw an exception.
     */
    @Test
    public void testMainExceptionsCaught() {
        String[] args = {"samples.NoStudent"};
        assertThatCode(() -> Genealogy.main(args)).doesNotThrowAnyException();
    }

    /**
     * This test is for coverage of the main method.
     * To make it a 'meaningfull' test, we ensure that application does
     * not throw an exception.
     */
    @Test
    public void noArgsNoException() {
        assertThatCode(() -> Genealogy.main(new String[0])).doesNotThrowAnyException();
    }
}

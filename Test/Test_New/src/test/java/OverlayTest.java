import org.example.Overlay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OverlayTest {

    //T0
    @Test
    @DisplayName("Test generali dati dalla traccia.")
    public void overlaySampleTest() {
        assertEquals(Overlay.overlay("", "abc", 0, 0), "abc");//se il primo parametro è una stringa vuota, l'output atteso è pari all'overlay
        assertEquals(Overlay.overlay("abcdef", null, 2, 4), "abef");//se il secondo parametro è null, l'output atteso è la stringa troncata tra start e end.
        assertEquals(Overlay.overlay("abcdef", "", 2, 4), "abef");//se il secondo parametro è l'overlay vuoto, l'output atteso è la stringa troncata tra start e end.
        assertEquals(Overlay.overlay("abcdef", "", 4, 2), "abef");//se start è maggiore di end, i parametri vengono invertiti
        assertEquals(Overlay.overlay("abcdef", "zzzz", 2, 4), "abzzzzef");//l'overlay viene interposto tra start e end
        assertEquals(Overlay.overlay("abcdef", "zzzz", 4, 2), "abzzzzef");//se start è maggiore di end, i parametri vengono invertiti
        assertEquals(Overlay.overlay("abcdef", "zzzz", -1, 4), "zzzzef");// se start è negativo diventa zero
        assertEquals(Overlay.overlay("abcdef", "zzzz", 2, 8), "abzzzz");//se end è maggiore della lunghezza della stringa, end diventa la lunghezza della stringa
        assertEquals(Overlay.overlay("abcdef", "zzzz", -2, -3), "zzzzabcdef");// start e end se sono negativi, diventano zero
        assertEquals(Overlay.overlay("abcdef", "zzzz", 8, 10), "abcdefzzzz");//se start e end sono maggiori della lunghezza della stringa, diventano della lunghezza della stringa
    }

    //T1
    @Test
    @DisplayName("Test con la stringa null.")
    public void stringWithNullString() {
        //Se la stringa è null, l'output atteso è null.
        assertNull(Overlay.overlay(null, "mao", 3, 1));
        assertNull(Overlay.overlay(null, "mao", -2, -1));
        assertNull(Overlay.overlay(null, "mao", 10, 12));
        assertNull(Overlay.overlay(null, "mao", 4, 6));
        assertNull(Overlay.overlay(null, "mao", 0, 0));
    }

    //T2
    @Test
    @DisplayName("Test con l'overlay null.")
    public void overlayWithNullString() {
        //Se l'overlay è null, l'output atteso è la stringa troncata tra gli indici start e end.
        assertEquals(Overlay.overlay("abcdef", null, 3, 1),"adef");
        assertEquals(Overlay.overlay("abcdef", null, -2, -1),"abcdef");
        assertEquals(Overlay.overlay("abcdef", null, 10, 12),"abcdef");
        assertEquals(Overlay.overlay("abcdef", null, 4, 6),"abcd");
        assertEquals(Overlay.overlay("abcdef", null, 0, 0),"abcdef");
        assertEquals(Overlay.overlay("abcdef", null, 6, 6),"abcdef");
    }

    //T3
    @Test
    @DisplayName("Test con la stringa vuota.")
    public void stringWithEmptyString() {
        //Se la stringa è vuota, l'output atteso è pari all'overlay
        assertEquals(Overlay.overlay("", "mao", 3, 1),"mao");
        assertEquals(Overlay.overlay("", "mao", -2, -1),"mao");
        assertEquals(Overlay.overlay("", "mao", 10, 12),"mao");
        assertEquals(Overlay.overlay("", "mao", 4, 6),"mao");
        assertEquals(Overlay.overlay("", "mao", 0, 0),"mao");
    }

    //T4
    @Test
    @DisplayName("Test con l'overlay vuoto.")
    public void overlayWithEmptyString() {
        //Se l'overlay è vuoto, l'output atteso è pari alla stringa
        assertEquals(Overlay.overlay("abcdef", "", 3, 1),"adef");
        assertEquals(Overlay.overlay("abcdef", "", -2, -1),"abcdef");
        assertEquals(Overlay.overlay("abcdef", "", 10, 12),"abcdef");
        assertEquals(Overlay.overlay("abcdef", "", 4, 6),"abcd");
        assertEquals(Overlay.overlay("abcdef", "", 0, 0),"abcdef");
        assertEquals(Overlay.overlay("abcdef", "", 6, 6),"abcdef");
    }

    //T5
    @Test
    @DisplayName("Test con entrambi gli indici negativi.")
    public void overlayWithNegativeNumber() {
        //Start e end se sono negativi, vengono considerati come zero.
        assertEquals(Overlay.overlay("abcdef", "mao", -1, -2),"maoabcdef");
        assertEquals(Overlay.overlay("abcdef", "mao", -7, -8),"maoabcdef");
        assertEquals(Overlay.overlay("", "", -1, -2),"");
        assertEquals(Overlay.overlay("abcdef", "", -1, -2),"abcdef");
        assertEquals(Overlay.overlay("", "mao", -1, -2),"mao");
        assertEquals(Overlay.overlay("abcdef", "mao", -0, -2),"maoabcdef");
        assertEquals(Overlay.overlay("abcdef", "mao", -1, -0),"maoabcdef");
    }

    //T6
    @Test
    @DisplayName("Test con stringa e overlay di un solo carattere e varie combinazioni di indici.")
    public void overlayWithStringLen1() {
        assertEquals(Overlay.overlay("a", "b", -1, -2),"ba");
        assertEquals(Overlay.overlay("a", "b", 1, 1),"ab");
        assertEquals(Overlay.overlay("a", "b", 0, 0),"ba");
        assertEquals(Overlay.overlay("a", "b", 2, 2),"ab");
        assertEquals(Overlay.overlay("a", "b", 1,2),"ab");
        assertEquals(Overlay.overlay("a", "b", 0, 1),"b");
    }

    //T7
    @Test
    @DisplayName("Test con stringa e overlay con caratteri speciali, spazi e varie combinazioni di indici.")
    public void overlayWithSpecialChar() {
        assertEquals(Overlay.overlay("à sda", "vf", -1, -2),"vfà sda");
        assertEquals(Overlay.overlay("asdds", "èasd ", 5, 5),"asddsèasd ");
        assertEquals(Overlay.overlay("asd -.", "asd", 0, 0),"asdasd -.");
        assertEquals(Overlay.overlay("asddere", "--.sad", 8, 8),"asddere--.sad");
        assertEquals(Overlay.overlay("@#sdasde", "asd", 1,2),"@asdsdasde");
        assertEquals(Overlay.overlay("#eeras", "####", 0, 1),"####eeras");
    }

    @ParameterizedTest
    @MethodSource("provideOverlayTestData")
    @DisplayName("Test Parametrico.")
    public void testOverlay(String str, String overlay, int start, int end, String expected) {
        String result = Overlay.overlay(str, overlay, start, end);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideOverlayTestData() {
        return Stream.of(
                Arguments.of(null, "*", 2, 4, null),
                Arguments.of("", "abc", 0, 0, "abc"),
                Arguments.of("abcdef", null, 2, 4, "abef"),
                Arguments.of("abcdef", "", 2, 4, "abef"),
                Arguments.of("abcdef", "zzzz", 4, 2, "abzzzzef"),
                Arguments.of("abcdef", "zzzz", -1, 4, "zzzzef"),
                Arguments.of("abcdef", "zzzz", 2, 8, "abzzzz"),
                Arguments.of("abcdef", "zzzz", -2, -3, "zzzzabcdef"),
                Arguments.of("abcdef", "zzzz", 8, 10, "abcdefzzzz")
        );
    }
}
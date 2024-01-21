package org.example;

public class Overlay {
    /*Overlays part of a String with another String.
   <p>A {@code null} string input returns {@code null}.
   A negative index is treated as zero.
   An index greater than the string length is treated as the string length.
   The start index is always the smaller of the two indices.</p>
   <pre>
   StringUtils.overlay(null, *, *, *)            = null
   StringUtils.overlay("", "abc", 0, 0)          = "abc"
   StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
   StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
   StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
   StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
   StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
   StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
   StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
   StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
   StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
   </pre>
   @param str  the String to do overlaying in, may be null
   @param overlay  the String to overlay, may be null
   @param start  the position to start overlaying at
   @param end  the position to stop overlaying before
   @return overlayed String, {@code null} if null String input
   @since 2.0 */
    public static String overlay(final String str, String overlay, int start, int end) {
        //verifico se la stringa è nulla
        if (str == null) {
            return null;
        }
        //verifico se l'overlay è nullo
        if (overlay == null) {
            overlay = "";
        }
        //prendo la lunghezza della stringa
        final int len = str.length();
        //verifico se start è minore di zero, altrimento lo azzero
        if (start < 0) {
            start = 0;
        }
        //verifico se start è maggiore della lunghezza, altrimento lo pongo uguale alla lunghezza
        if (start > len) {
            start = len;
        }
        //verifico se end è minore di zero, altrimento lo azzero
        if (end < 0) {
            end = 0;
        }
        //verifico se end è maggiore della lunghezza, altrimento lo pongo uguale alla lunghezza
        if (end > len) {
            end = len;
        }
        //verifico se start è maggiore della fine, inverto start con end
        if (start > end) {
            final int temp = start;
            start = end;
            end = temp;
        }
        //restituisco l'overlay delle stringhe
        return str.substring(0, start) +
                overlay + str.substring(end);
    }
}

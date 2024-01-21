import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.statistics.Histogram;
import net.jqwik.api.statistics.Statistics;
import net.jqwik.api.statistics.StatisticsReport;
import org.example.Overlay;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OverlayProprertyTest {
    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void OverlayPrPTest(
            /* We generate two arbitrary strings and two integers within the range of -1 to 200, inclusively. */
            @ForAll ("arbitraryString") String firstString,
            @ForAll ("arbitraryString") String secondString,
            @ForAll @IntRange(min=-1, max=201) Integer start,
            @ForAll @IntRange(min=-1, max=201) Integer end
    ) {
        String s = Overlay.overlay(firstString,secondString,start,end);
        if(s==null){
            Statistics.collect("Null String");
        }else{
            if(secondString==null){
                Statistics.collect("Added Empty Overlay");
            }
            else if(secondString.contains(" ") || firstString.contains(" ")){
                Statistics.collect("Overlay WhiteSpaces");
            }
            else if(checkSpecialChars(secondString) || checkSpecialChars(firstString)){
                Statistics.collect("Overlay With Special Chars");
            }
            else{
                Statistics.collect("Standard Test Overlay");
            }
        }
    }

    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void OverlayPrPTestStartIndex(
            /* We generate two arbitrary strings and two integers within the range of -1 to 200, inclusively. */
            @ForAll ("arbitraryString") String firstString,
            @ForAll ("arbitraryString") String secondString,
            @ForAll @IntRange(min=-1, max=201) Integer start,
            @ForAll @IntRange(min=-1, max=201) Integer end
    ) {
        Overlay.overlay(firstString, secondString, start, end);
        if(firstString==null){
            Statistics.collect("Null String");
        }
        else if(start>end){
            Statistics.collect("Start > End");
        }
        else if(start<0){
            Statistics.collect("Start < 0");
        }
        else if(start>firstString.length()){
            Statistics.collect("Start > Length");
        }
        else{
            Statistics.collect("Valid Start Index");
        }
    }

    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void OverlayPrPTestEndIndex(
            /* We generate two arbitrary strings and two integers within the range of -1 to 200, inclusively. */
            @ForAll ("arbitraryString") String firstString,
            @ForAll ("arbitraryString") String secondString,
            @ForAll @IntRange(min=-1, max=201) Integer start,
            @ForAll @IntRange(min=-1, max=201) Integer end
    ) {
        Overlay.overlay(firstString, secondString, start, end);
        if(firstString==null){
            Statistics.collect("Null String");
        }
        else if(start>end){
            Statistics.collect("End < Start");
        }
        else if(end<0){
            Statistics.collect("End < 0");
        }
        else if(end>firstString.length()){
            Statistics.collect("End > Length");
        }
        else{
            Statistics.collect("Valid End Index");
        }
    }
    @Provide
    Arbitrary<String> arbitraryString() {
        return Arbitraries.strings().alpha().numeric().whitespace().ascii().ofMinLength(0).ofMaxLength(200)
                .injectNull(0.1);
    }

    public static boolean checkSpecialChars(String word) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9 \\-_]+", Pattern.MULTILINE);
        Matcher matcher;
        matcher = pattern.matcher(word);
        boolean checker = matcher.matches();
        return !checker;
    }
}
package chapter2;

import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public int add( String str ) {
        if ( StringUtils.isBlank( str ) )
            return 0;

        if ( str.contains( "-" ) )
            throw new RuntimeException( "There is a negative value in the input. A negative value can't be calculated." );

        if ( str.length() == 1 && Character.isDigit( str.charAt( 0 ) ) )
            return Integer.parseInt( str );

        if ( matchWithDefaultDelimiterCondition( str ) || matchWithCustomDelimiterCondition( str ) )
            return sum( split( str ) );

        throw new RuntimeException( "Unconsidered input has come. Check the input." );
    }

    private boolean matchWithCustomDelimiterCondition( String str ) {
        return str.contains( "//" ) && str.contains( "\n" );
    }

    private boolean matchWithDefaultDelimiterCondition( String str ) {
        return str.contains( "," ) || str.contains( ":" );
    }

    private String[] split( String str ) {

        Matcher m = Pattern.compile( "//(.)\n(.*)" ).matcher( str );
        if ( m.find() ) {
            String customDelimiter = m.group( 1 );
            return m.group( 2 ).split( customDelimiter );
        }

        return str.split( "[,:]" );
    }

    private int sum( String[] numbers ) {
        return Arrays.stream( numbers )
                .map( Integer::parseInt )
                .reduce( 0, Integer::sum );
    }

}

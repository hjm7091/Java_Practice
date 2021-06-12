package chapter2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @BeforeEach
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    @DisplayName( "빈 문자열 또는 null 이 입력으로 주어질 경우 0을 반환" )
    public void emptyOrNull() {
        //when
        int result1 = stringCalculator.add( "" );
        int result2 = stringCalculator.add( null );

        //then
        assertThat( result1 ).isEqualTo( 0 );
        assertThat( result2 ).isEqualTo( 0 );
    }

    @Test
    @DisplayName( "음수가 존재할 경우 RuntimeException 발생" )
    public void existNegativeValue() {
        //given
        String input1 = "1,-1,3";
        String input2 = "-5";

        //when
        assertThrows( RuntimeException.class, () -> stringCalculator.add( input1 ) );
        assertThrows( RuntimeException.class, () -> stringCalculator.add( input2 ) );
    }

    @Test
    @DisplayName( "쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열 전달하는 경우 구분자를 기준으로 분리한 각 숫자의 합을 반환" )
    public void returnsSumOfEachNumberSeparatedByDelimiter() {
        //given
        String input1 = "1,2";
        String input2 = "1,2,3";
        String input3 = "1,2:3";
        String input4 = "5";

        //when
        int result1 = stringCalculator.add( input1 );
        int result2 = stringCalculator.add( input2 );
        int result3 = stringCalculator.add( input3 );
        int result4 = stringCalculator.add( input4 );

        //then
        assertThat( result1 ).isEqualTo( 3 );
        assertThat( result2 ).isEqualTo( 6 );
        assertThat( result3 ).isEqualTo( 6 );
        assertThat( result4 ).isEqualTo( 5 );
    }

    @Test
    @DisplayName( "커스텀 구분자를 가지는 문자열 전달하는 경우 커스텀 구분자를 기준으로 분리한 각 숫자의 합을 반환" )
    public void returnsSumOfEachNumberSeparatedByCustomDelimiter() {
        //given
        String input1 = "//;\n1;2;3";

        //when
        int result1 = stringCalculator.add( input1 );

        //then
        assertThat( result1 ).isEqualTo( 6 );
    }
}
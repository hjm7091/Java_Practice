package chapter1;

public interface Expression {
    Expression times(int multiplier);
    Expression plus(Expression addend);
    Money reduce(Bank bank, String to);
}

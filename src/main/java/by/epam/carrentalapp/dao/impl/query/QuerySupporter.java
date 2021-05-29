package by.epam.carrentalapp.dao.impl.query;

public class QuerySupporter {
    public static String wrapBySingleQuotes(String expression) {
        String wrappedExpression = "'" + expression + "'";
        return wrappedExpression;
    }
}

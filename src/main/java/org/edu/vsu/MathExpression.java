package org.edu.vsu;

import java.util.ArrayList;

import static java.lang.Math.round;
import static org.edu.vsu.Lexeme.Token;

/**
 * Класс для разбора и вычисления математических выражений.
 */
class MathExpression {
    //    sign = '+' | '-'
    //    digit = '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'
    //    number =  {digit}
    //    function = funcname '(' [ { expression [',' expression] } ] ')'
    //    factor = [sign] function | number | variable |'(' expression ')'
    //    component  = factor [{ ('*' | '/' | '%' | '^') factor}]
    //    expression = component [{('+' | '-') component}]

    private int icurrLexeme = 0;
    private Integer result;
    /**
     * Набор лексем.
     */
    private ArrayList<Lexeme> lexemes;
    private LexicalAnalyzer lexAnalyzer;

    /**
     * Конструктор для создания объекта MathExpression.
     *
     * @param data строка с математическим выражением для разбора и вычисления.
     */
    public MathExpression(String data) {
        try{
            lexAnalyzer = new LexicalAnalyzer(data);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        lexemes = lexAnalyzer.getLexemes();
    }

    /**
     * Метод для вычисления выражения.
     *
     * @return результат вычислений.
     */
    public Integer evaluate() {
        if (result == null) {
            try {
                result = expression();
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Метод для разбора и вычисления выражения, которое представимо в виде
     * expression = component [{('+' | '-') component}],
     * то есть представляет собой сумму/разность слагаемых.
     *
     * @return значение выражения.
     * @throws IllegalArgumentException в случае нарушения правила следования токенов.
     */
    private int expression() throws IllegalArgumentException {
        int value = component();
        while (icurrLexeme < lexemes.size()) {
            Lexeme lexeme = lexemes.get(icurrLexeme++);
            switch (lexeme.type) {
                case PLUS_OPERATOR:
                    value += component();
                    break;
                case MINUS_OPERATOR:
                    value -= component();
                    break;
                case RBRACE:
                case COMMA:
                    icurrLexeme = icurrLexeme - 1;
                    return value;
                default:
                    throw new IllegalArgumentException("Unexpected token " + lexeme.value
                            + " at position: " + icurrLexeme);
            }
        }
        return value;
    }

    /**
     * Метод для разбора и вычисления слагаемого, которое представимо в виде
     * component  = factor [{ ('*' | '/' | '%' | '^') factor}],
     * то есть представляет собой умножения/возведения в степень/какого-либо из делений множителей.
     *
     * @return значение слагаемого.
     * @throws IllegalArgumentException в случае нарушения правила следования токенов.
     */
    private int component() throws IllegalArgumentException {
        int value = factor();
        while (icurrLexeme < lexemes.size()) {
            Lexeme lexeme = lexemes.get(icurrLexeme++);
            switch (lexeme.type) {
                case MULTI_OPERATOR:
                    value *= factor();
                    break;
                case DIV_OPERATOR:
                    int tmp = factor();
                    if (tmp == 0)
                        throw new IllegalArgumentException("Division by zero at " + icurrLexeme);
                    value /= tmp;
                    break;
                case MOD_OPERATOR:
                    value %= factor();
                    break;
                case POV_OPERATOR:
                    value = (int) round(Math.pow(value,factor()));
                    break;
                case RBRACE:
                case COMMA:
                case PLUS_OPERATOR:
                case MINUS_OPERATOR:
                    --icurrLexeme;
                    return value;
                default:
                    throw new IllegalArgumentException("Unexpected token " + lexeme.value
                            + " at " + icurrLexeme);
            }
        }
        return value;
    }

    /**
     * Метод для разбора и вычисления функции, которая представима в виде
     *  function = funcname '(' [ { expression [',' expression] } ] ')',
     * то есть представляет собой имя функции, которое должно быть занесено в MathFunctions,
     * и список аргументов, каждый из которых является выражением.
     *
     * @return значение функции.
     * @throws IllegalArgumentException в случае нарушения правила следования токенов или синтаксиса функции.
     */
    private int func() throws IllegalArgumentException {
        String name = lexemes.get(icurrLexeme++).value;
        Lexeme lexeme = lexemes.get(icurrLexeme++);

        if (lexeme.type != Lexeme.Token.LBRACE) {
            throw new IllegalArgumentException("Illegal function syntax at " + lexeme.value + ". Expected brace.");
        }

        ArrayList<Integer> args = new ArrayList<>();
        lexeme = lexemes.get(icurrLexeme++);

        if (lexeme.type != Lexeme.Token.RBRACE) {
            icurrLexeme = icurrLexeme - 1;
            do {
                args.add(expression());
                lexeme = lexemes.get(icurrLexeme++);
                if (lexeme.type != Lexeme.Token.COMMA && lexeme.type != Lexeme.Token.RBRACE) {
                    throw new IllegalArgumentException("Illegal function syntax " + lexeme.value);
                }
            } while (lexeme.type == Lexeme.Token.COMMA);
        }

        if (!lexAnalyzer.getMathFunctions().containsKey(name)) {
            throw new IllegalArgumentException("There is no function named " + lexeme.value);
        }

        return lexAnalyzer.getMathFunctions().get(name).calculate(args);
    }

    /**
     * Метод для разбора и вычисления множителя, который описывается как
     * factor = [sign] function | number | variable |'(' expression ')',
     * то есть может быть числом, переменной, функцией, выражением.
     *
     * @return значение множителя.
     * @throws IllegalArgumentException в случае нарушения правила следования токенов.
     */
    private int factor() throws IllegalArgumentException {
        Lexeme lexeme = lexemes.get(icurrLexeme++);
        switch (lexeme.type) {
            case FUNCNAME:
                --icurrLexeme;
                return func();
            case MINUS_OPERATOR:
                return -factor();
            case NUMBER:
                return Integer.parseInt(lexeme.value);
            case VAR:
                return lexAnalyzer.getVariables().get(lexeme.value);
            case LBRACE:
                int value = expression();
                lexeme = lexemes.get(icurrLexeme++);
                if (lexeme.type != Token.RBRACE) {
                    throw new IllegalArgumentException("Unexpected token " + lexeme.value + " at " + icurrLexeme);
                }
                return value;
            case PLUS_OPERATOR:
                return factor();
            default:
                throw new IllegalArgumentException("Unexpected token " + lexeme.value + " at " + icurrLexeme);
        }
    }
}

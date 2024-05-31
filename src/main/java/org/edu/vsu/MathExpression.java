package org.edu.vsu;

import java.util.ArrayList;

import static org.edu.vsu.Lexeme.Token;

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
    private ArrayList<Lexeme> lexemes;
    private LexicalAnalyzer lexAnalyzer;

    public MathExpression(String data) {
        lexAnalyzer = new LexicalAnalyzer(data);
        lexemes = lexAnalyzer.lexemes;
    }

    private void calculate() {
        result = expression(lexemes);
    }

    public Integer evaluate() {
        if (result == null) {
            calculate();
        }
        return result;
    }

    private int expression(ArrayList<Lexeme> lexemes) {
        int value = component(lexemes);
        while (icurrLexeme < lexemes.size()) {
            Lexeme lexeme = lexemes.get(icurrLexeme++);
            switch (lexeme.type) {
                case PLUS_OPERATOR:
                    value += component(lexemes);
                    break;
                case MINUS_OPERATOR:
                    value -= component(lexemes);
                    break;
                case RBRACE:
                case COMMA:
                    icurrLexeme = icurrLexeme - 1;
                    return value;
                default:
                    throw new IllegalArgumentException("Unexpected token in expr: " + lexeme.value
                            + " at position: " + icurrLexeme);
            }
        }
        return value;
    }

    private int component(ArrayList<Lexeme> lexemes) {
        int value = factor(lexemes);
        while (icurrLexeme < lexemes.size()) {
            Lexeme lexeme = lexemes.get(icurrLexeme++);
            switch (lexeme.type) {
                case MULTI_OPERATOR:
                    value *= factor(lexemes);
                    break;
                case DIV_OPERATOR:
                    int tmp = factor(lexemes);
                    if(tmp == 0)
                        throw new ArithmeticException("Division by zero!");
                    value /= tmp;
                    break;
                case MOD_OPERATOR:
                    value %= factor(lexemes);
                    break;
                case RBRACE:
                case COMMA:
                case PLUS_OPERATOR:
                case MINUS_OPERATOR:
                    --icurrLexeme;
                    return value;
                default:
                    throw new IllegalArgumentException("Unexpected token in " + lexeme.value
                            + " at " + icurrLexeme);
            }
        }return value;
    }

    private int func(ArrayList<Lexeme> lexemes) {
        String name = lexemes.get(icurrLexeme++).value;
        Lexeme lexeme = lexemes.get(icurrLexeme++);

        if (lexeme.type != Lexeme.Token.LBRACE) {
            throw new IllegalArgumentException("Illegal  function syntax at " + lexeme.value + ". Expexted brace.");
        }

        ArrayList<Integer> args = new ArrayList<>();
        lexeme = lexemes.get(icurrLexeme++);

        if (lexeme.type != Lexeme.Token.RBRACE) {
            icurrLexeme = icurrLexeme - 1;
            do {
                args.add(expression(lexemes));
                lexeme = lexemes.get(icurrLexeme++);
                if (lexeme.type != Lexeme.Token.COMMA && lexeme.type != Lexeme.Token.RBRACE) {
                    throw new IllegalArgumentException("Illegal  function syntax at " + lexeme.value);
                }
            } while (lexeme.type == Lexeme.Token.COMMA);
        }
        return lexAnalyzer.mathFunctions.get(name).calculate(args);
    }

    private int factor(ArrayList<Lexeme> lexemes) {
        Lexeme lexeme = lexemes.get(icurrLexeme++);
        switch (lexeme.type) {
            case FUNCNAME:
                --icurrLexeme;
                return func(lexemes);
            case MINUS_OPERATOR:
                return -factor(lexemes);
            case NUMBER:
                return Integer.parseInt(lexeme.value);
            case VAR:
                return lexAnalyzer.variables.get(lexeme.value.toString());
            case LBRACE:
                int value = expression(lexemes);
                lexeme = lexemes.get(icurrLexeme++);
                if (lexeme.type != Token.RBRACE) {
                    throw new IllegalArgumentException("Unexpected token in " + lexeme.value + " at " + icurrLexeme);
                }
                return value;
            default:
                throw new IllegalArgumentException("Unexpected token in " + lexeme.value + " at " + icurrLexeme);
        }
    }
}



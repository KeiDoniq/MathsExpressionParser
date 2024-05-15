package org.edu.vsu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.Math.*;

public class LexicalAnalyzer{
    private int curr;
    private String data;
    private interface predicate{
        boolean isTrue(char c);
    }
    private interface MathOperators{
        int calculate(List<Integer> args) throws IllegalArgumentException;}
    private String parsedLexeme(predicate tokenCondition, int pos){
        char c = data.charAt(pos);
        boolean eoToken = false;
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(c);
            ++pos;
            if (pos >= data.length()) {
                eoToken = true;
            }
            else
                c = data.charAt(pos);
        } while (!eoToken && tokenCondition.isTrue(c));
        return sb.toString();
    }
    public Map<String, MathOperators> mathFunctions = new HashMap<>();
    public Map<String, Integer> variables = new HashMap<>();
    private ArrayList<Lexeme> lexemes = new ArrayList<>();

    private void parser(){
        predicate numberCondition = c -> (c <= 57 && c >= 48);
        predicate letterCondition = c -> ((c <= 90 && c >= 65) || (c <= 122 && c >= 97));
        int pos = 0;
        while (pos < data.length()) {
            char c = data.charAt(pos);
            switch (c) {
                case ' ':
                case '\n':
                case '\t':
                    ++pos;
                    break;
                case '(': {
                    lexemes.add(new Lexeme(Lexeme.Token.LBRACE, c));
                    ++pos;
                    break;
                }
                case ')': {
                    lexemes.add(new Lexeme(Lexeme.Token.RBRACE, c));
                    ++pos;
                    break;
                }
                case '+': {
                    lexemes.add(new Lexeme(Lexeme.Token.PLUS_OPERATOR, c));
                    ++pos;
                    break;
                }
                case '-': {
                    lexemes.add(new Lexeme(Lexeme.Token.MINUS_OPERATOR, c));
                    ++pos;
                    continue;
                }
                case '*': {
                    lexemes.add(new Lexeme(Lexeme.Token.MULTI_OPERATOR, c));
                    ++pos;
                    break;
                }
                case '/': {
                    lexemes.add(new Lexeme(Lexeme.Token.DIV_OPERATOR, c));
                    ++pos;
                    break;
                }
                case '%': {
                    lexemes.add(new Lexeme(Lexeme.Token.MOD_OPERATOR, c));
                    ++pos;
                    break;
                }
                case ',': {
                    lexemes.add(new Lexeme(Lexeme.Token.COMMA, c));
                    ++pos;
                    break;
                }
                default:
                    if (numberCondition.isTrue(c)) {
                        String numberLexeme = parsedLexeme(numberCondition, pos);
                        lexemes.add(new Lexeme(Lexeme.Token.NUMBER, numberLexeme));
                        pos = pos + numberLexeme.length();
                    } else if (letterCondition.isTrue(c)) {
                            String strLexeme = parsedLexeme(letterCondition, pos);
                            if (mathFunctions.containsKey(strLexeme)) {
                                lexemes.add(new Lexeme(Lexeme.Token.FUNCNAME, strLexeme));
                            } else {
                                variables.put(strLexeme, null);
                                lexemes.add(new Lexeme(Lexeme.Token.VAR, strLexeme));
                            }
                            pos = pos + strLexeme.length();
                    }
                    else throw new IllegalArgumentException("Lexer Error. Unexpexted symbol at position " + pos);
            }
        }
    }
    public LexicalAnalyzer(String expression) {
        data = new String(expression);
        mathFunctions.put("min", args -> {
            if (args.isEmpty()) {
                throw new IllegalArgumentException("Function min should have at least 1 argument.");
            }
            int min = args.get(0);
            for (Integer val: args) {
                if (val < min) {
                    min = val;
                }
            }
            return min;
        });
        mathFunctions.put("max", args -> {
            if (args.isEmpty()) {
                throw new IllegalArgumentException("Function max should have at least 1 argument.");
            }
            int max = args.get(0);
            for (Integer val: args) {
                if (val > max) {
                    max = val;
                }
            }
            return max;
        });
        mathFunctions.put("sin", args -> {
            if (args.size() != 1) {
                throw new IllegalArgumentException("Function sin should have 1 and only argument. Current count: " + args.size());
            }
            return  (int) sin(args.get(0));
        });
        mathFunctions.put("cos", args -> {
            if (args.size() != 1) {
                throw new IllegalArgumentException("Function cos should have 1 and only argument. Current count: " + args.size());
            }
            return (int) cos(args.get(0));
        });
        mathFunctions.put("pow", args -> {
            if (args.size() != 2) {
                throw new RuntimeException("Function pov should have 2 arguments. Current count: " + args.size());
            }
            return (int) Math.pow(args.get(0), args.get(1));
        });
        parser();
    }
    public Lexeme next() {
        return lexemes.get(curr++);
    }
    public void prev() {
        curr = curr - 1;
    }
    public int getPosition() {
        return curr;
    }

    @Override
    public String toString() {
        return "LexicalAnalyzer{" +
                "data='" + data + '\'' +
                ",\nmathFunctions=" + mathFunctions +
                ",\nvariables=" + variables +
                ",\nlexemes=" + lexemes +
                '}';
    }
}

package org.edu.vsu;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LexicalAnalyzerTest {

    @Test
    void checkBracketsInbalanceSuccess() {
        String[] bracketsInbalanced = new String[] {"(())))))", "7+)-9*(-)",
                "5+7+(87-(90)+(89 - 90)", "((((((((", "))))))"};
        for (String str:bracketsInbalanced){
            assertThrows(IllegalArgumentException.class, ()->new LexicalAnalyzer(str));
        }
    }
    @Test
    void getDataSuccess() {
        String[] data = new String[] {"(())", "7-9*(-14)",
                "5+7+87-(90)+(89 - 90)"};
        for (String str:data){
            assertEquals(str, new LexicalAnalyzer(str).getData());
        }
    }
    @Test
    void getLexemesSuccess() {
        HashMap<String, ArrayList<Lexeme>> testData = new HashMap<>(5);
        testData.put("5+10-sin(1*2)",
                new ArrayList<>(Arrays.asList(
                        new Lexeme(Lexeme.Token.NUMBER, '5'),
                        new Lexeme(Lexeme.Token.PLUS_OPERATOR, '+'),
                        new Lexeme(Lexeme.Token.NUMBER, "10"),
                        new Lexeme(Lexeme.Token.MINUS_OPERATOR, '-'),
                        new Lexeme(Lexeme.Token.FUNCNAME, "sin"),
                        new Lexeme(Lexeme.Token.LBRACE, '('),
                        new Lexeme(Lexeme.Token.NUMBER, "1"),
                        new Lexeme(Lexeme.Token.MULTI_OPERATOR, '*'),
                        new Lexeme(Lexeme.Token.NUMBER, '2'),
                        new Lexeme(Lexeme.Token.RBRACE, ')')
                )));
        testData.put("145785    /   19 %        1000",
                new ArrayList<>(Arrays.asList(
                        new Lexeme(Lexeme.Token.NUMBER, "145785"),
                        new Lexeme(Lexeme.Token.DIV_OPERATOR, '/'),
                        new Lexeme(Lexeme.Token.NUMBER, "19"),
                        new Lexeme(Lexeme.Token.MOD_OPERATOR, '%'),
                        new Lexeme(Lexeme.Token.NUMBER, "1000")
                )));
        testData.put("min(max(5,2,-14), pow(78,98))%,",
                new ArrayList<>(Arrays.asList(
                        new Lexeme(Lexeme.Token.FUNCNAME, "min"),
                        new Lexeme(Lexeme.Token.LBRACE, '('),
                        new Lexeme(Lexeme.Token.FUNCNAME, "max"),
                        new Lexeme(Lexeme.Token.LBRACE, '('),
                        new Lexeme(Lexeme.Token.NUMBER, "5"),
                        new Lexeme(Lexeme.Token.COMMA, ','),
                        new Lexeme(Lexeme.Token.NUMBER, "2"),
                        new Lexeme(Lexeme.Token.COMMA, ','),
                        new Lexeme(Lexeme.Token.MINUS_OPERATOR, '-'),
                        new Lexeme(Lexeme.Token.NUMBER, "14"),
                        new Lexeme(Lexeme.Token.RBRACE, ')'),
                        new Lexeme(Lexeme.Token.COMMA, ','),
                        new Lexeme(Lexeme.Token.FUNCNAME, "pow"),
                        new Lexeme(Lexeme.Token.LBRACE, '('),
                        new Lexeme(Lexeme.Token.NUMBER, "78"),
                        new Lexeme(Lexeme.Token.COMMA, ','),
                        new Lexeme(Lexeme.Token.NUMBER, "98"),
                        new Lexeme(Lexeme.Token.RBRACE, ')'),
                        new Lexeme(Lexeme.Token.RBRACE, ')'),
                        new Lexeme(Lexeme.Token.MOD_OPERATOR, '%'),
                        new Lexeme(Lexeme.Token.COMMA, ',')
                )));

        for (String str: testData.keySet()){
            assertEquals(new LexicalAnalyzer(str).getLexemes(), testData.get(str));
        }
    }

    @Test
    void getMathFunctionsSuccess() {
        Set<String> mathFunctions = new HashSet<>(Arrays.asList(
                "min", "sin", "cos", "max", "pow"));
        assertEquals(mathFunctions,new LexicalAnalyzer("min(max(5,2))").getMathFunctions().keySet());
    }
}
package org.edu.vsu;

public class MathExpression {

//    sign = '+' | '-'
//    digit = '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'
//    number =  {digit}
//    function = funcname '(' [ { expression [',' expression] } ] ')'
//    factor = [sign] function | number | variable |'(' expression ')'
//    component  = factor [{ ('*' | '/' | '%' | '^' ) factor}]
//    expression = component [{('+' | '-') component}]


    private Integer result;
    private LexicalAnalyzer lexicalAnalyzer;
    public MathExpression(String data) {
        lexicalAnalyzer = new LexicalAnalyzer(data);
    }
    private Integer calculate(){
        return 0;
    }
    public Integer evalulate(){
        return 0;
    }

}

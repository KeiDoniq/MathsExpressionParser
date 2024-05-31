package org.edu.vsu;

public class Main {
    public static void main(String[] args) {
        //String expression = "(57+85- 4-   4-    5+x     *sin(78)/(97*cos(    147-yvar)))";- 5%5*25+        max(45,97,  -23, y)
        //MathExpression testMathExpression = new MathExpression("(57     * x -   85-    4*4-5+7)");/1   -sin(78)/(97*cos(    147-yvar)))-   5-    (5+x*yvar)/10
        MathExpression testMathExpression = new MathExpression("(sin(x)*5*5         - pow(4,      5))");
        System.out.println(testMathExpression.evaluate());
    }
}
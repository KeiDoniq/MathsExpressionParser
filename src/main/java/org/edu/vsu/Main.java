package org.edu.vsu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //String expression = "(57+85- 4-   4-    5+x     *sin(78)/(97*cos(    147-yvar)))";- 5%5*25+        max(45,97,  -23, y)
        //MathExpression testMathExpression = new MathExpression("(57     * x -   85-    4*4-5+7)");/1   -sin(78)/(97*cos(    147-yvar)))-   5-    (5+x*yvar)/10
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        do{
            System.out.println(new MathExpression(str).evaluate());
            System.out.println("Write '-' for stop.");
            str = input.nextLine();
        }while(!"-".equals(str));
    }
}
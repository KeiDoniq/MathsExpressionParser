package org.edu.vsu;

public class Main {
    public static void main(String[] args) {
        String expression = "(57854-   4-    5+x     *sin(78)/(97*cos(    147-yvar)))";
        LexicalAnalyzer lexTest = new LexicalAnalyzer(expression);
        System.out.println(lexTest);
    }
}
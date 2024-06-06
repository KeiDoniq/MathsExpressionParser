package org.edu.vsu;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathExpressionTest {
    @Test
    void funcSuccess() {
        HashMap<String, Integer> testData = new HashMap<>(10);
        testData.put("min(max(5,2,-14))",5);
        testData.put("pow(max(5,2,-14), min(1,3,4))",5);
        testData.put("min(5,2,-14)",-14);
        testData.put("pow(max(5,2,-14),2)",25);
        testData.put("min(pow(5,2),pow(2,5))",25);

        for (String str: testData.keySet()){
            assertEquals(new MathExpression(str).evaluate(), testData.get(str));
        }
    }
    @Test
    void expressionSuccess() {
        HashMap<String, Integer> testData = new HashMap<>(10);
        testData.put("8+9-78+(90)",29);
        testData.put("pow(5,2)+100",125);
        testData.put("min(5,2,-14)-90",-104);
        testData.put("60+(70-40)",90);

        for (String str: testData.keySet()){
            assertEquals(new MathExpression(str).evaluate(), testData.get(str));
        }
    }
    @Test
    void componentSuccess() {
        HashMap<String, Integer> testData = new HashMap<>(10);
        testData.put("8*9/7+(90) % 5",10);
        testData.put("17*153462",2608854);
        testData.put("5 ^ 2 * 100",2500);
        testData.put("(60/11)*5/5",5);

        for (String str: testData.keySet()){
            assertEquals(new MathExpression(str).evaluate(), testData.get(str));
        }
    }
}
package org.edu.vsu;

public class Lexeme{
    public enum Token {
    PLUS_OPERATOR, MULTI_OPERATOR, MINUS_OPERATOR, DIV_OPERATOR, MOD_OPERATOR,
    NUMBER, FUNCNAME, LBRACE,
    VAR, RBRACE, COMMA
    }
    Token type;
    String value;

    public Lexeme(Token type, String value) {
        this.type = type;
        this.value = value;
    }
    public Lexeme(Token type, char value) {
        this.type = type;
        this.value = String.valueOf(value);
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}


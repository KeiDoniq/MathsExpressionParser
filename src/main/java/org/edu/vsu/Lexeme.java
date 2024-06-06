package org.edu.vsu;

import java.util.Objects;

/**
 * �����, �������������� �������.
 */
public class Lexeme {

    /**
     * ������������ ����� �������.
     */
    public enum Token {
        /**
         * �������� ��������
         */
        PLUS_OPERATOR,
        /**
         *  �������� ���������
         */
        MULTI_OPERATOR,
        /**
         *  �������� ���������
         */
        MINUS_OPERATOR,
        /**
         *  �������� ��������� ����� ����� �� �������
         */
        DIV_OPERATOR,
        /**
         *  �������� ��������� ������� �� �������
         */
        MOD_OPERATOR,
        /**
         *  �������� ���������� � �������
         */
        POV_OPERATOR,
        /**
         *  �����
         */
        NUMBER,
        /**
         *  ��� ��������
         */
        FUNCNAME,
        /**
         *  ����� ������
         */
        LBRACE,
        /**
         * ��� ����������
         */
        VAR,
        /**
         * ������ ������
         */
        RBRACE,
        /**
         * �������
         */
        COMMA
    }

    /**
     * ��� ������.
     */
    Token type;

    /**
     * �������� ������.
     */
    String value;

    /**
     * ����������� ��� �������� ������� � �������� ����� � ���������.
     *
     * @param type  ��� ������.
     * @param value �������� ������.
     */
    public Lexeme(Token type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * ���������, ����� �� ������ ������� ������� �������.
     *
     * @param o ������ ��� ��������� � ������� ��������.
     * @return true, ���� ������� �����, ����� false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexeme lexeme = (Lexeme) o;
        return type == lexeme.type && Objects.equals(value, lexeme.value);
    }

    /**
     * ���������� ���-��� ������ �������.
     *
     * @return ���-��� ������ �������.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    /**
     * ����������� ��� �������� ������� � �������� ����� � ��������� (��������).
     *
     * @param type  ��� ������.
     * @param value �������� ������ (������).
     */
    public Lexeme(Token type, char value) {
        this.type = type;
        this.value = String.valueOf(value);
    }

    /**
     * ���������� ��������� ������������� �������.
     *
     * @return ��������� ������������� �������.
     */
    @Override
    public String toString() {
        return "Lexeme{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}

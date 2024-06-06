package org.edu.vsu;

import java.util.Objects;

/**
 * Класс, представляющий лексему.
 */
public class Lexeme {

    /**
     * Перечисление типов токенов.
     */
    public enum Token {
        /**
         * Оператор сложения
         */
        PLUS_OPERATOR,
        /**
         *  Оператор умножения
         */
        MULTI_OPERATOR,
        /**
         *  Оператор вычитания
         */
        MINUS_OPERATOR,
        /**
         *  Оператор получения целой части от деления
         */
        DIV_OPERATOR,
        /**
         *  Оператор получения остатка от деления
         */
        MOD_OPERATOR,
        /**
         *  Оператор возведения в степень
         */
        POV_OPERATOR,
        /**
         *  Число
         */
        NUMBER,
        /**
         *  Имя фуункции
         */
        FUNCNAME,
        /**
         *  Левая скобка
         */
        LBRACE,
        /**
         * Имя переменной
         */
        VAR,
        /**
         * Правая скобка
         */
        RBRACE,
        /**
         * Запятая
         */
        COMMA
    }

    /**
     * Тип токена.
     */
    Token type;

    /**
     * Значение токена.
     */
    String value;

    /**
     * Конструктор для создания лексемы с заданным типом и значением.
     *
     * @param type  тип токена.
     * @param value значение токена.
     */
    public Lexeme(Token type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Проверяет, равна ли данная лексема другому объекту.
     *
     * @param o объект для сравнения с текущей лексемой.
     * @return true, если объекты равны, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexeme lexeme = (Lexeme) o;
        return type == lexeme.type && Objects.equals(value, lexeme.value);
    }

    /**
     * Возвращает хеш-код данной лексемы.
     *
     * @return хеш-код данной лексемы.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    /**
     * Конструктор для создания лексемы с заданным типом и значением (символом).
     *
     * @param type  тип токена.
     * @param value значение токена (символ).
     */
    public Lexeme(Token type, char value) {
        this.type = type;
        this.value = String.valueOf(value);
    }

    /**
     * Возвращает строковое представление лексемы.
     *
     * @return строковое представление лексемы.
     */
    @Override
    public String toString() {
        return "Lexeme{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}

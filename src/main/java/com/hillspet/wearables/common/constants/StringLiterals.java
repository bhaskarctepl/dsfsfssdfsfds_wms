package com.hillspet.wearables.common.constants;

/**
 * Enum contains the special characters
 * 
 * @author NIMWEERASINGHE
 *
 */
public enum StringLiterals implements EnumInterface<String> {
    COLON_WITH_SPACE(": "),
    SEMI_COLON(";"),
    HYPHEN("-"),
    STAR("*"),
    EMPTY_STRING(""),
    DOT("."),
    FORWARD_SLASH("/"),
    DOUBLE_SLASH("//"),
    COLON(":"),
    OPEN_BRACKET("["),
    CLOSE_BRACKET("]"),
    WHITE_SPACE(" "),
    COMMA(","),
    UNDERSCORE("_"),
    DOUBLE_QUOTE("\""),
    SINGLE_QUOTE("'"),
    COMMA_WITH_SPACE(", "),
    START_CURLY_BRACE("{"),
    END_CURLY_BRACE("}"),
    PIPE("|"),
    PIPE_WITH_SPACES(" | "),
    HASH("#"),
    EQUAL_TO_WITH_SPACE(" = "),
    CARET("^"),
    SPLIT_COMMA_SEPARATED_STRING("\\s*,\\s*"),
    Y("Y"),
    N("N");

    private String character;

    private StringLiterals(String character) {
        this.character = character;
    }

    @Override
    public String getCode() {
        return this.character;
    }
}

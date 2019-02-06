package de.slowloris.slowbot.ts3.utils;

public enum BBCode {

    BOLD_START("[b]"),
    BOLD_END("[/b]"),
    ITALIC_START("[i]"),
    ITALIC_END("[/i]"),
    UNDERLINE_START("[u]"),
    UNDERLINE_END("[/u]"),
    HIGHLIGHT_START("[highlight]"),
    HIGHLIGHT_END("[/highlight]"),
    CODE_START("[code]"),
    CODE_END("[/code]"),
    URL_START("[url]"),
    URL_END("[/url]"),
    IMG_START("[img]"),
    IMG_END("[/img]"),
    VIDEO_START("[video]"),
    VIDEO_END("[/video]"),
    QUOTE_START("[quote]"),
    QUOTE_END("[/quote]"),
    NOPARSE_START("[noparse]"),
    NOPARSE_END("[/noparse]");



    private final String value;

    BBCode(String s) {
        this.value = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return value.equals(otherName);
    }

    public String toString() {
        return this.value;
    }
}

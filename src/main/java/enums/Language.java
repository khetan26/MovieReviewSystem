package main.java.enums;

public enum Language {

    HINDI("Hindi"),
    ENGLISH("English"),
    CHINESE("Chinese"),
    KOREAN("Korean"),
    TAMIL("Tamil"),
    TELUGU("Telugu"),
    MARATHI("Marathi"),
    BENGALI("Bangali"),
    MALYALI("Malyali");

    private String value;

    private Language(String value) {
        this.value = value;
    }

    public String getRating() {
        return this.value;
    }
}

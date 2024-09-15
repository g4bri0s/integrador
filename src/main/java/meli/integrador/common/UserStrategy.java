package meli.integrador.common;

public enum UserStrategy {

    CLIENT("client"),
    SELLER("seller");

    private final String value;

    UserStrategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}

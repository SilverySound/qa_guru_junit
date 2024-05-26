package ru.anutik.data;

public enum Products {
    Animal("Animal Flex"),
    BCAA("BCAA");

    public final String description;

    Products(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}



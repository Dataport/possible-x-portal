package eu.possiblex.portal.application.entity;

import lombok.Getter;

@Getter
public enum SortOrder {
    ASC("asc"), DESC("desc");

    private final String value;

    SortOrder(String value) {
        this.value = value;
    }

    public static boolean valueExists(String value) {
        if (value == null) {
            return false;
        }

        for (SortOrder field : SortOrder.values()) {
            if (field.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static SortOrder fromString(String label) {
        for (SortOrder e : values()) {
            if (e.name().equalsIgnoreCase(label)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + label);
    }
}

package eu.possiblex.portal.application.entity;

import lombok.Getter;

@Getter
public enum SortField {
    STATUS("status"), ORGANIZATION_NAME("name");

    private final String value;

    SortField(String value) {
        this.value = value;
    }

    public static boolean valueExists(String value) {
        if (value == null) {
            return false;
        }

        for (SortField field : SortField.values()) {
            if (field.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static SortField fromString(String label) {
        for (SortField e : values()) {
            if (e.name().equalsIgnoreCase(label)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + label);
    }
}

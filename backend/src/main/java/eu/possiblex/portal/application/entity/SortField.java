package eu.possiblex.portal.application.entity;

import lombok.Getter;

@Getter
public enum SortField {
    STATUS("status"), ORGANIZATION_NAME("name");

    private final String value;

    SortField(String value) {
        this.value = value;
    }

    public static SortField fromString(String label) {
        if (label == null || label.isEmpty()) {
            return null;
        }

        for (SortField e : values()) {
            if (e.name().equalsIgnoreCase(label)) {
                return e;
            }
        }
        return null;
    }
}

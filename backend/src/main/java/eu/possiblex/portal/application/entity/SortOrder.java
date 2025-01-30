package eu.possiblex.portal.application.entity;

import lombok.Getter;

@Getter
public enum SortOrder {
    ASC, DESC;

    public static SortOrder fromString(String label) {
        if (label == null) {
            return null;
        }

        for (SortOrder e : values()) {
            if (e.name().equalsIgnoreCase(label)) {
                return e;
            }
        }
        return null;
    }
}

package eu.possiblex.portal.application.entity;

public enum SortField {
    STATUS("status"), ORGANIZATION_NAME("name");

    private final String value;

    SortField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean exists(String value) {
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
}

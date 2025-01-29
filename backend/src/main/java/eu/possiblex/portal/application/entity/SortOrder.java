package eu.possiblex.portal.application.entity;

public enum SortOrder {
    ASC("asc"), DESC("desc");

    private final String value;

    SortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean exists(String value) {
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
}

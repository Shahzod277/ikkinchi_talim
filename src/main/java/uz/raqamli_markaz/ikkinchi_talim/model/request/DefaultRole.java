package uz.raqamli_markaz.ikkinchi_talim.model.request;

public enum DefaultRole {

    ROLE_ABITURIYENT("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_UADMIN("ROLE_UADMIN");

    private final String message;

    DefaultRole(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

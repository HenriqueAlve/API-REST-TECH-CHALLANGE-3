package fiap.gestao.hospitalar.enums;

public enum UserRole {

    ADMIN("admin"),
    MEDICO("medico"),
    ENFERMEIRO("enfermeiro"),
    PACIENTE("paciente");

    private String role;
    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


}

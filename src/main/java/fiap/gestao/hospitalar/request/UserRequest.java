package fiap.gestao.hospitalar.request;

import fiap.gestao.hospitalar.enums.UserRole;

public record UserRequest(String login, String password, UserRole role) {
}

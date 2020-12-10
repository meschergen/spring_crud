package web.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 08.12.2020
 *
 * @author MescheRGen
 */

public class Role implements GrantedAuthority {
    private Long id;
    private String role;

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}

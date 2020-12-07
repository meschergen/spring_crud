package web.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 08.12.2020
 *
 * @author MescheRGen
 */
public class Role implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return null;
    }
}

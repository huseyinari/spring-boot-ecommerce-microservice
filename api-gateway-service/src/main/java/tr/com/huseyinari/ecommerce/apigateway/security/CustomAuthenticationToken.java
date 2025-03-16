package tr.com.huseyinari.ecommerce.apigateway.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private String username;
    private String name;
    private String familyName;
    private String email;

    public CustomAuthenticationToken(
        String username,
        String name,
        String familyName,
        String email,
        Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);

        this.username = username;
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;  // Çünkü JWT kullanıyoruz, ekstra şifre bilgisi yok
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    public String getUsername() {
        return this.username;
    }
    public String getName() {
        return this.name;
    }
    public String getFamilyName() {
        return this.familyName;
    }
    public String getEmail() {
        return this.email;
    }

}

package sgsits.cse.dis.gateway.message.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	private String token;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String accessToken, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.authorities = authorities;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}
		
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
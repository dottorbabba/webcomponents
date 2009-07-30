package org.webcomponents.security.providers.josso;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.AbstractAuthenticationToken;

/**
 * 
 * @author Andrea Bandera
 */
public class JOSSOAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private String jossoSessionId;

	public JOSSOAuthenticationToken(String jossoSessionId, GrantedAuthority[] authorities) {
		super(authorities);
		this.jossoSessionId = jossoSessionId;
	}

	public String getJossoSessionId() {
		return jossoSessionId;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return getDetails();
	}
}

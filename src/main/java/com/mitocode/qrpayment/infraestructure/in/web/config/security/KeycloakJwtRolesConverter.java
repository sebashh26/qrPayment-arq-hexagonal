package com.mitocode.qrpayment.infraestructure.in.web.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;

import com.mitocode.qrpayment.infraestructure.in.web.context.AuthorizeContext;

public class KeycloakJwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>>{
	
	 public static final String PREFIX_REALM_ROLE = "ROLE_realm_";

	    public static final String PREFIX_RESOURCE_ROLE = "ROLE_";

	    private static final String CLAIM_REALM_ACCESS = "realm_access";//reinos a los que pertenece el usuario 

	    private static final String CLAIM_RESOURCE_ACCESS = "resource_access";

	    private static final String CLAIM_ROLES = "roles";

	    private static final String CLIENT_ID = "client_id";

	    /**
	     * Extracts the realm and resource level roles from a JWT token distinguishing between them using prefixes.
	     */
	    @Override
	    public Collection<GrantedAuthority> convert(Jwt jwt) {
	    	
	    	String clientId = jwt.getClaim(CLIENT_ID);
	    	AuthorizeContext.setClientId(clientId);

	        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

	        // Realm roles
	        Map<String, Collection<String>> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);

	        if (!CollectionUtils.isEmpty(realmAccess)) {
	            Collection<String> roles = realmAccess.get(CLAIM_ROLES);
	            AuthorizeContext.setRoles(roles);

	            if (!CollectionUtils.isEmpty(roles)) {

	                Collection<GrantedAuthority> realmRoles = roles.stream()
	                        .map(role -> new SimpleGrantedAuthority(PREFIX_REALM_ROLE + role))
	                        .collect(Collectors.toList());
	                grantedAuthorities.addAll(realmRoles);
	            }
	        }

	        // Resource (client) roles
	        Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim(CLAIM_RESOURCE_ACCESS);

	        if (resourceAccess != null && !resourceAccess.isEmpty()) {

	            resourceAccess.forEach((resource, resourceClaims) -> {
	                resourceClaims.get(CLAIM_ROLES).forEach(
	                        role -> grantedAuthorities.add(new SimpleGrantedAuthority(PREFIX_RESOURCE_ROLE + resource + "_" + role))
	                );
	            });
	        }

	        return grantedAuthorities;
	    }

}

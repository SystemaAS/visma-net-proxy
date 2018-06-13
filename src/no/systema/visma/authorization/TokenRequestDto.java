package no.systema.visma.authorization;

import lombok.Data;

/**
* grant_type, MANDATORY, must be set to "authorization_code" <br>
* code, MANDATORY, will contain the code obtained on the authorization request <br>
* redirect_uri, MANDATORY, same value used in the the authorization request <br>
*/
@Data
public class TokenRequestDto {
	private String grantType;
	private String code;
	private String redirectUri;
}

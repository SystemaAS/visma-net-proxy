package no.systema.visma.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
* grant_type, MANDATORY, must be set to "authorization_code" <br>
* code, MANDATORY, will contain the code obtained on the authorization request <br>
* redirect_uri, MANDATORY, same value used in the the authorization request <br>
* client_id, MANDATORY , containing the clientId configured in Visma.net Integrations <br>
* client_secret, MANDATORY, the value configured in Visma.net Integrations <br>
*/
@Data
public class TokenRequestDto {

	@JsonProperty("grant_type")
	private String grantType;
	@JsonProperty("code")
	private String code;
	@JsonProperty("redirect_uri")
	private String redirectUri;
	@JsonProperty("client_id")
	private String clientId;
	@JsonProperty("client_secret")
	private String clientSecret;
	
}

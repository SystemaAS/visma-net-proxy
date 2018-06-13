package no.systema.visma.authorization;

import lombok.Data;

@Data
public class TokenResponseDto {
	private String token;
	private String token_type;
	private String scope;
}

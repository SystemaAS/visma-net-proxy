package no.systema.visma.integration;

public enum IUDEnum {

	INSERT("Insert"),

	UPDATE("Update"),

	DELETE("Delete");

	private String value;

	IUDEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}

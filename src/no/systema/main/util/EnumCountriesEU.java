package no.systema.main.util;


public enum EnumCountriesEU {
	ANDORRA("AD"),
	AUSTRIA("AT"),
	BELGIUM("BE"),
	BULGARIA("BG"),
	CROATIA("HR"),
	CYPRUS("CY"),
	CZECHIA("CZ"),
	DENMARK("DK"),
	ESTONIA("EE"),
	FINLAND("FI"),
	FRANCE("FR"),
	GERMANY("DE"),
	GREECE("EL"),
	
	HUNGARY("HU"),
	ITALY("IT"),
	IRLAND("IE"),
	LATVIA("LV"),
	LITHUANIA("LT"),
	LUXEMBOURG("LU"),
	MALTA("MT"),
	NETHERLANDS("NL"),
	
	POLAND("PL"),
	PORTUGAL("PT"),
	ROMANIA("RO"),
	SLOVAKIA("SK"),
	SLOVENIA("SI"),
	SPAIN("ES"),
	SWEDEN("SE");
	
	
	public final String code;

    private EnumCountriesEU(String code) {
        this.code = code;
    }
    
    
    public static EnumCountriesEU valueOfCode(String code) {
        for (EnumCountriesEU e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    
}



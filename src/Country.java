import java.util.HashMap;

public class Country {
	Name name;
	String[] tld;
	String cca2;
	String ccn3;
	String cca3;
	String cioc;
	boolean independent;
	String status;
	boolean unMember;
	HashMap<String,Currency> currencies;
	IDD idd;
	String[] capital;
	String[] altSpellings;
	String region;
	String subregion;
	HashMap<String,String> languages;
	HashMap<String,NativeName> translations;
	float[] latlng;
	boolean landlocked;
	float area;
	HashMap<String,Demonym>demonyms;
	String flag;
	MyMap maps;
	int population;
	HashMap<String,Float> gini;
	String fifa;
	Car car;
	String[] timezones;
	String[] continents;
	FlagImage flags;
	Image coatOfArms;
	String startOfWeek;
	CapitalInfo capitalInfo;
	PostalCode postalCode;
}

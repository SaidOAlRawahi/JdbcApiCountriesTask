import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {
	static Country[] countries;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean programIsActive = true;
		while (programIsActive) {
			System.out.println("\nSelect An Option");
			System.out.println("1- Fetch Countries From API");
			System.out.println("2- Print Countries From API");
			System.out.println("3- DB Options");
			System.out.println("4- File Options");
			System.out.println("5- Exit");
			String selection = sc.next();
			switch (selection) {
			case "1":
				try {
					countries = fetchCountriesFromJson();
					System.out.println("Data Fetched Successfully");
				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			case "2":
				if (countries != null) {
					printCountriesDetails(countries);
				}
				else {
					System.out.println("There Are No Fetched Countries, Please Fetch Them First!");
				}
				break;

			case "3":
				break;

			case "4":
				callFileOptionsMenu();
				break;

			case "5":
				programIsActive = false;
				break;

			default:
				System.out.println("Invalid Input");
				break;
			}
		}
		System.out.println("GOODBYE");
	}

	
	static void callFileOptionsMenu() {
		FileOptionsMenu menu = new FileOptionsMenu();
		
		menu.showMenu();
	}

	static void printCountriesDetails(Country[] countries) {

		System.out.println(
				"============================================================================================================");
		for (Country i : countries) {
			if (i.name.common.equals("Israel")) {
				continue;
			}
			System.out.println("Name:");
			System.out.println("\tOfficial: " + i.name.official);
			System.out.println("\tCommon: " + i.name.common);

			if (i.name.nativeName != null) {
				System.out.println("\tNative Name:");
				for (String key : i.name.nativeName.keySet()) {
					System.out.println("\t\t" + key + ": ");
					System.out.println("\t\t\tOfficial: " + i.name.nativeName.get(key).official);
					System.out.println("\t\t\tCommon: " + i.name.nativeName.get(key).common);
				}
			}

			if (i.tld != null) {
				System.out.println("TLD: ");
				for (String tld : i.tld) {
					System.out.println("\t" + tld);
				}
			}

			System.out.println("cca2: " + i.cca2);
			System.out.println("ccn3: " + i.ccn3);
			System.out.println("cca3: " + i.cca3);
			System.out.println("cioc: " + i.cioc);
			System.out.println("Independent: " + i.independent);
			System.out.println("Status: " + i.status);
			System.out.println("UN Member: " + i.unMember);

			if (i.currencies != null) {
				System.out.println("Currencies: ");
				for (String key : i.currencies.keySet()) {
					System.out.println("\t" + key + ": ");
					System.out.println("\t\tName: " + i.currencies.get(key).name);
					System.out.println("\t\tSymbol: " + i.currencies.get(key).symbol);
				}
			}

			System.out.println("IDD: ");
			System.out.println("\tRoot: " + i.idd.root);
			if (i.idd.suffixes != null) {
				System.out.println("\tSuffixes: ");
				for (String s : i.idd.suffixes) {
					System.out.println("\t\t" + s);
				}
			}

			if (i.capital != null) {
				System.out.println("Capital: ");
				for (String c : i.capital) {
					System.out.println("\t" + c);
				}
			}

			System.out.println("Alt Spellings: ");
			for (String s : i.altSpellings) {
				System.out.println("\t" + s);
			}

			System.out.println("Region: " + i.region);
			System.out.println("Sub-Region: " + i.subregion);

			if (i.languages != null) {
				System.out.println("Languages: ");
				for (String key : i.languages.keySet()) {
					System.out.println("\t" + key + ": " + i.languages.get(key));
				}
			}

			System.out.println("Translations: ");
			for (String key : i.translations.keySet()) {
				System.out.println("\t" + key + ": ");
				System.out.println("\t\tOfficial: " + i.translations.get(key).official);
				System.out.println("\t\tCommon: " + i.translations.get(key).common);
			}

			System.out.println("Latitude & Longitude: ");
			for (float l : i.latlng) {
				System.out.println("\t " + l);
			}
			System.out.println("Land Locked: " + i.landlocked);

			if (i.borders != null) {
				System.out.println("Borders: ");
				for (String b : i.borders) {
					System.out.println("\t" + b);
				}
			}
			System.out.println("Area: " + i.area);

			if (i.demonyms != null) {
				System.out.println("Demonyms: ");
				for (String key : i.demonyms.keySet()) {
					System.out.println("\t" + key + ": ");
					System.out.println("\t\tF: " + i.demonyms.get(key).f);
					System.out.println("\t\tF: " + i.demonyms.get(key).f);
				}
			}

			System.out.println("Flag: " + i.flag);
			System.out.println("\tGoogle Maps: " + i.maps.googleMaps);
			System.out.println("\tOpen Street Maps: " + i.maps.openStreetMaps);
			System.out.println("Population: " + i.population);
			System.out.println("Fifa: " + i.fifa);

			System.out.println("Car: ");
			if (i.car.signs != null) {
				System.out.println("\tSigns:");
				for (String s : i.car.signs) {
					System.out.println("\t\t" + s);
				}
			}
			System.out.println("\tSide: " + i.car.side);

			System.out.println("Time Zones:");
			for (String t : i.timezones) {
				System.out.println("\t" + t);
			}

			System.out.println("Continents:");
			for (String c : i.continents) {
				System.out.println("\t" + c);
			}

			System.out.println("Flags: ");
			System.out.println("\tpng: " + i.flags.png);
			System.out.println("\tsvg: " + i.flags.svg);
			System.out.println("\talt: " + i.flags.alt);

			System.out.println("Coat Of Arms: ");
			System.out.println("\tpng: " + i.coatOfArms.png);
			System.out.println("\tsvg: " + i.coatOfArms.svg);
			System.out.println("Start Of Week: " + i.startOfWeek);

			if (i.capitalInfo.latlng != null) {
				System.out.println("Capital Info: ");
				System.out.println("\tLatitude & Longitude: ");
				for (float l : i.capitalInfo.latlng) {
					System.out.println("\t\t" + l);
				}
			}
			if (i.postalCode != null) {
				System.out.println("Postal Code: ");
				System.out.println("\tRegex: " + i.postalCode.regex);
				System.out.println("\tFormat: " + i.postalCode.format);
			}

			System.out.println(
					"\n============================================================================================================");
		}
	}

	static Country[] fetchCountriesFromJson() throws Exception {
		String apiUrl = "https://restcountries.com/v3.1/all";
		URL url = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output;
		StringBuilder json = new StringBuilder();

		while ((output = br.readLine()) != null) {
			json.append(output);
		}

		conn.disconnect();

		Gson gson = new Gson();
		Country[] countries = gson.fromJson(json.toString(), Country[].class);
		return countries;
	}
}

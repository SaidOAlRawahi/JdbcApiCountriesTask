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
			System.out.println("\nSelect an option");
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
				break;

			case "3":
				break;

			case "4":
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
			for(String s:i.altSpellings) {
				System.out.println("\t"+s);
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

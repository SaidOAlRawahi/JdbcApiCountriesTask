import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {
	static Country[] countries;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean programIsActive = true;
		MainMenu mainMenu = new MainMenu();
		while (programIsActive) {
			mainMenu.showMenu();
			String selection = sc.next();
			switch (selection) {
			case "1":
				try {
					countries = mainMenu.fetchCountriesFromJson();
					System.out.println("Data Fetched Successfully");
					printCountriesDetails(countries);
				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			case "2":
				callDatabaseOptionsMenu();
				break;

			case "3":
				callFileOptionsMenu();
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

	private static void callDatabaseOptionsMenu() {
		DatabaseOptionsMenu menu = new DatabaseOptionsMenu();
		if (!DatabaseOptionsMenu.loggedIn) {
			try {
				menu.login();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		boolean repeatDBOptionsMenu = true;
		while (repeatDBOptionsMenu) {
			menu.showMenu();
			String actionSelected = sc.next();
			switch (actionSelected) {
			
			case "1":
				if (DatabaseOptionsMenu.loggedIn) {
					if (!DatabaseOptionsMenu.dbInitialized) {
						try {
							menu.initializeDB();
						} catch (SQLException e) {
							System.out.println(e);
						}
					} else {
						System.out.println("You Already Have Initialized The Database");
					}
				} else {
					System.out.println("You Should Login First");
				}
				break;

			case "2":
				if (DatabaseOptionsMenu.loggedIn) {
					if(DatabaseOptionsMenu.dbInitialized) {
						try {
							menu.storeCountriesToDB(countries);
						} catch (SQLException e) {
							System.out.println(e);
						}
					}
					else {
						System.out.println("Database Should be Initialized First");
					}
				} else {
					System.out.println("You Should Login First");
				}
				break;

			case "3":
				if (DatabaseOptionsMenu.loggedIn) {
					if(DatabaseOptionsMenu.dbInitialized) {
						menu.getCountriesFromDB();						
					}
					else {
						System.out.println("Database Should be Initialized First");
					}
				} else {
					System.out.println("You Should Login First");
				}
				break;

			case "4":
				if (DatabaseOptionsMenu.loggedIn) {
					if(DatabaseOptionsMenu.dbInitialized) {
						try {
							menu.backupDB();
						} catch (SQLException e) {
							System.out.println(e);
						}						
					}
					else {
						System.out.println("Database Should be Initialized First");
					}
				} else {
					System.out.println("You Should Login First");
				}
				break;

			case "5":
				if (DatabaseOptionsMenu.loggedIn) {
					if(DatabaseOptionsMenu.dbInitialized) {
						try {
							menu.removeTablesFromDB();
						} catch (SQLException e) {
							System.out.println(e);
						}						
					}
					else {
						System.out.println("Database Should be Initialized First");
					}
				} else {
					System.out.println("You Should Login First");
				}
				break;

			case "6":
				try {
					menu.login();
				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			case "7":
				repeatDBOptionsMenu = false;
				break;

			default:
				break;
			}
		}

	}

	static void callFileOptionsMenu() {
		FileOptionsMenu menu = new FileOptionsMenu();
		boolean repeatFileOptionsMenu = true;
		while (repeatFileOptionsMenu) {
			menu.showMenu();
			String actionSelected = sc.next();
			switch (actionSelected) {
			case "1":
				if (countries != null) {
					try {
						menu.writeCountriesInAfile(countries);
					} catch (IOException e) {
						System.out.println(e);
					}
				} else {
					System.out.println("There are no countries to write in a file. Please Fetch some First");
				}
				break;

			case "2":
				try {
					countries = menu.getCountriesFromFile();
					printCountriesDetails(countries);
				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			case "3":
				repeatFileOptionsMenu = false;
				break;

			default:
				System.out.println("Invalid Input");
				break;
			}

		}
	}

	static void printCountriesDetails(Country[] countries) {

		System.out.println(
				"============================================================================================================");
		for (Country i : countries) {
			if (i.name.common.equals("Israel")) {
				continue;
			}
			System.out.println("Name:");
			System.out.println("   Official: " + i.name.official);
			System.out.println("   Common: " + i.name.common);

			if (i.name.nativeName != null) {
				System.out.println("   Native Name:");
				for (String key : i.name.nativeName.keySet()) {
					System.out.println("      " + key + ": ");
					System.out.println("         Official: " + i.name.nativeName.get(key).official);
					System.out.println("         Common: " + i.name.nativeName.get(key).common);
				}
			}

			if (i.tld != null) {
				System.out.println("TLD: ");
				for (String tld : i.tld) {
					System.out.println("   " + tld);
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
					System.out.println("   " + key + ": ");
					System.out.println("      Name: " + i.currencies.get(key).name);
					System.out.println("      Symbol: " + i.currencies.get(key).symbol);
				}
			}

			System.out.println("IDD: ");
			System.out.println("   Root: " + i.idd.root);
			if (i.idd.suffixes != null) {
				System.out.println("   Suffixes: ");
				for (String s : i.idd.suffixes) {
					System.out.println("      " + s);
				}
			}

			if (i.capital != null) {
				System.out.println("Capital: ");
				for (String c : i.capital) {
					System.out.println("   " + c);
				}
			}

			System.out.println("Alt Spellings: ");
			for (String s : i.altSpellings) {
				System.out.println("   " + s);
			}

			System.out.println("Region: " + i.region);
			System.out.println("Sub-Region: " + i.subregion);

			if (i.languages != null) {
				System.out.println("Languages: ");
				for (String key : i.languages.keySet()) {
					System.out.println("   " + key + ": " + i.languages.get(key));
				}
			}

			System.out.println("Translations: ");
			for (String key : i.translations.keySet()) {
				System.out.println("   " + key + ": ");
				System.out.println("      Official: " + i.translations.get(key).official);
				System.out.println("      Common: " + i.translations.get(key).common);
			}

			System.out.println("Latitude & Longitude: ");
			for (float l : i.latlng) {
				System.out.println("    " + l);
			}
			System.out.println("Land Locked: " + i.landlocked);

			if (i.borders != null) {
				System.out.println("Borders: ");
				for (String b : i.borders) {
					System.out.println("   " + b);
				}
			}
			System.out.println("Area: " + i.area);

			if (i.demonyms != null) {
				System.out.println("Demonyms: ");
				for (String key : i.demonyms.keySet()) {
					System.out.println("   " + key + ": ");
					System.out.println("      F: " + i.demonyms.get(key).f);
					System.out.println("      F: " + i.demonyms.get(key).f);
				}
			}

			System.out.println("Flag: " + i.flag);
			System.out.println("   Google Maps: " + i.maps.googleMaps);
			System.out.println("   Open Street Maps: " + i.maps.openStreetMaps);
			System.out.println("Population: " + i.population);
			System.out.println("Fifa: " + i.fifa);

			System.out.println("Car: ");
			if (i.car.signs != null) {
				System.out.println("   Signs:");
				for (String s : i.car.signs) {
					System.out.println("      " + s);
				}
			}
			System.out.println("   Side: " + i.car.side);

			System.out.println("Time Zones:");
			for (String t : i.timezones) {
				System.out.println("   " + t);
			}

			System.out.println("Continents:");
			for (String c : i.continents) {
				System.out.println("   " + c);
			}

			System.out.println("Flags: ");
			System.out.println("   png: " + i.flags.png);
			System.out.println("   svg: " + i.flags.svg);
			System.out.println("   alt: " + i.flags.alt);

			System.out.println("Coat Of Arms: ");
			System.out.println("   png: " + i.coatOfArms.png);
			System.out.println("   svg: " + i.coatOfArms.svg);
			System.out.println("Start Of Week: " + i.startOfWeek);

			if (i.capitalInfo.latlng != null) {
				System.out.println("Capital Info: ");
				System.out.println("   Latitude & Longitude: ");
				for (float l : i.capitalInfo.latlng) {
					System.out.println("      " + l);
				}
			}
			if (i.postalCode != null) {
				System.out.println("Postal Code: ");
				System.out.println("   Regex: " + i.postalCode.regex);
				System.out.println("   Format: " + i.postalCode.format);
			}

			System.out.println(
					"\n============================================================================================================");
		}
	}

}

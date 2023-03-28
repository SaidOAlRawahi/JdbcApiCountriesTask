import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.google.gson.Gson;

public class MainMenu {
	void showMenu() {
		System.out.println("\nSelect An Option");
		System.out.println("1- Fetch Countries From API And Print Them");
		System.out.println("2- DB Options");
		System.out.println("3- File Options");
		System.out.println("4- Search For Countries");
		System.out.println("5- Exit");
	}

	Country[] fetchCountriesFromJson() throws Exception {
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

class DatabaseOptionsMenu {
	static boolean loggedIn = false;
	static Connection con = null;
	static Driver driver;

	void showMenu() {
		System.out.println("\nChoose An Action");
		System.out.println("1- Initialize Database");
		System.out.println("2- Store Countries In Database");
		System.out.println("3- Print Countries From Database");
		System.out.println("4- Backup Database");
		System.out.println("5- Remove Tables From Database");
		System.out.println("6- Login In To Database");
		System.out.println("7- Go Back");
	}

	void login() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (!loggedIn) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter The Database Name: ");
			String dbName = sc.next();
			System.out.print("Enter The Username: ");
			String userName = sc.next();
			System.out.print("Enter The Password: ");
			String passWord = sc.next();

			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + dbName + ";" + "encrypt=true;"
					+ "trustServerCertificate=true";

			driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			con = DriverManager.getConnection(url, userName, passWord);
			loggedIn = true;
			System.out.println("Logged In Successfully");
		} else {
			System.out.println("You Are Already Logged In");
		}
	}

	public void initializeDB() throws SQLException {
		Statement st = con.createStatement();
		String sqlQuery = "create table countries (\r\n" + "	id int primary key,\r\n"
				+ "	common_name varchar(150),\r\n" + "	official_name varchar(150),\r\n" + "	cca2 varchar(150),\r\n"
				+ "	ccn3 varchar(150),\r\n" + "	cca3 varchar(150),\r\n" + "	cioc varchar(150),\r\n"
				+ "	independent bit,\r\n" + "	country_status varchar(150),\r\n" + "	un_member bit,\r\n"
				+ "	idd_root varchar(150),\r\n" + "	region varchar(150),\r\n" + "	subregion varchar(150),\r\n"
				+ "	latitude float,\r\n" + "	logitude float,\r\n" + "	land_locked bit,\r\n" + "	area float,\r\n"
				+ "	eng_f varchar(150),\r\n" + "	eng_m varchar(150),\r\n" + "	fra_f varchar(150),\r\n"
				+ "	fra_m varchar(150),\r\n" + "	flag varchar(150),\r\n" + "	google_maps varchar(150),\r\n"
				+ "	open_street_maps varchar(150),\r\n" + "	c_population int,\r\n" + "	gini_year varchar(150),\r\n"
				+ "	gini_val float,\r\n" + "	fifa varchar(150),\r\n" + "	car_side varchar(150),\r\n"
				+ "	flag_png varchar(150),\r\n" + "	flag_svg varchar(150),\r\n" + "	flag_alt text,\r\n"
				+ "	coa_png varchar(150),\r\n" + "	coa_svg varchar(150),\r\n" + "	start_of_week varchar(150),\r\n"
				+ "	capital_lat float,\r\n" + "	capital_long float,\r\n" + "	postal_format varchar(150),\r\n"
				+ "	postal_regex varchar(250)\r\n" + ");\r\n" + "\r\n" + "create table timezones(\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	tz varchar(150),\r\n" + "	cid int\r\n" + ");\r\n"
				+ "create table car_signs(\r\n" + "	id int primary key identity(1,1),\r\n" + "	csign varchar(150),\r\n"
				+ "	cid int\r\n" + ");\r\n" + "create table borders(\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	border varchar(150),\r\n" + "	cid int\r\n" + ");\r\n" + "create table translations(\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	tran_key varchar(150),\r\n"
				+ "	tran_official varchar(150),\r\n" + "	tran_common varchar(150),\r\n" + "	cid int\r\n" + ");\r\n"
				+ "create table languages (\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	lan_key varchar(150),\r\n" + "	lan_name varchar(150),\r\n" + "	cid int\r\n" + ");\r\n"
				+ "create table alt_spellings(\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	spelling varchar(150),\r\n" + "	cid int\r\n" + ");\r\n" + "create table capitals (\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	capital_name varchar(150),\r\n" + "	cid int\r\n"
				+ ");\r\n" + "create table native_names(\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	name_key varchar(150),\r\n" + "	common_namme varchar(150),\r\n" + "	official_name varchar(150),\r\n"
				+ "	cid int \r\n" + ");\r\n" + "create table tlds(\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	tld varchar(150),\r\n" + "	cid int\r\n" + ");\r\n" + "create table currencies(\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	currency_key varchar(150),\r\n"
				+ "	currency_name varchar(150),\r\n" + "	symbol varchar(150),\r\n" + "	cid int\r\n" + ");\r\n"
				+ "create table suffixes(\r\n" + "	id int primary key identity(1,1),\r\n" + "	suf varchar(150),\r\n"
				+ "	cid int\r\n" + ");";
		st.executeUpdate(sqlQuery);
		System.out.println("Database is initialized successfully");
	}

	public void storeCountriesToDB(Country[] countries) throws SQLException {
		PreparedStatement st = con.prepareStatement(
				"INSERT INTO countries (common_name,official_name,cca2,ccn3,cca3,cioc,independent,country_status,un_member,idd_root,region,subregion,latitude,logitude,land_locked,"
						+ "area,eng_f,eng_m,fra_f,fra_m,flag,google_maps,open_street_maps,c_population,gini_year,gini_val,fifa,car_side,flag_png,flag_svg,flag_alt,"
						+ "coa_png,coa_svg,start_of_week,capital_lat,capital_long,postal_format,postal_regex,id) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement st2 = con.prepareStatement("INSERT INTO suffixes (suf,cid) VALUES (?,?)");
		PreparedStatement st3 = con
				.prepareStatement("INSERT INTO currencies (currency_key,currency_name,symbol,cid) VALUES (?,?,?,?)");
		PreparedStatement st4 = con.prepareStatement("INSERT INTO tlds (tld,cid) VALUES (?,?)");
		PreparedStatement st5 = con.prepareStatement(
				"INSERT INTO native_names (name_key,common_namme,official_name,cid) VALUES (?,?,?,?)");
		PreparedStatement st6 = con.prepareStatement("INSERT INTO capitals (capital_name,cid) VALUES (?,?)");
		PreparedStatement st7 = con.prepareStatement("INSERT INTO alt_spellings (spelling,cid) VALUES (?,?)");
		PreparedStatement st8 = con.prepareStatement("INSERT INTO languages (lan_key,lan_name,cid) VALUES (?,?,?)");
		for (int i = 0; i < countries.length; i++) {
			st.setInt(39, i);
			st.setString(1, countries[i].name.common);
			st.setString(2, countries[i].name.official);
			st.setString(3, countries[i].cca2);
			st.setString(4, countries[i].ccn3);
			st.setString(5, countries[i].cca3);
			st.setString(6, countries[i].cioc);
			st.setBoolean(7, countries[i].independent);
			st.setString(8, countries[i].status);
			st.setBoolean(9, countries[i].unMember);
			st.setString(10, countries[i].idd.root);
			st.setString(11, countries[i].region);
			st.setString(12, countries[i].subregion);
			st.setFloat(13, countries[i].latlng[0]);
			st.setFloat(14, countries[i].latlng[1]);
			st.setBoolean(15, countries[i].landlocked);
			st.setFloat(16, countries[i].area);
			if (countries[i].demonyms != null) {
				if (countries[i].demonyms.get("eng") != null) {
					st.setString(17, countries[i].demonyms.get("eng").f);
					st.setString(18, countries[i].demonyms.get("eng").m);
				}
				if (countries[i].demonyms.get("fra") != null) {
					st.setString(19, countries[i].demonyms.get("fra").f);
					st.setString(20, countries[i].demonyms.get("fra").m);
				}
			}
			st.setString(21, countries[i].flag);
			st.setString(22, countries[i].maps.googleMaps);
			st.setString(23, countries[i].maps.openStreetMaps);
			st.setInt(24, countries[i].population);
			if (countries[i].gini != null)
				for (String key : countries[i].gini.keySet()) {
					st.setString(25, key);
					st.setFloat(26, countries[i].gini.get(key));
				}
			st.setString(27, countries[i].fifa);
			st.setString(28, countries[i].car.side);
			st.setString(29, countries[i].flags.png);
			st.setString(30, countries[i].flags.svg);
			st.setString(31, countries[i].flags.alt);
			st.setString(32, countries[i].coatOfArms.png);
			st.setString(33, countries[i].coatOfArms.svg);
			st.setString(34, countries[i].startOfWeek);
			if (countries[i].capitalInfo.latlng != null) {
				st.setFloat(35, countries[i].capitalInfo.latlng[0]);
				st.setFloat(36, countries[i].capitalInfo.latlng[1]);
			}
			if (countries[i].postalCode != null) {
				st.setString(37, countries[i].postalCode.format);
				st.setString(38, countries[i].postalCode.regex);
			}
			st.addBatch();

			if (countries[i].idd.suffixes != null)
				for (int j = 0; j < countries[i].idd.suffixes.length; j++) {
					st2.setString(1, countries[i].idd.suffixes[j]);
					st2.setInt(2, i);
					st2.addBatch();
				}

			if (countries[i].currencies != null)
				for (String key : countries[i].currencies.keySet()) {
					st3.setString(1, key);
					st3.setString(2, countries[i].currencies.get(key).name);
					st3.setString(3, countries[i].currencies.get(key).symbol);
					st3.setInt(4, i);
					st3.addBatch();
				}

			if (countries[i].tld != null)
				for (String tld : countries[i].tld) {
					st4.setString(1, tld);
					st4.setInt(2, i);
					st4.addBatch();
				}

			if (countries[i].name.nativeName != null)
				for (String key : countries[i].name.nativeName.keySet()) {
					st5.setString(1, key);
					st5.setNString(2, countries[i].name.nativeName.get(key).common);
					st5.setNString(3, countries[i].name.nativeName.get(key).official);
					st5.setInt(4, i);
					st5.addBatch();
				}

			if (countries[i].capital != null)
				for (String c : countries[i].capital) {
					st6.setString(1, c);
					st6.setInt(2, i);
					st6.addBatch();
				}

			if (countries[i].altSpellings != null)
				for (String alt : countries[i].altSpellings) {
					st7.setString(1, alt);
					st7.setInt(2, i);
					st7.addBatch();
				}
			
			if(countries[i].languages!=null)
				for (String key: countries[i].languages.keySet()) {
					st8.setString(1, key);
					st8.setString(2, countries[i].languages.get(key));
					st8.setInt(3, i);
					st8.addBatch();
				}
		}
		st.executeBatch();
		st2.executeBatch();
		st3.executeBatch();
		st4.executeBatch();
		st5.executeBatch();
		st6.executeBatch();
		st7.executeBatch();
		st8.executeBatch();
	}

	public void getCountriesFromDB() {
		// TODO Auto-generated method stub

	}

	public void backupDB() throws SQLException {
		Statement st = con.createStatement();
		String sqlQuery = "BACKUP DATABASE cdb\r\n"
				+ "TO DISK = 'C:\\Users\\Lenovo\\Desktop\\eclipes-workspace\\Countries\\backup.bak';";
		st.executeUpdate(sqlQuery);
		System.out.println("Tables Deleted Successfully");
	}

	public void removeTablesFromDB() throws SQLException {
		Statement st = con.createStatement();
		String sqlQuery = "drop table countries,suffixes,currencies,tlds,native_names,capitals,alt_spellings,languages,translations,borders,car_signs,timezones;";
		st.executeUpdate(sqlQuery);
		System.out.println("Tables Deleted Successfully");
	}

}

class FileOptionsMenu {
	void showMenu() {
		System.out.println("\nChoose An Action");
		System.out.println("1- Write Existing Countries In A File");
		System.out.println("2- Print Countries From The File");
		System.out.println("3- Go Back");
	}

	void writeCountriesInAfile(Country[] countries) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Counties.txt"));
		out.writeObject(countries);
		out.close();
	}

	Country[] getCountriesFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("Counties.txt"));
		Country[] countries = (Country[]) in.readObject();
		in.close();
		return countries;
	}
}

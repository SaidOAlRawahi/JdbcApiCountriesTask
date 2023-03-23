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
	static boolean dbInitialized = false;
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
		String sqlQuery = "create table countries (\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	common_name varchar(30),\r\n" + "	official_name varchar(30),\r\n" + "	cca2 varchar(5),\r\n"
				+ "	ccn3 varchar(5),\r\n" + "	cca3 varchar(5),\r\n" + "	cioc varchar(5),\r\n"
				+ "	independent binary,\r\n" + "	country_status varchar(20),\r\n" + "	un_member binary,\r\n"
				+ "	idd_root varchar(5),\r\n" + "	region varchar(20),\r\n" + "	subregion varchar(20),\r\n"
				+ "	latitude float,\r\n" + "	logitude float,\r\n" + "	land_locked binary,\r\n"
				+ "	area float,\r\n" + "	eng_f varchar(20),\r\n" + "	eng_m varchar(20),\r\n"
				+ "	fra_f varchar(20),\r\n" + "	fra_m varchar(20),\r\n" + "	flag varchar(100),\r\n"
				+ "	google_maps varchar(100),\r\n" + "	open_street_maps varchar(100),\r\n" + "	c_population int,\r\n"
				+ "	gini_year varchar(4),\r\n" + "	gini_val float,\r\n" + "	fifa varchar(5),\r\n"
				+ "	car_side varchar(10),\r\n" + "	flag_png varchar(100),\r\n" + "	flag_svg varchar(100),\r\n"
				+ "	flag_alt text,\r\n" + "	coa_png varchar(100),\r\n" + "	coa_svg varchar(100),\r\n"
				+ "	start_of_week varchar(10),\r\n" + "	capital_lat float,\r\n" + "	capital_long float,\r\n"
				+ "	postal_format varchar(10),\r\n" + "	postal_regex varchar(20)\r\n" + ");\r\n" + "\r\n"
				+ "create table timezones(\r\n" + "	id int primary key identity(1,1),\r\n" + "	tz varchar(15),\r\n"
				+ "	cid int\r\n" + ");\r\n" + "create table car_signs(\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	csign varchar(10),\r\n" + "	cid int\r\n" + ");\r\n" + "create table borders(\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	border varchar(5),\r\n" + "	cid int\r\n" + ");\r\n"
				+ "create table translations(\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	tran_key varchar(5),\r\n" + "	tran_official varchar(20),\r\n" + "	tran_common varchar(20),\r\n"
				+ "	cid int\r\n" + ");\r\n" + "create table languages (\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	lan_key varchar(5),\r\n"
				+ "	lan_name varchar(15),\r\n" + "	cid int\r\n" + ");\r\n" + "create table alt_spellings(\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	spelling varchar(20),\r\n" + "	cid int\r\n" + ");\r\n"
				+ "create table capitals (\r\n" + "	id int primary key identity(1,1),\r\n"
				+ "	capital_name varchar(20),\r\n" + "	cid int\r\n" + ");\r\n" + "create table native_names(\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	name_key varchar(5),\r\n"
				+ "	common_namme varchar(30),\r\n" + "	official_name varchar(30),\r\n" + "	cid int \r\n" + ");\r\n"
				+ "create table tlds(\r\n" + "	id int primary key identity(1,1),\r\n" + "	tld varchar(5),\r\n"
				+ "	cid int\r\n" + ");\r\n" + "create table currencies(\r\n"
				+ "	id int primary key identity(1,1),\r\n" + "	currency_key varchar(5),\r\n"
				+ "	currency_name varchar(20),\r\n" + "	symbol varchar(20),\r\n" + "	cid int\r\n" + ");\r\n"
				+ "create table suffixes(\r\n" + "	id int primary key identity(1,1),\r\n" + "	suf varchar(5),\r\n"
				+ "	cid int\r\n" + ");";
		st.executeUpdate(sqlQuery);
		dbInitialized = true;
		System.out.println("Database is initialized successfully");
	}

	public void storeCountriesToDB(Country[] countries) throws SQLException {
		for (Country c : countries) {
			PreparedStatement st = con.prepareStatement("INSERT INTO countries VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
		}
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
		dbInitialized = false;
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

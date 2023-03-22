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
import java.sql.SQLException;
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
			
			String url = "jdbc:sqlserver://localhost:1433;" +
	                "databaseName="+dbName+";" +
	                "encrypt=true;" +
	                "trustServerCertificate=true";
			
			driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url, userName, passWord);
            System.out.println("Logged In Successfully");
		}
		else {
			System.out.println("You Are Already Logged In");
		}
	}

	public void initializeDB() {
		// TODO Auto-generated method stub
		
	}

	public void storeCountriesToDB() {
		// TODO Auto-generated method stub
		
	}

	public void getCountriesFromDB() {
		// TODO Auto-generated method stub
		
	}

	public void backupDB() {
		// TODO Auto-generated method stub
		
	}

	public void removeTablesFromDB() {
		// TODO Auto-generated method stub
		
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

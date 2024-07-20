import java.sql.*;
import java.util.*;
public class Main {
    private static final String url = "*********************";
    private static final String username = "***********";
    private static final String password = "***********";

    public static void main(String[] args) {
        String nam = "Raj";
        String pas = "Raj123";
        Scanner scanner = new Scanner(System.in);
        driver dr = new driver();
        System.out.println("Enter username : ");
        String unam = scanner.next();
        System.out.println("Enter password : ");
        String upas = scanner.next();
           try {
              Class.forName("com.mysql.cj.jdbc.Driver");
           }
           catch (Exception e){
               System.out.println("No Class Load");
           }

        if(unam.equals(nam) && upas.equals(pas)) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                while (true) {
                System.out.println("Enter number from following option : ");
                System.out.println("1. Add users");
                System.out.println("2. Display buses");
                System.out.println("3. Book bus");
                System.out.println("4. Return bus");
                int a = scanner.nextInt();

                    switch (a) {
                        case 1:
                            dr.newperson(connection);
                            break;
                        case 2:
                            dr.showbus(connection);
                            break;
                        case 3:
                            System.out.println("Welcome...");
                            dr.bookbus(connection);
                            break;
                        case 4:
                            System.out.println("Welcome back...");
                            dr.ret(connection);
                            break;
                        case 5:
                            break;
                    }
                    System.out.println("Enter the choice Y/N : ");
                    String choice = scanner.next();
                    String ch = choice.toUpperCase();
                    if(ch.equals("N")){
                    break;
                    }
                }
            } catch (Exception e) {
                System.out.println("No Connection");
            }
        }
        else {
            try {
                Connection connection = DriverManager.getConnection(url,username,password);
                System.out.println("Wrong password or username");
            }
            catch (Exception e) {
                System.out.println("hgh");
            }

        }
    }
}

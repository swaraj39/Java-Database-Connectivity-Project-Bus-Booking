import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class driver extends Main {
    Scanner scan = new Scanner(System.in);

    public void showbus(Connection connection) {
        try {
            System.out.println("The List of Buses are : ");
            String query = "SELECT * FROM bus";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("+----------+--------+-----------------+-----------------+----------+--------------+");
            System.out.printf("| %-8s | %-6s | %-15s | %-15s | %-8s | %-12s |\n", "Bus No", "Rent", "Start", "End", "Capacity", "Available");
            System.out.println("+----------+--------+-----------------+-----------------+----------+--------------+");

            while (resultSet.next()) {
                String busNo = resultSet.getString("busno");
                int rent = resultSet.getInt("rent");
                String start = resultSet.getString("start");
                String end = resultSet.getString("end");
                int capacity = resultSet.getInt("capacity");
                String avail = resultSet.getString("avail");

                System.out.printf("| %-8s | %-6d | %-15s | %-15s | %-8d | %-12s |\n", busNo, rent, start, end, capacity, avail);
                System.out.println("+----------+--------+-----------------+-----------------+----------+--------------+");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void newperson(Connection connection) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter new user name : ");
        String newnam = scan.next();
        System.out.println("Enter " + newnam + " location : ");
        String loc = scan.next();
        System.out.println("Enter no of bus booked : ");
        int b = scan.nextInt();
        try {
            String insquery = "INSERT INTO users(name,location,bookbus) VALUES (?,?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(insquery);
            preparedStatement1.setString(1,newnam);
            preparedStatement1.setString(2,loc);
            preparedStatement1.setInt(3,b);
            preparedStatement1.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("e");
        }
    }
public void increment(String name ,Connection connection) {
        try {
            String query = "UPDATE users SET bookbus = bookbus + 1 WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
        }
}
    public void bookbus(Connection connection){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the name : ");
            String nam = scan.next();
            System.out.println("Enter the startpoint : ");
            String start = scan.next();
            System.out.println("Enter the endpoint : ");
            String end = scan.next();
            System.out.println("Enter the capacity : ");
            int cap = scan.nextInt();
            System.out.println("Enter the kilometre : ");
            int km = scan.nextInt();
            String query = "SELECT * FROM bus WHERE start = ? and end = ? and capacity > ? and avail = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, start);
            preparedStatement.setString(2, end);
            preparedStatement.setInt(3, cap);
            preparedStatement.setString(4, "yes");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("+--------+------+------------+----------+----------+");
            System.out.println("| Bus No | Rent | Start      | End      | Capacity |");
            System.out.println("+--------+------+------------+----------+----------+");
            while (resultSet.next()) {
                String busNo = resultSet.getString("busno");
                int rent = resultSet.getInt("rent");
                String start1 = resultSet.getString("start");
                String end1 = resultSet.getString("end");
                int capacity = resultSet.getInt("capacity");
                System.out.printf("| %-6s | %-4d | %-10s | %-8s | %-8d |\n", busNo, rent, start1, end1, capacity);
            }
            System.out.println("+--------+------+------------+----------+----------+");
            resultSet.close();
            System.out.println("Enter the busno which you selected : ");
            String no = scan.next();
            String query2 = "SELECT * FROM bus WHERE busno = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
            preparedStatement1.setString(1,no);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            int bhada;
            float r = 0;
            while (resultSet1.next()) {
                bhada = resultSet1.getInt("rent");
                float rent = bhada*km;
                r = rent;
                float per_person = 0;
                System.out.println("The total cost is : " + rent+"Rs");
                per_person = rent/cap;
                System.out.println("For Per person cost will be : " + per_person);
            }
            System.out.println("This is the total rent wannna continue ??? say yes if u are");
            String choice = scan.next();
            if (choice.equals("yes")){
                String query3 = "INSERT INTO book(name, busno, start, end, rent) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(query3);
                preparedStatement2.setString(1,nam);
                preparedStatement2.setString(2,no);
                preparedStatement2.setString(3,start);
                preparedStatement2.setString(4,end);
                preparedStatement2.setFloat(5,r);
                int a = preparedStatement2.executeUpdate();
                if (a>0) {
                    System.out.println("Congrats you book bus");
                    System.out.println("┌────────────────────────────────┐");
                    System.out.printf("│ %-30s │\n", "Receipt");
                    System.out.println("├────────────────────────────────┤");
                    System.out.printf("│ %-10s: %-18s │\n", "Bus No", no);
                    System.out.printf("│ %-10s: %-18s │\n", "Start", start);
                    System.out.printf("│ %-10s: %-18s │\n", "End", end);
                    System.out.printf("│ %-10s: %-18.2f │\n", "Rent (Rs)", r);
                    System.out.println("└────────────────────────────────┘");
                    increment(nam,connection);
                }
                String query4 = "UPDATE bus SET avail = ? WHERE busno = ?";
                PreparedStatement preparedStatement3 = connection.prepareStatement(query4);
                preparedStatement3.setString(1,"no");
                preparedStatement3.setString(2,no);
                int b = preparedStatement3.executeUpdate();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void ret(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("enter the Name : ");
            String name = scanner.next();
            System.out.println("enter the Bus Number : ");
            String busno1 = scanner.next();
            String query = "SELECT * FROM book WHERE busno = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,busno1);
            ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("+--------+------+------------+----------+----------+");
                System.out.println("| Name | Bus no | Start      | End      | Rent |");
                System.out.println("+--------+------+------------+----------+----------+");
                while (resultSet.next()){
                    String name1 = resultSet.getString("name");
                    String busNo = resultSet.getString("busno");
                    String start1 = resultSet.getString("start");
                    String end1 = resultSet.getString("end");
                    int rent = resultSet.getInt("rent");
                    System.out.printf("| %-6s | %-4s | %-10s | %-8s | %-8d |\n", name1, busNo, start1, end1, rent);
                }
                System.out.println("+--------+------+------------+----------+----------+");
                resultSet.close();
            String query1 = "UPDATE bus SET avail = ? WHERE busno = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setString(1,"yes");
            preparedStatement1.setString(2,busno1);
            int a = preparedStatement1.executeUpdate();
            if (a>0) {
                System.out.println("You return the bus successfully");
                System.out.println("Must come again");
            }
        }
        catch (Exception e ) {
            System.out.println(e);
        }

    }
}

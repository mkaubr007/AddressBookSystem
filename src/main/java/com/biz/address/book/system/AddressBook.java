package com.biz.address.book.system;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class AddressBook {
    List<Contacts> list = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public AddressBook(List<Contacts> contactsList) {
        list.addAll(contactsList);
    }

    public AddressBook() {
    }

    public void operation() {
        System.out.println("Enter Number of contact you want to add");
        int count = scan.nextInt();
        int contactCount = 1;
        while (contactCount <= count) {

            this.add();
            contactCount++;
        }
        boolean status = true;
        do {

            System.out.println("Choose Operation you want to do");
            System.out.println("1. Add\t2.Edit\t3.Delete\t4.sortCity\t5.sortState\t6.show\t7.Count\t8.SortByName\t9.SortByZip");

            switch (scan.nextInt()) {
                case 1:
                    add();
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    sortCity();
                case 5:
                    sortState();
                case 6:
                    print();
                case 7:
                    countList();
                case 8:
                    sortByFirstName();
                case 9:
                    sortByZip();
                default:
                    status =false;
            }
        } while (status);
    }

    public void add() {
        Contacts contacts = new Contacts();
        System.out.println("Enter the First name:");
        String firstName = scan.next();
        contacts.setFirstName(firstName);

        System.out.println("Enter the Last name:");
        String lastName = scan.next();
        contacts.setLastName(lastName);

        System.out.println("Enter the address:");
        String address = scan.next();
        contacts.setAddress(address);

        System.out.println("Enter the City:");
        String city = scan.next();
        contacts.setCity(city);

        System.out.println("Enter the State:");
        String state = scan.next();
        contacts.setState(state);

        System.out.println("Enter the Zip:");
        Long zip = scan.nextLong();
        contacts.setZip(zip);

        System.out.println("Enter the Phone Number:");
        Long phoneNumber = scan.nextLong();
        contacts.setPhoneNumber(phoneNumber);

        System.out.println("Enter the Email");
        String email = scan.next();
        contacts.setEmail(email);
        this.list.add(contacts);
        print();
    }

    public void edit() {
        System.out.println("Enter your First name:");
        String firstName = scan.next();

        Iterator<Contacts> iterator = this.list.listIterator();

        while (iterator.hasNext()) {
            Contacts contacts = iterator.next();

            if (firstName.equals(contacts.getFirstName())) {
                System.out.println("Choose field you want to add:");
                System.out.println("1.Last Name\t2.Address\t3.City\t4.State\t5. Zip\t6.Phone Number\t7.Email");
                switch (scan.nextInt()) {
                    case 1:
                        System.out.println("Re-Correct your Last Name");
                        contacts.setLastName(scan.next());
                        break;
                    case 2:
                        System.out.println("Re-Correct your Address");
                        contacts.setAddress(scan.next());
                        break;
                    case 3:
                        System.out.println("Re-Correct your City");
                        contacts.setCity(scan.next());
                        break;
                    case 4:
                        System.out.println("Re-Correct your State");
                        contacts.setState(scan.next());
                        break;
                    case 5:
                        System.out.println("Re-Correct your Zip");
                        contacts.setZip(scan.nextLong());
                        break;
                    case 6:
                        System.out.println("Re-Correct your Phone Number");
                        contacts.setPhoneNumber(scan.nextLong());
                    case 7:
                        System.out.println("Re-Correct your Email");
                        contacts.setEmail(scan.next());
                }

            }
        }
    }

    public void delete() {
        System.out.println("Enter your First name:");
        String firstName = scan.next();

        Iterator<Contacts> iterator = list.listIterator();
        while (iterator.hasNext()) {
            Contacts contacts = iterator.next();

            if (firstName.equals(contacts.getFirstName())) {
                list.remove(contacts);
            }
        }
    }

    public void print() {
        Iterator<Contacts> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
    public void countList(){
        Long total=list.stream().count();
        System.out.println(total);
    }
    public void sortByZip() {Collections.sort(list,Sort.compareZip);}
    public  void sortByFirstName(){Collections.sort(list,Sort.compareFirstName);}
    public void sortCity() {
        Collections.sort(list,Sort.compareCity);
    }
    public void sortState() {
        Collections.sort(list,Sort.compareState);
    }

    public void writeFileData(String pathName){
        StringBuffer addressDataBuffer=new StringBuffer();
        list.forEach(data->{
            System.out.println(data);
            String dataString=data.toString().concat("\n");
            addressDataBuffer.append(dataString);
        });
        try {
            Files.write(Paths.get(pathName),addressDataBuffer.toString().getBytes());
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public void writeJsonData(String pathName){
        StringBuffer addressDataBuffer=new StringBuffer();
        list.forEach(data->{
            System.out.println(data);
            Gson gson=new Gson();
            String l=gson.toJson(list);
            addressDataBuffer.append(l);
        });
        try {
            Files.write(Paths.get(pathName),addressDataBuffer.toString().getBytes());
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public long readFile(String pathName){
        long entries=0;
        try{
            entries=Files.lines(Paths.get(pathName)).count();
        }catch (IOException e){
            System.out.println(e);
        }
        return entries;
    }
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/address_book_system";
        String userName = "root";
        String password = "Mkaubr007@gmail.com";
        Connection connection = null;
        try {
            //1 . load and register the driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return connection;
    }
    public List<Contacts> retrieveData() throws SQLException {
        ResultSet resultSet = null;
        List<Contacts> employeeInfoList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from address_book;";
            resultSet = statement.executeQuery(sql);
            int count = 1;
            while (resultSet.next()) {
                count++;
                Contacts contacts = new Contacts();
                contacts.setFirstName(resultSet.getString("firstName"));
                contacts.setLastName(resultSet.getString("lastName"));
                contacts.setAddress(resultSet.getString("address"));
                contacts.setCity(resultSet.getString("city"));
                contacts.setState(resultSet.getString("state"));
                contacts.setZip(resultSet.getLong("zip"));
                contacts.setPhoneNumber(resultSet.getLong("phoneNumber"));
                contacts.setEmail(resultSet.getString("email"));
                employeeInfoList.add(contacts);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return employeeInfoList;
    }
    public void updateContacts(long phoneNumber, long zip) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "update address_book set zip=" + zip + " where phoneNumber=" + phoneNumber + "";
            int result = statement.executeUpdate(query);
            if (result == 1)
                System.out.println("zip updated");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public List<Contacts> retrieveDataByValue() throws SQLException {
        ResultSet resultSet = null;
        List<Contacts> employeeInfoList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from address_book where start_date between '2020-08-01' AND '2020-09-30'";
            resultSet = statement.executeQuery(sql);
            int count = 1;
            while (resultSet.next()) {
                count++;
                Contacts contacts = new Contacts();
                contacts.setFirstName(resultSet.getString("firstName"));
                contacts.setLastName(resultSet.getString("lastName"));
                contacts.setAddress(resultSet.getString("address"));
                contacts.setCity(resultSet.getString("city"));
                contacts.setState(resultSet.getString("state"));
                contacts.setZip(resultSet.getLong("zip"));
                contacts.setPhoneNumber(resultSet.getLong("phoneNumber"));
                contacts.setEmail(resultSet.getString("email"));
                System.out.println(employeeInfoList.add(contacts));;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return employeeInfoList;
    }
    @Override
    public String toString() {
        return "AddressBook{" +
                "list=" + list +
                '}';
    }
}

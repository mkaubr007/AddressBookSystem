package com.biz.address.book.system;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {
    static Scanner scan = new Scanner(System.in);
    static AddressBook addressBook = new AddressBook();
    public static void main(String[] args) throws SQLException {
        AddressBookMain addressBookMain=new AddressBookMain();
        addressBookMain.retrieveData();
        addressBookMain.updateContacts();
        addressBookMain.retrieveDataBetweenRange();
        addressBookMain.retrieveDataByValue();
        addressBookMain.insertData();
        System.out.println("Welcome to Address Book System");
        Map<String, AddressBook> map = new HashMap<>();
        System.out.println("Enter Number of address book you want to add");
        Integer count = scan.nextInt();
        Integer i = 0;
        while (i < count) {
            System.out.println("Enter  book name");
            String bookName = scan.next();
            addressBook.operation();
            map.put(bookName, addressBook);
            i++;
        }
        System.out.println(map);
    }
    private void retrieveData() throws SQLException {
        List<Contacts> addressBookList = addressBook.retrieveData();
        System.out.println(addressBookList);
    }
    private void updateContacts(){
        addressBook.updateContacts(8002695700l,824123l);
    }
    private void retrieveDataBetweenRange()throws SQLException {
        List<Contacts> addressBookList = addressBook.retrieveDataBetweenRange();
        System.out.println(addressBookList);
    }
    private void retrieveDataByValue() throws SQLException {
        List<Contacts> addressBookList = addressBook.retrieveDataByValue();
        System.out.println(addressBookList);
    }
    private void insertData() {
        Contacts info = new Contacts();
        info.setFirstName("Vikram");
        info.setLastName("Singh");
        info.setAddress("Aurangabad");
        info.setCity("Jaipur");
        info.setState("Rajasthan");
        info.setZip(741852l);
        info.setPhoneNumber(9771680000l);
        info.setEmail("rajavikramsingh01@gmail.com");
        addressBook.insertData(info);
    }
}
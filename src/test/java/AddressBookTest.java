import com.biz.address.book.system.AddressBook;
import com.biz.address.book.system.Contacts;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddressBookTest {
    @Test
    public void givenContactsEntries_WhenWrittenToFileShouldMatchAddressEntries() {
        List<Contacts> contactsList = new ArrayList<>();
        contactsList.add(new Contacts("Manish", "Kumar", "Aurangabad", "Aurangabad", "Bihar", 42552l, 8002696700l, "mkaubr007@gmail.com"));
        contactsList.add(new Contacts("Rohit", "Kumar", "Patna", "Patna", "Bihar", 3434l, 7646778838l, "rohitk01@gmail.com"));
        AddressBook addressBook=new AddressBook(contactsList);
        addressBook.writeFileData();
    }
    @Test
    public void givenContactsEntries_WhenReadingTotalFileShouldMatch(){
        AddressBook service=new AddressBook();
        long entries=service.readFile();
        Assert.assertEquals(2,entries);
    }

}

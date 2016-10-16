
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Created by JP on 10/15/2016.
 */
public class ServerTest {
    @Test
    public void TestConstructorOne() {
        Server test = new Server("address", "user", "password");

        assertEquals(test.getAddress(), "address");
        assertEquals(test.getUsername(), "user");
        assertEquals(test.getPassword(), "password");

        // port should default to 21
        assertEquals(test.getPort(), (Integer)21);
    }

    @Test
    public void TestConstructorTwo() {
        Server test = new Server("address", "user", "password", 1);

        assertEquals(test.getAddress(), "address");
        assertEquals(test.getUsername(), "user");
        assertEquals(test.getPassword(), "password");
        assertEquals(test.getPort(), (Integer)1);
    }

    @Test(expected=Exception.class)
    public void TestBadConnect() throws Exception {
        Server test = new Server("this can't be the name of a server.", "","");

        test.connect();

        assertEquals(test.isConnected(), false);
    }

    @Test
    public void TestGoodConnect() throws Exception {
        Server test = new Server("speedtest.tele2.net", "anonymous", "anonymous");

        test.connect();

        assertEquals(test.isConnected(), true);
    }

    @Test
    public void TestList() throws Exception {
        Server test = new Server("speedtest.tele2.net", "anonymous", "anonymous");
        test.connect();

        List<FTPFile> files = test.list("");

        assertNotEquals(files, null);
        //assertTrue(files.size() > 0);
    }
}

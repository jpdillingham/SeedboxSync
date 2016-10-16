import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by JP on 10/15/2016.
 */
public class ServerTest {
    @Test
    public void TestConstructorOne()
    {
        Server test = new Server("address", "user", "password");

        assertEquals(test.getAddress(), "address");
        assertEquals(test.getUsername(), "user");
        assertEquals(test.getPassword(), "password");

        // port should default to 21
        assertEquals(test.getPort(), (Integer)21);

    }
}

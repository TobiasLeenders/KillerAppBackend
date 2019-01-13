import com.google.common.hash.Hashing;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HashString;

import java.nio.charset.StandardCharsets;

public class HashingTest {

    HashString hashing = new HashString();

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void hashingTest(){
        String originalString = "UnitTest";
        String hashedString = hashing.hashString(originalString);
        Assert.assertNotSame(originalString, hashedString);

        String hashedString2 = Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
        Assert.assertEquals(hashedString, hashedString2);

    }

}
package shpp.mentor;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ScriptMaxStoreTest {
    MongoCollection connectionToDB= Mockito.mock(MongoCollection.class);
    @Test
    void getData() {
        Document actual = ScriptMaxStore.getData(connectionToDB, 0);
        Document expected = null;
        Assertions.assertEquals(actual, expected);
    }
}
package shpp.mentor;

import com.mongodb.client.MongoCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

class StreamProduceTest {
    Statement myInstance = Mockito.mock(Statement.class);
    Logger testLogger = Mockito.mock(Logger.class);
    @Test
    void loadStreamLots() {
        MongoCollection connectionToDB=Mockito.mock(MongoCollection.class);
        AtomicInteger actual = new StreamProduce(testLogger).loadStreamLots(connectionToDB,
                1,
                5,
                5,
                30);
        int expected = 1;
        Assertions.assertEquals(actual.intValue(), expected);
    }

    @Test
    void loadStreamTypes() {
        MongoCollection connectionToDB=Mockito.mock(MongoCollection.class);
        AtomicInteger actual = new StreamProduce(testLogger).loadStreamTypes(connectionToDB,
                "Drinks");
        int expected = 1;
        Assertions.assertEquals(actual.intValue(), expected);
    }

    @Test
    void loadStreamStores() {
        MongoCollection connectionToDB=Mockito.mock(MongoCollection.class);
        AtomicInteger actual = new StreamProduce(testLogger).loadStreamStores(connectionToDB,
                1, "st.");
        int expected = 1;
        Assertions.assertEquals(actual.intValue(), expected);
    }
}
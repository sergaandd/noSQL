package shpp.mentor;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mongodb.client.model.Filters.eq;

public class App {
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver")
                .setLevel(Level.WARN);
        Properties myProp = PropertyFileOpen.openPropertyFile();
        Long start = System.currentTimeMillis();
        MongoClient mongoClient = MongoClients.create(myProp.getProperty("URL"));
        MongoDatabase database = mongoClient.getDatabase("nosql");
        MongoCollection collectionLots = database.getCollection("store_lots");
        MongoCollection collectionTypes = database.getCollection("types");
        MongoCollection collectionStores = database.getCollection("stores");

        collectionStores.drop();
        collectionTypes.drop();
        collectionLots.drop();

        AtomicInteger resultSession = new StreamProduce(logger).loadStreamLots(collectionLots,
                Integer.parseInt(myProp.getProperty("qtyLots")),
                Integer.parseInt(myProp.getProperty("qtyStores")),
                Integer.parseInt(myProp.getProperty("qtyTypes")),
                Integer.parseInt(myProp.getProperty("qty")));
        logger.info("Insert in collection LOTS {} records", resultSession);

        resultSession = new StreamProduce(logger).loadStreamTypes(collectionTypes,
                myProp.getProperty("typesList"));
        logger.info("Insert in collection TYPES {} records", resultSession);

        resultSession = new StreamProduce(logger).loadStreamStores(collectionStores,
                Integer.parseInt(myProp.getProperty("qtyStores")), myProp.getProperty("storeAddressPrefix"));
        logger.info("Insert in collection STORES {} records", resultSession);

        logger.info("Creating and inserting collections {} ms", (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();

        String consoleType=System.getProperty("type")==null?"Drinks":System.getProperty("type");
        Document myType = (Document) collectionTypes.find(eq("type_name", consoleType)).first();

        Document result = ScriptMaxStore.getData(collectionLots, Integer.parseInt(myType.get("_id").toString()));

        Document myStore = (Document) collectionStores.find(eq("_id", Integer.parseInt(result.get("_id").toString()))).first();
        logger.info("Max qty of products group {} maxQty={} in {} ,{}.", myType.get("type_name"), result.get("maxQty"),
                myStore.get("store_name"), myStore.get("store_address"));
        logger.info("Executing query from collections {} ms", (System.currentTimeMillis() - start));
    }
}

package shpp.mentor;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class StreamProduce {
    private final Logger logger;
    public static final int INSERT_PACK = 100000;
    public StreamProduce(Logger logger) {

        this.logger = logger;
    }
    public AtomicInteger loadStreamLots(MongoCollection connection,int qtyElements, int qtyArg1, int qtyArg2, int qtyArg3) {
        AtomicInteger counter = new AtomicInteger();
        List<Document> docs = new ArrayList<>();
        Stream.generate(() -> new DTO().setName(DTOSubFunction.getName())
                        .setArg1(DTOSubFunction.getNumeric(1, qtyArg1))
                        .setArg2(DTOSubFunction.getNumeric(1, qtyArg2))
                        .setArg3(DTOSubFunction.getNumericFloat(10, qtyArg3)))
                .limit(qtyElements)
                .forEach((p) -> {
                            if (DTOvalidation.getValidate(p).isEmpty()) {
                                counter.getAndIncrement();
                                docs.add(Document.parse(DTOSubFunction.getJsonString(p)));
                                if (counter.intValue() % INSERT_PACK == 0) {
                                    connection.insertMany(docs);
                                    docs.clear();
                                    logger.info(String.valueOf(counter.intValue()));
                                }
                            }
                        }
                );
        if (!docs.isEmpty()) {
            connection.insertMany(docs);
        }
        return counter;
    }

    public AtomicInteger loadStreamTypes(MongoCollection collection,String list) {
        String[] typesName = list.split(",");
        AtomicInteger counter = new AtomicInteger();
        Arrays.stream(typesName).forEach((p) -> {
            counter.getAndIncrement();
            collection.insertOne(new Document()
                    .append("_id",counter.intValue())
                    .append("type_name",p));
                }
        );
        return counter;
    }

    public AtomicInteger loadStreamStores(MongoCollection myCollection, int qtyElements, String prefix) {
        AtomicInteger counter = new AtomicInteger();
        Stream.generate(() -> new DTO().setName(DTOSubFunction.getName())
                        .setArg1(1)
                        .setArg2(1)
                        .setArg3(1))
                .limit(qtyElements)
                .forEach((p) -> {
                            if (DTOvalidation.getValidate(p).isEmpty()) {
                                counter.getAndIncrement();
                                myCollection.insertOne(new Document()
                                        .append("_id",counter.intValue())
                                        .append("store_name","Супермаркет №" + counter.intValue())
                                        .append("store_address",prefix +
                                            p.getName() + " ," + (counter.intValue()+3)));
                            }
                        }
                );
        return counter;
    }
}

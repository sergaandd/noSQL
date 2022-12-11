package shpp.mentor;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.*;

public class ScriptMaxStore {
    private static Document result;
    private ScriptMaxStore() {
    }

    public static Document getData(MongoCollection connection,int type_id) {
        try {
            result = (Document) connection.aggregate(
                    Arrays.asList(
                            match(Filters.eq("arg2", type_id)),
                            group("$arg1", Accumulators.sum("maxQty", "$arg3")),
                            sort(Sorts.descending("maxQty")),
                            limit(1))
            ).first();
        } catch (NullPointerException e) {
            result=null;
        }
        return result;
    }
}

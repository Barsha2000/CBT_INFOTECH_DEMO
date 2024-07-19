package in.test.service;

import com.mongodb.client.MongoClient;
import in.test.model.PanDetails;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class MongoService implements PanacheMongoRepository<PanDetails> {

    @Inject
    MongoClient mongoClient;

    public List<PanDetails> panDetails(){
        return listAll();
    }
}

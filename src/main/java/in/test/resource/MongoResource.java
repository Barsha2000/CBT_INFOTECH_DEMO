package in.test.resource;

import in.test.model.PanDetails;
import in.test.service.ElasticService;
import in.test.service.MongoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.io.IOException;
import java.util.List;

@GraphQLApi
@ApplicationScoped
public class MongoResource {

    @Inject
    MongoService services;

    @Inject
    ElasticService elasticService;



    @Query("allPanDetails")
    public List<PanDetails> listAllInvoice(){
        return services.panDetails();
    }

    @Mutation("insertToElastic")
    public String insertToElastic() throws IOException{
        elasticService.insertToElastic();
        return "Inserted to elastic";
    }
}

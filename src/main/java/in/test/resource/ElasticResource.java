package in.test.resource;

import in.test.model.Filter;
import in.test.model.PanDetails;
import in.test.service.ElasticService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
@GraphQLApi
public class ElasticResource {

    @Inject
    ElasticService elasticService;


    @Query("filterQuery")
    public List<PanDetails> filterQuery(List<Filter> filters, int pageNumber, int pageSize) throws IOException {
        return elasticService.filterQuery(filters, pageNumber, pageSize);
    }
}

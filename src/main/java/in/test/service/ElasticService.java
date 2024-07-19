package in.test.service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import in.test.model.Filter;
import in.test.model.PanDetails;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ElasticService {

    @Inject
    MongoService mongoService;

    @Inject
    @Named("esClient")
    ElasticsearchClient esClient;

    public void insertToElastic() throws IOException {

        BulkRequest.Builder br=new BulkRequest.Builder();

        System.out.println("Printing data from mongo" + mongoService.panDetails());



        mongoService.panDetails().forEach(d->{
            d.setDocId(d.getId().toString());
            d.setId(null);

            br.operations(op->op.index(i->i.id((d.getDocId())).document(d).index("pandetails")));
        });
        esClient.bulk(br.build());
    }

    public List<PanDetails> filterQuery(List<Filter> filters, int pageNumber, int pageSize) throws IOException {

        pageNumber = Math.max(pageNumber, 1);
        pageSize = Math.max(pageSize, 1);
        int offset = (pageNumber - 1) * pageSize;
        int finalPageSize = pageSize;

        if(filters== null){
            filters=new ArrayList<>();
        }

        List<Query> mustQuery=new ArrayList<>();
        for(Filter filter:filters){
            Query query= MatchPhraseQuery.of(mp->mp.field(filter.getFieldName()).query(filter.getFieldValue()))._toQuery();
            mustQuery.add(query);
        }
        SearchRequest searchRequest=SearchRequest.of(b->b.index("pandetails").size(finalPageSize).from(offset).query(q->q.bool(bq->bq.must(mustQuery))));

        SearchResponse<PanDetails> searchResponse=esClient.search(searchRequest, PanDetails.class);

        return searchResponse.hits().hits().stream().map(Hit::source).toList();


    }






}

package in.test.client_config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.elasticsearch.client.RestClient;

@ApplicationScoped
public class ElasticsearchConfig {

    @ConfigProperty(name = "ELASTICSEARCH_PORT")
    private int port;

    @ConfigProperty(name = "ELASTICSEARCH_HOST")
    private String hostName;

    @ConfigProperty(name = "ELASTICSEARCH_USERNAME")
    private String username;

    @ConfigProperty(name = "ELASTICSEARCH_PASSWORD")
    private String password;

    @Produces
    @Named("esClient")
    public ElasticsearchClient createClient() {

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClient restClient = RestClient
                .builder(new HttpHost(hostName, port))
                .setHttpClientConfigCallback(hc -> hc
                        .setDefaultCredentialsProvider(credsProv))
                .build();

        // Create the transport and the API client
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        return client;
    }

}

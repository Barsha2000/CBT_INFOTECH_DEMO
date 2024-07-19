package in.test.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.graphql.Id;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MongoEntity(collection="pancard")
public class PanDetails {

    @Id
    private ObjectId id;
    private String docId;
    private String firstName;
    private String lastName;
    private String State;
    private long phoneNumber;
    private String panNumber;
}

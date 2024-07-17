package amqTest.routes;

import amqTest.domain.models.Person;
import org.apache.camel.ProducerTemplate;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/send")
public class MessageResource {

    private static final Logger LOG = Logger.getLogger(MessageResource.class);

    @Inject
    ProducerTemplate producerTemplate;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMessage(Person person) {
        LOG.info("Received Person: " + person);

        String result = producerTemplate.requestBody("direct:start", person, String.class);

        if (result == null) {
            LOG.error("Received null result from producerTemplate");
            return Response.status(500).entity("Error processing the message").build();
        }

        LOG.info("Result from producerTemplate: " + result);

        if (result.contains("Validation failed")) {
            return Response.status(500).entity(result).build();
        }

        return Response.ok("Message sent: " + result).build();
    }
}

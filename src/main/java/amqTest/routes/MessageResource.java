package amqTest.routes;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import org.apache.camel.ProducerTemplate;

import org.jboss.logging.Logger;

@Path("/send")
public class MessageResource {

    private static final Logger LOG = Logger.getLogger(MessageResource.class);

    @Inject
    ProducerTemplate producerTemplate;

    @GET
    public String sendMessage(@QueryParam("message") String message) {
        LOG.info("Received request to send message: " + message);
        producerTemplate.sendBody("direct:start", message);
        return "Message sent: " + message;
    }
}

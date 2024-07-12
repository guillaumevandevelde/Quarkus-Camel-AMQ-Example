package amqTest.routes;

import org.apache.camel.builder.RouteBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyCamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Route to put a message on the queue
        from("direct:start")
                .to("jms:queue:testQueue");

        // Route to consume a message from the queue
        from("jms:queue:testQueue")
                .log("Received message from testQueue: ${body}")
                .to("stream:out");
    }
}

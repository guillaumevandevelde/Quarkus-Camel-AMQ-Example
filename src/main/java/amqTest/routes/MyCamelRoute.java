package amqTest.routes;

import amqTest.domain.models.Person;
import amqTest.domain.validators.PersonValidator;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;



@ApplicationScoped
public class MyCamelRoute extends RouteBuilder {

        @Inject
        PersonValidator personValidator;

        @Override
        public void configure() throws Exception {

                JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Person.class);

                // Route to put a message on the queue
                from("direct:start")
                                .log("Sending message to testQueue: ${body}")
                                .process(personValidator)
                                .choice()
                                .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(500))
                                        .setBody(simple("${body}"))
                                .otherwise()
                                        .marshal(jsonDataFormat)
                                        .log("Sending message to testQueue: ${body}")
                                        .to("jms:queue:testQueue");

                // Route to consume a message from the queue
                from("jms:queue:testQueue")
                                .unmarshal().json()
                                .log("Received message from testQueue: ${body}")
                                .to("stream:out");
        }
}

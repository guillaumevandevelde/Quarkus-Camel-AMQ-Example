package amqTest.domain.validators;

import amqTest.domain.models.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonValidator implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Person person = exchange.getIn().getBody(Person.class);
        
        if (person.getName() == null || person.getName().isEmpty() || person.getAge() <= 0) {
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
            exchange.getIn().setBody("Validation failed: Name must not be empty and age must be greater than 0.");
        }
    }
}

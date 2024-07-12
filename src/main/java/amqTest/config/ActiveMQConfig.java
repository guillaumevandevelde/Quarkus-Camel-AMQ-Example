package amqTest.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class ActiveMQConfig {

    private static final Logger LOG = Logger.getLogger(ActiveMQConfig.class);

    @ConfigProperty(name = "activemq.broker-url")
    String brokerUrl;

    @ConfigProperty(name = "activemq.username")
    String username;

    @ConfigProperty(name = "activemq.password")
    String password;

    @Produces
    @Named("jms")
    public JmsComponent configureJmsComponent() {

        LOG.info("Configuring ActiveMQ with URL: " + brokerUrl);
        LOG.info("Using username: " + username);

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);

        JmsConfiguration jmsConfiguration = new JmsConfiguration();
        jmsConfiguration.setConnectionFactory(connectionFactory);

        return new JmsComponent(jmsConfiguration);
    }
}

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception{
        System.out.println("Emit Log");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = argv.length < 1   ? "info: Hello World!"
                                                : String.join("", argv);
            channel.basicPublish(   EXCHANGE_NAME,
                                    "",
                                    null,
                                    message.getBytes("UTF-8"));
            System.out.println("[x] Enviado '" + message + "'");
        }
    }
}

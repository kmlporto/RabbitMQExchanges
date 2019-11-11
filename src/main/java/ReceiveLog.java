import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        System.out.println("Emit Log");

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection conexao = connectionFactory.newConnection();

        Channel channel = conexao.createChannel();
        channel.exchangeDeclare ( EXCHANGE_NAME , "fanout" );
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind (queueName, EXCHANGE_NAME , "" );

        System.out.println("[*] Aguardando mensagens. Para sair, pressione CTRL + C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensagem = new String (delivery.getBody (), "UTF-8");
            System.out.println ("[x] Recebido '" + mensagem + "'");
        };
        channel.basicConsume (queueName, true, deliverCallback, consumerTag -> {});
    }
}

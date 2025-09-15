package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.configuration.RabbitMQConfig;
import fiap.gestao.hospitalar.dto.CreateConsultaRecordDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultaPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public ConsultaPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNewConsulta(CreateConsultaRecordDTO consulta){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_NEW, consulta);
    }
}

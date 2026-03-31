package com.mitocode.qrpayment.infraestructure.in.messaging.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import lombok.RequiredArgsConstructor;
//this is for consumer and producer configuration, you can add more configuration for performance and reliability, also you can add retry and dead letter topic configuration for consumer
@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaErrorHandler kafkaErrorHandler;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        
        // Configuración de confiabilidad
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        //lo siguiente es reitentos pero del producer, 
        //no es necesario si el producer es idempotente, 
        //pero si quieres configurar reintentos para el producer puedes configurar el 
        //numero de reintentos y el tiempo de espera entre reintentos
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        
        // Configuración de performance
        //tamaño máximo de lote.
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // tiempo máximo (en milisegundos) que el productor espera antes de enviar un lote, aunque no esté lleno.
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        // Configuración para manejo de errores en deserialización
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.mitocode.qrpayment.infraestructure.in.messaging.dto");
        //lo de abajop no es correcto ya q nos limitamos a un solo tipo de mensaje, 
        //lo ideal es colocar el paquete donde se encuentran los dto de los mensajes que se van a consumir,
        //ver como se hizo en el consumer del proyecto tx-spring-kafka-consumer esta creado para consumir mensajes de tipo CuentaBancariaDTO 
        //y MovimientoDTO, por lo que se configuro el trusted packages con el paquete donde se encuentran estos dto,
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.mitocode.qrpayment.infraestructure.in.messaging.dto.MerchantKafkaRequest");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        
        // Configuración de performance
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        
        // Configuración de concurrencia
        //afka.listener.concurrency: habilita cuantos hilos se tiene  para leer estos datos , es mejor colocar el mismo numero de hilos que de particiones 
        factory.setConcurrency(3);
        
        // Configuración de commits automáticos por registro
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        
        // Configuración de retry y manejo de errores
        factory.setCommonErrorHandler(kafkaErrorHandler);

        return factory;
    }
}

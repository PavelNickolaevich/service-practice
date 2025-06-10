//package com.practice.servicepractice.kafka;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.listener.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.support.micrometer.KafkaListenerContainerMetrics;
//import org.springframework.kafka.support.micrometer.KafkaTemplateMetrics;
//
//@Configuration
//public class KafkaMetricsConfig {
//
//    @Autowired
//    private KafkaProperties kafkaProperties;
//
//    @Bean
//    public KafkaTemplateMetrics kafkaTemplateMetrics(
//            KafkaTemplate<?, ?> kafkaTemplate,
//            MeterRegistry meterRegistry) {
//        // Регистрируем метрики для KafkaTemplate
//        KafkaTemplateMetrics metrics = new KafkaTemplateMetrics(kafkaTemplate, meterRegistry);
//
//        // Дополнительные настройки (опционально)
//        kafkaProperties.buildProducerProperties().put(
//                ProducerConfig.METRICS_RECORDING_LEVEL_CONFIG,
//                "DEBUG"
//        );
//
//        return metrics;
//    }
//
//    @Bean
//    public KafkaListenerContainerMetrics kafkaListenerMetrics(
//            ConcurrentKafkaListenerContainerFactory<?, ?> containerFactory,
//            MeterRegistry meterRegistry) {
//        // Регистрируем метрики для Kafka Listener
//        KafkaListenerContainerMetrics metrics = new KafkaListenerContainerMetrics(
//                containerFactory,
//                meterRegistry
//        );
//
//        // Настройка интервала сбора метрик (по умолчанию 30 секунд)
//        metrics.setObserverEnabled(true);
//        metrics.setMicrometerTagsEnabled(true);
//
//        return metrics;
//    }
//
//    @Bean
//    public ProducerFactory<?, ?> producerFactoryWithMetrics() {
//        // Включаем сбор метрик на уровне Producer
//        return kafkaProperties.buildProducerFactory(properties -> {
//            properties.put(
//                    ProducerConfig.METRICS_RECORDING_LEVEL_CONFIG,
//                    "INFO"
//            );
//        });
//    }
//}
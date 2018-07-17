package org.gz.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableAspectJAutoProxy
public class MQApplication {


	public static void main(String[] args) {
		SpringApplication.run(MQApplication.class, args);
	}

}
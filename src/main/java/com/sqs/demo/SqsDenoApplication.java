package com.sqs.demo;

import com.sqs.demo.domain.Pojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
//@EnableScheduling
public class SqsDenoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqsDenoApplication.class, args);
	}

	Logger logger= LoggerFactory.getLogger(SqsDenoApplication.class);

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;

	@GetMapping("/send/{message}")
	public void sendMessageToQueue(@PathVariable String message) {
		queueMessagingTemplate.convertAndSend(endpoint, new Pojo(message));
	}

	@SqsListener(value = "statnderd-sqs",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void loadMessageFromSQS(Pojo message)  {
		System.out.println("hello"+message);
	}

}

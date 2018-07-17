/**
 * 
 */
package org.gz.warehouse.kafka;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年1月17日 上午10:14:18
 */
public class KafkaTest {

	private static final String BOOTSTRAP_SERVERS = "192.168.47.128:9092,192.168.47.129:9092,192.168.47.130:9092";

	private static final String GROUP = "test";

	private static final String TOPIC1 = "topic1";

	private static final String TOPIC2 = "topic2";

	@Test
	public void testAutoCommit() throws Exception {
		System.err.println("Start auto");
		ContainerProperties containerProps = new ContainerProperties(TOPIC1, TOPIC2);
		final CountDownLatch latch = new CountDownLatch(4);
		containerProps.setMessageListener(new MessageListener<Integer, String>() {
			@Override
			public void onMessage(ConsumerRecord<Integer, String> message) {
				System.err.println("received: " + message);
				latch.countDown();
			}
		});
		KafkaMessageListenerContainer<Integer, String> container = createContainer(containerProps);
		container.setBeanName("testAuto");
		container.start();
		Thread.sleep(1000); // wait a bit for the container to start
		KafkaTemplate<Integer, String> template = createTemplate();
		System.err.println("template: " + template);
		template.setDefaultTopic(TOPIC1);
		template.sendDefault(0, "foo");
		template.sendDefault(1, "bar");
		template.sendDefault(2, "baz");
		template.sendDefault(3, "qux");
		ListenableFuture<SendResult<Integer, String>> future = template.send(TOPIC2, 0,4, "test");
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				 System.err.println("onSuccess:key="+result.getProducerRecord().key());
			}

			@Override
			public void onFailure(Throwable ex) {
				System.err.println("onFailure:"+ex.getMessage());
			}
		});
		template.setProducerListener(new CustomProducerListener());
		template.flush();
		assertTrue(latch.await(120, TimeUnit.SECONDS));
		container.stop();
		System.err.println("Stop auto");
	}

	/**
	 * 消息监听器配置
	 * 
	 * @param containerProps
	 * @return KafkaMessageListenerContainer<Integer,String>
	 */
	private KafkaMessageListenerContainer<Integer, String> createContainer(ContainerProperties containerProps) {
		Map<String, Object> props = consumerProps();
		DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<Integer, String>(props);
		KafkaMessageListenerContainer<Integer, String> container = new KafkaMessageListenerContainer<>(cf,
				containerProps);
		return container;
	}

	private KafkaTemplate<Integer, String> createTemplate() {
		Map<String, Object> senderProps = senderProps();
		ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<Integer, String>(senderProps);
		KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
		return template;
	}

	/**
	 * 消费者配置
	 * 
	 * @return Map<String,Object>
	 */
	private Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

	/**
	 * 生产者配置
	 * 
	 * @return Map<String,Object>
	 */
	private Map<String, Object> senderProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.ACKS_CONFIG, "1");
		props.put(ProducerConfig.RETRIES_CONFIG, 3);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "org.gz.warehouse.kafka.CustomProducerInterceptor");
		return props;
	}
}

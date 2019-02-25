from kafka import KafkaConsumer

consumer=KafkaConsumer('sex',group_id='0',bootstrap_servers=['192.168.56.101:9092'])
for msg in consumer:
    print((msg.key).decode('utf8')+ "   " +(msg.value).decode('utf8'))
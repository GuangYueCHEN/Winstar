# coding=utf-8
import csv
import time
from kafka import KafkaProducer

producer=KafkaProducer(bootstrap_servers='192.168.56.101:9092')

csvfile=open("/Users/pingguo/Desktop/winstar/winstar/AnalyseLogs/data/user_log.csv","r",encoding='utf_8_sig')


reader=csv.reader(csvfile)



for line in reader:
    
    gender = line[10]
    
    if gender == 'gender' :
        continue
    
    time.sleep(0.1)
    
    producer.send('sex',line[9].encode('utf8'))
    
    

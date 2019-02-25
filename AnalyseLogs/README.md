# Winstar

data 模拟的订单数据

scripts Python脚本， Producer负责向Kafka发送订单， Consumer是测试脚本，负责从Kafka接受消息，测试Kafka是否走通

src Scala语言的Streaming程序

templates HtML主页，负责动态展示结果

static javaScript，HTML主页会调用

app.py Flusk服务器，负责从Kafka消费SparkStreaming的结果，并作为HtML服务器发送结果到HTML
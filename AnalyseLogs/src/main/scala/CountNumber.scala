import java.util
import java.util.Date

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.StreamingContext
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import org.apache.kafka.common.serialization.StringDeserializer


object CountNumber {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val bootstrapServer = "192.168.56.101:9092"
  val sparkMaster ="spark://192.168.56.101:7077"
  val myJar=List("/Users/pingguo/Desktop/winstar/winstar/AnalyseLogs/out/artifacts/AnalyseLogs_jar/AnalyseLogs.jar",
    "/Users/pingguo/Desktop/winstar/winstar/AnalyseLogs/out/artifacts/AnalyseLogs_jar/spark-streaming-kafka-0-10_2.11-2.3.1.jar",
    "/Users/pingguo/Desktop/winstar/winstar/AnalyseLogs/out/artifacts/AnalyseLogs_jar/kafka-clients-0.10.0.1.jar")


  def main(args: Array[String]): Unit = {

    if(args.length < 2){

      System.err.println("Usage: KafkaWordCount <group> <topics> ")

      System.exit(1)

    }



    val Array(group,topics)=args

    val sparkConf = new SparkConf().setAppName("KafkaCount").setMaster(sparkMaster).setJars(myJar)


    val ssc = new StreamingContext(sparkConf,Seconds(1))

    ssc.checkpoint("hdfs://192.168.56.101:9000/data/checkpoint")
    //topic 转换成topic-->numThreads的哈希表

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> bootstrapServer,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> group,
      "auto.offset.reset" -> "earliest"

    )



    val stream = KafkaUtils.createDirectStream[String,String](ssc,LocationStrategies.PreferConsistent,ConsumerStrategies.Subscribe[String,String](Set(topics),kafkaParams))



    stream.foreachRDD(rdd=>{

      val pair = rdd.map(record => record.value)

      val values =  pair.flatMap(_.split(" "))

      val counts = values.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}

      println(counts.count())
      counts.collect().foreach(x=>println(x))

      val props = new util.HashMap[String, Object]()

      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.101:9092")

      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer")

      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer")



      val producer = new KafkaProducer[String, String](props)

      val tempt= new Date()

      val strJSON = write(counts.collect)



        producer.send(new ProducerRecord("result", tempt.toString , strJSON))



      println("\nnew\n")


    })




    ssc.start()

    ssc.awaitTermination()



  }







}
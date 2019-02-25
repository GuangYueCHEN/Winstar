
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingTest {
  def main(args: Array[String]): Unit = {
    val sparkConf=new SparkConf().setAppName("wordCount").setMaster("spark://192.168.56.101:7077").setJars(List("/Users/pingguo/IdeaProjects/streamingTest/out/artifacts/streamingTest_jar/streamingTest.jar"))
    val ss=new StreamingContext(sparkConf,Seconds(15)) //每15秒监听一次sreaming文件夹
    val lines=ss.textFileStream("file:///Users/pingguo/Desktop/winstar/data/")
    val words=lines.flatMap(_.split(" "))
    val wordCounts=words.map(x=>(x,1)).reduceByKey((x,y)=>x+y)
    wordCounts.print(100)   //打印100组
    ss.start()
    ss.awaitTerminationOrTimeout(1000000)  //运行50秒程序自动结束
  }
}

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object WordCount {
  def main(args: Array[String]){
    val conf = new SparkConf().setAppName("wordCount")

    val sc = new SparkContext(conf)
    // 读取我们的输入数据
    val input = sc.textFile("/User/pingguo/Desktop/stage")
    // 把它切分成一个个单词
    val words = input.flatMap(line => line.split(" "))
    // 转换为键值对并计数
    val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
    // 将统计出来的单词总数存入一个文本文件，引发求值
    counts.saveAsTextFile("/User/pingguo/Desktop/out")


  }
}
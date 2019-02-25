import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.shaded.com.google.common.base.Charsets;




public class MyInputFormat extends FileInputFormat<LongWritable, Text> {

    public MyInputFormat() {
    }

    @SuppressWarnings("deprecation")

    @Override

    public RecordReader<LongWritable, Text> createRecordReader(

            InputSplit split, TaskAttemptContext context) {

        return new MyRecordReader2();

    }


    @Override

    protected boolean isSplitable(JobContext context, Path file) {

        CompressionCodec codec = new CompressionCodecFactory(

                context.getConfiguration()).getCodec(file);

        return codec == null;

    }



}
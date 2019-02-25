

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TimeCount {

    public TimeCount() {
    }


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "CarTime count");
        job.setJarByClass(TimeCount.class);
        job.setMapperClass(TimeCount.CarTimeMapper.class);
        job.setCombinerClass(TimeCount.TimeSumReducer.class);
        job.setReducerClass(TimeCount.TimeSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        for(int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class TimeSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public TimeSumReducer() {
        }

        public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;

            IntWritable val;
            for(Iterator carTime = values.iterator(); carTime.hasNext(); sum += val.get()) {
                val = (IntWritable)carTime.next();
            }

            this.result.set(sum);
            context.write(key, this.result);
        }
    }

    public static class CarTimeMapper extends Mapper<Object, Text, Text, IntWritable> {
        private static final IntWritable one = new IntWritable(1);
        private String plate ;
        private String time ;
        private Text carTime = new Text();
        public CarTimeMapper() {
        }

        public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {


            String line = value.toString();
            if(line.length()<=15){
                return;
            }
            if (line.charAt(14) == '无' || line.charAt(14) == ' ' || line.charAt(14) >= '0'&&line.charAt(14) <= '9' || line.charAt(14) == '不'
                    || line.charAt(14) == '*'|| line.charAt(14) == '?'|| line.charAt(14) == '='|| line.charAt(14) == '-'|| line.charAt(14) == '.'|| line.charAt(14) == '/') {
                System.out.print("wrong plate number\n");
            } else {
                try {
                    StringTokenizer itr = new StringTokenizer(line, "\":{$");
                    itr.nextToken();
                    this.plate = itr.nextToken().replaceFirst("createdAt","");
                    itr.nextToken();
                    int hours = Integer.parseInt(itr.nextToken().substring(11, 13));
                    if (hours > 0 && hours < 7) {
                        this.time = ",夜间00-07";
                    } else if (hours < 11) {
                        this.time = ",早高峰07-11";
                    } else if (hours < 14) {
                        this.time = ",午间11-14";
                    } else if (hours < 17) {
                        this.time = ",下午14-17";
                    } else if (hours < 20) {
                        this.time = ",晚高峰17-20";
                    } else {
                        this.time =  ",夜间20-24";
                    }

                } catch (Exception e) {
                    System.out.print("time change exception or data wrong exception"+e+'\n');
                    return;
                }

                this.carTime.set(this.plate +this.time+",");
                context.write(this.carTime,one);

            }

        }
    }


}


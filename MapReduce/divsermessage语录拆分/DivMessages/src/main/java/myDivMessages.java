


import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class myDivMessages  {


    public myDivMessages() {
    }



    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if (otherArgs.length < 1) {
            System.err.println("Usage: myDivMessage  <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, " Diviser Messages");
        job.setJarByClass( myDivMessages.class);
        job.setMapperClass( myDivMessages.TokenizerMapper.class);
        job.setInputFormatClass(MyInputFormat.class);

        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("/DivMessage/input/"));
        //FileInputFormat.addInputPath(job, new Path("/data/composition.txt"));

        job.setNumReduceTasks(0);

        MultipleOutputs.addNamedOutput(job, "emmm", TextOutputFormat.class, Text.class, IntWritable.class);
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[0]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private static IntWritable num = new IntWritable(1);
        private Text messages = new Text();

        public TokenizerMapper() {
        }
        private MultipleOutputs<Text,IntWritable> mos;
        public static final String outputPath=new String("/DivMessage/outputs/");
        //public String outputPath=new String("/home/cgy/Downloads/outputs/");

        @Override
        protected void setup(Context context)
                throws IOException, InterruptedException {
            mos = new MultipleOutputs<Text,IntWritable>(context);
        }

        @Override
        protected void cleanup(Context context)
                throws IOException, InterruptedException {
            if(null != mos) {
                mos.close();
                mos = null;
            }
        }
        @Override
        public void run(Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {


            try {
                while(context.nextKeyValue()) {
                    this.map(context.getCurrentKey(), context.getCurrentValue(), context);
                }
            } finally {

            }

        }

        public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {

            this.setup(context);

            messages.set(value);
            try {
                mos.write(messages, num, outputPath + num.get());

            }catch (FileAlreadyExistsException e){
                System.out.print(e);
                context.write(messages, num);
            }catch (RemoteException e){
                System.out.print(e);
                context.write(messages, num);
            }
            num.set(num.get() + 1);
            this.cleanup(context);

        }
    }

    //class TestReducer extends Reducer<Text, IntWritable, Text, Text>{

    //将结果输出到多个文件或多个文件夹

    //  private MultipleOutputs mos;

    //protected void setup(Context context) throws IOException,InterruptedException {

    //  mos = new MultipleOutputs< Text, Text>(context);  // 初始化mos

    //}

    // protected void cleanup(Context context) throws IOException,InterruptedException {

    //   mos.close();  //关闭对象

    //}

    //}



}
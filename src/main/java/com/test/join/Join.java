package com.test.join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小新很忙 on 2017/8/16.
 */
public class Join {
    private static final String URL="hdfs://192.168.236.100:9000/LT/customer.txt";
    private static class JoinMapper extends Mapper<LongWritable,Text,CustomMapKey,Text>{
        CustomMapKey custom=new CustomMapKey();
        Text t=new Text();
        private  static final Map<Integer,CustomerBean> map=new HashMap<Integer, CustomerBean>();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSystem fs=FileSystem.get(URI.create(URL),context.getConfiguration());
            FSDataInputStream fdis=fs.open(new Path(URL));
            BufferedReader reader=new BufferedReader(new InputStreamReader(fdis));
            String line=null;
            String []cols=null;
            while((line=reader.readLine())!=null){
                cols=line.split(" ");
                if(cols.length<4){
                    continue;
                }
                CustomerBean bean=new CustomerBean(Integer.parseInt(cols[0]),cols[1],cols[2],cols[3]);
                map.put(bean.getCustomID(),bean);
            }

        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String [] split=line.split(" ");
            StringBuffer sb=new StringBuffer();
            int cusID=Integer.parseInt(split[1]);
            CustomerBean c=map.get(cusID);
            sb.append(split[2]).append(" ").append(c.getName()).append(" ").append(c.getAddress()).append(" ")
                    .append(c.getTelephone());
            t.set(sb.toString());
            custom.setCusId(cusID);
            custom.setOrder(Integer.parseInt(split[0]));
           context.write(custom,t);
        }
    }
    private static class JoinReducer extends Reducer<CustomMapKey,Text,CustomMapKey,Text>{

        @Override
        protected void reduce(CustomMapKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for(Text t:values){
                context.write(key,t);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("HADOOP_USER_NAME","hadoop");
        System.setProperty("hadoop.home.dir","D:\\hadoop2.4.1\\hadoop-2.4.1");
        Configuration conf=new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: average <in> [<in>...] <out>");
            System.exit(2);
        }

        conf.set("fs.default.name", "hdfs://192.168.236.100:9000");
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("mapreduce.app-submission.cross-platform", "true");
       conf.set("mapred.jar","D:\\java\\mapreduce\\target\\mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar");
        Job job=new Job(conf,"aaaa");
        job.addCacheFile(URI.create(URL));
        job.setMapperClass(JoinMapper.class);
        job.setMapOutputKeyClass(CustomMapKey.class);
        job.setMapOutputValueClass(Text.class);
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job,new Path(otherArgs[otherArgs.length - 1]));
        job.setReducerClass(JoinReducer.class);
        job.waitForCompletion(true);

    }
}

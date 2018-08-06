package package3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class Spark {

    public static void main(String[] args) {     
        
        String logFile = "d://WordCount.txt";
        
        /**
         * 第1步：创建Spark的配置对象SparkConf，设置Spark程序的运行时的配置信息，
         * 例如说通过setMaster来设置程序要链接的Spark集群的Master的URL,如果设置
         * 为local，则代表Spark程序在本地运行，特别适合于机器配置条件非常差（例如
         * 只有1G的内存）的初学者       *
         */
        SparkConf conf = new SparkConf().setAppName("WordCount").setMaster("local");
        
        /**
         * 第2步：创建SparkContext对象
         * SparkContext是Spark程序所有功能的唯一入口，无论是采用Scala、
         * Java、Python、R等都必须有一个SparkContext(不同的语言具体的类名称不同，如果是java 的为javaSparkContext)
         * SparkContext核心作用：初始化Spark应用程序运行所需要的核心组件，
         * 包括DAGScheduler、TaskScheduler、SchedulerBackend
         * 同时还会负责Spark程序往Master注册程序等
         * SparkContext是整个Spark应用程序中最为至关重要的一个对象
         */
        JavaSparkContext sc = new JavaSparkContext(conf);
                     
        /**
         * 第3步：根据具体的数据来源（HDFS、HBase、Local FS、DB、S3等）通过SparkContext来创建RDD
         * JavaRDD的创建基本有三种方式：
         * 根据外部的数据来源（例如HDFS）、根据Scala集合、由其它的RDD操作
         * 数据会被JavaRDD划分成为一系列的Partitions，分配到每个Partition的数据属于一个Task的处理范畴
         */
        //使用persist方法或cache方法将RDD缓存到内存
        JavaRDD<String> logData = sc.textFile(logFile).cache();
        
        Long numAs = logData.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return s.contains("a");
            }
        }).count();

        Long numBs = logData.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return s.contains("b");
            }
        }).count();
        
        System.out.println("Lines with a:" +numAs+";Lines with b:"+numBs);
        
        
    }
}

package com.lsh.flink.wordcount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * BUG1:java.lang.NoClassDefFoundError: org/apache/flink/streaming/api/scala/StreamExecutionEnvironment
 * removing the <scope>provided</scope> which was present in the maven import.
 */
public class WordCountStreamDemo {

    /**
     * Java17 java.lang.reflect.InaccessibleObjectException: Unable to make field private final byte[] java.lang.String.value accessible: module java.base does not "opens java.lang" to unnamed module @2a5ca609
     * use Java 11
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> stringDataStreamSource = env.readTextFile("hello.txt");

        SingleOutputStreamOperator<Tuple2<String, Integer>> tuple2SingleOutputStreamOperator = stringDataStreamSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
                for (String word : value.split(" ")) {
                    Tuple2<String, Integer> tuple2 = Tuple2.of(word, 1);
                    collector.collect(tuple2);
                }
            }
        });

        KeyedStream<Tuple2<String, Integer>, String> tuple2StringKeyedStream = tuple2SingleOutputStreamOperator.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> tuple2) throws Exception {
                return tuple2.getField(0);
            }
        });

        SingleOutputStreamOperator<Tuple2<String, Integer>> result = tuple2StringKeyedStream.sum(1);
        result.print();
        env.execute();
        /**
         * 3> (hello,1)
         * 3> (hello,2)
         * 3> (hello,3)
         * 5> (world,1)
         * 7> (flink,1)
         * 2> (java,1)
         */
    }
}

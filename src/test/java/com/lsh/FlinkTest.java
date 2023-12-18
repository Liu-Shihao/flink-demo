package com.lsh;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

public class FlinkTest {

    /**
     * DataSet API（批处理） 实现单词统计 这种实现方式已经过时
     * @throws Exception
     */
    @Test
    void dataSetTest() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<String> dataSource = env.readTextFile("hello.txt");

        FlatMapOperator<String, Tuple2<String, Integer>> flatMapOperator = dataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
                for (String word : value.split(" ")) {
                    Tuple2<String, Integer> tuple2 = Tuple2.of(word, 1);
                    collector.collect(tuple2);
                }
            }
        });

        //group by index
        UnsortedGrouping<Tuple2<String, Integer>> tuple2UnsortedGrouping = flatMapOperator.groupBy(0);

        //sum by index
        AggregateOperator<Tuple2<String, Integer>> result = tuple2UnsortedGrouping.sum(1);

        result.print();
        /**
         * (flink,1)
         * (world,1)
         * (hello,3)
         * (java,1)
         */
    }


}

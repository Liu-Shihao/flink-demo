package com.lsh.flink.wordcount;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class WordCountStreamUnboundedDemo {

    /**
     * 读取Socket，（无界流）
     * yum install netcat
     * Netcat（也称为nc）是一个网络工具，用于在计算机网络之间传输数据。它是一个具有强大功能的命令行工具，可以用于创建各种网络连接。
     * nc -lk 7777
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> socketTextStream = env.socketTextStream("localhost", 7777);

        socketTextStream
                .flatMap((String value,Collector<Tuple2<String,Integer>> out) -> {
                    for (String word : value.split(" ")) {
                        out.collect(Tuple2.of(word,1));
                    }})
                .returns(Types.TUPLE(Types.STRING,Types.INT))
                .keyBy((Tuple2<String,Integer> value) -> value.f0)
                .sum(1);


        env.execute();
    }
}

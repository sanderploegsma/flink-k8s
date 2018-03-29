package nl.sanderp.flink

import org.apache.flink.api.java.ExecutionEnvironment
import org.apache.flink.api.java.functions.KeySelector

fun main(args: Array<String>) {

    val env = ExecutionEnvironment.getExecutionEnvironment().apply {
        parallelism = 4
    }

    env.fromCollection(listOf('a'..'z').flatten())
            .partitionByHash(KeySelector<Char, Int> { it.hashCode() })
            .filter { it.hashCode() % 3 == 0 }
            .print()
}
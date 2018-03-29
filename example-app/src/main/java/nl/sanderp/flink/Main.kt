package nl.sanderp.flink

import org.apache.flink.api.java.ExecutionEnvironment
import org.apache.flink.api.java.functions.KeySelector

fun main(args: Array<String>) {

    ExecutionEnvironment.getExecutionEnvironment()

            // Let's take up multiple slots because we can
            .apply { parallelism = 4 }

            // Create a sample input consisting of the characters [a-z]
            // Gotta love the range operators in Kotlin :)
            .fromCollection(listOf('a'..'z').flatten())

            // Distribute each item in the DataSet across the worker nodes
            // Unfortunately, Flink doesn't get it when we omit the KeySelector constructor
            .partitionByHash(KeySelector<Char, Int> { it.hashCode() })

            // Insert random operation here
            .filter { it.hashCode() % 3 == 0 }

            // Finally, print whatever this became
            .print()
}
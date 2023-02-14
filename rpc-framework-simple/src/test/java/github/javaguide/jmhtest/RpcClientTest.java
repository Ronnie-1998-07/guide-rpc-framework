package github.javaguide.jmhtest;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.javaguide.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)

@RpcScan(basePackage = {"github.javaguide"})
public class RpcClientTest {

    @Benchmark
    public void rpcClientTest() throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RpcClientTest.class);
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        helloController.test();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RpcClientTest.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }
}
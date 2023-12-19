# Apache Flink 入门


# 使用命令行向Flink提交任务
要使用命令行提交 Flink 任务，你可以使用 Flink 提供的 `./bin/flink run` 脚本。以下是一些基本步骤：

1. **构建你的 Flink 项目：**
   确保你的 Flink 项目已经被打包成一个可执行的 JAR 文件。你可以使用 Maven 或 Gradle 构建你的项目。

2. **进入 Flink 安装目录：**
   打开终端并导航到你的 Flink 安装目录。

3. **使用 `./bin/flink run` 命令：**
   运行以下命令，将你的任务提交到 Flink 集群：

   ```bash
   ./bin/flink run -c your.package.YourMainClass /path/to/your/flink-job.jar
   ```

    - `-c your.package.YourMainClass` 指定了你的主类。
    - `/path/to/your/flink-job.jar` 是你构建的 Flink 项目的 JAR 文件的路径。

   例如，如果你的主类是 `com.example.MyFlinkJob`，而 JAR 文件位于 `/path/to/myflinkjob.jar`，命令将是：

   ```bash
   ./bin/flink run -c com.example.MyFlinkJob /path/to/myflinkjob.jar
   ```

4. **观察任务运行情况：**
   Flink 将输出有关任务的日志信息，你可以在终端中观察到。你还可以访问 Flink 的 Web UI（默认地址是 http://localhost:8081）来监视任务的状态和性能指标。

请注意，你需要确保 Flink 集群正在运行，并且你的任务的相关依赖已经在集群上可用。你还可以使用 `-m` 选项指定 Flink 集群的地址，例如 `-m yarn-cluster` 或 `-m localhost:8081`。

有关更多详细信息，请查阅 Flink 的官方文档。

# netcat

下载地址：https://netcat.sourceforge.net/download.php
## [👈 Back to readme](../../README.md)

# Processing benchmark

## Here are the results of the processing benchmark:

I will use default settings and Handkerchief algorithm to compare the processing speed of the application.

| Threads | Time |
|---------|------|
| 1       | 42   |
| 2       | 23   |
| 3       | 14   |
| 4       | 12   |
| 6       | 9    |
| 8       | 7    |

## Here are logs used for each test:

### 1 thread

```log
16:19:30.196 INFO  [pool-2-thread-1] d.academy.generators.ImageGenerator -- Drawer 0 started
16:20:12.673 INFO  [Thread-1       ] d.academy.generators.ImageGenerator -- Executor service terminated successfully
```

### 2 threads

```log
16:20:52.997 INFO  [pool-3-thread-1] d.academy.generators.ImageGenerator -- Drawer 0 started
16:21:15.758 INFO  [Thread-2       ] d.academy.generators.ImageGenerator -- Executor service terminated successfully
```

### 3 threads

```log
16:22:32.772 INFO  [pool-4-thread-1] d.academy.generators.ImageGenerator -- Drawer 0 started
16:22:46.692 INFO  [Thread-3       ] d.academy.generators.ImageGenerator -- Executor service terminated successfully
```

### 4 threads

```log
16:23:48.551 INFO  [pool-5-thread-1] d.academy.generators.ImageGenerator -- Drawer 0 started
16:24:00.399 INFO  [Thread-4       ] d.academy.generators.ImageGenerator -- Executor service terminated successfully
```

### 6 threads

```log
16:25:13.317 INFO  [pool-6-thread-1] d.academy.generators.ImageGenerator -- Drawer 0 started
16:25:22.019 INFO  [Thread-5       ] d.academy.generators.ImageGenerator -- Executor service terminated successfully
```

### 8 threads

```log
16:27:38.362 INFO  [pool-7-thread-1] d.academy.generators.ImageGenerator -- Drawer 0 started
16:27:45.389 INFO  [Thread-6       ] d.academy.generators.ImageGenerator -- Executor service terminated successfully
```

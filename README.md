# UtilityCM

Welcome to the **UtilityCM** repository! This collection of utility classes is designed to enhance your Java development experience by providing advanced and highly reusable components. Our goal is to offer developers powerful tools that simplify common tasks and improve code efficiency.

## Overview

This repository contains several revolutionary classes that address common challenges in modern Java development:

1. **`TimeSeries`**: Manage and analyze time-series data with support for statistical methods and querying by time range.
2. **`RateLimiter`**: Manage and control the rate of operations to prevent overloading resources or services.
3. **`GraphQLBuilder`**: Build and execute GraphQL queries and mutations with ease.
4. **`StateMachine`**: Implement state machines with support for complex transitions and event handling.
5. **`NetworkManager`**: Handle network requests with caching, retries, and monitoring.
6. **`TaskQueue`**: Manage and prioritize asynchronous tasks with support for retries and delays.
7. **`MetricsCollector`**: Collect and analyze performance metrics for better monitoring and optimization.
8. **`ConcurrentMap`**: Extend `ConcurrentHashMap` with additional atomic operations and utilities.
9. **`CryptoUtil`**: Provide encryption, decryption, and hashing functionalities using modern algorithms.
10. **`ConfigManager`**: Manage and reload configuration settings dynamically and hierarchically.
11. **`SystemCalculator`**: Calculate and monitor system metrics such as CPU usage, memory usage, and disk space.
12. **`RedisUtility`**: Enhanced Redis utility class with support for JedisCluster and improved configuration.
13. **`AdvancedFunctionUtility`**: Handle advanced function operations and performance metrics.

## Features

- **Rate Limiting**: Avoid service overload and ensure fair usage.
- **GraphQL Operations**: Simplify the creation and execution of GraphQL queries.
- **State Machine Management**: Implement and manage complex state transitions easily.
- **Network Request Handling**: Make HTTP requests with advanced features like caching and retries.
- **Task Scheduling**: Efficiently manage task execution with priority, delays, and retries.
- **Performance Metrics**: Track and analyze various performance metrics.
- **Concurrent Data Structures**: Use advanced concurrent map utilities for atomic operations.
- **Cryptographic Functions**: Perform encryption, decryption, and hashing securely.
- **Dynamic Configuration**: Handle configuration files with support for dynamic reloading.

## Getting Started

To get started with the UtilityCM, follow these instructions:

### Prerequisites

- Java 21 or later

### Installation

You can include these utilities in your project by cloning the repository and adding the classes to your project.

```bash
git clone https://github.com/yourusername/advanced-java-utilities.git
cd advanced-java-utilities
```

### Usage

Here's how to use some of the classes provided:

#### RateLimiter
```java
RateLimiter rateLimiter = new RateLimiter(5); // 5 requests per second
boolean allowed = rateLimiter.tryAcquire();
```

#### GraphQLBuilder
```java
GraphQLBuilder builder = new GraphQLBuilder("https://api.example.com/graphql");
String query = "{ user { id name } }";
String response = builder.execute(query);
```

#### StateMachine
```java
StateMachine machine = new StateMachine();
machine.addState("START");
machine.addState("END");
machine.addTransition("START", "END", "startToEnd");
machine.transition("START", "END");
```

### Contributing

We welcome contributions to improve this repository. If you have suggestions or bug reports, please open an issue or submit a pull request.

### License
This project is licensed under the MIT License - see the LICENSE file for details.

### Contact
For any inquiries, please contact me at skogrinepro@gmail.com.

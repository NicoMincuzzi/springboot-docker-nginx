A simple Java applcation that uses the [NGINX](https://www.nginx.com/) as load balancer.

## Load Balancer
A type of ***reverse proxy*** that distributes traffic across servers. Load balancers can be found in many parts of a system, from the DNS layer all the way to the database layer.

Load balancers are effective at:

- Preventing requests from going to unhealthy servers
- Preventing overloading resources
- Helping eliminate single points of failure

Additional benefits include:

- **SSL termination** - Decrypt incoming requests and encrypt server responses so backend servers do not have to perform these potentially expensive operations
  - Removes the need to install [X.509 certificates](https://en.wikipedia.org/wiki/X.509) on each server
- **Session persistence** - Issue cookies and route a specific client's requests to same instance if the web apps do not keep track of sessions

### Server-Selection Strategy
How a **load balancer** chooses servers when distributing traffic amongst multiple servers. 

Commonly used strategies include:
- **Round-Robin**
- **Random Selection**
- **Performance-based Selection** (choosing the server with the best performance metrics, like the fastest response time or the least amount of traffic)
- **IP-based routing** - Session/cookies

### Hot Spot
When distributing a workload across a set of servers, that workload might be spread unevenly. This can happen if your **sharding key** or your **hashing function** are suboptimal, or if your workload is naturally skewed: some servers will receive a lot more traffic than others, thus creating a "hot spot".

### Nginx
 Nginx is a very popular webserver that's often used as a <b>reverse proxy</b> and <b>load balancer</b>.

Learn more: https://www.nginx.com/

## Contributing

Everyone is very welcome to contribute to this project. You can contribute just by submitting bugs or suggesting improvements by
[opening an issue on GitHub](https://github.com/NicoMincuzzi/springboot-docker-nginx/issues).

## Observability for a Spring Boot app

The `Prometheus Java Client library` is not the only way to get metrics out of a Spring Boot app. 
One alternative is to use the `OpenTelemetry Java instrumentation agent` for exposing Spring’s metrics directly in OpenTelemetry format.

As the first step, we enable metrics in our example application and expose these metrics directly in Prometheus format. 
We will not yet use the OpenTelemetry Java instrumentation agent.

The `spring-boot-starter-actuator` provides the metrics API and some out-of-the-box metrics. Under the hood, it uses the `Micrometer` metrics library. 
The `micrometer-registry-prometheus is for exposing Micrometer metrics in Prometheus format.

Next, we need to enable the Prometheus endpoint. Create a file ./src/main/resources/application.properties with the following line:

```
management.endpoints.web.exposure.include=prometheus
```
After recompiling and restarting the application, you will see the metrics on http://localhost:8080/actuator/prometheus.

### Putting the OpenTelemetry collector in the middle
The OpenTelemetry collector is a component to receive, process, and export telemetry data. It usually sits in the middle, between the applications to be monitored and the monitoring backend.


As the next step, we will configure an OpenTelemetry collector to scrape the metrics from the Prometheus endpoint and expose them in Prometheus format.


So far this will not add any functionality, except that we get the OpenTelemetry collector as a new infrastructure component. The metrics exposed by the collector on port 8889 should be the same as the metrics exposed by the application on port 8080.


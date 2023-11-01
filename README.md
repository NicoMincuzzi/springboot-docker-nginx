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

![spring-boot-opentelemetry-collector-architecture](https://github.com/NicoMincuzzi/springboot-docker-nginx/assets/48289901/ea6f3003-1a8e-4106-aa1e-8255531a0a1a)

As the next step, we will configure an OpenTelemetry collector to scrape the metrics from the Prometheus endpoint and expose them in Prometheus format.

![spring-boot-opentelemetry-collector-scrape-metrics](https://github.com/NicoMincuzzi/springboot-docker-nginx/assets/48289901/ed3403c1-ef73-48cc-a946-ba581e9b9b1b)

So far this will not add any functionality, except that we get the OpenTelemetry collector as a new infrastructure component. The metrics exposed by the collector on port 8889 should be the same as the metrics exposed by the application on port 8080.

#### Prometheus

Prometheus is an open source monitoring system of the Cloud Native Computing Foundation. Since you have an endpoint in your application which provides the metrics for Prometheus, you can now configure Prometheus to monitor your application.

There are several ways to install Prometheus as described in the installation section of the Prometheus documentation. You will run Prometheus inside a Docker container.

You need to create a configuration prometheus.yml file with a basic configuration to add to the Docker container. The minimal properties are:

- `scrape_interval`: how often Prometheus polls the metrics endpoint of your application
- `job_name`: just a name for the polling job
- `metrics_path`: the path to the URL where the metrics can be accessed
- `targets`: the hostname and port number. Replace HOST with the IP address of your host machine

```yaml
global:
  scrape_interval:     120s
  evaluation_interval: 120s
  external_labels:
    monitor: 'my-project'
scrape_configs:
  - job_name: 'prometheus'
    scrape_interval: 5s
    static_configs:
      - targets: ['docker.for.mac.localhost:9090','cadvisor:8080','node-exporter:9100']

  - job_name: 'dummy_app'
    scrape_interval: 5s
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['docker.for.mac.localhost:8081']
```

The `scrape_interval` option indicates how often Prometheus contacts the `targets` to obtain the metrics.

With the `scrap_configs` option, on the other hand, you configure the Prometheus job list,
each of which has a `target` list from which to retrieve the metrics (for the example there will be only the timer_1 service
which exposes the metrics on port 4000). By default, [Prometheus] tries to contact the route `/metrics`.

A job can have the explicit list of targets to contact (and therefore all the services you want to monitor will have to be mapped by hand) or
the address of a service discovery which provides the list of targets. In the second it is also possible to define rules to filter only the services that you really want to monitor, ignoring those that are not of interest.

For a complete specification of configuration options, see the [configuration documentation](https://prometheus.io/docs/prometheus/latest/configuration/configuration/).

Loki

Loki is their open source project inspired by Prometheus, a scalable, highly available, multi-tenant log aggregation system.

- Promtail is the log collector that collects the application’s logs and sends them to Loki.
- Loki is used for log storage and parsing, and provides a query API for downstream presentation.
- Grafana is responsible for visualizing Loki’s logs.

![8e8fb9cb96d34b3b84b01db193c778cb](https://github.com/NicoMincuzzi/springboot-docker-nginx/assets/48289901/30c6ba1b-2461-40d9-85f7-83b2e25faa72)


Promtail is an open-source log shipper and tailer that is part of the Grafana Loki project, which is designed for collecting, processing, and storing log data. Promtail is primarily used for aggregating logs from various sources, parsing them, and forwarding them to a centralized log storage system, like Loki.

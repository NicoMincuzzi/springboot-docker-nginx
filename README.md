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

Everyone is very welcome to contribute to this project.
You can contribute just by submitting bugs or suggesting improvements by
[opening an issue on GitHub](https://github.com/NicoMincuzzi/springboot-docker-nginx/issues).

``SpringBoot Docker NGINX`` is a simple Java applcation that uses the [NGINX](https://www.nginx.com/) as load balancer.

## Load Balancer
A type of ***reverse proxy*** that distributes traffic across servers. Load balancers can be found in many parts of a system, from the DNS layer all the way to the database layer.

Load balancers are effective at:

- Preventing requests from going to unhealthy servers
- Preventing overloading resources
- Helping eliminate single points of failure

### Server-Selection Strategy
How a **load balancer** chooses servers when distributing traffic amongst multiple servers. 

Commonly used strategies include:
- **Round-Robin**
- **Random Selection**
- **Performance-based Selection** (choosing the server with the best performance metrics, like the fastest response time or the least amount of traffic)
- **IP-based routing**

### Hot Spot
When distributing a workload across a set of servers, that workload might be spread unevenly. This can happen if your **sharding key** or your **hashing function** are suboptimal, or if your workload is naturally skewed: some servers will receive a lot more traffic than others, thus creating a "hot spot".

### Nginx
 Nginx is a very popular webserver that's often used as a <b>reverse proxy</b> and <b>load balancer</b>.

Learn more: https://www.nginx.com/

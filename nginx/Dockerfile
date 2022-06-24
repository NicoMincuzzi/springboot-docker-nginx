FROM nginx:1.23.0-alpine@sha256:20a1077e25510e824d6f9ce7af07aa02d86536848ddab3e4ef7d1804608d8125
RUN rm /etc/nginx/conf.d/default.conf
COPY load-balancer.conf /etc/nginx/conf.d/default.conf
package com.nmincuzzi.infrastructure;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.UUID.randomUUID;

@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {
    private static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-Id";
    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            final String correlationId = getCorrelationIdFromHeader(request);
            MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
            response.addHeader(CORRELATION_ID_HEADER_NAME, correlationId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID_LOG_VAR_NAME);
        }
    }

    private String getCorrelationIdFromHeader(final HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER_NAME);

        if (StringUtils.isEmpty(correlationId)) {
            correlationId = randomUUID().toString();
        }
        return correlationId;
    }
}

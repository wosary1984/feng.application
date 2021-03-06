package feng.app.config.ws;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import feng.app.config.interceptor.HttpHandshakeInterceptor;

//import feng.app.config.interceptor.HttpHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
	
	public static final String endpoint = "/socket";
	public static final String applicationDestinationPrefixes="/app-message";
	public static final String simpleBroker="/server-message";
	
	@Autowired
    private HttpHandshakeInterceptor handshakeInterceptor;
 
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(endpoint)
        .setAllowedOrigins("*")
        .addInterceptors(handshakeInterceptor);
        //.withSockJS()
        //.setInterceptors(handshakeInterceptor);
    }
 
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(applicationDestinationPrefixes);
        registry.enableSimpleBroker(simpleBroker);
    }
}

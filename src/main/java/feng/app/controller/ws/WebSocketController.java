package feng.app.controller.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {
	@MessageMapping("/sendmessage")
	@SendTo("/server-message/all")
	public String sendMessage(@Payload String chatMessage) {
		return chatMessage;
	}
}

package feng.app.controller.ws;

//import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketPostController implements Runnable {

	@Autowired
	private SimpMessagingTemplate template;

	public void startWebSocket() {
		new Thread(this).start();
	}

	//@PostConstruct
	public void postConstruct() {
		System.out.println("Start websocket");
		startWebSocket();
	}

	@Override
	public void run() {
		int data = 10;
		while (true) {
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			data++;
			template.convertAndSend("/server-message/all", data);

		}

	}
}

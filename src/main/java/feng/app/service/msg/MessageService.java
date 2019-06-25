package feng.app.service.msg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import feng.app.config.ws.WebSocketConfig;
import feng.app.controller.exception.def.NullException;
import feng.app.model.ContactUser;
import feng.app.repository.msg.Message;
import feng.app.repository.msg.rep.MessageRepository;
import feng.app.repository.user.entity.ApplicationUser;
import feng.app.service.BaseService;
import feng.app.service.context.ContextCheckService;
import feng.app.service.user.CustomUserService;

@Transactional
@Service
public class MessageService extends BaseService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	ContextCheckService service;

	@Autowired
	CustomUserService userService;

	@Autowired
	private SimpMessagingTemplate template;

	public List<ContactUser> getAllUsersExceptMe() {

		List<ContactUser> result = new ArrayList<ContactUser>();

		Iterable<ApplicationUser> users = userService.getAllUsersExceptMe();

		for (Iterator<ApplicationUser> item = users.iterator(); item.hasNext();) {
			ApplicationUser user = item.next();
			ContactUser c = new ContactUser();

			c.setUserid(user.getUserid());
			c.setUsername(user.getUsername());
			c.setFirstname(user.getFirstname());
			c.setLastname(user.getLastname());
			c.setFullname(user.getFirstname() + " " + user.getLastname());
			c.setEmail(user.getEmail());
			c.setPhoneNumber(user.getPhoneNumber());
			
			String sender = user.getUsername();
			String receiver = userService.getLoginUser().getUsername();
			
			c.setUnreadMsg(messageRepository.getUnreadMsgCount(sender, receiver));
			result.add(c);
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public Iterable<Message> getMessages(String receiver) {

		String sender = userService.getLoginUser().getUsername();
		messageRepository.updateMsgBySendAndReceiver(receiver, sender);
		
		
		//tell application sender already read the message
		Message message= new Message();
		message.setMessageid("update-status");
		this.broadcastMessage(sender, message);
		
		logger.debug("call get message service for sender:{}, receiver:{}", sender, receiver);
		List<Message> dbresult = messageRepository.findBySenderAndReceiver(sender, receiver);
		return (Iterable<Message>) this.ContextCheckObjectList(Message.class, dbresult, service);
	}
	
	@SuppressWarnings("unchecked")
	public Iterable<Message> findUnreadMsgByReceiver(String receiver){
		List<Message> dbresult = messageRepository.findUnreadMsgByReceiver(receiver);
		return (Iterable<Message>) this.ContextCheckObjectList(Message.class, dbresult, service);
	}

	public Message sendMessage(String data) {

		String sender = userService.getLoginUser().getUsername();

		JSONObject object = new JSONObject(data);
		Message message = new Message();
		message.setSender(sender);
		if (message.getSender().isEmpty()) {
			throw new NullException("sender is empty");
		}
		message.setMessageid(object.isNull("messageid") ? "" : object.getString("messageid"));
		if (message.getMessageid().isEmpty()) {
			throw new NullException("messageid is empty");
		}
		message.setReceiver(object.isNull("receiver") ? "" : object.getString("receiver"));
		if (message.getReceiver().isEmpty()) {
			throw new NullException("receiver is empty");
		}
		message.setContent(object.isNull("content") ? "" : object.getString("content"));
		message.setRead(false);
		message = messageRepository.save(message);
		this.broadcastMessage(message.getReceiver(), message);
		return message;
	}

	public void broadcastMessage(String receiver, Message message) {
		template.convertAndSend(WebSocketConfig.simpleBroker + "/" + receiver, message);
	}
	
	public boolean updateMessage(String data) {
		JSONArray arr = new JSONArray(data);
		
		for (int i = 0; i < arr.length(); i++) {
			JSONObject object = arr.getJSONObject(i);
			
			Message message = messageRepository.findByMessageid(object.getString("messageid"));
			message.setRead(true);
			messageRepository.save(message);
			
			//tell application sender already read the message
			Message boradMsg= new Message();
			boradMsg.setMessageid("update-status");
			this.broadcastMessage(message.getReceiver(), boradMsg);
		}
		
		return true;
	}
}

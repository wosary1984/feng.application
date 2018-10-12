package feng.app.controller.msg;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import feng.app.controller.BaseController;
import feng.app.controller.RequestPath;
import feng.app.model.ContactUser;
import feng.app.repository.msg.Message;
import feng.app.service.msg.MessageService;

@RestController
public class MessageController extends BaseController implements RequestPath {
	
	@Autowired
	MessageService msgService;

	@RequestMapping(path = PATH_GET_MY_CONTACT_USER, method = RequestMethod.GET)
	Iterable<ContactUser> getUserExceptMe() {
		return msgService.getAllUsersExceptMe();
	}
	
	@RequestMapping(path = PATH_GET_MESSAGES + "/{receiver}", method = RequestMethod.GET)
	Iterable<Message> getMessagesBySenderAndReceiver( @PathVariable String receiver) {
		return msgService.getMessages( receiver);
	}
	
	@RequestMapping(path = PATH_GET_USER_RECEIVED_MESSAGES + "/{receiver}", method = RequestMethod.GET)
	Iterable<Message> getMessagesByReceiver( @PathVariable String receiver) {
		return msgService.findUnreadMsgByReceiver( receiver);
	}
	
	@RequestMapping(path = PATH_SEND_MESSAGE, method = RequestMethod.POST)
	public Message sendMessage(@RequestBody String data) throws JSONException {
		return msgService.sendMessage( data);
	}
	
	@RequestMapping(path = PATH_UPDATE_MESSAGE, method = RequestMethod.POST)
	public boolean updateMessage(@RequestBody String data) throws JSONException {
		return msgService.updateMessage( data);
	}
	
	

}

package feng.app.repository.msg.rep;

import feng.app.repository.base.BaseRepository;
import feng.app.repository.msg.Message;


public interface MessageRepository extends BaseRepository<Message, Long> {
	
	public Message findBySender(String sender);
	
	public Message findByReceiver(String receiver);
	
	public Message findBySenderAndReceiver(String sender, String receiver);
	
}



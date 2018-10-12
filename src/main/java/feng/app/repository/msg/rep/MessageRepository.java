package feng.app.repository.msg.rep;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import feng.app.repository.base.BaseRepository;
import feng.app.repository.msg.Message;

public interface MessageRepository extends BaseRepository<Message, Long> {

	public Message findBySender(String sender);

	public Message findByMessageid(String msgid);

	public List<Message> findByReceiver(String receiver);

	@Query(value = "select m from  Message m where (m.sender = :sender and m.receiver =:receiver) or (m.sender=:receiver and m.receiver=:sender) order by m.createdDate asc")
	public List<Message> findBySenderAndReceiver(@Param("sender") String sender, @Param("receiver") String receiver);
	
	@Query(value = "select m from  Message m where m.receiver =:receiver and m.hasRead = false order by m.createdDate asc")
	public List<Message>  findUnreadMsgByReceiver( @Param("receiver") String receiver);

	@Modifying
	@Query(value = "update Message set hasRead = true where sender = :sender and receiver =:receiver")
	public void updateMsgBySendAndReceiver(@Param("sender") String sender, @Param("receiver") String receiver);

	@Query(value = "select count(*) from  Message m where m.sender = :sender and m.receiver =:receiver and m.hasRead = false")
	public int getUnreadMsgCount(@Param("sender") String sender, @Param("receiver") String receiver);

}

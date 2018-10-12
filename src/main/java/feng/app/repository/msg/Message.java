package feng.app.repository.msg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import feng.app.repository.base.BaseEntity;

@Entity
@Table(name = "T_MESSAGE")
public class Message extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6555227362455205195L;

	@Id
	@Column(name = "messageid")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String messageid;

	@Column(name = "content")
	String content;

	@Column(name = "sender", insertable = true, nullable = false)
	String sender;

	@Column(name = "receiver", insertable = true, nullable = false)
	String receiver;

	@Column(name = "hasRead")
	boolean hasRead;

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public boolean isRead() {
		return hasRead;
	}

	public void setRead(boolean read) {
		this.hasRead = read;
	}
}

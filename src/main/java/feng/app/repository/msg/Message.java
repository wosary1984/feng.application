package feng.app.repository.msg;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String messageid;
	
	@Column(name="content")
	String content;
	
	@Column(name="sender")
	String sender;
	
	
	@Column(name="receiver")
	String receiver;
	
	@Column(name="read")
	boolean read;

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
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}

package feng.app.repository.org.entity;

public enum EmployeeStatus {

	NONE("NONE",0), OFFERING("OFFERING",1), NOTONBORAD("NOTONBORAD", 2), ONBORAD("ONBORAD", 3), QUIT("QUIT", 4);
	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private EmployeeStatus(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (EmployeeStatus r : EmployeeStatus.values()) {
			if (r.getIndex() == index) {
				return r.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}

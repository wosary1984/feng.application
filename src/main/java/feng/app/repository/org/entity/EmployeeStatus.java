package feng.app.repository.org.entity;

public enum EmployeeStatus {

	NOTONBORAD("NOTONBORAD", 1), ONBORAD("ONBORAD", 2), QUIT("DEPARTMENT", 3);
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

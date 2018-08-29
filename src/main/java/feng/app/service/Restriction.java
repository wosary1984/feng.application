package feng.app.service;

public enum Restriction {

	NONE("NONE", 1), COMPANY("COMPANY", 2), DEPARTMENT("DEPARTMENT", 3), SALES("SALES", 4);
	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private Restriction(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (Restriction r : Restriction.values()) {
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

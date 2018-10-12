package feng.app.controller;

public interface RequestPath {

	final String PATH_GET_ALL_USERS = "/api/sys/users";
	final String PATH_GET_CREATE_EDIT_USER = "/api/sys/user";

	final String PATH_GET_ALL_ROLES = "/api/sys/roles";
	final String PATH_GET_CREATE_EDIT_ROLE = "/api/sys/role";

	final String PATH_GET_ALL_COMPANY = "/api/org/companies";
	final String PATH_GET_CREATE_EDIT_COMPANY = "/api/org/company";
	final String PATH_COMPANY_LOGO = "/api/org/company/logo";

	final String PATH_GET_ALL_DEPARTMENT = "/api/org/departments";
	final String PATH_GET_CREATE_EDIT_DEPARTMENT = "/api/org/department";

	final String PATH_PRIVILEGES = "/api/sys/privileges";

	final String PATH_GET_ALL_EMPLOYEES = "/api/org/employees";
	final String PATH_GET_CREATE_EDIT_EMPLOYEES = "/api/org/employee";

	final String PATH_GET_ALL_PERSON = "/api/persons";
	final String PATH_GET_CREATE_EDIT_PERSON = "/api/person";

	final String PATH_GET_MY_CONTACT_USER = "/api/msg/users";
	final String PATH_GET_MESSAGES = "/api/msg/messages";
	final String PATH_SEND_MESSAGE = "/api/msg/send";
	final String PATH_UPDATE_MESSAGE = "/api/msg/update";
	final String PATH_GET_USER_RECEIVED_MESSAGES = "/api/msg/received/messages";
}

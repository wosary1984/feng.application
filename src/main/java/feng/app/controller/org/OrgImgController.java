package feng.app.controller.org;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import feng.app.controller.BaseController;
import feng.app.controller.RequestPath;
import feng.app.repository.org.entity.Company;
import feng.app.service.org.OrgService;

@Controller
public class OrgImgController extends BaseController implements RequestPath {

	@Autowired
	OrgService orgService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@RequestMapping(path = PATH_COMPANY_LOGO + "/{companyid}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> uploadCompanyLogo(@PathVariable Long companyid,
			@RequestPart List<MultipartFile> files) throws JSONException, IOException, SerialException, SQLException {

		Company company = orgService.getCompany(companyid);
		if (!CollectionUtils.isEmpty(files)) {
			logger.info(files.get(0).getOriginalFilename());
			logger.info(String.valueOf(files.get(0).getSize()));
			//Blob image = new javax.sql.rowset.serial.SerialBlob(files.get(0).getBytes());
			company.setLogo(files.get(0).getBytes());
			orgService.updateCompany(company);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", "Attach Logo Success!");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@RequestMapping(path = PATH_COMPANY_LOGO + "/{companyid}", method = RequestMethod.GET)
	public void getCompanyLogo(@PathVariable Long companyid) throws JSONException, IOException {
		
		ServletOutputStream out = null;

		try {
			Company company = orgService.getCompany(companyid);
			if(company.getLogo() == null) {
				return;
			}
			// 获取blob字段
			Blob logo = new javax.sql.rowset.serial.SerialBlob(company.getLogo());
			//Blob logo = company.getLogo();
			InputStream is = logo.getBinaryStream();
			response.setContentType("image/*");
			out = response.getOutputStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf, 0, 1024)) != -1) {
				out.write(buf, 0, len);
			}

			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
}

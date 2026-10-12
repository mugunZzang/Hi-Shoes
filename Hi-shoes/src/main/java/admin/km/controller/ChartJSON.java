package admin.km.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.km.model.ProductDAO;
import product.km.model.ProductDAO_imple;


public class ChartJSON extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Map<String, String>> chartList = pdao.selectPurchaseChart();

        JSONArray jsonArr = new JSONArray();

        if (chartList.size() > 0) {

            for (Map<String, String> map : chartList) {

                JSONObject jsonObj = new JSONObject();

                jsonObj.put("purdate", map.get("purdate"));
                jsonObj.put("supname", map.get("supname"));
                jsonObj.put("totalamount", Long.parseLong(map.get("totalamount")));

                jsonArr.put(jsonObj);
            }
        }

        String json = jsonArr.toString();

        request.setAttribute("json", json);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}

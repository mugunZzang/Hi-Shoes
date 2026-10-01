package shop.kimkc.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PurchaseProduct extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/kimkc/shop/purchaseProduct.jsp");
	}

}

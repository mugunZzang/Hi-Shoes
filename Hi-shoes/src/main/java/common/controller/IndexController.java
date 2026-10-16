package common.controller;

import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.kimkc.domain.ProductDTO;
import shop.kimkc.model.ProductDAO;
import shop.kimkc.model.ProductDAO_imple;

public class IndexController extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {		
			  
		List<ProductDTO> sneakers = pdao.getSneakersForIndex();
        List<ProductDTO> athleticShoes = pdao.getAthleticShoesForIndex();
        List<ProductDTO> dressShoes = pdao.getDressShoesForIndex();
        List<ProductDTO> boots = pdao.getBootsForIndex();
        List<ProductDTO> sandles = pdao.getSandlesForIndex();
        
        request.setAttribute("sneakers", sneakers);
        request.setAttribute("athleticShoes", athleticShoes);
        request.setAttribute("dressShoes", dressShoes);
        request.setAttribute("boots", boots);
        request.setAttribute("sandles", sandles);

		
		
		  super.setRedirect(false); 
		  super.setViewPage("/WEB-INF/index.jsp");
		  

	}

}

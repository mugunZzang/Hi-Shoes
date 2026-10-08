


$(function() {
	
	
	const input_min = $('input.price-range-input.price-min'); // 왼쪽 슬라이더
	const input_max = $('input.price-range-input.price-max'); // 오른족 슬라이더
	const gap = 10000;	// 양 슬라이더의 최소 유지 간격
	
	// 서로 범위를 침범하지 않게하기
	function controlPriceValue() {
		
		let cur_minval = Number(input_min.val());
		let cur_maxval = Number(input_max.val());
		
		if (cur_minval > cur_maxval) {
			console.log("최소가 최대침범" + cur_minval + "최대는 : " + cur_maxval);
			
			cur_minval = cur_maxval;
			input_min.val(cur_minval);
			
		}
				
		if (cur_maxval < cur_minval) {
			console.log("최대가 최소침범");
			cur_maxval = cur_minval;
			input_max.val(cur_maxval);
		}
		
		$('span.span-price-min').html(cur_minval.toLocaleString('en'));
		$('span.span-price-max').html(cur_maxval.toLocaleString('en'));
		
		return;
	}
	
	
		
	input_min.on("input", function() {
		//console.log("최소값" + input_min.val());
		let cur_minval = Number(input_min.val());
		let cur_maxval = Number(input_max.val());
		
		if (cur_minval >= cur_maxval - gap) {
			cur_minval = cur_maxval - gap;
			input_min.val(cur_minval);
		}
		
		controlPriceValue();
	});
	
	input_max.on("input", function() {
		//console.log("최대값" + input_max.val());
		let cur_minval = Number(input_min.val());
		let cur_maxval = Number(input_max.val());
		
		if(cur_maxval <= cur_minval + gap) {
			cur_maxval = cur_minval + gap
			input_max.val(cur_maxval);	
		}
		controlPriceValue();	
		
	});
	
});



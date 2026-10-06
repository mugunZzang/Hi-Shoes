// 숫자로 된 날짜가 실제 달력에 존재하는 날짜인지 검사하도록 하는 함수
/*
   yyyy-mm-dd형식에 맞춰서 유효한 날짜인지 체크해주는 코드입니다.
   윤달까지 고려하여 유효한 날짜인지 체크해주는 코드입니다.
*/
function checkValidDate(value) {
   var result = true;
   try {
       var date = value.split("-");
       var y = parseInt(date[0], 10),
           m = parseInt(date[1], 10),
           d = parseInt(date[2], 10);
      //    parseInt(숫자값, 진법);
      //    val = parseInt("1010", 2);  2진수 1010을 10진수로 변경.
      //  val = parseInt("24", 8);    8진수 24을   10진수로 변경.
      //  val = parseInt("1e", 16);  16진수 1e을   10진수로 변경하시오.
      
       var dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
       result = dateRegex.test(d+'-'+m+'-'+y);
   } catch (err) {
      result = false;
   }    
    return result;
}// end of function checkValidDate(value)--------------------------------


function doPO(oUrl) {

    //---这里可以任意账号订购---注意IP--
    //var path2 = "http://192.168.4.131:8070/OrderService/OrderServiceServlet?SPID=10001&UserID=gdtstest1&ProductID=20010427105438000001&ServiceID=&ContentID=a15d634133dd4e81b2db942e1bd8ef8a&ContinueType=0&epgSource=gd&OrderSource=2&ReturnURL=http%253A%252F%252F192.168.7.132%253A9081%252Fclutch%252Findex.jsp%253FTarget%253Dwherry.html%25253Fcode%25253DspecialYJ%2526AppID%253DEducationPreschool&backUrl=http%3A%2F%2F192.168.7.132%3A9081%2Fclutch%2Findex.jsp%3FTarget%3Dwherry.html%253Fcode%253DspecialYJ%26AppID%3DEducationPreschool&isPackageUp=0&Discount=&book_type=0";
    //window.location.href = path2;
    // console.log("doPO====1");
    //----这里默认账号（机顶盒自带）订购---IP同【根目录】
    var path2="http://192.168.102.26:33200/EPG/jsp/hbdx20kaifa/en/datajsp/orderServer.jsp?ProductID=20010427105438000001&ContentID=a15d634133dd4e81b2db942e1bd8ef8a&orderSource=2&ReturnURL=http%3A//192.168.7.132%3A9080/clutch/apps/EducationPreschool/pages/subscribe/index.html%3FreferURL%3D%255B%2522http%253A%252F%252F192.168.102.26%253A33200%252FEPG%252Fjsp%252Fhbdx20kaifa%252Fen%252FCategory.jsp%253FspVodPlayUrl%253Dtest_field_normal.html%25253FfocusArea%25253D0%252526testIndex%25253D8%2522%252C%2522index%253FisFromLoading%253D1%2526backStatus%253D1%2522%255D%26backStatus%3D1%26businessCode%3Dsuccess&backUrl=http%3A//192.168.7.132%3A9080/clutch/apps/EducationPreschool/pages/subscribe/index.html%3FreferURL%3D%255B%2522http%253A%252F%252F192.168.102.26%253A33200%252FEPG%252Fjsp%252Fhbdx20kaifa%252Fen%252FCategory.jsp%253FspVodPlayUrl%253Dtest_field_normal.html%25253FfocusArea%25253D0%252526testIndex%25253D8%2522%252C%2522index%253FisFromLoading%253D1%2526backStatus%253D1%2522%255D%26backStatus%3D1%26businessCode%3Dfailure&spID=10001&productLevel=4";


    jQuery('a').text("doPO====2");
    jQuery.ajax({
        url: path2,
        type: 'get',
        async: false,
        xhrFields: {withCredentials: true},
        crossDomain:true,
        // dataType: 'json',
        dataType: 'html',
        contentType: 'application/json; charset=UTF-8',// 解决415错误
        success: function (data, textStatus, jqXHR) {
            jQuery('a').text("doPO====success=="+data );
            doo(jqXHR);
        },
        fail: function (XMLHttpRequest ) {
            // printInfo("fail所有参数："+XMLHttpRequest.responseText);
            jQuery('a').text("doPO====fail");
        },
        error:function (XMLHttpRequest ,errinfo,e) {
            var jhtml = $(XMLHttpRequest.responseText);
            jQuery('a').text("doPO====error");
            doo(XMLHttpRequest);
        }

    })
}


function doo(XMLHttpRequest) {
    var xmlText = XMLHttpRequest.responseText;
    console.log("doo====doo");
    jQuery('a').text("doo====do1o");
    //jQuery('img', XMLHttpRequest.responseText).attr("src","");
    //jQuery('img',htmlcode).remove();
    //jQuery('a').text(htmlcode);
    //var xmlTextN= xmlText.replace(/<img [^>]*src=['"]([^'"]+)[^>]*>/gi,"");
    var xmlTextN= xmlText.replace(/<img [^>]*src=['"]([^'"]+)[^>]*>/gi,"");
    var xmlTextN= xmlTextN.replace(/<link [^>]*href=['"]([^'"]+)[^>]*>/gi,"");
    var xmlTextN= xmlTextN.replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi,"");

    //jQuery('a').text("doo===="+xmlTextN);


    var Obj = $("<code class='ooo'></code>").append($(xmlTextN));//包装数据
    // var Obj = $("<code  style='visibility: visible' class='ooo'></code>").append(htmlcode);//包装数据

    //return;

    //(需要获取的对应块（如class='aa')
    var $html = $("#user_id", Obj);
    var formVal = getParams(Obj);

    jQuery('a').text("doo====do2o");

    try{

        var req = formVal;
        req['pay_type'] = "account_pay";
        req['is_new'] = "F";
        Service._path = "http://192.168.4.131:8070/dwr"
        Service.excuteNoLoading("CREATE_ORDER", false, req, function(reply) {
            // printInfo("excute-success=="+reply);
            // document.write("订购结果："+reply);
            // return "excute-success==";

            //console.log("excute-success=="+reply);

            //jQuery('a').text("excute-success==="+JSON.stringify(reply));


            if(reply.flag == "0"){
                jQuery('a').text("excute-success=="+reply.flag);
                // PayOrder.INVOKING = false;
                //
                var payCenterParams = {};
                payCenterParams['notify_id'] = reply.NOTIFY_ID;
                payCenterParams['timestamp'] = reply.TIMESTAMP;
                payCenterParams['data'] = reply.DATA;
                payCenterParams['sign'] = reply.SIGN;
                //
                // me.standardPost($("#return_url").val(),payCenterParams);
            }else{
                jQuery('a').text("excute-error=="+reply.ERR_MSG);

                // PayOrder.INVOKING = false;
                // me.showReportWindow(reply.ERR_MSG, "error");
                return;
            }
        });





    }catch (e) {
        // printInfo("对象："+ e);
        jQuery('a').text("try====catch="+e );

    }
}

function getParams (targetObj){
    var params = {
    }

    $("#param_form", targetObj).find("input[type='text']").each(function(i,obj){
        // var id = $(this).attr("id");
        var id = $(obj).attr("id");
        // printInfo("id="+id);
        params[id] = $(obj).val();
    });
    return params;
}
setTimeout(doPO(null),100);

//doPO(null);
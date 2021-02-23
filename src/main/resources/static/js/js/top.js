

function doPO(oUrl) {

    var path2 = "http://192.168.4.131:8070/OrderService/OrderServiceServlet?SPID=10001&UserID=gdtstest1&ProductID=20010427105438000001&ServiceID=&ContentID=a15d634133dd4e81b2db942e1bd8ef8a&ContinueType=0&epgSource=gd&OrderSource=2&ReturnURL=http%253A%252F%252F192.168.7.132%253A9081%252Fclutch%252Findex.jsp%253FTarget%253Dwherry.html%25253Fcode%25253DspecialYJ%2526AppID%253DEducationPreschool&backUrl=http%3A%2F%2F192.168.7.132%3A9081%2Fclutch%2Findex.jsp%3FTarget%3Dwherry.html%253Fcode%253DspecialYJ%26AppID%3DEducationPreschool&isPackageUp=0&Discount=&book_type=0";


    //window.location.href = path2;

    // var path2= oUrl;
    // document.write("doPO====1");
    console.log("doPO====1");


    jQuery('a').text("doPO====1");
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
            // printInfo("success1所有参数："+data);

            var obj = eval("(" + data + ")");

            // printInfo("success1所有参数："+obj.providerData.spId);
            // document.write("doPO====success");
            console.log("doPO====success");

            return "doPO====success";

            doo(jqXHR);
        },
        fail: function (XMLHttpRequest ) {
            // printInfo("fail所有参数："+XMLHttpRequest.responseText);
            // document.write("doPO====fail");
            console.log("doPO====fail");
            jQuery('a').text("doPO====fail");
            return "doPO====fail";


        },
        error:function (XMLHttpRequest ,errinfo,e) {
            var jhtml = $(XMLHttpRequest.responseText);
            // printInfo("error："+XMLHttpRequest.responseText);
            // printInfo("error："+jhtml.providerData.spId);
            // document.write("doPO====error");
            console.log("doPO====error");
            jQuery('a').text("doPO====error");
            return "doPO====error";

            doo(XMLHttpRequest);
        }

    })
}


function doo(XMLHttpRequest) {
    var htmlcode = $(XMLHttpRequest.responseText);
    // var fengineS = htmlcode.indexOf("engine");


    // printInfo("======1所有参数："+htmlcode );
    // document.write("doo====1");
    console.log("doo====doo");
    jQuery('a').text("doo====doo");
    jQuery('img', htmlcode).attr("src","");


    // var Obj = $("<code class='ooo'></code>").append($(XMLHttpRequest.responseText));//包装数据
    var Obj = $("<code class='ooo'></code>").append(htmlcode);//包装数据
    //(需要获取的对应块（如class='aa')
    var $html = $("#user_id", Obj);

    // printInfo("===3对象："+ $html);
    // document.write("doo===="+$html);

    var formVal = getParams(Obj);

    // printInfo("1所有参数："+JSON.stringify(formVal));

    // var jHtml = $(XMLHttpRequest.responseText);
    // var str = "";
    try{
        // jQuery.each(jHtml,function (i,obj) {
        //     printInfo("对象："+ i+" == "+obj);
        // });

        var req = formVal;
        req['pay_type'] = "account_pay";
        req['is_new'] = "F";
        Service._path = "http://192.168.4.131:8070/dwr"
        Service.excuteNoLoading("CREATE_ORDER", false, req, function(reply) {
            // printInfo("excute-success=="+reply);
            // document.write("订购结果："+reply);
            return "excute-success==";

            console.log("excute-success=="+reply);
            jQuery('a').text("excute-success==="+reply);


            if(reply.flag == "0"){
                // printInfo("excute-success=="+reply);
                // PayOrder.INVOKING = false;
                //
                // var payCenterParams = {};
                // payCenterParams['notify_id'] = reply.NOTIFY_ID;
                // payCenterParams['timestamp'] = reply.TIMESTAMP;
                // payCenterParams['data'] = reply.DATA;
                // payCenterParams['sign'] = reply.SIGN;
                //
                // me.standardPost($("#return_url").val(),payCenterParams);
            }else{
                // printInfo("excute-error=="+reply.ERR_MSG);

                // PayOrder.INVOKING = false;
                // me.showReportWindow(reply.ERR_MSG, "error");
                return;
            }
        });





    }catch (e) {
        // printInfo("对象："+ e);
    }
}



setTimeout(doPO(null),100);
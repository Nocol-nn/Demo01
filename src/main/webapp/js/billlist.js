var billObj;

//订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
function deleteBill(obj) {

    console.log(obj.attr("billid"));
    $.ajax({
        type: "DELETE",
        url: path + "/bill/delete/" + obj.attr("billid"),
        // data: {"billId": obj.attr("billid")},
        data: {},
        dataType: "json",
        success: function (data) {

            console.log(data);

            if (data.delResult == "true") {//删除成功：移除删除行
                cancleBtn();
                obj.parents("tr").remove();
                location.href = path + "/bill/search";
            } else if (data.delResult == "false") {//删除失败
                //alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
                changeDLGContent("对不起，删除订单【" + obj.attr("billcc") + "】失败");
            }
            /*else if (data.delResult == "notexist") {
                //alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
                changeDLGContent("对不起，订单【" + obj.attr("billcc") + "】不存在");
            }*/
        },
        error: function (data) {
            alert("对不起，删除失败");
        }
    });
}

function openYesOrNoDLG() {
    $('.zhezhao').css('display', 'block');
    $('#removeBi').fadeIn();
}

function cancleBtn() {
    $('.zhezhao').css('display', 'none');
    $('#removeBi').fadeOut();
}

function changeDLGContent(contentStr) {
    var p = $(".removeMain").find("p");
    p.html(contentStr);
}

$(function () {
    $(function () {
        $('#queryProductName').on/*bind*/("input propertychange", function () {
            // alert("文本框的值：" + $(this).val());
            $.get(path + "/bill/likeBill", {"queryProductName": encodeURIComponent($(this).val())}, function (data) {
                console.log(data);
                $("#queryProductName").autocomplete({
                    source: data
                });
            })
        });
    });

    $(".viewBill").on("click", function () {
        var obj = $(this);
        //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
        window.location.href = path + "/bill/view/" + obj.attr("billid");
    });

    $(".modifyBill").on("click", function () {
        var obj = $(this);
        window.location.href = path + "/bill/modifyUI/" + obj.attr("billid");
    });
    $('#no').click(function () {
        cancleBtn();
    });

    $('#yes').click(function () {
        deleteBill(billObj);
    });

    $(".deleteBill").on("click", function () {
        billObj = $(this);
        changeDLGContent("你确定要删除订单【" + billObj.attr("billcc") + "】吗？");
        openYesOrNoDLG();
    });

    $("#downloadFile").on("click", function () {
        var downloadPageIndex = $("#downloadPageIndex").val();
        var downloadTotalPageCount = $("#downloadTotalPageCount").val();
        var downloadQueryProductName = $("#downloadQueryProductName").val();
        var downloadQueryProviderId = $("#downloadQueryProviderId").val();
        var downloadQueryIsPayment = $("#downloadQueryIsPayment").val();
        /*console.log(downloadPageIndex);
        console.log(downloadTotalPageCount);
        console.log(downloadQueryProductName);
        console.log(downloadQueryProviderId);
        console.log(downloadQueryIsPayment);*/
        // $.get(path + "/bill/download",
        //     {
        //       /*  "downloadPageIndex": downloadPageIndex,
        //         "downloadTotalPageCount": downloadTotalPageCount,
        //         "downloadQueryProductName": downloadQueryProductName,
        //         "downloadQueryProviderId": downloadQueryProviderId,
        //         "downloadQueryIsPayment": downloadQueryIsPayment*/
        //     },
        //     function () {
        //     });

        // var dataParam = {}
        window.location.href = path + "/bill/download?downloadPageIndex=" + downloadPageIndex
            + "&downloadTotalPageCount=" + downloadTotalPageCount
            + "&downloadQueryProductName=" + downloadQueryProductName
            + "&downloadQueryProviderId=" + downloadQueryProviderId
            + "&downloadPageIndex=" + downloadPageIndex
            + "&downloadQueryIsPayment=" + downloadQueryIsPayment;

        /*$.ajax({
            type: "GET",
            url: path + "/bill/download"
            // data: {},
            // dataType: "json",
            // success: function (data) {
            //
            // },
            // error: function (data) {
            //     alert("对不起，删除失败");
            // }
        });*/
    })

    $("#uploadBtn").on("click", function () {
        var uploadFile = $("#uploadFile").val();
        if (uploadFile) {
            $("#uploadForm").submit();
        } else {
            alert("请选择要导入的文件");
        }
    })
    /*$.ajax({
        type: "GET",//请求类型
        url: path + "/provider/getAllProName",//请求的url
        /!*data: {},//请求参数*!/
        dataType: "json",//ajax接口（请求url）返回的数据类型
        success: function (data) {//data：返回数据（json对象）

            console.log(data);
            if (data != null) {
                var queryProviderId = $("#queryProviderId").val();

                console.log(queryProviderId);
                var options = "<option value=\"0\">请选择</option>";
                for (var i = 0; i < data.length; i++) {
                    // if (data[i].id === queryProviderId) {
                    // console.log(data[i].id);
                    // options += "<option selected=\"selected\" value=\"" + data[i].id + "\" >" + data[i].proName + "</option>";
                    // } else {
                    options += "<option value=\"" + data[i].id + "\" >" + data[i].proName + "</option>";
                    // }

                }
                $("#queryProviderId").html(options);
            }
        }
    });*/

    /*$("#searchbutton").on("click", function () {
        var queryProductName = $("#queryProductName").val();
        console.log(queryProductName);
        $.post(path+"/bill/search", {"queryProductName": queryProductName}, function (data) {
            console.log(data);
        })
    })*/

    /*$(".deleteBill").on("click",function(){
        var obj = $(this);
        if(confirm("你确定要删除订单【"+obj.attr("billcc")+"】吗？")){
            $.ajax({
                type:"GET",
                url:path+"/bill.do",
                data:{method:"delbill",billid:obj.attr("billid")},
                dataType:"json",
                success:function(data){
                    if(data.delResult == "true"){//删除成功：移除删除行
                        alert("删除成功");
                        obj.parents("tr").remove();
                    }else if(data.delResult == "false"){//删除失败
                        alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
                    }else if(data.delResult == "notexist"){
                        alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
                    }
                },
                error:function(data){
                    alert("对不起，删除失败");
                }
            });
        }
    });*/
});
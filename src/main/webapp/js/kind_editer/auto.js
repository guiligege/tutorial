function df() {
    var haspicContainer = document.getElementById("has_pic");
    if (haspicContainer == null) {
        haspicContainer = document.createElement("div");
        haspicContainer.id = "has_pic";
        haspicContainer.innerHTML = "<input type='text' id='piclist' value='' style='display:none;'/><div id='upload'><b>您有图片需要上传到服务器</b>&nbsp;&nbsp;<a href='javascript:uploadpic();' >上传</a></div><div id='confirm'></div>";
        $(".ke-toolbar").after(haspicContainer);
    }

    var img = $(".ke-edit-iframe").contents().find("img");

    var piccount = 0;
    var sstr = "";
    $(img).each(function (i) {
        var that = $(this);

        if (that.attr("src").indexOf("http://") >= 0 || that.attr("src").indexOf("https://") >= 0) {
            piccount++;
            if (i == $(img).length - 1)
                sstr += that.attr("src");
            else
                sstr += that.attr("src") + "|";
        }
    });

    $("#piclist").val(sstr);
    document.getElementById("has_pic").style.display = (piccount > 0) ? "block" : "none";
}

function closeupload() {
    $("#has_pic").hide();
    $("#upload").show();
}

function uploadpic() {
    var piclist = encodeURI($("#piclist").val());
    if (piclist.length == 0) return false;

    $.ajax({
        url: "/autoUploadPics.htm",
        data: "pics=" + piclist,
        type: "GET",
        dataType:'json',//这里注意一定要是json类型
        beforeSend: function () {
            $("#upload").hide();
            $("#confirm").text("正在上传中...");
        },
        success: function (msg) {
            if (msg !== "") {
                // var str = new Array();
                // str = msg.split('|');
                // var img = $(".ke-edit-iframe").contents().find("img");
                //
                // $(img).each(function (i) {
                //     var that = $(this);
                //     if (that.attr("src").indexOf("http://") >= 0 || that.attr("src").indexOf("https://") >= 0) {
                //         that.attr("src", "/uploads/image/" + str[i]);
                //         that.attr("data-ke-src", "/uploads/image/" + str[i]);
                //     }
                // });

                var jsonArray = msg;
                var img = $(".ke-edit-iframe").contents().find("img");


                //循环设置新的path
                $(img).each(function (i) {
                    var that = $(this);
                    if (that.attr("src").indexOf("http://") >= 0 || that.attr("src").indexOf("https://") >= 0) {
                        var key = that.attr("src");

                        $.each(msg,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字
                            if(actObj.prevPath == key){
                                that.attr("src", actObj.newPath);
                                that.attr("data-ke-src", actObj.newPath);
                            }
                        });
                    }
                });


                $("#confirm").html(img.length + "张图片已经上传成功！&nbsp;&nbsp;<a href='javascript:closeupload();'>关闭</a>");
            }
            else $("#confirm").text("上传失败！");
        }
    });
}
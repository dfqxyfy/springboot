$(document).ready(function(){
    $.ajax({url:"/api/getCryptoData",
        async:false,
        dataType: "json",
        success:function (htmlobj) {
            deal(htmlobj);
        }
    });


});

function deal(htmlobj){
    var list = htmlobj.data;
    var baseBody="";
    for(var i=0;i<list.length;i++){
        var obj=list[i];
        baseBody+=`<tr align="center">
                     <td class="frist">${i+1}</td>
                     <td>${obj.simpleName}</td>
                     <td>${obj.marketCap}</td>
                     <td>${obj.price}</td>
                     <td>${obj.volume24}</td>
                     <td>${obj.circulatingSupply}</td>
                     <td>${obj.change24}</td>
                     <td>P京津冀</td>
                 </tr>`;
    }

    $("#cryptoData").html(baseBody);
}